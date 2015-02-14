package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class ImageService extends BaseService {
	private static final String SQL_SEARCH_IMAGE_PREFIX = "select i.imageId, i.imageName, i.imageUrl, i.imageOrder, i.createByName, date_format(i.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, i.createByIP, c.categoryName from doc_image i, sys_category c";
	private static final String SQL_SEARCH_IMAGE_SUFFIX = "order by ";

	public PagingList searchImage(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String imageName = params.get("imageName");
		String imageDescription = params.get("imageDescription");
		String createByName = params.get("createByName");
		String createByIP = params.get("createByIP");
		String categoryId = params.get("categoryId");
		String createByTimeStart = params.get("createByTimeStart");
		String createByTimeEnd = params.get("createByTimeEnd");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "imageOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_IMAGE_PREFIX,
				SQL_SEARCH_IMAGE_SUFFIX + sortField + " " + sortOrder);
		helper.setParam(true, "i.categoryId=c.categoryId");
		helper.setParam(StringUtils.isNotEmpty(imageName),
				"i.imageName like concat('%',?,'%')", imageName);
		helper.setParam(StringUtils.isNotEmpty(imageDescription),
				"i.imageDescription like concat('%',?,'%')", imageDescription);
		helper.setParam(StringUtils.isNotEmpty(createByName),
				"i.createByName like concat('%',?,'%')", createByName);
		helper.setParam(StringUtils.isNotEmpty(createByIP),
				"i.createByIP like concat('%',?,'%')", createByIP);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "i.categoryId=?",
				categoryId);
		helper.setParam(StringUtils.isNotEmpty(createByTimeStart),
				"unix_timestamp(i.imageByTime) > unix_timestamp(?)",
				createByTimeStart);
		helper.setParam(StringUtils.isNotEmpty(createByTimeEnd),
				"unix_timestamp(i.imageByTime) < unix_timestamp(?)",
				createByTimeEnd);

		return helper;
	}

	public int createImage(HttpServletRequest request, String imageUrl) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				imageUrl);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_image").usingGeneratedKeyColumns("imageId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String imageUrl) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String imageName = (String) request.getAttribute("imageName");
		String imageDescription = (String) request
				.getAttribute("imageDescription");
		String imageOrder = (String) request.getAttribute("imageOrder");
		String categoryId = (String) request.getAttribute("categoryId");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("imageName", imageName);
		parameters.put("imageDescription", imageDescription);
		parameters.put("imageUrl", imageUrl);
		parameters.put("imageOrder", Integer.parseInt(imageOrder));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("categoryId", Integer.parseInt(categoryId));

		return parameters;
	}

	private static final String SQL_GET_IMAGE_BY_ID = "select * from doc_image where imageId=?";

	public HashMap<String, Object> getImageById(String imageId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_IMAGE_BY_ID,
				imageId);
	}

	private static final String SQL_UPDATE_IMAGE_BY_ID = "update doc_image set imageName=?, imageDescription=?, imageUrl=?, imageOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where imageId=?";
	private static final String SQL_UPDATE_IMAGE_NO_URL_BY_ID = "update doc_image set imageName=?, imageDescription=?, imageOrder=? where imageId=?";

	public void updateImageById(HttpServletRequest request, String imageUrl) {
		if (StringUtils.isEmpty(imageUrl)) {
			Object[] parameters = buildUpdateCondition(request);
			jt.update(SQL_UPDATE_IMAGE_NO_URL_BY_ID, parameters);
		} else {
			Object[] parameters = buildUpdateCondition(request, imageUrl);
			jt.update(SQL_UPDATE_IMAGE_BY_ID, parameters);
		}

	}

	private Object[] buildUpdateCondition(HttpServletRequest request) {
		String imageId = (String) request.getAttribute("imageId");
		String imageName = (String) request.getAttribute("imageName");
		String imageDescription = (String) request
				.getAttribute("imageDescription");
		String imageOrder = (String) request.getAttribute("imageOrder");

		return new Object[] { imageName, imageDescription, imageOrder, imageId };
	}

	@SuppressWarnings("unchecked")
	private Object[] buildUpdateCondition(HttpServletRequest request,
			String imageUrl) {
		String imageId = (String) request.getAttribute("imageId");
		String imageName = (String) request.getAttribute("imageName");
		String imageDescription = (String) request
				.getAttribute("imageDescription");
		String imageOrder = (String) request.getAttribute("imageOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		return new Object[] { imageName, imageDescription, imageUrl,
				imageOrder, (Integer) loginUser.get("userId"),
				loginUser.get("realName"), CoreUtil.getIPAddr(request), imageId };
	}

	private static final String SQL_DELETE_IMAGE = "delete from doc_image where imageId=?";

	public void deleteImage(final String[] imageIds) {
		jt.batchUpdate(SQL_DELETE_IMAGE, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(imageIds[i]));
			}

			@Override
			public int getBatchSize() {
				return imageIds.length;
			}
		});
	}

	// Extend
	private static final String SQL_GET_IMAGE_UNSELECTED_FOR_ARTICLE_PREFIX = "select i.imageId, i.imageName, i.imageDescription from doc_image i ";
	private static final String SQL_GET_IMAGE_UNSELECTED_FOR_ARTICLE_SUFFIX = "order by i.imageOrder asc";

	public PagingList searchUnselectedImage(HttpServletRequest request,
			HashSet<Object> imageIds) {
		QueryHelper helper = buildQueryConditionForArticleUnselectedImage(
				request, imageIds);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionForArticleUnselectedImage(
			HttpServletRequest request, HashSet<Object> imageIds) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String imageName = params.get("imageName");
		String imageDescription = params.get("imageDescription");
		String categoryId = params.get("categoryId");

		QueryHelper helper = new QueryHelper(
				SQL_GET_IMAGE_UNSELECTED_FOR_ARTICLE_PREFIX,
				SQL_GET_IMAGE_UNSELECTED_FOR_ARTICLE_SUFFIX);

		helper.setParam(StringUtils.isNotEmpty(imageName),
				"i.imageName like concat('%',?,'%')", imageName);
		helper.setParam(StringUtils.isNotEmpty(imageDescription),
				"i.imageDescription like concat('%',?,'%')", imageDescription);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "i.categoryId=?",
				categoryId);

		if (imageIds.size() > 0) {
			SqlHelper sqlHelper = new SqlHelper();
			String notIn = sqlHelper.buildWhereNotIn("i.imageId", imageIds);
			helper.setParam(true, notIn);
		}

		return helper;
	}

	private static final String SQL_GET_SELECTED_IMAGE_BY_ARTICLE_ID = "select i.imageId, i.imageName, i.imageDescription from doc_image i, doc_article_image ai where ai.imageId=i.imageId and ai.articleId=? order by i.imageOrder asc";

	public PagingList getSelectedImageByArticleId(HttpServletRequest request,
			int articleId) {
		return getPagingList(SQL_GET_SELECTED_IMAGE_BY_ARTICLE_ID, request,
				new Object[] { articleId });
	}

	private static final String SQL_GET_IMAGE_BY_IDS = "select imageUrl from doc_image where ";

	public List<Map<String, Object>> getImageByIds(String[] imageIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_IMAGE_BY_IDS
				+ sqlHelper.buildWhereIn("imageId", imageIds));
	}
}
