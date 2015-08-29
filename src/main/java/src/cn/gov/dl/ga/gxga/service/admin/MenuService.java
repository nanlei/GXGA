package cn.gov.dl.ga.gxga.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gov.dl.ga.gxga.model.Category;
import cn.gov.dl.ga.gxga.model.CategoryItem;
import cn.gov.dl.ga.gxga.service.BaseService;

public class MenuService extends BaseService {
	private static final String SQL_GET_ALL_CATEGORIES = "select distinct sys_category.categoryid, sys_category.categoryname, sys_category.categoryorder "
			+ "from sys_category,sys_permission,sys_permissiongroup_permission,sys_role_permissiongroup,sys_user "
			+ "where sys_category.categoryid=sys_permission.categoryid "
			+ "and sys_permission.permissionid=sys_permissiongroup_permission.permissionid "
			+ "and sys_permissiongroup_permission.permissiongroupid=sys_role_permissiongroup.permissiongroupid "
			+ "and sys_role_permissiongroup.roleid=sys_user.roleid "
			+ "and sys_permission.ismenu = 'Y' "
			+ "and userid = ? "
			+ "order by sys_category.categoryorder";

	public List<Category> getCategories(String userId) {
		List<Category> categories = new ArrayList<Category>();

		List<Map<String, Object>> list = jt.queryForList(
				SQL_GET_ALL_CATEGORIES, userId);

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);

			int categoryId = (Integer) map.get("categoryid");
			String categoryName = (String) map.get("categoryname");

			Category category = new Category();
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName);

			categories.add(category);
		}

		return categories;
	}

	private static final String SQL_GET_PERMISSIONS = "select distinct sys_permission.permissionid, sys_permission.categoryid, sys_permission.permissionname, sys_permission.permissionurl, sys_permission.permissionorder "
			+ "from sys_permission, sys_permissiongroup_permission, sys_role_permissiongroup, sys_user "
			+ "where sys_permission.permissionid=sys_permissiongroup_permission.permissionid "
			+ "and sys_permissiongroup_permission.permissiongroupid=sys_role_permissiongroup.permissiongroupid "
			+ "and sys_role_permissiongroup.roleid=sys_user.roleid "
			+ "and sys_permission.ismenu='Y' "
			+ "and sys_user.userid = ? "
			+ "order by sys_permission.categoryid,sys_permission.permissionorder";

	public List<CategoryItem> getPermissions(String userId) {
		List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();

		List<Map<String, Object>> list = jt.queryForList(SQL_GET_PERMISSIONS,userId);

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);

			int permissionId = (Integer) map.get("permissionid");
			int categoryId = (Integer) map.get("categoryid");
			String permissionName = (String) map.get("permissionname");
			String permissionURL = (String) map.get("permissionurl");
			int permissionOrder = (Integer) map.get("permissionorder");

			CategoryItem categoryItem = new CategoryItem();
			categoryItem.setCategoryItemId(permissionId);
			categoryItem.setCategoryItemName(permissionName);
			categoryItem.setCategoryItemURL(permissionURL);
			categoryItem.setCategoryItemOrder(permissionOrder);
			categoryItem.setCategoryId(categoryId);

			categoryItems.add(categoryItem);
		}

		return categoryItems;
	}
}
