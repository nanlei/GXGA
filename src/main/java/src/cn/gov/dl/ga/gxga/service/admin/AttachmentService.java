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

public class AttachmentService extends BaseService {
	private static final String SQL_SEARCH_ATTACHMENT_PREFIX = "select a.attachmentId, a.attachmentName, a.attachmentUrl, a.attachmentOrder, a.createByName, date_format(a.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, a.createByIP, c.categoryName from doc_attachment a, sys_category c";
	private static final String SQL_SEARCH_ATTACHMENT_SUFFIX = "order by a.attachmentOrder asc";

	public PagingList searchAttachment(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String attachmentName = params.get("attachmentName");
		String attachmentDescription = params.get("attachmentDescription");
		String createByName = params.get("createByName");
		String createByIP = params.get("createByIP");
		String categoryId = params.get("categoryId");
		String createByTimeStart = params.get("createByTimeStart");
		String createByTimeEnd = params.get("createByTimeEnd");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_ATTACHMENT_PREFIX,
				SQL_SEARCH_ATTACHMENT_SUFFIX);
		helper.setParam(true, "a.categoryId=c.categoryId");
		helper.setParam(StringUtils.isNotEmpty(attachmentName),
				"a.attachmentName like concat('%',?,'%')", attachmentName);
		helper.setParam(StringUtils.isNotEmpty(attachmentDescription),
				"a.attachmentDescription like concat('%',?,'%')",
				attachmentDescription);
		helper.setParam(StringUtils.isNotEmpty(createByName),
				"a.createByName like concat('%',?,'%')", createByName);
		helper.setParam(StringUtils.isNotEmpty(createByIP),
				"a.createByIP like concat('%',?,'%')", createByIP);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "a.categoryId=?",
				categoryId);
		helper.setParam(StringUtils.isNotEmpty(createByTimeStart),
				"unix_timestamp(a.attachmentByTime) > unix_timestamp(?)",
				createByTimeStart);
		helper.setParam(StringUtils.isNotEmpty(createByTimeEnd),
				"unix_timestamp(a.attachmentByTime) < unix_timestamp(?)",
				createByTimeEnd);

		return helper;
	}

	public int createAttachment(HttpServletRequest request, String attachmentUrl) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				attachmentUrl);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_attachment").usingGeneratedKeyColumns("attachmentId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String attachmentUrl) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String attachmentName = (String) request.getAttribute("attachmentName");
		String attachmentDescription = (String) request
				.getAttribute("attachmentDescription");
		String attachmentOrder = (String) request
				.getAttribute("attachmentOrder");
		String categoryId = (String) request.getAttribute("categoryId");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("attachmentName", attachmentName);
		parameters.put("attachmentDescription", attachmentDescription);
		parameters.put("attachmentUrl", attachmentUrl);
		parameters.put("attachmentOrder", Integer.parseInt(attachmentOrder));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("categoryId", Integer.parseInt(categoryId));

		return parameters;
	}

	private static final String SQL_GET_ATTACHMENT_BY_ID = "select * from doc_attachment where attachmentId=?";

	public HashMap<String, Object> getAttachmentById(String attachmentId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_ATTACHMENT_BY_ID, attachmentId);
	}

	private static final String SQL_UPDATE_ATTACHMENT_BY_ID = "update doc_attachment set attachmentName=?, attachmentDescription=?, attachmentUrl=?, attachmentOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where attachmentId=?";
	private static final String SQL_UPDATE_ATTACHMENT_NO_URL_BY_ID = "update doc_attachment set attachmentName=?, attachmentDescription=?, attachmentOrder=? where attachmentId=?";

	public void updateAttachmentById(HttpServletRequest request,
			String attachmentUrl) {
		if (StringUtils.isEmpty(attachmentUrl)) {
			Object[] parameters = buildUpdateCondition(request);
			jt.update(SQL_UPDATE_ATTACHMENT_NO_URL_BY_ID, parameters);
		} else {
			Object[] parameters = buildUpdateCondition(request, attachmentUrl);
			jt.update(SQL_UPDATE_ATTACHMENT_BY_ID, parameters);
		}

	}

	private Object[] buildUpdateCondition(HttpServletRequest request) {
		String attachmentId = (String) request.getAttribute("attachmentId");
		String attachmentName = (String) request.getAttribute("attachmentName");
		String attachmentDescription = (String) request
				.getAttribute("attachmentDescription");
		String attachmentOrder = (String) request
				.getAttribute("attachmentOrder");

		return new Object[] { attachmentName, attachmentDescription,
				attachmentOrder, attachmentId };
	}

	@SuppressWarnings("unchecked")
	private Object[] buildUpdateCondition(HttpServletRequest request,
			String attachmentUrl) {
		String attachmentId = (String) request.getAttribute("attachmentId");
		String attachmentName = (String) request.getAttribute("attachmentName");
		String attachmentDescription = (String) request
				.getAttribute("attachmentDescription");
		String attachmentOrder = (String) request
				.getAttribute("attachmentOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		return new Object[] { attachmentName, attachmentDescription,
				attachmentUrl, attachmentOrder,
				(Integer) loginUser.get("userId"), loginUser.get("realName"),
				CoreUtil.getIPAddr(request), attachmentId };
	}

	private static final String SQL_DELETE_ATTACHMENT = "delete from doc_attachment where attachmentId=?";

	public void deleteAttachment(final String[] attachmentIds) {
		jt.batchUpdate(SQL_DELETE_ATTACHMENT,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(attachmentIds[i]));
					}

					@Override
					public int getBatchSize() {
						return attachmentIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_GET_ATTACHMENT_UNSELECTED_FOR_ARTICLE_PREFIX = "select a.attachmentId, a.attachmentName, a.attachmentDescription from doc_attachment a ";
	private static final String SQL_GET_ATTACHMENT_UNSELECTED_FOR_ARTICLE_SUFFIX = "order by a.attachmentOrder asc";

	public PagingList searchUnselectedAttachment(HttpServletRequest request,
			HashSet<Object> attachmentIds) {
		QueryHelper helper = buildQueryConditionForArticleUnselectedAttachment(
				request, attachmentIds);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionForArticleUnselectedAttachment(
			HttpServletRequest request, HashSet<Object> attachmentIds) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String attachmentName = params.get("attachmentName");
		String attachmentDescription = params.get("attachmentDescription");
		String categoryId = params.get("categoryId");

		QueryHelper helper = new QueryHelper(
				SQL_GET_ATTACHMENT_UNSELECTED_FOR_ARTICLE_PREFIX,
				SQL_GET_ATTACHMENT_UNSELECTED_FOR_ARTICLE_SUFFIX);

		helper.setParam(StringUtils.isNotEmpty(attachmentName),
				"a.attachmentName like concat('%',?,'%')", attachmentName);
		helper.setParam(StringUtils.isNotEmpty(attachmentDescription),
				"a.attachmentDescription like concat('%',?,'%')",
				attachmentDescription);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "a.categoryId=?",
				categoryId);

		if (attachmentIds.size() > 0) {
			SqlHelper sqlHelper = new SqlHelper();
			String notIn = sqlHelper.buildWhereNotIn("a.attachmentId",
					attachmentIds);
			helper.setParam(true, notIn);
		}

		return helper;
	}

	private static final String SQL_GET_SELECTED_ATTACHMENT_BY_ARTICLE_ID = "select a.attachmentId, a.attachmentName, a.attachmentDescription from doc_attachment a, doc_article_attachment aa where aa.attachmentId=a.attachmentId and aa.articleId=? order by a.attachmentOrder asc";

	public PagingList getSelectedAttachmentByArticleId(
			HttpServletRequest request, int articleId) {
		return getPagingList(SQL_GET_SELECTED_ATTACHMENT_BY_ARTICLE_ID,
				request, new Object[] { articleId });
	}

	private static final String SQL_GET_ATTACHMENT_BY_IDS = "select attachmentUrl from doc_attachment where ";

	public List<Map<String, Object>> getAttachmentByIds(String[] attachmentIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_ATTACHMENT_BY_IDS
				+ sqlHelper.buildWhereIn("attachmentId", attachmentIds));
	}

}
