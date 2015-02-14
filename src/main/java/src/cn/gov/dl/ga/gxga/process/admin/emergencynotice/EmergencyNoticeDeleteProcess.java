package cn.gov.dl.ga.gxga.process.admin.emergencynotice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmergencyNoticeService;

public class EmergencyNoticeDeleteProcess extends Process {
	private EmergencyNoticeService emergencyNoticeService;

	public void setEmergencyNoticeService(
			EmergencyNoticeService emergencyNoticeService) {
		this.emergencyNoticeService = emergencyNoticeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] noticeIds = ((String) request.getAttribute("noticeIds"))
				.split(",");

		List<Map<String, Object>> noticeList = emergencyNoticeService
				.getNoticeByIds(noticeIds);

		for (int i = 0; i < noticeList.size(); i++) {
			Map<String, Object> notice = noticeList.get(i);

			String noticeImageUrl = (String) notice.get("noticeImageUrl");
			String noticeAttachmentUrl = (String) notice
					.get("noticeAttachmentUrl");

			new File(servletContext.getRealPath(noticeImageUrl)).delete();
			new File(servletContext.getRealPath(noticeAttachmentUrl)).delete();
		}

		emergencyNoticeService.deleteNotice(noticeIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
