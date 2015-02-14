package cn.gov.dl.ga.gxga.process.admin.category;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.CategoryService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class CategorySearchProcess extends Process {
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String condition = request.getParameter("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String categoryName = params.get("categoryName");

		PagingList categories = categoryService.searchCategory(request,
				categoryName);

		model.put("total", categories.getRowCount());
		model.put("data", categories.getList());

		return new Result(this.getSuccessView(), model);
	}

}
