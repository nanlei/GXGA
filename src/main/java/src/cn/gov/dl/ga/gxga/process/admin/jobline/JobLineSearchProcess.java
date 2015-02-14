package cn.gov.dl.ga.gxga.process.admin.jobline;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobLineService;

public class JobLineSearchProcess extends Process {
	private JobLineService jobLineService;

	public void setJobLineService(JobLineService jobLineService) {
		this.jobLineService = jobLineService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList articles = jobLineService.searchArticlesByJobId(request);

		model.put("total", articles.getRowCount());
		model.put("data", articles.getList());

		return new Result(this.getSuccessView(), model);
	}

}
