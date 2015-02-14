package cn.gov.dl.ga.gxga.process.admin.category;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.CategoryService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class CategoryCreateProcess extends Process {
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String categoryName = params.get("categoryName");
		String categoryOrder = params.get("categoryOrder");

		Object[] parameters = new Object[] { categoryName, categoryOrder };

		categoryService.addCategory(parameters);
		
		SystemCache.getInstants().refresh(Constant.CACHE_CATEGORY);
		
		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
