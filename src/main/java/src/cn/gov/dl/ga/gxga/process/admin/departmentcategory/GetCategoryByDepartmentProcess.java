package cn.gov.dl.ga.gxga.process.admin.departmentcategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentCategoryService;

public class GetCategoryByDepartmentProcess extends Process {
	private DepartmentCategoryService departmentCategoryService;

	public void setDepartmentCategoryService(
			DepartmentCategoryService departmentCategoryService) {
		this.departmentCategoryService = departmentCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentId = (String) request.getAttribute("departmentId");

		List<Map<String, Object>> categories = departmentCategoryService
				.getCategoryByDepartment(departmentId);

		model.put("data", categories);

		return new Result(this.getSuccessView(), model);
	}

}
