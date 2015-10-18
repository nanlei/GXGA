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

public class ExternalTransactionService extends BaseService {
	private static final String SQL_SEARCH_EXTERNAL_TRANSACTION_BY_TYPE_PREFIX = "select oet.transactionId, oet.transactionNo, oet.transactionTitle, oet.transactionStatus, oet.transactionOrder, oet.modifiedBy, oet.modifiedByName, date_format(oet.modifiedByTime,'%Y-%m-%d %H:%i:%s') as modifiedByTime, oet.modifiedByIP from oa_external_transaction oet ";
	private static final String SQL_SEARCH_EXTERNAL_TRANSACTION_BY_TYPE_SUFFIX = "order by ";

	// Search by type
	public PagingList searchExternalTransactionByType(
			HttpServletRequest request, String transactionType) {
		QueryHelper helper = buildQueryConditionByTransactionType(request,
				transactionType);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionByTransactionType(
			HttpServletRequest request, String transactionType) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String transactionNo = params.get("transactionNo");
		String transactionTitle = params.get("transactionTitle");
		String transactionStatus = params.get("transactionStatus");
		String modifiedByTimeStart = params.get("modifiedByTimeStart");
		String modifiedByTimeEnd = params.get("modifiedByTimeEnd");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "articleOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_EXTERNAL_TRANSACTION_BY_TYPE_PREFIX,
				SQL_SEARCH_EXTERNAL_TRANSACTION_BY_TYPE_SUFFIX + sortField
						+ " " + sortOrder);

		helper.setParam(true, "oet.transactionType=?", transactionType);
		helper.setParam(StringUtils.isNotEmpty(transactionNo),
				"oet.transactionNo like concat('%',?,'%')", transactionNo);
		helper.setParam(StringUtils.isNotEmpty(transactionTitle),
				"oet.transactionTitle like concat('%',?,'%')", transactionTitle);
		helper.setParam(StringUtils.isNotEmpty(transactionStatus),
				"oet.transactionStatus=?", transactionStatus);
		helper.setParam(StringUtils.isNotEmpty(modifiedByTimeStart),
				"unix_timestamp(oet.modifiedByTime) > unix_timestamp(?)",
				modifiedByTimeStart);
		helper.setParam(StringUtils.isNotEmpty(modifiedByTimeEnd),
				"unix_timestamp(oet.modifiedByTime) < unix_timestamp(?)",
				modifiedByTimeEnd);

