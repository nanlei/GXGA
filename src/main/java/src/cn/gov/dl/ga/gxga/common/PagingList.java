package cn.gov.dl.ga.gxga.common;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cn.gov.dl.ga.gxga.core.Constant;

/**
 * Spring MVC形分页
 * 
 * @author Nan Lei
 * 
 */
public class PagingList {
	private static String DBTYPE = "ORACLE"; // 数据库类型

	static {
		try {
			ResourceBundle res = ResourceBundle.getBundle("jdbc");
			String driver = res.getString("jdbc.driverClassName").trim();
			if (driver != null && driver.indexOf("mysql") != -1) {
				DBTYPE = "MYSQL";
			}
		} catch (Exception e) {
			System.err.println("PagingList read jdbc.properties error: "
					+ e.toString());
		}
	}

	private int rowCount = 0; // 记录总数
	private int pageCount = 1; // 分页总数
	private int pageSize = Constant.DEFAULT_PAGE_SIZE; // 每页记录数10
	private int defaultPageSize = Constant.DEFAULT_PAGE_SIZE; // 每页默认记录数，为分页宏提供数据支持
	private int maxPageSize = Constant.MAX_PAGE_SIZE;// 每页最大记录数1000
	private int pageNum = 1; // 当前页数
	private int startIndex = 1; // 起始记录数
	private int endIndex = 1; // 结束记录数
	private String srcSqlForCount;// 用于计算记录总量的SQL语句
	private String srcSql;// 源SQL语句
	private List<Map<String, Object>> list;// 记录列表

	public PagingList(String srcSql, int pageNum, int pageSize,
			JdbcTemplate jdbcTemplate, Object... params) {
		this(getCountSql(srcSql), srcSql, pageNum, pageSize, jdbcTemplate,
				params);
	}

	public PagingList(String srcSql, Map<String, Object> namedParams,
			int pageNum, int pageSize,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this(getCountSql(srcSql), srcSql, namedParams, pageNum, pageSize,
				namedParameterJdbcTemplate);
	}

	public PagingList(String srcSqlForCount, String srcSql, int pageNum,
			int pageSize, JdbcTemplate jdbcTemplate, Object... params) {
		preProcessParams(srcSqlForCount, srcSql, pageNum, pageSize);
		execute(jdbcTemplate, params);
	}

	public PagingList(String srcSqlForCount, String srcSql,
			Map<String, Object> namedParams, int pageNum, int pageSize,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		preProcessParams(srcSqlForCount, srcSql, pageNum, pageSize);
		execute(namedParameterJdbcTemplate, namedParams);
	}

	/**
	 * 预处理SQL语句和页面参数
	 */
	private void preProcessParams(String srcSqlForCount, String srcSql,
			int pageNum, int pageSize) {
		this.srcSqlForCount = srcSqlForCount;
		this.srcSql = srcSql;
		if (pageNum > 0) {
			this.pageNum = pageNum;
		}
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
		if (pageSize > Constant.MAX_PAGE_SIZE) {
			this.pageSize = Constant.MAX_PAGE_SIZE;
		}
	}

	/**
	 * 根据给定的数据计算相关分页信息
	 */
	private void execute(JdbcTemplate jdbcTemplate, Object[] params) {
		// 计算记录总数
		this.rowCount = jdbcTemplate.queryForObject(srcSqlForCount, params,
				Integer.class);

		// 计算分页数及起止记录
		countPage();

		Object[] tmp = null;
		if (params != null) {
			int pLen = params.length;
			tmp = new Object[pLen + 2];
			System.arraycopy(params, 0, tmp, 0, pLen);
			if (DBTYPE.equals("MYSQL")) {
				tmp[pLen] = new Integer(getStartIndex() - 1);
				tmp[pLen + 1] = new Integer(pageSize);
			} else {
				tmp[pLen] = getEndIndexInteger();
				tmp[pLen + 1] = getStartIndexInteger();
			}
		} else {
			if (DBTYPE.equals("MYSQL")) {
				tmp = new Object[] { new Integer(getStartIndex() - 1),
						new Integer(pageSize) };
			} else {
				tmp = new Object[] { getEndIndexInteger(),
						getStartIndexInteger() };
			}
		}
		this.list = jdbcTemplate
				.queryForList(
						getPagingSql(srcSql, Constant.NORMAL_MARK,
								Constant.NORMAL_MARK), tmp);
	}

