package cn.gov.dl.ga.gxga.service.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class DutyPlanService extends BaseService {
	private static final String SQL_SEARCH_DUTY_PLAN_PREFIX = "select dp.dpId, d.departmentName, dp.dpUrl, dp.createByName, date_format(dp.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, dp.createByIP from fun_duty_plan dp, hr_department d ";
	private static final String SQL_SEARCH_DUTY_PLAN_SUFFIX = "order by ";

	public PagingList searchDutyPlan(HttpServletRequest request)
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

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "dpId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int user_departmentId = (Integer) loginUser.get("departmentId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_DUTY_PLAN_PREFIX,
				SQL_SEARCH_DUTY_PLAN_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "dp.departmentId=d.departmentId");
		if (user_departmentId == 0) {
			helper.setParam(StringUtils.isNotEmpty(departmentId),
					"dp.departmentId=?", departmentId);
		} else {
			helper.setParam(true, "dp.departmentId=?", user_departmentId);
		}

		return helper;

	}

	public int createDutyPlan(HttpServletRequest request, String dpUrl)
			throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				dpUrl);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_duty_plan").usingGeneratedKeyColumns("dpId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String dpUrl) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String departmentId = (String) request.getAttribute("departmentId");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("departmentId", departmentId);
		parameters.put("dpUrl", dpUrl);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		return parameters;
	}

	private static final String SQL_CHECK_DUTY_PLAN_BY_DEPARTMENT_ID = "select count(*) from fun_duty_plan where departmentId=?";

	public int checkDutyPlanByDepartmentId(String departmentId) {
		return jt.queryForObject(SQL_CHECK_DUTY_PLAN_BY_DEPARTMENT_ID,
				Integer.class, departmentId);
	}

	private static final String SQL_GET_DUTY_PLAN_BY_ID = "select * from fun_duty_plan where dpId=?";

	public HashMap<String, Object> getDutyPlanById(String dpId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_DUTY_PLAN_BY_ID, dpId);
	}

	private static final String SQL_DELETE_DUTY_PLAN_BY_ID = "delete from fun_duty_plan where dpId=?";

	public void deleteDutyById(String dpId) {
		jt.update(SQL_DELETE_DUTY_PLAN_BY_ID, dpId);
	}
}
