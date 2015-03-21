package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

public class OvertimeMealService extends BaseService {
	private static final String SQL_SEARCH_OVERTIMEMEAL_PREFIX = "select om.omId, date_format(om.overtimeDate,'%Y-%m-%d') as overtimeDate, d.departmentName, om.sts, c.constantName as statusName, createByName, date_format(om.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, om.createByIP, om.feedbackByName, date_format(om.feedbackByTime,'%Y-%m-%d %H:%i:%s') as feedbackByTime, om.feedbackByIP from oa_overtime_meal om, hr_department d, sys_constant c ";
	private static final String SQL_SEARCH_OVERTIMEMEAL_SUFFIX = "order by ";

	public PagingList searchOvertimeMeal(HttpServletRequest request)
			throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	@SuppressWarnings("unchecked")
	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String departmentId = params.get("departmentId");
		String overtimeDate = params.get("overtimeDate").replaceAll("T", " ");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "omId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;
		
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int roleId = (Integer) loginUser.get("roleId");
		int user_departmentId = (Integer) loginUser.get("departmentId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_OVERTIMEMEAL_PREFIX,
				SQL_SEARCH_OVERTIMEMEAL_SUFFIX + sortField + " " + sortOrder);

		if (roleId != 1) {// Not Super Admin
			helper.setParam(true, "om.departmentId=?", user_departmentId);
		}
		helper.setParam(true, "om.departmentId=d.departmentId");
		helper.setParam(true,
				"om.sts=c.constantValue and c.constantType='OVERTIMEMEALSTATUS'");
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"om.departmentId=?", departmentId);

		if (StringUtils.isNotEmpty(overtimeDate)) {
			helper.setParam(true, "date_format(om.overtimeDate,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(overtimeDate)));
		}

		return helper;
	}

	public int createOvertimeMeal(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"oa_overtime_meal").usingGeneratedKeyColumns("omId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String departmentId = params.get("departmentId");
		String remark = params.get("remark");
		String overtimeDate = params.get("overtimeDate").replaceAll("T", " ");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		Date d_overtimeMealDate = new SimpleDateFormat("yyyy-MM-dd")
				.parse(overtimeDate);
		parameters.put("overtimeDate",
				new Timestamp(d_overtimeMealDate.getTime()));
		parameters.put("departmentId", departmentId);
		parameters.put("remark", remark);
		parameters.put("sts", Constant.STS_NEW);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_OVERTIME_MEAL_BY_ID = "select * from oa_overtime_meal where omId=?";

	public HashMap<String, Object> getOvertimeMealById(String omId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_OVERTIME_MEAL_BY_ID, omId);
	}

	private static final String SQL_UPDATE_OVERTIME_MEAL_BY_ID = "update oa_overtime_meal set departmentId=?, overtimeDate=?, remark=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where omId=?";

	public int updateOvertimeMealById(Object[] parameters) {
		return jt.update(SQL_UPDATE_OVERTIME_MEAL_BY_ID, parameters);
	}

	private static final String SQL_DELETE_OVERTIME_MEAL = "delete from oa_overtime_meal where omId=?";

	public void deleteAssetRepair(final String[] omIds) {
		jt.batchUpdate(SQL_DELETE_OVERTIME_MEAL,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(omIds[i]));
					}

					@Override
					public int getBatchSize() {
						return omIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_DISPOSE_OVERTIME_MEAL_BY_ID = "update oa_overtime_meal set sts='WAI' where omId=?";

	public void disposeOvertimeMeal(String omId) {
		jt.update(SQL_DISPOSE_OVERTIME_MEAL_BY_ID, omId);
	}

	private static final String SQL_REPLY_OVERTIME_MEAL_BY_ID = "update oa_overtime_meal set sts='RUN', feedback=?, feedbackBy=?, feedbackByName=?, feedbackByTime=now(), feedbackByIP=? where omId=?";

	public int replyOvertimeMeal(Object[] parameters) {
		return jt.update(SQL_REPLY_OVERTIME_MEAL_BY_ID, parameters);
	}
}
