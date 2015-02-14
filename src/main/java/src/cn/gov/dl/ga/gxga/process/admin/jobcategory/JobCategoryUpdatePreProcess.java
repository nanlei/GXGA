package cn.gov.dl.ga.gxga.process.admin.jobcategory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobCategoryService;

public class JobCategoryUpdatePreProcess extends Process {
	private JobCategoryService jobCategoryService;

	public void setJobCategoryService(JobCategoryService jobCategoryService) {
		this.jobCategoryService = jobCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String jobCategoryId = (String) request.getAttribute("jobCategoryId");

		Map<String, Object> jobCategory = jobCategoryService
				.getJobCategoryById(jobCategoryId);

		model.put("jobCategory", jobCategory);

		return new Result(this.getSuccessView(), model);
	}

}
