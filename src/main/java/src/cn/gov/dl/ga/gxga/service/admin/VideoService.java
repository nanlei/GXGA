package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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

public class VideoService extends BaseService {
	private static final String SQL_SEARCH_VIDEO_PREFIX = "select v.videoId, v.videoName, v.videoUrl, v.videoOrder, v.createByName, date_format(v.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, v.createByIP, c.categoryName from doc_video v, sys_category c";
	private static final String SQL_SEARCH_VIDEO_SUFFIX = "order by ";

	public PagingList searchVideo(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String videoName = params.get("videoName");
		String videoDescription = params.get("videoDescription");
		String createByName = params.get("createByName");
		String createByIP = params.get("createByIP");
		String categoryId = params.get("categoryId");
		String createByTimeStart = params.get("createByTimeStart");
		String createByTimeEnd = params.get("createByTimeEnd");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "videoOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_VIDEO_PREFIX,
				SQL_SEARCH_VIDEO_SUFFIX + sortField + " " + sortOrder);
		helper.setParam(true, "v.categoryId=c.categoryId");
		helper.setParam(StringUtils.isNotEmpty(videoName),
				"v.videoName like concat('%',?,'%')", videoName);
		helper.setParam(StringUtils.isNotEmpty(videoDescription),
				"v.videoDescription like concat('%',?,'%')", videoDescription);
		helper.setParam(StringUtils.isNotEmpty(createByName),
				"v.createByName like concat('%',?,'%')", createByName);
		helper.setParam(StringUtils.isNotEmpty(createByIP),
				"v.createByIP like concat('%',?,'%')", createByIP);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "v.categoryId=?",
				categoryId);
		helper.setParam(StringUtils.isNotEmpty(createByTimeStart),
				"unix_timestamp(v.videoByTime) > unix_timestamp(?)",
				createByTimeStart);
		helper.setParam(StringUtils.isNotEmpty(createByTimeEnd),
				"unix_timestamp(v.videoByTime) < unix_timestamp(?)",
				createByTimeEnd);

		return helper;
	}

	public int createVideo(HttpServletRequest request, String videoUrl) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				videoUrl);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_video").usingGeneratedKeyColumns("videoId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String videoUrl) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String videoName = (String) request.getAttribute("videoName");
		String videoDescription = (String) request
				.getAttribute("videoDescription");
		String videoOrder = (String) request.getAttribute("videoOrder");
		String categoryId = (String) request.getAttribute("categoryId");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("videoName", videoName);
		parameters.put("videoDescription", videoDescription);
		parameters.put("videoUrl", videoUrl);
		parameters.put("videoOrder", Integer.parseInt(videoOrder));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("categoryId", Integer.parseInt(categoryId));

		return parameters;
	}

	private static final String SQL_GET_VIDEO_BY_ID = "select * from doc_video where videoId=?";

	public HashMap<String, Object> getVideoById(String videoId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_VIDEO_BY_ID,
				videoId);
	}

	private static final String SQL_UPDATE_VIDEO_BY_ID = "update doc_video set videoName=?, videoDescription=?, videoUrl=?, videoOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where videoId=?";
	private static final String SQL_UPDATE_VIDEO_NO_URL_BY_ID = "update doc_video set videoName=?, videoDescription=?, videoOrder=? where videoId=?";

	public void updateVideoById(HttpServletRequest request, String videoUrl) {
		if (StringUtils.isEmpty(videoUrl)) {
			Object[] parameters = buildUpdateCondition(request);
			jt.update(SQL_UPDATE_VIDEO_NO_URL_BY_ID, parameters);
		} else {
			Object[] parameters = buildUpdateCondition(request, videoUrl);
			jt.update(SQL_UPDATE_VIDEO_BY_ID, parameters);
		}

	}

	private Object[] buildUpdateCondition(HttpServletRequest request) {
		String videoId = (String) request.getAttribute("videoId");
		String videoName = (String) request.getAttribute("videoName");
		String videoDescription = (String) request
				.getAttribute("videoDescription");
		String videoOrder = (String) request.getAttribute("videoOrder");

		return new Object[] { videoName, videoDescription, videoOrder, videoId };
	}

	@SuppressWarnings("unchecked")
	private Object[] buildUpdateCondition(HttpServletRequest request,
			String videoUrl) {
		String videoId = (String) request.getAttribute("videoId");
		String videoName = (String) request.getAttribute("videoName");
		String videoDescription = (String) request
				.getAttribute("videoDescription");
		String videoOrder = (String) request.getAttribute("videoOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		return new Object[] { videoName, videoDescription, videoUrl,
				videoOrder, (Integer) loginUser.get("userId"),
				loginUser.get("realName"), CoreUtil.getIPAddr(request), videoId };
	}

	private static final String SQL_DELETE_VIDEO = "delete from doc_video where videoId=?";
	private static final String SQL_UPDATE_ARTICLE_VIDEO_ID = "update doc_article set videoId=0 where videoId=?";

	public void deleteVideo(final String[] videoIds) {
		jt.batchUpdate(SQL_DELETE_VIDEO, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(videoIds[i]));
			}

			@Override
			public int getBatchSize() {
				return videoIds.length;
			}
		});
		jt.batchUpdate(SQL_UPDATE_ARTICLE_VIDEO_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(videoIds[i]));
					}

					@Override
					public int getBatchSize() {
						return videoIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_GET_VIDEO_BY_IDS = "select videoUrl from doc_video where ";

	public List<Map<String, Object>> getVideoByIds(String[] videoIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_VIDEO_BY_IDS
				+ sqlHelper.buildWhereIn("videoId", videoIds));
	}

	private static final String SQL_GET_VIDEO_AJAX = "select videoId as id, videoName as text from doc_video where categoryId=? order by videoOrder asc";

	public List<Map<String, Object>> getVideoForAjax(String categoryId) {
		return jt.queryForList(SQL_GET_VIDEO_AJAX, categoryId);
	}

}
