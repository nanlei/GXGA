package cn.gov.dl.ga.gxga.process.admin.meeting;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MeetingService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MeetingUpdateProcess extends Process {
	private MeetingService meetingService;

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String meetingId = params.get("meetingId");
		String meetingSubject = params.get("meetingSubject");
		String meetingRoom = params.get("meetingRoom");
		String meetingStartTime = params.get("meetingStartTime").replace("T",
				" ");
		String meetingEndTime = params.get("meetingEndTime").replace("T", " ");

		String departmentId = params.get("departmentId");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(meetingStartTime);

		Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(meetingEndTime);

		Object[] parameters = new Object[] { departmentId, meetingSubject,
				meetingRoom, new Timestamp(startTime.getTime()),
				new Timestamp(endTime.getTime()), createBy, createByName,
				createByIP, meetingId };

		meetingService.updateMeetingById(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
