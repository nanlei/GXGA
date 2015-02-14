package cn.gov.dl.ga.gxga.process.admin.download;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DownloadService;

public class DownloadUpdatePreProcess extends Process {
	private DownloadService downloadService;

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String downloadId = (String) request.getAttribute("downloadId");

		HashMap<String, Object> download = downloadService.getDownloadById(downloadId);

		model.put("download", download);

		return new Result(this.getSuccessView(), model);
	}

}
