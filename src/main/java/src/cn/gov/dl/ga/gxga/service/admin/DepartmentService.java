package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
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

public class DepartmentService extends BaseService {
	private static final String SQL_SEARCH_DEPARTMENT_PREFIX = "select d.departmentId, d.departmentName, dt.constantName as departmentType, d.departmentCode, u.departmentName as upperDepartmentName, case when d.isLeaf='Y' then '是' else '否' end isLeaf, d.departmentOrder from hr_department d, sys_constant dt, hr_department u";
	private static final String SQL_SEARCH_DEPARTMENT_SUFFIX = "order by ";

	public PagingList searchDepartment(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String departmentName = params.get("departmentName");
		String departmentType = params.get("departmentType");
		String departmentCode = params.get("departmentCode");
		String upperId = params.get("upperId");
		String isLeaf = params.get("isLeaf");
		String departmentOrder = params.get("departmentOrder");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "departmentOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_DEPARTMENT_PREFIX,
				SQL_SEARCH_DEPARTMENT_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "d.upperId=u.departmentId");
		helper.setParam(true,
				"d.departmentType=dt.constantValue and dt.constantType='DEPARTMENTTYPE'");
		helper.setParam(StringUtils.isNotEmpty(departmentName),
				"d.departmentName like concat('%',?,'%')", departmentName);
		helper.setParam(StringUtils.isNotEmpty(departmentType),
				"d.departmentType = ?", departmentType);
		helper.setParam(StringUtils.isNotEmpty(departmentCode),
				"d.departmentCode like concat('%',?,'%')", departmentCode);
		helper.setParam(StringUtils.isNotEmpty(upperId), "d.upperId = ?", upperId);
		helper.setParam(StringUtils.isNotEmpty(isLeaf), "d.isLeaf = ?", isLeaf);
		helper.setParam(StringUtils.isNotEmpty(departmentOrder),
				"d.departmentOrder like concat('%',?,'%')", departmentOrder);

		return helper;
	}

	private static final String SQL_GET_UPPER_DEPARTMENT = "select departmentId as id, case when departmentId=1 then 'base' else concat('',upperId) end upperId, departmentName as text from hr_department order by departmentOrder";

	public List<Map<String, Object>> getUpperDepartment() {
		return jt.queryForList(SQL_GET_UPPER_DEPARTMENT);
	}

	public int addDepartment(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"hr_department").usingGeneratedKeyColumns("departmentId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String departmentName = objectMap.get("departmentName");
		String departmentType = objectMap.get("departmentType");
		String departmentCode = objectMap.get("departmentCode");
		String upperId = objectMap.get("upperId");
		String isLeaf = objectMap.get("isLeaf");
		String departmentOrder = objectMap.get("departmentOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("departmentName", departmentName);
		parameters.put("departmentType", departmentType);
		parameters.put("departmentCode", departmentCode);
		parameters.put("upperId", upperId);
		parameters.put("isLeaf", isLeaf);
		parameters.put("departmentOrder", departmentOrder);

		return parameters;
	}

	private static final String SQL_GET_DEPARTMENT_BY_ID = "select * from hr_department where departmentId=?";

	public HashMap<String, Object> getDepartmentById(String departmentId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_DEPARTMENT_BY_ID, departmentId);
	}

	private static final String SQL_UPDATE_DEPARTMENT = "update hr_department set departmentName=?, departmentType=?, departmentCode=?, upperId=?, isLeaf=?, departmentOrder=? where departmentId=?";

	public int updateDepartment(Object[] parameters) {
		return jt.update(SQL_UPDATE_DEPARTMENT, parameters);
	}

	private static final String SQL_DELETE_DEPARTMENT = "delete from hr_department where departmentId=?";

	public void deleteDepartment(final String[] departmentIds) {
		jt.batchUpdate(SQL_DELETE_DEPARTMENT,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(departmentIds[i]));
					}

					@Override
					public int getBatchSize() {
						return departmentIds.length;
					}
				});
	}
}
