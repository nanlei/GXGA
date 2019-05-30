package cn.gov.dl.ga.gxga.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class EmployeeService extends BaseService {
	private static final String SQL_SEARCH_EMPLOYEE_PREFIX = "select u.userName, e.employeeId, e.departmentId, d.departmentName, e.employeeOrder, e.employeeName, e.employeeGender, e.employeeIdNo, e.employeePoliceNo, case when isDeptAdmin='Y' then '是' else '否' end isDeptAdmin from hr_employee e, sys_user u, hr_department d ";
	private static final String SQL_SEARCH_EMPLOYEE_SUFFIX = "order by ";

	public PagingList searchEmployee(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String employeeName = params.get("employeeName");
		// String employeeGender = params.get("employeeGender");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "departmentOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_EMPLOYEE_PREFIX,
				SQL_SEARCH_EMPLOYEE_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "e.departmentId=d.departmentId");
		helper.setParam(true, "e.userId=u.userId");
		helper.setParam(StringUtils.isNotEmpty(employeeName),
				"employeeName like concat('%',?,'%')", employeeName);

		return helper;
	}

	private static final String SQL_GET_EMPLOYEE_BY_ID = "select e.*, u.userName from hr_employee e, sys_user u where e.userId=u.userId and e.employeeId=?";

	public HashMap<String, Object> getEmployeeById(String employeeId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_EMPLOYEE_BY_ID,
				employeeId);
	}

	private static final String SQL_UPDATE_EMPLOYEE = "update hr_employee set departmentId=?, employeeOrder=?, employeeName=?, employeeGender=?, employeeIdNo=?, employeePoliceNo=?, employeeBirth=?, employeePolitical=?, employeeNationality=?, employeeIdentity=?, employeeAddress=?, employeeFamily=?, employeeEducation=?, employeeSchool=?, employeeDegree=?, employeeMajor=?, employeeHighEducation=?, employeeHighSchool=?, employeeHighDegree=?, employeeHighMajor=?, employeeJobTitle=?, employeeOtherCertificate=?, employeePosition=?, employeePositionLevel=?, employeeStartTime=?, employeeJob=?, employeeWorkStartTime=?, employeePoliceStartTime=?, employeePoliceSpecialty=?, employeeOtherSpecialty=?, employeeRewards=?, isDeptAdmin=? where employeeId=?";

	public int updateEmployee(Object[] parameters) {
		return jt.update(SQL_UPDATE_EMPLOYEE, parameters);
	}

	// Extend
	private static final String SQL_GET_DEPARTMENTID_BY_EMPLOYEEID = "select departmentId from hr_employee where employeeId=?";

	public int getDepartmentIdByEmployeeId(int employeeId) {
		return jt.queryForObject(SQL_GET_DEPARTMENTID_BY_EMPLOYEEID,
				new Object[] { employeeId }, Integer.class);
	}

	private static final String SQL_GET_EMPLOYEES_BY_DEPARTMENTID = "select userId as id, employeeName as text from hr_employee where departmentId=?";

	public List<Map<String, Object>> getEmployeesByDepartmentId(
			String departmentId) {
		return jt.queryForList(SQL_GET_EMPLOYEES_BY_DEPARTMENTID, departmentId);
	}
	
	private static final String SQL_GET_DEPT_ADMIN_EMPLOYEES = "select userId as id, employeeName as text from hr_employee where isDeptAdmin='Y'";
	
	public List<Map<String, Object>> getDeptAdminEmployees(){
		return jt.queryForList(SQL_GET_DEPT_ADMIN_EMPLOYEES);
	}
}
