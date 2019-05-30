package cn.gov.dl.ga.gxga.process.front.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class JobArticleProcess extends Process {
	private IndexService indexService;
	private JobHeaderService jobHeaderService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleId = (String) request.getAttribute("articleId");
		String jobId = (String) request.getAttribute("jobId");

		Map<String, Object> article = indexService.queryArticleById(articleId);

		HashMap<String, Object> job = jobHeaderService.getJobHeaderById(jobId);

		List<Map<String, Object>> jobList = indexService.getJobList();

		model.put("article", article);

		model.put("job", job);

		model.put("jobList", jobList);

		return new Result(this.getSuccessView(), model);
	}

}
