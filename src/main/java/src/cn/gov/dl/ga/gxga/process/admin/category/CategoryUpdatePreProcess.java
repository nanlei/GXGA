package cn.gov.dl.ga.gxga.process.admin.category;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.CategoryService;

public class CategoryUpdatePreProcess extends Process {
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String categoryId = (String) request.getAttribute("categoryId");

		HashMap<String, Object> category = categoryService
				.getCategoryById(categoryId);

		model.put("category", category);

		return new Result(this.getSuccessView(), model);
	}

}
