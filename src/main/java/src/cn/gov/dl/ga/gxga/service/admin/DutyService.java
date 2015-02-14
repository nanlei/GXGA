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

public class DutyService extends BaseService {
	private static final String SQL_SEARCH_DUTY_PREFIX = "select d.dutyId, date_format(d.dutyDate,'%Y-%m-%d') as dutyDate, d.dutyManager, d.dutyLeader, d.dutyPolice, d.createByName, date_format(d.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, d.createByIP from fun_duty d ";
	private static final String SQL_SEARCH_DUTY_SUFFIX = "order by ";

	public PagingList searchDuty(HttpServletRequest request) throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String dutyDate = params.get("dutyDate");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "dutyId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_DUTY_PREFIX,
				SQL_SEARCH_DUTY_SUFFIX + sortField + " " + sortOrder);

		if (StringUtils.isNotEmpty(dutyDate)) {
			helper.setParam(true, "date_format(d.dutyDate,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(dutyDate)));
		}

		return helper;

	}

	public int createDuty(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_duty").usingGeneratedKeyColumns("dutyId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String dutyDate = params.get("dutyDate");
		String dutyManager = params.get("dutyManager");
		String dutyLeader = params.get("dutyLeader");
		String dutyPolice = params.get("dutyPolice");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		Date d_dutyDate = new SimpleDateFormat("yyyy-MM-dd").parse(dutyDate);
		parameters.put("dutyDate", new Timestamp(d_dutyDate.getTime()));
		parameters.put("dutyManager", dutyManager);
		parameters.put("dutyLeader", dutyLeader);
		parameters.put("dutyPolice", dutyPolice);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		return parameters;
	}

	private static final String SQL_GET_DUTY_BY_ID = "select * from fun_duty where dutyId=?";

	public HashMap<String, Object> getDutyById(String dutyId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_DUTY_BY_ID,
				dutyId);
	}

	private static final String SQL_UPDATE_DUTY_BY_ID = "update fun_duty set dutyDate=?, dutyManager=?, dutyLeader=?, dutyPolice=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where dutyId=?";

	public int updateDuty(Object[] parameters) {
		return jt.update(SQL_UPDATE_DUTY_BY_ID, parameters);
	}

	private static final String SQL_DELETE_DUTY_BY_ID = "delete from fun_duty where dutyId=?";

	public void deleteDuty(final String[] dutyIds) {
		jt.batchUpdate(SQL_DELETE_DUTY_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(dutyIds[i]));
					}

					@Override
					public int getBatchSize() {
						return dutyIds.length;
					}
				});
	}
}
