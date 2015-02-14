package cn.gov.dl.ga.gxga.process.admin.attachment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.AttachmentService;

public class AttachmentDeleteProcess extends Process {
	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] attachmentIds = ((String) request
				.getAttribute("attachmentIds")).split(",");

		List<Map<String, Object>> attachmentList = attachmentService
				.getAttachmentByIds(attachmentIds);

		for (int i = 0; i < attachmentList.size(); i++) {
			Map<String, Object> attachment = attachmentList.get(i);

			String attachmentUrl = (String) attachment.get("attachmentUrl");

			new File(servletContext.getRealPath(attachmentUrl)).delete();
		}

		attachmentService.deleteAttachment(attachmentIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
