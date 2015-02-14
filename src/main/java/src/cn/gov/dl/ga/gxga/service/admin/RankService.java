package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class RankService extends BaseService {
	private static final String SQL_SEARCH_RANK_PREFIX = "select r.rankId, d.departmentName, r.rankValue, r.rankOrder, r.createBy, r.createByName, date_format(r.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, r.createByIP from fun_rank r, hr_department d ";
	private static final String SQL_SEARCH_RANK_SUFFIX = "order by ";

	public PagingList searchRank(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "rankOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		String departmentId = params.get("departmentId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_RANK_PREFIX,
				SQL_SEARCH_RANK_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "r.departmentId=d.departmentId");
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"r.departmentId=?", departmentId);

		return helper;
	}

	private static final String SQL_CHECK_RANK_DEPARTMENT = "select count(*) from fun_rank where departmentId=?";

	public int checkRankDepartment(String departmentId) {
		return jt.queryForObject(SQL_CHECK_RANK_DEPARTMENT,
				new Object[] { departmentId }, Integer.class);
	}

	public int createRank(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_rank").usingGeneratedKeyColumns("rankId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String departmentId = params.get("departmentId");
		String rankValue = params.get("rankValue");
		String rankOrder = params.get("rankOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("departmentId", departmentId);
		parameters.put("rankValue", rankValue);
		parameters.put("rankOrder", rankOrder);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_RANK_BY_ID = "select * from fun_rank where rankId=?";

	public HashMap<String, Object> getRankById(String rankId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_RANK_BY_ID,
				rankId);
	}

	private static final String SQL_UPDATE_RANK_BY_ID = "update fun_rank set rankValue=?, rankOrder=? where rankId=?";

	public int updateRank(Object[] parameters) {
		return jt.update(SQL_UPDATE_RANK_BY_ID, parameters);
	}

	private static final String SQL_DELETE_RANK_BY_ID = "delete from fun_rank where rankId=?";

	public void deleteRankById(final String[] rankIds) {
		jt.batchUpdate(SQL_DELETE_RANK_BY_ID,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(rankIds[i]));
					}

					@Override
					public int getBatchSize() {
						return rankIds.length;
					}
				});
	}
}
