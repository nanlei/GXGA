package cn.gov.dl.ga.gxga.process.admin.download;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DownloadService;

public class DownloadDeleteProcess extends Process {
	private DownloadService downloadService;

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] downloadIds = ((String) request
				.getAttribute("downloadIds")).split(",");

		List<Map<String, Object>> downloadList = downloadService
				.getDownloadByIds(downloadIds);

		for (int i = 0; i < downloadList.size(); i++) {
			Map<String, Object> download = downloadList.get(i);

			String downloadUrl = (String) download.get("downloadUrl");

			new File(servletContext.getRealPath(downloadUrl)).delete();
		}

		downloadService.deleteDownload(downloadIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
