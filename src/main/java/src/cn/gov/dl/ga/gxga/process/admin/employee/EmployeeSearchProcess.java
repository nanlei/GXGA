package cn.gov.dl.ga.gxga.process.admin.employee;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmployeeService;

public class EmployeeSearchProcess extends Process {
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList employees = employeeService.searchEmployee(request);

		model.put("total", employees.getRowCount());
		model.put("data", employees.getList());

		return new Result(this.getSuccessView(), model);
	}

}
