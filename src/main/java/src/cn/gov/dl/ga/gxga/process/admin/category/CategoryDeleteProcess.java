package cn.gov.dl.ga.gxga.process.admin.category;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.CategoryService;

public class CategoryDeleteProcess extends Process {
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] categoryIds = ((String) request
				.getAttribute("categoryIds")).split(",");

		categoryService.deleteCategory(categoryIds);

		model.put("status", "true");
		
		SystemCache.getInstants().refresh(Constant.CACHE_CATEGORY);

		return new Result(this.getSuccessView(), model);
	}

}
