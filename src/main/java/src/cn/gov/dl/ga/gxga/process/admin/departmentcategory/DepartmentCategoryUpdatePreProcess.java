package cn.gov.dl.ga.gxga.process.admin.departmentcategory;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentCategoryService;

public class DepartmentCategoryUpdatePreProcess extends Process {
	private DepartmentCategoryService departmentCategoryService;

	public void setDepartmentCategoryService(
			DepartmentCategoryService departmentCategoryService) {
		this.departmentCategoryService = departmentCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String dcId = (String) request.getAttribute("dcId");
		String departmentId = (String) request.getAttribute("departmentId");

		HashMap<String, Object> category = departmentCategoryService
				.getCategoryById(dcId, departmentId);

		model.put("category", category);

		return new Result(this.getSuccessView(), model);
	}

}
