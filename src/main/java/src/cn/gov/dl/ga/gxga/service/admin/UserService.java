package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.MD5;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class UserService extends BaseService {
	private static final String SQL_USER_LOGON = "select userId, userName, realName, password, u.roleId, r.roleName, isLock, isEmployee, employeeId from sys_user u, sys_role r where u.roleId=r.roleId and userName=?";

	public Map<String, Object> checkLogon(String userName) {
		try {
			return jt.queryForMap(SQL_USER_LOGON, userName);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private static final String SQL_USER_UPDATE_LOGON = "update sys_user set lastLoginIP=?, lastLoginTime=now() where userId=?";

	public void updateUserLogon(Object[] parameters) {
		jt.update(SQL_USER_UPDATE_LOGON, parameters);
	}

	private static final String SQL_SEARCH_USER_PREFIX = "select u.userId, u.userName, u.realName, u.bindIP, u.lastLoginIP, date_format(u.lastLoginTime,'%Y-%m-%d %H:%i:%s') as lastLoginTime, r.roleName, case when isLock='Y' then '是' else '否' end isLock, case when isEmployee='Y' then '是' else '否' end isEmployee, case when isSignRole='Y' then '是' else '否' end isSignRole, userOrder from sys_user u, sys_role r";
	private static final String SQL_SEARCH_USER_SUFFIX = "order by ";

	public PagingList searchUser(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String userName = params.get("userName");
		String realName = params.get("realName");
		String isLock = params.get("isLock");
		String isSignRole = params.get("isSignRole");
		String bindIP = params.get("bindIP");
		String roleId = params.get("roleId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "userOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_USER_PREFIX,
				SQL_SEARCH_USER_SUFFIX + sortField + " " + sortOrder);
		helper.setParam(true, "u.roleId=r.roleId");
		helper.setParam(StringUtils.isNotEmpty(userName),
				"u.userName like concat('%',?,'%')", userName);
		helper.setParam(StringUtils.isNotEmpty(realName),
				"u.realName like concat('%',?,'%')", realName);
		helper.setParam(StringUtils.isNotEmpty(isLock), "u.isLock=?", isLock);
		helper.setParam(StringUtils.isNotEmpty(isSignRole), "u.isSignRole=?",
				isSignRole);
		helper.setParam(StringUtils.isNotEmpty(bindIP), "u.bindIP=?", bindIP);
		helper.setParam(StringUtils.isNotEmpty(roleId), "u.roleId=?", roleId);

		return helper;
	}

	private static final String SQL_CHECK_USERNAME = "select count(*) from sys_user where userName=?";

	public int checkUserName(String userName) {
		return jt.queryForObject(SQL_CHECK_USERNAME, new Object[] { userName },
				Integer.class);
	}

	// private static final String SQL_INSERT_USER =
	// "insert into sys_user(userName, realName, password, bindIP, userOrder, roleId, isLock) values(?,?,?,?,?,?,?)";

	public int addUser(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildUserInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"sys_user").usingGeneratedKeyColumns("userId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private static final String SQL_UPDATE_USER_EMPLOYEEID = "update sys_user set employeeId=? where userId=?";

	public void addUserAndEmployee(HttpServletRequest request) {
		// add user
		HashMap<String, Object> parametersUser = buildUserInsertCondition(request);
		SimpleJdbcInsert insertUser = new SimpleJdbcInsert(jt).withTableName(
				"sys_user").usingGeneratedKeyColumns("userId");
		Number idUser = insertUser.executeAndReturnKey(parametersUser);
		int userId = idUser.intValue();

		String realName = (String) parametersUser.get("realName");

		// add employee
		HashMap<String, Object> parametersEmployee = buildEmployeeInsertCondition(
				userId, realName);
		SimpleJdbcInsert insertEmployee = new SimpleJdbcInsert(jt)
				.withTableName("hr_employee").usingGeneratedKeyColumns(
						"employeeId");
		Number idEmployee = insertEmployee
				.executeAndReturnKey(parametersEmployee);
		int employeeId = idEmployee.intValue();

		// update user
		jt.update(SQL_UPDATE_USER_EMPLOYEEID, employeeId, userId);

	}

	private HashMap<String, Object> buildEmployeeInsertCondition(int userId,
			String employeeName) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);
		parameters.put("employeeName", employeeName);
		parameters.put("departmentId", 0);
		return parameters;
	}

	private HashMap<String, Object> buildUserInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String userName = objectMap.get("userName");
		String realName = objectMap.get("realName");
		String bindIP = objectMap.get("bindIP");
		String userOrder = objectMap.get("userOrder");
		String roleId = objectMap.get("roleId");
		String isLock = objectMap.get("isLock");
		String isSignRole = objectMap.get("isSignRole");
		String isEmployee = objectMap.get("isEmployee");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", userName);
		parameters.put("realName", realName);
		parameters.put("password", MD5.encode("123"));
		parameters.put("bindIP", bindIP);
		parameters.put("userOrder", userOrder);
		parameters.put("roleId", roleId);
		parameters.put("isLock", isLock);
		parameters.put("isSignRole", isSignRole);
		parameters.put("isEmployee", isEmployee);

		return parameters;
	}

	private static final String SQL_GET_USER_BY_ID = "select * from sys_user where userId=?";

	public HashMap<String, Object> getUserById(String userId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_USER_BY_ID,
				userId);
	}

	private static final String SQL_UPDATE_USER = "update sys_user set realName=?, bindIP=?, userOrder=?, roleId=?, isLock=?, isSignRole=? where userId=?";

	public int updateUser(Object[] parameters) {
		return jt.update(SQL_UPDATE_USER, parameters);
	}

	private static final String SQL_DELETE_USER = "delete from sys_user where userId=?";
	private static final String SQL_DELETE_EMPLOYEE_BY_USERID = "delete from hr_employee where userId=?";

	public void deleteUser(final String[] userIds) {
		jt.batchUpdate(SQL_DELETE_USER, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(userIds[i]));
			}

			@Override
			public int getBatchSize() {
				return userIds.length;
			}
		});

		jt.batchUpdate(SQL_DELETE_EMPLOYEE_BY_USERID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(userIds[i]));
					}

					@Override
					public int getBatchSize() {
						return userIds.length;
					}
				});
	}

	private static final String SQL_RESET_PASSOWRD = "update sys_user set password=? where userId=?";

	public int updatePassword(Object[] parameters) {
		return jt.update(SQL_RESET_PASSOWRD, parameters);
	}
}
