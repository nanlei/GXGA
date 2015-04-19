package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class EmergencyNoticeService extends BaseService {
	private static final String SQL_SEARCH_EMERGENCY_NOTICE_PREFIX = "select n.noticeId, n.noticeTitle, n.noticeOrder, case when n.noticeStatus='NEW' then '不显示' else '正常' end noticeStatus, n.createBy, n.createByName, date_format(n.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, n.createByIP from fun_emergency_notice n ";
	private static final String SQL_SEARCH_EMERGENCY_NOTICE_SUFFIX = "order by ";

	public PagingList searchEmergencyNotice(HttpServletRequest request)
			throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String noticeTitle = params.get("noticeTitle");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "noticeOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_EMERGENCY_NOTICE_PREFIX,
				SQL_SEARCH_EMERGENCY_NOTICE_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(StringUtils.isNotEmpty(noticeTitle),
				"n.noticeTitle like concat('%',?,'%')", noticeTitle);

		return helper;
	}

	public int createEmergencyNotice(HttpServletRequest request,
			String imageFilePath, String attachmentFilePath) {
		HashMap<String, Object> parameters = buildInsertCondition(request);
		parameters.put("noticeImageUrl", imageFilePath);
		parameters.put("noticeAttachmentUrl", attachmentFilePath);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_emergency_notice").usingGeneratedKeyColumns("noticeId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String noticeTitle = (String) request.getAttribute("noticeTitle");
		String noticeOrder = (String) request.getAttribute("noticeOrder");
		String noticeStatus = (String) request.getAttribute("noticeStatus");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("noticeTitle", noticeTitle);
		parameters.put("noticeOrder", Integer.parseInt(noticeOrder));
		parameters.put("noticeStatus", noticeStatus);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_EMERGENCY_NOTICE_BY_ID = "select * from fun_emergency_notice where noticeId=?";

	public HashMap<String, Object> getEmergencyNoticeById(String noticeId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_EMERGENCY_NOTICE_BY_ID, noticeId);
	}

	private static final String SQL_UPDATE_NOTICE_BY_ID = "update fun_emergency_notice set noticeTitle=?, noticeOrder=?, noticeStatus=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where noticeId=?";
	private static final String SQL_UPDATE_NOTICE_IMAGE_BY_ID = "update fun_emergency_notice set noticeImageUrl=? where noticeId=?";
	private static final String SQL_UPDATE_NOTICE_ATTACHMENT_BY_ID = "update fun_emergency_notice set noticeAttachmentUrl=? where noticeId=?";

	@SuppressWarnings("unchecked")
	public void updateNoticeById(HttpServletRequest request,
			String imageFilePath, String attachmentFilePath) {
		String noticeId = (String) request.getAttribute("noticeId");
		String noticeTitle = (String) request.getAttribute("noticeTitle");
		String noticeOrder = (String) request.getAttribute("noticeOrder");
		String noticeStatus = (String) request.getAttribute("noticeStatus");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		Object[] parameters = new Object[] { noticeTitle, noticeOrder,
				noticeStatus, (Integer) loginUser.get("userId"),
				loginUser.get("realName"), CoreUtil.getIPAddr(request),
				noticeId };

		jt.update(SQL_UPDATE_NOTICE_BY_ID, parameters);

		if (StringUtils.isNotEmpty(imageFilePath)) {
			jt.update(SQL_UPDATE_NOTICE_IMAGE_BY_ID, imageFilePath, noticeId);
		}

		if (StringUtils.isNotEmpty(attachmentFilePath)) {
			jt.update(SQL_UPDATE_NOTICE_ATTACHMENT_BY_ID, attachmentFilePath,
					noticeId);
		}

	}

	private static final String SQL_DELETE_NOTICE = "delete from fun_emergency_notice where noticeId=?";

	public void deleteNotice(final String[] noticeIds) {
		jt.batchUpdate(SQL_DELETE_NOTICE, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(noticeIds[i]));
			}

			@Override
			public int getBatchSize() {
				return noticeIds.length;
			}
		});
	}

	// Extend
	private static final String SQL_GET_NOTICE_BY_IDS = "select noticeImageUrl, noticeAttachmentUrl from fun_emergency_notice where ";

	public List<Map<String, Object>> getNoticeByIds(String[] noticeIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_NOTICE_BY_IDS
				+ sqlHelper.buildWhereIn("noticeId", noticeIds));
	}

}
