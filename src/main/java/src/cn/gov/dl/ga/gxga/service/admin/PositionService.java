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

public class PositionService extends BaseService {
	private static final String SQL_SEARCH_POSITION_PREFIX = "select positionId, positionName, pc.constantName as positionCategory, pt.constantName as positionType, positionCode, positionOrder from hr_position p, sys_constant pc, sys_constant pt ";
	private static final String SQL_SEARCH_POSITION_SUFFIX = "order by ";

	public PagingList searchPosition(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String positionName = params.get("positionName");
		String positionCategory = params.get("positionCategory");
		String positionType = params.get("positionType");
		String positionCode = params.get("positionCode");
		String positionOrder = params.get("positionOrder");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "positionOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_POSITION_PREFIX,
				SQL_SEARCH_POSITION_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(
				true,
				"p.positionCategory=pc.constantValue and p.positionType=pt.constantValue and pc.constantType='POSITIONCATEGORY' and pt.constantType='POSITIONTYPE'");
		helper.setParam(StringUtils.isNotEmpty(positionName),
				"positionName like concat('%',?,'%')", positionName);
		helper.setParam(StringUtils.isNotEmpty(positionCategory),
				"positionCategory like concat('%',?,'%')", positionCategory);
		helper.setParam(StringUtils.isNotEmpty(positionType),
				"positionType like concat('%',?,'%')", positionType);
		helper.setParam(StringUtils.isNotEmpty(positionCode),
				"positionCode like concat('%',?,'%')", positionCode);
		helper.setParam(StringUtils.isNotEmpty(positionOrder),
				"positionOrder like concat('%',?,'%')", positionOrder);

		return helper;
	}

	public int addPosition(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"hr_position").usingGeneratedKeyColumns("positionId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String positionName = objectMap.get("positionName");
		String positionCategory = objectMap.get("positionCategory");
		String positionType = objectMap.get("positionType");
		String positionCode = objectMap.get("positionCode");
		String positionOrder = objectMap.get("positionOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("positionName", positionName);
		parameters.put("positionCategory", positionCategory);
		parameters.put("positionType", positionType);
		parameters.put("positionCode", positionCode);
		parameters.put("positionOrder", positionOrder);

		return parameters;
	}

	private static final String SQL_GET_POSITION_BY_ID = "select * from hr_position where positionId=?";

	public HashMap<String, Object> getPositionById(String positionId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_POSITION_BY_ID,
				positionId);
	}

	private static final String SQL_UPDATE_POSITION = "update hr_position set positionName=?, positionCategory=?, positionType=?, positionCode=?, positionOrder=? where positionId=?";

	public int updatePosition(Object[] parameters) {
		return jt.update(SQL_UPDATE_POSITION, parameters);
	}

	private static final String SQL_DELETE_POSITION = "delete from hr_position where positionId=?";

	public void deletePosition(final String[] positionIds) {
		jt.batchUpdate(SQL_DELETE_POSITION, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(positionIds[i]));
			}

			@Override
			public int getBatchSize() {
				return positionIds.length;
			}
		});
	}
}
