package cn.gov.dl.ga.gxga.process.front.download;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DownloadService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class DownloadProcess extends Process {
	private DownloadService downloadService;

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String downloadId = (String) request.getAttribute("downloadId");

		Map<String, Object> download = downloadService
				.getDownloadById(downloadId);

		model.put("fileUrl", download.get("downloadUrl"));
		model.put(
				"fileName",
				download.get("downloadDescription")
						+ "."
						+ CoreUtil.getFileExtensionName((String) download
								.get("downloadUrl")));

		return new Result(this.getSuccessView(), model);
	}
}
