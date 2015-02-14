package cn.gov.dl.ga.gxga.process.front.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobCategoryService;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class JobSubListProcess extends Process {
	private IndexService indexService;
	private JobHeaderService jobHeaderService;
	private JobCategoryService jobCategoryService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	public void setJobCategoryService(JobCategoryService jobCategoryService) {
		this.jobCategoryService = jobCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		String jobId = (String) request.getAttribute("jobId");
		String jobCategoryId = (String) request.getAttribute("jobCategoryId");

		HashMap<String, Object> job = jobHeaderService.getJobHeaderById(jobId);
		HashMap<String, Object> jobCategory = jobCategoryService
				.getJobCategoryById(jobCategoryId);

		PagingList articleList = indexService.getJobArticles(request,
				jobCategoryId);

		List<Map<String, Object>> jobList = indexService.getJobList();

		model.put("articleList", articleList);

		model.put("jobList", jobList);

		model.put("job", job);
		model.put("jobCategory", jobCategory);

		return new Result(this.getSuccessView(), model);
	}

}
