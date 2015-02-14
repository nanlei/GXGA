package cn.gov.dl.ga.gxga.process.admin.meeting;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MeetingService;

public class MeetingCreateProcess extends Process {
	private MeetingService meetingService;

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		meetingService.createMeeting(request);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
