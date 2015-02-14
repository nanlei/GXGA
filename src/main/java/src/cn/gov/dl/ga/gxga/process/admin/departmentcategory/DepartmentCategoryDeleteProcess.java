package cn.gov.dl.ga.gxga.process.admin.departmentcategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentCategoryService;

public class DepartmentCategoryDeleteProcess extends Process {
	private DepartmentCategoryService departmentCategoryService;

	public void setDepartmentCategoryService(
			DepartmentCategoryService departmentCategoryService) {
		this.departmentCategoryService = departmentCategoryService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] dcIds = ((String) request.getAttribute("dcIds"))
				.split(",");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		final int departmentId = (Integer) loginUser.get("departmentId");

		if (departmentId == 0) {
			departmentCategoryService.deleteCategoryById(dcIds);
		} else {
			List<Map<String, Object>> dcList = departmentCategoryService
					.getCategoryByIdAndDepartmentId(dcIds, departmentId);
			String[] ids = new String[] {};
			for (int i = 0; i < dcList.size(); i++) {
				ids[i] = (String) dcList.get(i).get("dcId");
			}
			departmentCategoryService.deleteCategoryById(ids);
		}

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
