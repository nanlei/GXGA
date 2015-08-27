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

public class MeetingService extends BaseService {
	private static final String SQL_SEARCH_MEETING_PREFIX = "select m.meetingId, m.meetingSubject, m.meetingRoom, d.departmentName, date_format(m.meetingStartTime,'%Y-%m-%d %H:%i:%s') as meetingStartTime, date_format(m.meetingEndTime,'%Y-%m-%d %H:%i:%s') as meetingEndTime, createByName, date_format(m.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, m.createByIP from oa_meeting m, hr_department d ";
	private static final String SQL_SEARCH_MEETING_SUFFIX = "order by ";

	public PagingList searchMeeting(HttpServletRequest request)
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

		String meetingStartTime = params.get("meetingStartTime");
		String meetingEndTime = params.get("meetingEndTime");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "meetingId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int roleId = (Integer) loginUser.get("roleId");
		int user_departmentId = (Integer) loginUser.get("departmentId");
		
		QueryHelper helper = new QueryHelper(SQL_SEARCH_MEETING_PREFIX,
				SQL_SEARCH_MEETING_SUFFIX + sortField + " " + sortOrder);

		if (roleId != 1) {// Not Super Admin
			helper.setParam(true, "m.departmentId=?", user_departmentId);
		}
		helper.setParam(true, "m.departmentId=d.departmentId");
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"m.departmentId=?", departmentId);
		if (StringUtils.isNotEmpty(meetingStartTime)) {
			helper.setParam(true,
					"date_format(m.meetingStartTime,'%Y-%m-%d')>=?",
					new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(meetingStartTime)));

		}
		if (StringUtils.isNotEmpty(meetingEndTime)) {
			helper.setParam(true,
					"date_format(m.meetingEndTime,'%Y-%m-%d')<=?",
					new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(meetingEndTime)));
		}
		return helper;
	}

	public int createMeeting(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"oa_meeting").usingGeneratedKeyColumns("meetingId");

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

		String meetingSubject = params.get("meetingSubject");
		String meetingRoom = params.get("meetingRoom");
		String departmentId = params.get("departmentId");

		String meetingStartTime = params.get("meetingStartTime").replace("T",
				" ");
		String meetingEndTime = params.get("meetingEndTime").replace("T", " ");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("meetingSubject", meetingSubject);
		parameters.put("meetingRoom", meetingRoom);
		parameters.put("departmentId", departmentId);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(meetingStartTime);
		parameters.put("meetingStartTime", new Timestamp(startTime.getTime()));

		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(meetingEndTime);
		parameters.put("meetingEndTime", new Timestamp(endTime.getTime()));

		return parameters;
	}

	private static final String SQL_GET_MEETING_BY_ID = "select * from oa_meeting where meetingId=?";

	public HashMap<String, Object> getMeetingById(String meetingId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_MEETING_BY_ID,
				meetingId);
	}

	private static final String SQL_UPDATE_MEETING_BY_ID = "update oa_meeting set departmentId=?, meetingSubject=?, meetingRoom=?, meetingStartTime=?, meetingEndTime=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where meetingId=?";

	public int updateMeetingById(Object[] parameters) {
		return jt.update(SQL_UPDATE_MEETING_BY_ID, parameters);
	}

	private static final String SQL_DELETE_MEETING = "delete from oa_meeting where meetingId=?";

	public void deleteMeeting(final String[] meetingIds) {
		jt.batchUpdate(SQL_DELETE_MEETING, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(meetingIds[i]));
			}

			@Override
			public int getBatchSize() {
				return meetingIds.length;
			}
		});
	}

}