	private void execute(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			Map<String, Object> namedParams) {
		// 计算记录总数
		this.rowCount = namedParameterJdbcTemplate.queryForObject(
				srcSqlForCount, namedParams, Integer.class);

		// 计算分页数及起止记录
		countPage();

		// 给namedParams对象增加起止参数
		if (DBTYPE.equals("MYSQL")) {// MySQL和Oracle参数不同
			namedParams.put(Constant.START_MARK, new Integer(
					getStartIndex() - 1));
			namedParams.put(Constant.END_MARK, new Integer(pageSize));
		} else {
			namedParams.put(Constant.START_MARK, getStartIndexInteger());
			namedParams.put(Constant.END_MARK, getEndIndexInteger());
		}
		this.list = namedParameterJdbcTemplate.queryForList(
				getPagingSql(srcSql, Constant.START_MARK, Constant.END_MARK),
				namedParams);
	}

	/**
	 * 计算分页数及起止记录
	 */
	private void countPage() {
		// 计算分页总数
		if ((rowCount % pageSize) == 0) {
			pageCount = rowCount / pageSize;
		} else {
			pageCount = rowCount / pageSize + 1;
		}
		if (pageCount == 0) {
			pageCount = 1;
		}

		// 判断pageNum是否过界
		if (pageNum > pageCount && rowCount != 0) {
			pageNum = pageCount;
		}

		// 计算起止记录
		startIndex = (pageNum - 1) * pageSize + 1;
		endIndex = (pageNum) * pageSize;
	}

	/**
	 * 获得对象列表
	 */
	public List<Map<String, Object>> getList() {
		return list;
	}

	/* 获得起始记录数 */
	public int getStartIndex() {
		return startIndex;
	}

	public Integer getStartIndexInteger() {
		return new Integer(startIndex);
	}

	/* 获得结束记录数 */
	public int getEndIndex() {
		return endIndex;
	}

	public Integer getEndIndexInteger() {
		return new Integer(endIndex);
	}

	/* 获得分页其它信息 */
	public int getPageCount() {
		return pageCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getMaxPageSize() {
		return maxPageSize;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 将源SQL语句增加分页功能
	 */
	private static String getPagingSql(String srcSql, String startMark,
			String endMark) {
		StringBuffer pagingSql = new StringBuffer();

		// 如果是MYSQL采用特殊方式处理
		if (DBTYPE.equals("MYSQL")) {
			pagingSql.append(srcSql);
			pagingSql.append(" LIMIT ");
			pagingSql.append(startMark);
			pagingSql.append(", ");
			pagingSql.append(endMark);
			return pagingSql.toString();
		}

		pagingSql.append("SELECT * FROM ( SELECT TBL_.*, ROWNUM R_ FROM ( ");
		pagingSql.append(srcSql);
		// 根据是否有order by子句选择不同的rownum截断方式
		if (srcSql.toLowerCase().indexOf("order by") == -1) {
			pagingSql.append(" ) TBL_ WHERE ROWNUM <= ");
			pagingSql.append(endMark);
			pagingSql.append(" ) WHERE R_ >= ");
			pagingSql.append(startMark);
		} else {
			pagingSql.append(" ) TBL_ ) WHERE R_ <= ");
			pagingSql.append(endMark);
			pagingSql.append(" AND R_ >= ");
			pagingSql.append(startMark);
		}
		return pagingSql.toString();
	}

	/**
	 * 将源SQL语句增加计算记录总数功能
	 */
	public static String getCountSql(String srcSql) {
		return "SELECT COUNT(*) FROM ( " + srcSql + " ) CTBL_";
	}
}