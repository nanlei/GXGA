package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;

public class CategoryService extends BaseService {
	private static final String SQL_SEARCH_CATEGORY = "select categoryId,categoryName,categoryOrder from sys_category where categoryName like concat('%',?,'%') order by categoryOrder asc";

	public PagingList searchCategory(HttpServletRequest request,
			String categoryName) {
		return getPagingList(SQL_SEARCH_CATEGORY, request,
				new Object[] { categoryName });
	}

	private static final String SQL_INSERT_CATEGORY = "insert into sys_category(categoryName, categoryOrder) values(?,?)";

	public int addCategory(Object[] parameters) {
		return jt.update(SQL_INSERT_CATEGORY, parameters);
	}

	private static final String SQL_GET_CATEGORY_BY_ID = "select * from sys_category where categoryId=?";

	public HashMap<String, Object> getCategoryById(String categoryId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_CATEGORY_BY_ID,
				categoryId);
	}

	private static final String SQL_UPDATE_CATEGORY = "update sys_category set categoryName=?, categoryOrder=? where categoryId=?";

	public int updateCategory(Object[] parameters) {
		return jt.update(SQL_UPDATE_CATEGORY, parameters);
	}

	private static final String SQL_DELETE_CATEGORY = "delete from sys_category where categoryId=?";

	public void deleteCategory(final String[] categoryIds) {
		jt.batchUpdate(SQL_DELETE_CATEGORY, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(categoryIds[i]));
			}

			@Override
			public int getBatchSize() {
				return categoryIds.length;
			}
		});

	}
}
