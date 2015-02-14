package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class PermissionGroupService extends BaseService {
	private static final String SQL_SEARCH_PERMISSIONGROUP_PREFIX = "select permissionGroupId, permissionGroupName, permissionGroupDescription, permissionGroupOrder, case when isInit='Y' then '是' else '否' end isInit from sys_permissiongroup";
	private static final String SQL_SEARCH_PERMISSIONGROUP_SUFFIX = "order by permissionGroupOrder asc";

	public PagingList searchPermissionGroup(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String permissionGroupName = params.get("permissionGroupName");
		String permissionGroupDescription = params
				.get("permissionGroupDescription");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_PERMISSIONGROUP_PREFIX,
				SQL_SEARCH_PERMISSIONGROUP_SUFFIX);

		helper.setParam(StringUtils.isNotEmpty(permissionGroupName),
				"permissionGroupName like concat('%',?,'%')",
				permissionGroupName);
		helper.setParam(StringUtils.isNotEmpty(permissionGroupDescription),
				"permissionGroupDescription like concat('%',?,'%')",
				permissionGroupDescription);

		return helper;
	}

	private static final String SQL_INSERT_PERMISSIONGROUP = "insert into sys_permissiongroup(permissionGroupName, permissionGroupDescription, permissionGroupOrder, isInit) values(?,?,?,?)";

	public int addPermissionGroup(Object[] parameters) {
		return jt.update(SQL_INSERT_PERMISSIONGROUP, parameters);
	}

	private static final String SQL_GET_PERMISSIONGROUP_BY_ID = "select * from sys_permissiongroup where permissionGroupId=?";

	public HashMap<String, Object> getPermissionGroupById(
			String permissionGroupId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_PERMISSIONGROUP_BY_ID, permissionGroupId);
	}

	private static final String SQL_UPDATE_PERMISSIONGROUP = "update sys_permissiongroup set permissionGroupName=?, permissionGroupDescription=?, permissionGroupOrder=?, isInit=? where permissionGroupId=?";

	public int updatePermissionGroup(Object[] parameters) {
		return jt.update(SQL_UPDATE_PERMISSIONGROUP, parameters);
	}

	private static final String SQL_DELETE_PERMISSIONGROUP_PP = "delete from sys_permissiongroup_permission where permissionGroupId=?";
	private static final String SQL_DELETE_PERMISSIONGROUP_RP = "delete from sys_role_permissiongroup where permissionGroupId=?";
	private static final String SQL_DELETE_PERMISSIONGROUP = "delete from sys_permissiongroup where permissionGroupId=?";

	public void deletePermissionGroup(final String[] permissionGroupIds) {
		jt.batchUpdate(SQL_DELETE_PERMISSIONGROUP_PP,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(permissionGroupIds[i]));
					}

					@Override
					public int getBatchSize() {
						return permissionGroupIds.length;
					}
				});

		jt.batchUpdate(SQL_DELETE_PERMISSIONGROUP_RP,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(permissionGroupIds[i]));
					}

					@Override
					public int getBatchSize() {
						return permissionGroupIds.length;
					}
				});

		jt.batchUpdate(SQL_DELETE_PERMISSIONGROUP,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(permissionGroupIds[i]));
					}

					@Override
					public int getBatchSize() {
						return permissionGroupIds.length;
					}
				});
	}

	// extend
	private static final String SQL_GET_PERMISSION_FROM_PP = "select permissionId from sys_permissiongroup_permission where permissionGroupId=?";

	public HashSet<Object> getPermissionFromPP(String permissionGroupId) {
		HashSet<Object> permissionIdSet = new HashSet<Object>();

		List<Integer> permissionIds = jt.queryForList(
				SQL_GET_PERMISSION_FROM_PP, new Object[] { permissionGroupId },
				Integer.class);
		for (int permissionId : permissionIds) {
			permissionIdSet.add(permissionId);
		}

		return permissionIdSet;

	}

	private static final String SQL_INSERT_PP = "insert into sys_permissiongroup_permission(permissionGroupId, permissionId) values(?,?)";

	public void addPermissionToPermissionGroup(final String[] permissionIds,
			final String permissionGroupId) {
		jt.batchUpdate(SQL_INSERT_PP, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(permissionGroupId));
				ps.setInt(2, Integer.parseInt(permissionIds[i]));
			}

			@Override
			public int getBatchSize() {
				return permissionIds.length;
			}
		});
	}

	private static final String SQL_GET_SELECTED_PERMISSION_BY_PERMISSIONGROUPID = "select p.permissionId, p.permissionName, p.permissionUrl from sys_permission p, sys_permissiongroup_permission pp where p.permissionId=pp.permissionId and pp.permissionGroupId=? order by p.permissionOrder asc";

	public PagingList getSelectedPermissionByPermissionGroupId(
			HttpServletRequest request) {
		String permissionGroupId = (String) request
				.getAttribute("permissionGroupId");
		return getPagingList(SQL_GET_SELECTED_PERMISSION_BY_PERMISSIONGROUPID,
				request, new Object[] { permissionGroupId });
	}

	private static final String SQL_DELETE_PP = "delete from sys_permissiongroup_permission where permissionGroupId=? and permissionId=?";

	public void deletePermissionFromPermissionGroup(
			final String[] permissionIds, final String permissionGroupId) {
		jt.batchUpdate(SQL_DELETE_PP, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(permissionGroupId));
				ps.setInt(2, Integer.parseInt(permissionIds[i]));
			}

			@Override
			public int getBatchSize() {
				return permissionIds.length;
			}
		});
	}

	private static final String SQL_GET_PERMISSIONGROUP_UNSELECTED_FOR_ROLE_PREFIX = "select p.permissionGroupId, p.permissionGroupName from sys_permissiongroup p";
	private static final String SQL_GET_PERMISSIONGROUP_UNSELECTED_FOR_ROLE_SUFFIX = "order by p.permissionGroupOrder asc";

	public PagingList getPermissionGroupUnselectedForRole(
			HttpServletRequest request, HashSet<Object> permissionGroupIds) {
		QueryHelper helper = buildQueryConditionForRoleUnselected(request,
				permissionGroupIds);
		logger.info("{}", helper.getQuerySql());
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionForRoleUnselected(
			HttpServletRequest request, HashSet<Object> permissionGroupIds) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String permissionGroupName = params.get("permissionGroupName");
		String permissionGroupDescription = params
				.get("permissionGroupDescription");

		QueryHelper helper = new QueryHelper(
				SQL_GET_PERMISSIONGROUP_UNSELECTED_FOR_ROLE_PREFIX,
				SQL_GET_PERMISSIONGROUP_UNSELECTED_FOR_ROLE_SUFFIX);
		helper.setParam(StringUtils.isNotEmpty(permissionGroupName),
				"p.permissionGroupName like concat('%',?,'%')",
				permissionGroupName);
		helper.setParam(StringUtils.isNotEmpty(permissionGroupDescription),
				"p.permissionGroupDescription like concat('%',?,'%')",
				permissionGroupDescription);

		if (permissionGroupIds.size() > 0) {
			SqlHelper sqlHelper = new SqlHelper();
			String notIn = sqlHelper.buildWhereNotIn("p.permissionGroupId",
					permissionGroupIds);
			helper.setParam(true, notIn);
		}

		return helper;
	}
}
