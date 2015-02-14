package cn.gov.dl.ga.gxga.model;

public class CategoryItem {
	private int categoryItemId;
	private String categoryItemName;
	private String categoryItemURL;
	private int categoryItemOrder;
	private int categoryId;

	public int getCategoryItemId() {
		return categoryItemId;
	}

	public void setCategoryItemId(int categoryItemId) {
		this.categoryItemId = categoryItemId;
	}

	public String getCategoryItemName() {
		return categoryItemName;
	}

	public void setCategoryItemName(String categoryItemName) {
		this.categoryItemName = categoryItemName;
	}

	public String getCategoryItemURL() {
		return categoryItemURL;
	}

	public void setCategoryItemURL(String categoryItemURL) {
		this.categoryItemURL = categoryItemURL;
	}

	public int getCategoryItemOrder() {
		return categoryItemOrder;
	}

	public void setCategoryItemOrder(int categoryItemOrder) {
		this.categoryItemOrder = categoryItemOrder;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