		return helper;
	}

	public int createExternalTransaction(HttpServletRequest request,
			String transactionType, String transactionConfig) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				transactionType, transactionConfig);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"oa_external_transaction").usingGeneratedKeyColumns(
				"transactionId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String transactionType,
			String transactionConfig) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String transactionNo = params.get("transactionNo");
		String transactionTitle = params.get("transactionTitle");
		String transactionOrder = params.get("transactionOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("transactionType", transactionType);
		parameters.put("transactionNo", transactionNo);
		parameters.put("transactionTitle", transactionTitle);
		parameters.put("transactionConfig", transactionConfig);
		parameters.put("transactionOrder", transactionOrder);
		parameters.put("transactionStatus", Constant.STS_NEW);

		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("modifiedBy", createBy);
		parameters.put("modifiedByName", createByName);
		parameters.put("modifiedByTime", new Timestamp(new Date().getTime()));
		parameters.put("modifiedByIP", CoreUtil.getIPAddr(request));
		return parameters;
	}

	private static final String SQL_GET_TRANSACTION_BY_ID = "select * from oa_external_transaction where transactionId=?";

	public HashMap<String, Object> getTransactionById(String transactionId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_TRANSACTION_BY_ID, transactionId);
	}

	private static final String SQL_UPDATE_TRANSACTION_BY_ID = "update oa_external_transaction set transactionNo=?, transactionTitle=?, transactionConfig=?, transactionOrder=?, transactionStatus=?, modifiedBy=?, modifiedByName=?, modifiedByTime=now(), modifiedByIP=? where transactionId=?";

	public int updateTransactionById(Object[] parameters) {
		return jt.update(SQL_UPDATE_TRANSACTION_BY_ID, parameters);
	}

	private static final String SQL_DELETE_TRANSACTION_BY_ID = "delete from oa_external_transaction where transactionId=?";
	private static final String SQL_DELETE_TRANSACTION_AUDIT_BY_TRANSACTION_ID = "delete from oa_external_transaction_audit where transactionId=?";

	public void deleteTransactionById(final String[] transactionIds) {
		jt.batchUpdate(SQL_DELETE_TRANSACTION_AUDIT_BY_TRANSACTION_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(transactionIds[i]));
					}

					@Override
					public int getBatchSize() {
						return transactionIds.length;
					}
				});
		jt.batchUpdate(SQL_DELETE_TRANSACTION_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(transactionIds[i]));
					}

					@Override
					public int getBatchSize() {
						return transactionIds.length;
					}
				});
	}

	// Audit
	private static final String SQL_GET_TRANSACTION_AUDIT_BY_TRANSACTION_ID_PREFIX = "select oeta.auditId, oeta.transactionId, oeta.auditBy, oeta.auditByName, date_format(oeta.auditByTime,'%Y-%m-%d %H:%i:%s') as auditByTime, oeta.auditByIP from oa_external_transaction_audit oeta ";
	private static final String SQL_GET_TRANSACTION_AUDIT_BY_TRANSACTION_ID_SUFFIX = "order by ";

	public PagingList searchAuditByTransactionId(HttpServletRequest request) {
		QueryHelper helper = buildQueryConditionByTransactionId(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionByTransactionId(
			HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String transactionId = params.get("transactionId");
		String auditByTimeStart = params.get("auditByTimeStart");
		String auditByTimeEnd = params.get("auditByTimeEnd");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "articleOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_GET_TRANSACTION_AUDIT_BY_TRANSACTION_ID_PREFIX,
				SQL_GET_TRANSACTION_AUDIT_BY_TRANSACTION_ID_SUFFIX + sortField
						+ " " + sortOrder);

		helper.setParam(true, "oeta.transactionId=?", transactionId);

		helper.setParam(StringUtils.isNotEmpty(auditByTimeStart),
				"unix_timestamp(oeta.auditByTime) > unix_timestamp(?)",
				auditByTimeStart);
		helper.setParam(StringUtils.isNotEmpty(auditByTimeEnd),
				"unix_timestamp(oeta.auditByTime) < unix_timestamp(?)",
				auditByTimeEnd);

		return helper;
	}

	private static final String SQL_GET_AUDIT_BY_ID = "select * from oa_external_transaction_audit oeta where oeta.auditId=?";

	public HashMap<String, Object> getAuditById(String auditId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_AUDIT_BY_ID,
				auditId);
	}

	public int createExternalTransactionAudit(HttpServletRequest request,
			String transactionId, String auditConfig) throws Exception {
		HashMap<String, Object> parameters = buildAuditInsertCondition(request,
				transactionId, auditConfig);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"oa_external_transaction_audit").usingGeneratedKeyColumns(
				"auditId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildAuditInsertCondition(
			HttpServletRequest request, String transactionId, String auditConfig)
			throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int auditBy = (Integer) loginUser.get("userId");
		String auditByName = (String) loginUser.get("realName");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("transactionId", transactionId);
		parameters.put("auditConfig", auditConfig);
		parameters.put("auditBy", auditBy);
		parameters.put("auditByName", auditByName);
		parameters.put("auditByTime", new Timestamp(new Date().getTime()));
		parameters.put("auditByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_DELETE_TRANSACTION_AUDIT_BY_ID = "delete from oa_external_transaction_audit where auditId=?";

	public void deleteAuditById(final String[] auditIds) {
		jt.batchUpdate(SQL_DELETE_TRANSACTION_AUDIT_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(auditIds[i]));
					}

					@Override
					public int getBatchSize() {
						return auditIds.length;
					}
				});
	}
}
