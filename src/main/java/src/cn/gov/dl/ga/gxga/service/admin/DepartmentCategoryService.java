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

public class DepartmentCategoryService extends BaseService {
	private static final String SQL_SEARCH_CATEGORY_BY_DEPARTMENT_ID_PREFIX = "select dc.dcId, dc.departmentId, d.departmentName, dc.dcName, dc.dcOrder from doc_department_category dc, hr_department d ";
	private static final String SQL_SEARCH_CATEGORY_BY_DEPARTMENT_ID_SUFFIX = "order by dc.departmentId asc, ";

	public PagingList searchCategoryByDepartmentId(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String departmentId = (String) params.get("departmentId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_CATEGORY_BY_DEPARTMENT_ID_PREFIX,
				SQL_SEARCH_CATEGORY_BY_DEPARTMENT_ID_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(true, "dc.departmentId=d.departmentId");
		helper.setParam(!"1".equals(departmentId), "dc.departmentId=?",
				departmentId);

		return helper;
	}

	public int addDepartmentCategory(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_department_category").usingGeneratedKeyColumns("dcId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String departmentId = objectMap.get("departmentId");
		String dcName = objectMap.get("dcName");
		String dcOrder = objectMap.get("dcOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("departmentId", departmentId);
		parameters.put("dcName", dcName);
		parameters.put("dcOrder", dcOrder);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_CATEGORY_BY_ID = "select * from doc_department_category where dcId=? and departmentId=?";

	public HashMap<String, Object> getCategoryById(String dcId,
			String departmentId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_CATEGORY_BY_ID,
				dcId, departmentId);
	}

	private static final String SQL_UPDATE_CATEGORY_BY_ID = "update doc_department_category set dcName=?, dcOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where dcId=? and departmentId=?";

	public int updateCategoryById(Object[] parameters) {
		return jt.update(SQL_UPDATE_CATEGORY_BY_ID, parameters);
	}

	private static final String SQL_DELETE_CATEGORY_BY_ID = "delete from doc_department_category where dcId=?";
	private static final String SQL_DELETE_ARTICLE_BY_DCID = "delete from doc_article where dcId=?";

	public void deleteCategoryById(final String[] dcIds) {
		jt.batchUpdate(SQL_DELETE_CATEGORY_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(dcIds[i]));
					}

					@Override
					public int getBatchSize() {
						return dcIds.length;
					}
				});
		jt.batchUpdate(SQL_DELETE_ARTICLE_BY_DCID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(dcIds[i]));
					}

					@Override
					public int getBatchSize() {
						return dcIds.length;
					}
				});

	}

	// Extend
	private static final String SQL_GET_CATEGORY_BY_DEPARTMENTID = "select dc.dcId as id, dc.dcName as text from doc_department_category dc where dc.departmentId=? order by dc.dcOrder asc";

	public List<Map<String, Object>> getCategoryByDepartment(String departmentId) {
		return jt.queryForList(SQL_GET_CATEGORY_BY_DEPARTMENTID, departmentId);
	}

	private static final String SQL_GET_CATEGORY_BY_ID_AND_DEPARTMENTID = "select dcId from doc_department_category where departmentId=? and ";

	public List<Map<String, Object>> getCategoryByIdAndDepartmentId(
			String[] dcIds, int departmentId) {
		SqlHelper helper = new SqlHelper();
		String whereDcId = helper.buildWhereIn("dcId", dcIds);
		return jt.queryForList(SQL_GET_CATEGORY_BY_ID_AND_DEPARTMENTID
				+ whereDcId, departmentId);
	}
}
