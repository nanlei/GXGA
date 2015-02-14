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

public class ArticleService extends BaseService {
	private static final String SQL_GET_ARTICLE_BY_TYPE_AND_CODE = "select articleId, articleType, articleCode, articleTitle, articleContent, articleOrder, createBy, createByName, createByTime, createByIP, pageView from doc_article where articleType=? and articleCode=?";

	public HashMap<String, Object> getArticleByTypeAndCode(String articleType,
			String articleCode) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_ARTICLE_BY_TYPE_AND_CODE, articleType, articleCode);
	}

	private static final String SQL_SEARCH_ARTICLES_BY_TYPE_PREFIX = "select a.articleId, a.articleType, a.articleTitle, a.articleOrder, c.constantName as articleStatus, a.createBy, a.createByName, date_format(a.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, a.createByIP, a.pageView, d.departmentName from doc_article a, hr_department d, sys_constant c ";
	private static final String SQL_SEARCH_ARTICLES_BY_TYPE_SUFFIX = "order by ";

	// Search by type
	public PagingList searchArticlesByType(HttpServletRequest request,
			String articleType) {
		QueryHelper helper = buildQueryConditionByArticleType(request,
				articleType);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionByArticleType(
			HttpServletRequest request, String articleType) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String articleTile = params.get("articleTitle");
		String articleStatus = params.get("articleStatus");
		String departmentId = params.get("departmentId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "articleOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_ARTICLES_BY_TYPE_PREFIX,
				SQL_SEARCH_ARTICLES_BY_TYPE_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(true, "a.departmentId=d.departmentId");
		helper.setParam(true,
				"a.articleStatus=c.constantValue and c.constantType='ARTICLESTATUS'");
		helper.setParam(true, "a.articleType=?", articleType);
		helper.setParam(StringUtils.isNotEmpty(articleTile),
				"a.articleTile like concat('%',?,'%')", articleTile);
		helper.setParam(StringUtils.isNotEmpty(articleStatus),
				"a.articleStatus=?", articleStatus);
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"a.departmentId=?", departmentId);

		return helper;
	}

	private static final String SQL_SEARCH_ARTICLES_BY_TYPE_AND_CODE_PREFIX = "select a.articleId, a.articleType, a.articleTitle, a.articleOrder, c.constantName as articleStatus, tc.constantName as articleCode, a.createBy, a.createByName, date_format(a.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, a.createByIP, a.pageView, d.departmentName from doc_article a, hr_department d, sys_constant c, sys_constant tc ";
	private static final String SQL_SEARCH_ARTICLES_BY_TYPE_AND_CODE_SUFFIX = "order by articleOrder asc";

	// Search by type & code
	public PagingList searchArticlesByTypeAndCode(HttpServletRequest request,
			String articleType, String articleCode) {
		QueryHelper helper = buildQueryConditionByArticleTypeAndCode(request,
				articleType, articleCode);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionByArticleTypeAndCode(
			HttpServletRequest request, String articleType, String articleCode) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String articleTile = params.get("articleTitle");
		String articleStatus = params.get("articleStatus");
		String departmentId = params.get("departmentId");

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_ARTICLES_BY_TYPE_AND_CODE_PREFIX,
				SQL_SEARCH_ARTICLES_BY_TYPE_AND_CODE_SUFFIX);

		helper.setParam(true, "a.departmentId=d.departmentId");
		helper.setParam(true,
				"a.articleStatus=c.constantValue and c.constantType='ARTICLESTATUS'");
		helper.setParam(true,
				"a.articleCode=tc.constantValue and tc.constantType=?",
				articleType);
		helper.setParam(true, "a.articleType=?", articleType);
		helper.setParam(StringUtils.isNotEmpty(articleCode), "a.articleCode=?",
				articleCode);
		helper.setParam(StringUtils.isNotEmpty(articleTile),
				"a.articleTile like concat('%',?,'%')", articleTile);
		helper.setParam(StringUtils.isNotEmpty(articleStatus),
				"a.articleStatus=?", articleStatus);
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"a.departmentId=?", departmentId);

		return helper;
	}

	public int createArticle(HttpServletRequest request, String articleType) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				articleType, null, null, null);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_article").usingGeneratedKeyColumns("articleId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	public int createArticle(HttpServletRequest request, String articleType,
			String articleCode) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				articleType, articleCode, null, null);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_article").usingGeneratedKeyColumns("articleId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String articleType, String articleCode,
			String dcId, String videoId) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		int departmentId = 0;
		if (StringUtils.isNotEmpty(params.get("departmentId"))) {
			departmentId = Integer.parseInt(params.get("departmentId"));
		} else {
			departmentId = (Integer) loginUser.get("departmentId");
		}

		String articleTitle = params.get("articleTitle");
		String articleOrder = params.get("articleOrder");

		String articleContent = (String) request.getAttribute("articleContent");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(articleType)) {
			parameters.put("articleType", articleType);
		} else {
			parameters.put("articleType", "");
		}
		if (StringUtils.isNotEmpty(articleCode)) {
			parameters.put("articleCode", articleCode);
		} else {
			parameters.put("articleCode", "");
		}
		if (StringUtils.isNotEmpty(dcId)) {
			parameters.put("dcId", dcId);
		} else {
			parameters.put("dcId", "0");
		}
		if (StringUtils.isNotEmpty(videoId)) {
			parameters.put("videoId", videoId);
		} else {
			parameters.put("videoId", "0");
		}

		parameters.put("articleTitle", articleTitle);
		parameters.put("articleOrder", articleOrder);
		parameters.put("articleContent", articleContent);
		parameters.put("articleStatus", Constant.STS_NEW);
		parameters.put("departmentId", departmentId);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("pageView", 0);
		return parameters;
	}

	private static final String SQL_GET_ARTICLE_BY_ID = "select * from doc_article where articleId=?";

	public HashMap<String, Object> getArticleById(String articleId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_ARTICLE_BY_ID,
				articleId);
	}

	private static final String SQL_UPDATE_ARTICLE_BY_ID = "update doc_article set articleTitle=?, articleContent=?, articleOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where articleId=?";

	public int updateArtileById(Object[] parameters) {
		return jt.update(SQL_UPDATE_ARTICLE_BY_ID, parameters);
	}

	private static final String SQL_UPDATE_ARTICLE_BY_TYPE_AND_CODE = "update doc_article set articleContent=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where articleType=? and articleCode=?";

	public int updateArtileByTypeAndCode(Object[] parameters) {
		return jt.update(SQL_UPDATE_ARTICLE_BY_TYPE_AND_CODE, parameters);
	}

	private static final String SQL_DELETE_AA_BY_ARTICLEID = "delete from doc_article_attachment where articleId=?";
	private static final String SQL_DELETE_AI_BY_ARTICLEID = "delete from doc_article_image where articleId=?";
	private static final String SQL_DELETE_AS_BY_ARTICLEID = "delete from doc_article_sign where articleId=?";
	private static final String SQL_DELETE_ARTICLE_BY_ID = "delete from doc_article where articleId=?";

	public void deleteArticleById(final String[] articleIds) {
		jt.batchUpdate(SQL_DELETE_AA_BY_ARTICLEID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(articleIds[i]));
					}

					@Override
					public int getBatchSize() {
						return articleIds.length;
					}
				});
		jt.batchUpdate(SQL_DELETE_AI_BY_ARTICLEID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(articleIds[i]));
					}

					@Override
					public int getBatchSize() {
						return articleIds.length;
					}
				});
		jt.batchUpdate(SQL_DELETE_AS_BY_ARTICLEID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(articleIds[i]));
					}

					@Override
					public int getBatchSize() {
						return articleIds.length;
					}
				});
		jt.batchUpdate(SQL_DELETE_ARTICLE_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(articleIds[i]));
					}

					@Override
					public int getBatchSize() {
						return articleIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_GET_ARTICLE_FROM_AI = "select imageId from doc_article_image where articleId=?";

	public HashSet<Object> getImageFromAI(int articleId) {
		HashSet<Object> imageIdSet = new HashSet<Object>();

		List<Integer> imageIds = jt.queryForList(SQL_GET_ARTICLE_FROM_AI,
				new Object[] { articleId }, Integer.class);
		for (int imageId : imageIds) {
			imageIdSet.add(imageId);
		}

		return imageIdSet;
	}

	private static final String SQL_INSERT_AI = "insert into doc_article_image(articleId,imageId) values(?,?)";

	public void addImagesToArticle(final int articleId, final String[] imageIds) {
		jt.batchUpdate(SQL_INSERT_AI, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, articleId);
				ps.setInt(2, Integer.parseInt(imageIds[i]));

			}

			@Override
			public int getBatchSize() {
				return imageIds.length;
			}
		});
	}

	private static final String SQL_DELETE_AI = "delete from doc_article_image where articleId=? and imageId=?";

	public void deleteImagesFromArticle(final int articleId,
			final String[] imageIds) {
		jt.batchUpdate(SQL_DELETE_AI, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, articleId);
				ps.setInt(2, Integer.parseInt(imageIds[i]));

			}

			@Override
			public int getBatchSize() {
				return imageIds.length;
			}
		});
	}

	private static final String SQL_GET_ATTACHMENT_FROM_AA = "select attachmentId from doc_article_attachment where articleId=?";

	public HashSet<Object> getAttachmentFromAA(int articleId) {
		HashSet<Object> attachmentIdSet = new HashSet<Object>();

		List<Integer> attachmentIds = jt.queryForList(
				SQL_GET_ATTACHMENT_FROM_AA, new Object[] { articleId },
				Integer.class);
		for (int attachmentId : attachmentIds) {
			attachmentIdSet.add(attachmentId);
		}

		return attachmentIdSet;
	}

	private static final String SQL_INSERT_AA = "insert into doc_article_attachment(articleId,attachmentId) values(?,?)";

	public void addAttachmentsToArticle(final int articleId,
			final String[] attachmentIds) {
		jt.batchUpdate(SQL_INSERT_AA, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, articleId);
				ps.setInt(2, Integer.parseInt(attachmentIds[i]));

			}

			@Override
			public int getBatchSize() {
				return attachmentIds.length;
			}
		});
	}

	private static final String SQL_DELETE_AA = "delete from doc_article_attachment where articleId=? and attachmentId=?";

	public void deleteAttachmentsFromArticle(final int articleId,
			final String[] attachmentIds) {
		jt.batchUpdate(SQL_DELETE_AA, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, articleId);
				ps.setInt(2, Integer.parseInt(attachmentIds[i]));

			}

			@Override
			public int getBatchSize() {
				return attachmentIds.length;
			}
		});
	}

	private static final String SQL_GET_SIGNLIST_BY_ARTICLEID_PREFIX = "select s.asId, s.articleId, s.departmentId, d.departmentName, s.comment, case when s.status='NEW' then '未签收' else '已签收' end status, s.signByName, date_format(s.signByTime,'%Y-%m-%d %H:%i:%s') as signByTime, s.signByIP from doc_article_sign s, hr_department d ";
	private static final String SQL_GET_SIGNLIST_BY_ARTICLEID_SUFFIX = "";

	public PagingList searchSignListByArticleId(HttpServletRequest request) {
		QueryHelper helper = buildQueryConditionForSignList(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionForSignList(
			HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String articleId = params.get("articleId");
		String status = params.get("status");

		QueryHelper helper = new QueryHelper(
				SQL_GET_SIGNLIST_BY_ARTICLEID_PREFIX,
				SQL_GET_SIGNLIST_BY_ARTICLEID_SUFFIX);

		helper.setParam(true, "s.departmentId=d.departmentId");
		helper.setParam(true, "s.articleId=?", articleId);
		helper.setParam(StringUtils.isNotEmpty(status), "s.status=?", status);

		return helper;
	}

	private static final String SQL_CHECK_SIGN_LIST = "select count(*) from doc_article_sign where articleId=? and departmentId=?";

	public int checkSignList(HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String articleId = params.get("articleId");
		String departmentId = params.get("departmentId");

		return jt.queryForObject(SQL_CHECK_SIGN_LIST, new Object[] { articleId,
				departmentId }, Integer.class);
	}

	public int addSignDepartment(HttpServletRequest request, String leaderId) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		parameters.put("articleId", Integer.parseInt(params.get("articleId")));
		parameters.put("departmentId",
				Integer.parseInt(params.get("departmentId")));
		parameters.put("status", "NEW");

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_article_sign").usingGeneratedKeyColumns("asId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		addUserForSign(Integer.parseInt(params.get("departmentId")), pk,
				leaderId);

		return pk;
	}

	private static final String SQL_GET_USER_FOR_SIGN_BY_DEPARTMENT_ID = "select u.userId from sys_user u, hr_employee e where u.employeeId=e.employeeId and e.departmentId=? and u.isSignRole='Y'";

	private void addUserForSign(int departmentId, int refId, String leaderId) {
		if (StringUtils.isNotEmpty(leaderId)) {
			SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
					"sys_user_action").usingGeneratedKeyColumns("actionId");

			HashMap<String, Object> params = new HashMap<String, Object>();

			params.put("actionType", "SIGN");
			params.put("userId", leaderId);
			params.put("departmentId", departmentId);
			params.put("refTable", "doc_article_sign");
			params.put("refId", refId);
			params.put("sts", Constant.STS_NEW);
			params.put("actionTime", new Timestamp(new Date().getTime()));

			insert.executeAndReturnKey(params);
		} else {
			List<Map<String, Object>> userSignList = jt.queryForList(
					SQL_GET_USER_FOR_SIGN_BY_DEPARTMENT_ID, departmentId);

			for (int i = 0; i < userSignList.size(); i++) {
				int userId = (Integer) userSignList.get(i).get("userId");

				SimpleJdbcInsert insert = new SimpleJdbcInsert(jt)
						.withTableName("sys_user_action")
						.usingGeneratedKeyColumns("actionId");

				HashMap<String, Object> params = new HashMap<String, Object>();

				params.put("actionType", "SIGN");
				params.put("userId", userId);
				params.put("departmentId", departmentId);
				params.put("refTable", "doc_article_sign");
				params.put("refId", refId);
				params.put("sts", Constant.STS_NEW);
				params.put("actionTime", new Timestamp(new Date().getTime()));

				insert.executeAndReturnKey(params);
			}
		}
	}

	private static final String SQL_GET_SIGNID_BY_ARTICLEID_AND_DEPARTMENTID = "select asId from doc_article_sign where articleId=? and ";
	private static final String SQL_DELETE_SIGN = "delete from doc_article_sign where articleId=? and departmentId=?";
	private static final String SQL_DELETE_USER_ACTION = "delete from sys_user_action where refId=?";

	public void deleteSignDepartment(final String articleId,
			final String[] departmentIds) {
		SqlHelper sqlHelper = new SqlHelper();
		String whereIn = sqlHelper.buildWhereIn("departmentId", departmentIds);
		List<Map<String, Object>> signIdList = jt.queryForList(
				SQL_GET_SIGNID_BY_ARTICLEID_AND_DEPARTMENTID + whereIn,
				articleId);
		for (int i = 0; i < signIdList.size(); i++) {
			int asId = (Integer) signIdList.get(i).get("asId");
			jt.update(SQL_DELETE_USER_ACTION, asId);
		}

		jt.batchUpdate(SQL_DELETE_SIGN, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(articleId));
				ps.setInt(2, Integer.parseInt(departmentIds[i]));
			}

			@Override
			public int getBatchSize() {
				return departmentIds.length;
			}
		});
	}

	// Extend for Branch department
	private static final String SQL_SEARCH_BRANCH_DEPARTMENT_BY_DCID_PREFIX = "select a.articleId, a.articleTitle, a.articleOrder, dc.dcName, c.constantName as articleStatus, a.createBy, a.createByName, date_format(a.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, a.createByIP, a.pageView, d.departmentName from doc_article a, hr_department d, sys_constant c, doc_department_category dc ";
	private static final String SQL_SEARCH_BRANCH_DEPARTMENT_BY_DCID_SUFFIX = "order by a.departmentId asc, ";

	public PagingList searchArticlesByDcId(HttpServletRequest request) {
		QueryHelper helper = buildQueryConditionByDcId(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	@SuppressWarnings("unchecked")
	private QueryHelper buildQueryConditionByDcId(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String dcId = params.get("dcId");
		int departmentId = (Integer) loginUser.get("departmentId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_BRANCH_DEPARTMENT_BY_DCID_PREFIX,
				SQL_SEARCH_BRANCH_DEPARTMENT_BY_DCID_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(true,
				"a.departmentId=d.departmentId and a.dcId=dc.dcId");
		helper.setParam(true,
				"a.articleStatus=c.constantValue and c.constantType='ARTICLESTATUS'");
		helper.setParam(StringUtils.isNotEmpty(dcId), "a.dcId=?", dcId);
		helper.setParam(!(0 == departmentId), "a.departmentId=?", departmentId);

		return helper;
	}

	public int createArticleForDepartment(HttpServletRequest request,
			String dcId, String videoId) {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				null, null, dcId, videoId);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_article").usingGeneratedKeyColumns("articleId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private static final String SQL_UPDATE_ARTICLE_FOR_DEPARTMENT_BY_ID = "update doc_article set articleTitle=?, articleContent=?, articleOrder=?, dcId=?, videoId=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where articleId=?";

	public int updateArtileForDepartmentById(Object[] parameters) {
		return jt.update(SQL_UPDATE_ARTICLE_FOR_DEPARTMENT_BY_ID, parameters);
	}

}
