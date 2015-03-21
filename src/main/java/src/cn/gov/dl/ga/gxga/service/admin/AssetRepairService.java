package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
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

public class AssetRepairService extends BaseService {
	private static final String SQL_SEARCH_ASSETREPAIR_PREFIX = "select ar.arId, ar.arCode, d.departmentName, ar.assetName, ar.assetLocation, ar.sts, c.constantName as statusName, createByName, date_format(ar.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, ar.createByIP, ar.feedbackByName, date_format(ar.feedbackByTime,'%Y-%m-%d %H:%i:%s') as feedbackByTime, ar.feedbackByIP from oa_asset_repair ar, hr_department d, sys_constant c ";
	private static final String SQL_SEARCH_ASSETREPAIR_SUFFIX = "order by ";

	public PagingList searchAssetRepair(HttpServletRequest request)
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
		String assetName = params.get("assetName");
		String createByName = params.get("createByName");
		String createByTime = params.get("createByTime");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "arId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;
		
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int roleId = (Integer) loginUser.get("roleId");
		int user_departmentId = (Integer) loginUser.get("departmentId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_ASSETREPAIR_PREFIX,
				SQL_SEARCH_ASSETREPAIR_SUFFIX + sortField + " " + sortOrder);

		if (roleId != 1) {// Not Super Admin
			helper.setParam(true, "ar.departmentId=?", user_departmentId);
		}
		helper.setParam(true, "ar.departmentId=d.departmentId");
		helper.setParam(true,
				"ar.sts=c.constantValue and c.constantType='ASSETREPAIRSTATUS'");
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"ar.departmentId=?", departmentId);
		helper.setParam(StringUtils.isNotEmpty(assetName),
				"ar.assetName like concat('%',?,'%')", assetName);
		helper.setParam(StringUtils.isNotEmpty(createByName),
				"ar.createByName like concat('%',?,'%')", createByName);
		if (StringUtils.isNotEmpty(createByTime)) {
			helper.setParam(true, "date_format(ar.createByTime,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(createByTime)));
		}
		return helper;
	}

	public int createAssetRepair(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"oa_asset_repair").usingGeneratedKeyColumns("arId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	private static final String SQL_GET_AR_CODE = "select count(*) from oa_asset_repair where substring(arCode,1,8)=?";

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(new Date());

		int arCodeCount = jt.queryForObject(SQL_GET_AR_CODE, Integer.class,
				today);

		arCodeCount++;

		StringBuffer arCode = new StringBuffer();
		arCode.append(today).append("-")
				.append(StringUtils.leftPad(arCodeCount + "", 3, '0'));

		String departmentId = params.get("departmentId");
		String assetName = params.get("assetName");
		String assetLocation = params.get("assetLocation");
		String remark = params.get("remark");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("departmentId", departmentId);
		parameters.put("arCode", arCode.toString());
		parameters.put("assetName", assetName);
		parameters.put("assetLocation", assetLocation);
		parameters.put("remark", remark);
		parameters.put("sts", Constant.STS_NEW);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_ASSET_REPAIR_BY_ID = "select * from oa_asset_repair where arId=?";

	public HashMap<String, Object> getAssetRepairById(String arId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_ASSET_REPAIR_BY_ID, arId);
	}

	private static final String SQL_UPDATE_ASSET_REPAIR_BY_ID = "update oa_asset_repair set departmentId=?, assetName=?, assetLocation=?, remark=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where arId=?";

	public int updateAssetRepairById(Object[] parameters) {
		return jt.update(SQL_UPDATE_ASSET_REPAIR_BY_ID, parameters);
	}

	private static final String SQL_DELETE_ASSET_REPAIR = "delete from oa_asset_repair where arId=?";

	public void deleteAssetRepair(final String[] arIds) {
		jt.batchUpdate(SQL_DELETE_ASSET_REPAIR,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(arIds[i]));
					}

					@Override
					public int getBatchSize() {
						return arIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_DISPOSE_ASSET_REPAIR_BY_ID = "update oa_asset_repair set sts='WAI' where arId=?";

	public void disposeAssetRepair(String arId) {
		jt.update(SQL_DISPOSE_ASSET_REPAIR_BY_ID, arId);
	}

	private static final String SQL_REPLY_ASSET_REPAIR_BY_ID = "update oa_asset_repair set sts='RUN', feedback=?, feedbackBy=?, feedbackByName=?, feedbackByTime=now(), feedbackByIP=? where arId=?";

	public int replyAssetRepair(Object[] parameters) {
		return jt.update(SQL_REPLY_ASSET_REPAIR_BY_ID, parameters);
	}
}
