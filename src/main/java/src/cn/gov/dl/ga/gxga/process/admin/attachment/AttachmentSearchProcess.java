package cn.gov.dl.ga.gxga.process.admin.attachment;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.AttachmentService;

public class AttachmentSearchProcess extends Process {
	private AttachmentService attachmentService;

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList attachments = attachmentService.searchAttachment(request);

		model.put("total", attachments.getRowCount());
		model.put("data", attachments.getList());

		return new Result(this.getSuccessView(), model);
	}

}
