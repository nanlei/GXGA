package cn.gov.dl.ga.gxga.process.front.attachment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class AttachmentDownloadProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String attachmentId = (String) request.getAttribute("attachmentId");

		Map<String, Object> attachment = indexService
				.getAttachmentById(attachmentId);

		model.put("fileUrl", attachment.get("attachmentUrl"));
		model.put(
				"fileName",
				attachment.get("attachmentName")
						+ "."
						+ CoreUtil.getFileExtensionName((String) attachment
								.get("attachmentUrl")));

		return new Result(this.getSuccessView(), model);
	}

}
