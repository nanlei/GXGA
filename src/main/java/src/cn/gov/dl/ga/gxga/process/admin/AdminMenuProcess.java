package cn.gov.dl.ga.gxga.process.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.Category;
import cn.gov.dl.ga.gxga.model.CategoryItem;
import cn.gov.dl.ga.gxga.service.admin.MenuService;

public class AdminMenuProcess extends Process {
	private MenuService menuService;

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> user = (Map<String, Object>) request.getSession()
				.getAttribute(Constant.LOGIN_USER);

		String userId = String.valueOf(user.get("userId"));

		List<Category> categories = menuService.getCategories(userId);

		List<CategoryItem> permissions = menuService.getPermissions(userId);

		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);

			int categoryId = category.getCategoryId();

			List<CategoryItem> items = new ArrayList<CategoryItem>();

			for (int j = 0; j < permissions.size(); j++) {
				CategoryItem categoryItem = permissions.get(j);

				if (categoryId == categoryItem.getCategoryId()) {
					items.add(categoryItem);
				}

			}

			category.setCategoryItems(items);

		}

		model.put("menus", categories);

		return new Result(this.getSuccessView(), model);
	}
}
