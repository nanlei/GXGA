package cn.gov.dl.ga.gxga.process.admin.emergencynotice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmergencyNoticeService;

public class EmergencyNoticeSearchProcess extends Process {
	private EmergencyNoticeService emergencyNoticeService;

	public void setEmergencyNoticeService(
			EmergencyNoticeService emergencyNoticeService) {
		this.emergencyNoticeService = emergencyNoticeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList notices = emergencyNoticeService
				.searchEmergencyNotice(request);

		model.put("total", notices.getRowCount());
		model.put("data", notices.getList());

		return new Result(this.getSuccessView(), model);
	}

}
