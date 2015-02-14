package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class RoleService extends BaseService {
	private static final String SQL_SEARCH_ROLE_PREFIX = "select roleId, roleName, roleDescription, roleOrder from sys_role";
	private static final String SQL_SEARCH_ROLE_SUFFIX = "order by roleOrder asc";

	public PagingList searchRole(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String roleName = params.get("roleName");
		String roleDescription = params.get("roleDescription");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_ROLE_PREFIX,
				SQL_SEARCH_ROLE_SUFFIX);

		helper.setParam(StringUtils.isNotEmpty(roleName),
				"roleName like concat('%',?,'%')", roleName);
		helper.setParam(StringUtils.isNotEmpty(roleDescription),
				"roleDescription like concat('%',?,'%')", roleDescription);

		return helper;
	}

	public void addRole(HttpServletRequest request) {
		int roleId = insertRole(request);
		insertRolePermissionGroup(roleId);
	}

	private int insertRole(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"sys_role").usingGeneratedKeyColumns("roleId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();
		return pk;
	}

	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String roleName = objectMap.get("roleName");
		String roleDescription = objectMap.get("roleDescription");
		String roleOrder = objectMap.get("roleOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleName", roleName);
		parameters.put("roleDescription", roleDescription);
		parameters.put("roleOrder", roleOrder);

		return parameters;
	}

	private final String SQL_GET_INIT_PERMISSIONGROUP_FOR_ADD_ROLE = "select permissionGroupId from sys_permissiongroup where isInit='Y'";

	private void insertRolePermissionGroup(final int roleId) {
		final List<Integer> permissionGroupIds = jt.queryForList(
				SQL_GET_INIT_PERMISSIONGROUP_FOR_ADD_ROLE, Integer.class);
		if (permissionGroupIds.size() > 0) {
			jt.batchUpdate(SQL_INSERT_RP, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setInt(1, roleId);
					ps.setInt(2, permissionGroupIds.get(i));
				}

				@Override
				public int getBatchSize() {
					return permissionGroupIds.size();
				}
			});
		}
	}

	private static final String SQL_GET_ROLE_BY_ID = "select * from sys_role where roleId=?";

	public HashMap<String, Object> getRoleById(String roleId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_ROLE_BY_ID,
				roleId);
	}

	private static final String SQL_UPDATE_ROLE = "update sys_role set roleName=?, roleDescription=?, roleOrder=? where roleId=?";

	public int updateRole(Object[] parameters) {
		return jt.update(SQL_UPDATE_ROLE, parameters);
	}

	private static final String SQL_CHECK_USER_ROLE = "select count(*) from sys_user where ";

	public int checkUserRole(String[] roleIds) {
		SqlHelper sqlHelper = new SqlHelper();
		String whereSql = sqlHelper.buildWhereIn("roleId", roleIds);
		return jt.queryForObject(SQL_CHECK_USER_ROLE + whereSql, Integer.class);
	}

	private static final String SQL_DELETE_ROLE_RP = "delete from sys_role_permissiongroup where roleId=?";
	private static final String SQL_DELETE_ROLE = "delete from sys_role where roleId=?";

	public void deleteRole(final String[] roleIds) {
		jt.batchUpdate(SQL_DELETE_ROLE_RP, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(roleIds[i]));
			}

			@Override
			public int getBatchSize() {
				return roleIds.length;
			}
		});

		jt.batchUpdate(SQL_DELETE_ROLE, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(roleIds[i]));
			}

			@Override
			public int getBatchSize() {
				return roleIds.length;
			}
		});
	}

	// extend
	private static final String SQL_GET_PERMISSIONGROUP_FROM_RP = "select permissionGroupId from sys_role_permissiongroup where roleId=?";

	public HashSet<Object> getPermissionGroupFromRP(String roleId) {
		HashSet<Object> permissionGroupIdSet = new HashSet<Object>();

		List<Integer> permissionGroupIds = jt.queryForList(
				SQL_GET_PERMISSIONGROUP_FROM_RP, new Object[] { roleId },
				Integer.class);
		for (int permissionGroupId : permissionGroupIds) {
			permissionGroupIdSet.add(permissionGroupId);
		}

		return permissionGroupIdSet;
	}

	private static final String SQL_GET_SELECTED_PERMISSIONGROUP_BY_ROLEID = "select p.permissionGroupId, p.permissionGroupName from sys_permissiongroup p, sys_role_permissiongroup rp where p.permissionGroupId=rp.permissionGroupId and rp.roleId=? order by p.permissionGroupOrder asc";

	public PagingList getSelectedPermissionGroupByRoleId(
			HttpServletRequest request) {
		String roleId = (String) request.getAttribute("roleId");
		return getPagingList(SQL_GET_SELECTED_PERMISSIONGROUP_BY_ROLEID,
				request, new Object[] { roleId });
	}

	private static final String SQL_INSERT_RP = "insert into sys_role_permissiongroup(roleId, permissionGroupId) values(?,?)";

	public void addPermissionGroupToRole(final String[] permissionGroupIds,
			final String roleId) {
		jt.batchUpdate(SQL_INSERT_RP, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(roleId));
				ps.setInt(2, Integer.parseInt(permissionGroupIds[i]));
			}

			@Override
			public int getBatchSize() {
				return permissionGroupIds.length;
			}
		});
	}

	private static final String SQL_DELETE_RP = "delete from sys_role_permissiongroup where roleId=? and permissionGroupId=?";

	public void deletePermissionGroupFromRole(
			final String[] permissionGroupIds, final String roleId) {
		jt.batchUpdate(SQL_DELETE_RP, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(roleId));
				ps.setInt(2, Integer.parseInt(permissionGroupIds[i]));
			}

			@Override
			public int getBatchSize() {
				return permissionGroupIds.length;
			}
		});
	}
}
