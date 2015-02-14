package cn.gov.dl.ga.gxga.process.admin.attachment;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.AttachmentService;

public class AttachmentUpdatePreProcess extends Process {
	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String attachmentId = (String) request.getAttribute("attachmentId");

		HashMap<String, Object> attachment = attachmentService
				.getAttachmentById(attachmentId);

		model.put("attachment", attachment);

		return new Result(this.getSuccessView(), model);
	}

}
