package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class ExternalIPService extends BaseService {
	private static final String SQL_SEARCH_IP_PREFIX = "select externalIPId, externalIP, externalIPName, externalIPDescription, externalIPOrder, case when isLock='Y' then '是' else '否' end isLock, createByName,date_format(createByTime,'%Y-%m-%d %H:%i:%s') as createByTime from sys_externalip";
	private static final String SQL_SEARCH_IP_SUFFIX = "order by externalIPOrder asc";

	public PagingList searchIP(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String externalIP = params.get("externalIP");
		String externalIPName = params.get("externalIPName");
		String externalIPDescription = params.get("externalIPDescription");
		String isLock = params.get("isLock");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_IP_PREFIX,
				SQL_SEARCH_IP_SUFFIX);
		helper.setParam(StringUtils.isNotEmpty(externalIP),
				"externalIP like concat('%',?,'%')", externalIP);
		helper.setParam(StringUtils.isNotEmpty(externalIPName),
				"externalIPName like concat('%',?,'%')", externalIPName);
		helper.setParam(StringUtils.isNotEmpty(externalIPDescription),
				"externalIPDescription like concat('%',?,'%')",
				externalIPDescription);
		helper.setParam(StringUtils.isNotEmpty(isLock), "isLock=?", isLock);

		return helper;
	}

	public int addExternalIP(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"sys_externalip").usingGeneratedKeyColumns("externalIPId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String externalIP = objectMap.get("externalIP");
		String externalIPName = objectMap.get("externalIPName");
		String externalIPDescription = objectMap.get("externalIPDescription");
		String externalIPOrder = objectMap.get("externalIPOrder");
		String isLock = objectMap.get("isLock");

		HashMap<String, Object> loginUser = (HashMap<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("externalIP", externalIP);
		parameters.put("externalIPName", externalIPName);
		parameters.put("externalIPDescription", externalIPDescription);
		parameters.put("externalIPOrder", externalIPOrder);
		parameters.put("isLock", isLock);
		parameters.put("createBy", loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new java.util.Date());
		
		return parameters;
	}

	private static final String SQL_GET_IP_BY_ID = "select * from sys_externalip where externalIPId=?";

	public HashMap<String, Object> getExternalIPById(String externalIPId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_IP_BY_ID,
				externalIPId);
	}

	private static final String SQL_UPDATE_EXTERNALIP = "update sys_externalip set externalIP=?, externalIPName=?, externalIPDescription=?, externalIPOrder=?, isLock=? where externalIPId=?";

	public int updateExternalIP(Object[] parameters) {
		return jt.update(SQL_UPDATE_EXTERNALIP, parameters);
	}

	private static final String SQL_DELETE_EXTERNALIP = "delete from sys_externalip where externalIPId=?";

	public void deleteExternalIP(final String[] externalIPIds) {
		jt.batchUpdate(SQL_DELETE_EXTERNALIP,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(externalIPIds[i]));
					}

					@Override
					public int getBatchSize() {
						return externalIPIds.length;
					}
				});
	}
}
