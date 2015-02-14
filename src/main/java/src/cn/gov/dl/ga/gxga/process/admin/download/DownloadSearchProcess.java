package cn.gov.dl.ga.gxga.process.admin.download;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DownloadService;

public class DownloadSearchProcess extends Process {
	private DownloadService downloadService;

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList downloads = downloadService.searchDownload(request);

		model.put("total", downloads.getRowCount());
		model.put("data", downloads.getList());

		return new Result(this.getSuccessView(), model);
	}

}
