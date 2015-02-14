package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class PermissionService extends BaseService {
	private static final String SQL_SEARCH_PERMISSION_PREFIX = "select p.permissionId, p.permissionName, p.permissionDescription, p.permissionUrl, p.permissionOrder, case when p.isMenu='Y' then '是' else '否' end isMenu, u.permissionName as upperPermissionName, c.categoryName from sys_permission p, sys_permission u, sys_category c";
	private static final String SQL_SEARCH_PERMISSION_SUFFIX = "order by p.permissionOrder asc";

	public PagingList searchPermission(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String permissionName = params.get("permissionName");
		String permissionDescription = params.get("permissionDescription");
		String permissionUrl = params.get("permissionUrl");
		String isMenu = params.get("isMenu");
		String categoryId = params.get("categoryId");
		String upperId = params.get("upperId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_PERMISSION_PREFIX,
				SQL_SEARCH_PERMISSION_SUFFIX);
		helper.setParam(true,
				"p.upperId=u.permissionId and p.categoryId=c.categoryId");
		helper.setParam(StringUtils.isNotEmpty(permissionName),
				"p.permissionName like concat('%',?,'%')", permissionName);
		helper.setParam(StringUtils.isNotEmpty(permissionDescription),
				"p.permissionDescription like concat('%',?,'%')",
				permissionDescription);
		helper.setParam(StringUtils.isNotEmpty(permissionUrl),
				"p.permissionUrl like concat('%',?,'%')", permissionUrl);
		helper.setParam(StringUtils.isNotEmpty(isMenu), "p.isMenu = ?", isMenu);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "p.categoryId = ?",
				categoryId);
		helper.setParam(StringUtils.isNotEmpty(upperId), "p.upperId = ?",
				upperId);

		return helper;
	}

	private static final String SQL_GET_PERMISSION_AJAX_PREFIX = "select permissionId as id, permissionName as text from sys_permission";
	private static final String SQL_GET_PERMISSION_AJAX_SUFFIX = "order by permissionOrder asc";

	public List<Map<String, Object>> getPermissionForAjax(
			HttpServletRequest request) {
		QueryHelper helper = new QueryHelper(SQL_GET_PERMISSION_AJAX_PREFIX,
				SQL_GET_PERMISSION_AJAX_SUFFIX);
		helper.setParam(true, "isMenu='Y'");

		String categoryId = (String) request.getAttribute("categoryId");

		helper.setParam(StringUtils.isNotEmpty(categoryId), "categoryId=?",
				categoryId);

		return jt.queryForList(helper.getQuerySql(), helper.getParams());
	}

	private static final String SQL_INSERT_PERMISSION_UPDATE = "update sys_permission set upperId=? where permissionId=?";

	public int createPermission(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		String isMenu = (String) parameters.get("isMenu");

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"sys_permission").usingGeneratedKeyColumns("permissionId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		if ("Y".equals(isMenu)) {
			jt.update(SQL_INSERT_PERMISSION_UPDATE, new Object[] { pk, pk });
		}
		
		return pk;

	}

	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String permissionName = objectMap.get("permissionName");
		String permissionUrl = objectMap.get("permissionUrl");
		String permissionDescription = objectMap.get("permissionDescription");
		String permissionOrder = objectMap.get("permissionOrder");
		String isMenu = objectMap.get("isMenu");
		String categoryId = objectMap.get("categoryId");
		String upperId = objectMap.get("upperId");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("permissionName", permissionName);
		parameters.put("permissionUrl", permissionUrl);
		parameters.put("permissionDescription", permissionDescription);
		parameters.put("permissionOrder", permissionOrder);
		parameters.put("isMenu", isMenu);
		parameters.put("categoryId", categoryId);
		if ("Y".equals(isMenu)) {
			parameters.put("upperId", 0);
		} else {
			parameters.put("upperId", upperId);
		}

		return parameters;
	}

	private static final String SQL_GET_PERMISSION_BY_ID = "select * from sys_permission where permissionId=?";

	public HashMap<String, Object> getPermissionById(String permissionId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_PERMISSION_BY_ID, permissionId);
	}

	private static final String SQL_UPDATE_PERMISSION_BY_ID = "update sys_permission set permissionName=?, permissionUrl=?, permissionDescription=?, permissionOrder=?, categoryId=?, isMenu=?, upperId=? where permissionId=?";

	public void updatePermissionById(HttpServletRequest request) {

		Object[] parameters = buildUpdateCondition(request);

		String permissionId = (String) request.getAttribute("permissionId");
		String isMenu = (String) request.getAttribute("isMenu");

		jt.update(SQL_UPDATE_PERMISSION_BY_ID, parameters);

		if ("Y".equals(isMenu)) {
			jt.update(SQL_INSERT_PERMISSION_UPDATE, permissionId);
		}

	}

	private Object[] buildUpdateCondition(HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String permissionId = objectMap.get("permissionId");
		String permissionName = objectMap.get("permissionName");
		String permissionUrl = objectMap.get("permissionUrl");
		String permissionDescription = objectMap.get("permissionDescription");
		String permissionOrder = objectMap.get("permissionOrder");
		String isMenu = objectMap.get("isMenu");
		String categoryId = objectMap.get("categoryId");
		String upperId = objectMap.get("upperId");

		if ("Y".equals(isMenu)) {
			upperId = permissionId;
		}

		Object[] parameters = new Object[] { permissionName, permissionUrl,
				permissionDescription, permissionOrder, categoryId, isMenu,
				upperId, permissionId };

		return parameters;
	}

	private static final String SQL_DELETE_PERMISSION_PP = "delete from sys_permissiongroup_permission where permissionId=?";
	private static final String SQL_DELETE_PERMISSION = "delete from sys_permission where permissionId=?";

	public void deletePermission(final String[] permissionIds) {
		jt.batchUpdate(SQL_DELETE_PERMISSION_PP,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(permissionIds[i]));
					}

					@Override
					public int getBatchSize() {
						return permissionIds.length;
					}
				});

		jt.batchUpdate(SQL_DELETE_PERMISSION,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(permissionIds[i]));
					}

					@Override
					public int getBatchSize() {
						return permissionIds.length;
					}
				});

	}

	// extend
	private static final String SQL_GET_PERMISSION_UNSELECTED_FOR_PERMISSIONGROUP_PREFIX = "select p.permissionId, p.permissionName, p.permissionUrl from sys_permission p, sys_category c, sys_permission u";
	private static final String SQL_GET_PERMISSION_UNSELECTED_FOR_PERMISSIONGROUP_SUFFIX = "order by p.permissionOrder asc";

	public PagingList getPermissionUnselectedForPermissionGroup(
			HttpServletRequest request, HashSet<Object> permissionIds) {
		QueryHelper helper = buildQueryConditionForPermissionGroupUnselected(
				request, permissionIds);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionForPermissionGroupUnselected(
			HttpServletRequest request, HashSet<Object> permissionIds) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String permissionName = params.get("permissionName");
		String permissionUrl = params.get("permissionUrl");
		String categoryId = params.get("categoryId");
		String upperId = params.get("upperId");

		QueryHelper helper = new QueryHelper(
				SQL_GET_PERMISSION_UNSELECTED_FOR_PERMISSIONGROUP_PREFIX,
				SQL_GET_PERMISSION_UNSELECTED_FOR_PERMISSIONGROUP_SUFFIX);
		helper.setParam(true,
				"p.upperId=u.permissionId and p.categoryId=c.categoryId");
		helper.setParam(StringUtils.isNotEmpty(permissionName),
				"p.permissionName like concat('%',?,'%')", permissionName);
		helper.setParam(StringUtils.isNotEmpty(permissionUrl),
				"p.permissionUrl like concat('%',?,'%')", permissionUrl);
		helper.setParam(StringUtils.isNotEmpty(categoryId), "p.categoryId=?",
				categoryId);
		helper.setParam(StringUtils.isNotEmpty(upperId), "p.upperId=?", upperId);

		if (permissionIds.size() > 0) {
			SqlHelper sqlHelper = new SqlHelper();
			String notIn = sqlHelper.buildWhereNotIn("p.permissionId",
					permissionIds);
			helper.setParam(true, notIn);
		}

		return helper;
	}
}
