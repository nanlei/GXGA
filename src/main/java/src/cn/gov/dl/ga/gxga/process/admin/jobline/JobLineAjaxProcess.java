package cn.gov.dl.ga.gxga.process.admin.jobline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobLineService;

public class JobLineAjaxProcess extends Process {
	private JobLineService jobLineService;

	public void setJobLineService(JobLineService jobLineService) {
		this.jobLineService = jobLineService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String ctrl = (String) request.getAttribute("ctrl");

		if ("jobCategory".equals(ctrl)) {
			String jobCategoryId = (String) request
					.getAttribute("jobCategoryId");

			List<Map<String, Object>> data = jobLineService
					.getJobCategoryList(jobCategoryId);

			model.put("data", data);
		} else {

			String jobId = (String) request.getAttribute("jobId");

			List<Map<String, Object>> data = jobLineService
					.getJobCategory(jobId);

			model.put("data", data);
		}

		return new Result(this.getSuccessView(), model);
	}

}
