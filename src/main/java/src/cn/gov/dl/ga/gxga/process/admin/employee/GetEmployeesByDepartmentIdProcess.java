package cn.gov.dl.ga.gxga.process.admin.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmployeeService;

public class GetEmployeesByDepartmentIdProcess extends Process {
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentId = (String) request.getAttribute("departmentId");

		List<Map<String, Object>> data = employeeService
				.getEmployeesByDepartmentId(departmentId);

		model.put("data", data);

		return new Result(this.getSuccessView(), model);
	}

}
