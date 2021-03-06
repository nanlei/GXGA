package cn.gov.dl.ga.gxga.process.admin.jobcategory;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobCategoryService;

public class JobCategorySearchProcess extends Process {
	private JobCategoryService jobCategoryService;

	public void setJobCategoryService(JobCategoryService jobCategoryService) {
		this.jobCategoryService = jobCategoryService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList jobCategories = jobCategoryService
				.searchJobCategory(request);

		model.put("total", jobCategories.getRowCount());
		model.put("data", jobCategories.getList());

		return new Result(this.getSuccessView(), model);
	}

}
