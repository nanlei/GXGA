package cn.gov.dl.ga.gxga.process.admin.departmentcategory;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentCategoryService;

public class DepartmentCategorySearchProcess extends Process {
	private DepartmentCategoryService departmentCategoryService;

	public void setDepartmentCategoryService(
			DepartmentCategoryService departmentCategoryService) {
		this.departmentCategoryService = departmentCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList categories = departmentCategoryService
				.searchCategoryByDepartmentId(request);

		model.put("total", categories.getRowCount());
		model.put("data", categories.getList());

		return new Result(this.getSuccessView(), model);
	}

}
