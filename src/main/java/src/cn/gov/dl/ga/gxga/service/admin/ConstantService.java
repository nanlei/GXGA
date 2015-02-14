package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class ConstantService extends BaseService {
	private static final String SQL_SEARCH_CONSTANT_PREFIX = "select constantId, constantType, constantValue, constantName, constantOrder, case when isLock='Y' then '是' else '否' end isLock from sys_constant";
	private static final String SQL_SEARCH_CONSTANT_SUFFIX = "order by ";

	public PagingList searchConstant(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String constantType = params.get("constantType");
		String constantValue = params.get("constantValue");
		String constantName = params.get("constantName");
		String isLock = params.get("isLock");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_CONSTANT_PREFIX,
				SQL_SEARCH_CONSTANT_SUFFIX + sortField + " " + sortOrder);
		helper.setParam(StringUtils.isNotEmpty(constantType),
				"constantType like concat('%',?,'%')", constantType);
		helper.setParam(StringUtils.isNotEmpty(constantValue),
				"constantValue like concat('%',?,'%')", constantValue);
		helper.setParam(StringUtils.isNotEmpty(constantName),
				"constantName like concat('%',?,'%')", constantName);
		helper.setParam(StringUtils.isNotEmpty(isLock), "isLock=?", isLock);

		return helper;
	}

	public int addConstant(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"sys_constant").usingGeneratedKeyColumns("constantId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String constantType = objectMap.get("constantType");
		String constantValue = objectMap.get("constantValue");
		String constantName = objectMap.get("constantName");
		String constantOrder = objectMap.get("constantOrder");
		String isLock = objectMap.get("isLock");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("constantType", constantType);
		parameters.put("constantValue", constantValue);
		parameters.put("constantName", constantName);
		parameters.put("constantOrder", constantOrder);
		parameters.put("isLock", isLock);

		return parameters;
	}

	private static final String SQL_GET_CONSTANT_BY_ID = "select * from sys_constant where constantId=?";

	public HashMap<String, Object> getConstantById(String constantId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_CONSTANT_BY_ID,
				constantId);
	}

	private static final String SQL_UPDATE_CONSTANT = "update sys_constant set constantType=?, constantValue=?, constantName=?, constantOrder=?, isLock=? where constantId=?";

	public int updateConstant(Object[] parameters) {
		return jt.update(SQL_UPDATE_CONSTANT, parameters);
	}

	private static final String SQL_DELETE_CONSTANT = "delete from sys_constant where constantId=?";

	public void deleteConstant(final String[] constantIds) {
		jt.batchUpdate(SQL_DELETE_CONSTANT, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(constantIds[i]));
			}

			@Override
			public int getBatchSize() {
				return constantIds.length;
			}
		});
	}

	// Extend
	private static final String SQL_GET_CONSTANTNAME_BY_TYPE_AND_VALUE = "select distinct constantName from sys_constant where constantType=? and constantValue=?";

	public String getConstantNameByTypeAndValue(String constantType,
			String constantValue) {
		return jt.queryForObject(SQL_GET_CONSTANTNAME_BY_TYPE_AND_VALUE,
				new Object[] { constantType, constantValue }, String.class);
	}
}
