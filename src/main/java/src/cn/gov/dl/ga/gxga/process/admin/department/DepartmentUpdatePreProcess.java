package cn.gov.dl.ga.gxga.process.admin.department;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentService;

public class DepartmentUpdatePreProcess extends Process {
	private DepartmentService departmentService;

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentId = (String) request.getAttribute("departmentId");

		HashMap<String, Object> department = departmentService
				.getDepartmentById(departmentId);

		model.put("department", department);

		return new Result(this.getSuccessView(), model);
	}

}
