package cn.gov.dl.ga.gxga.process.admin.jobheader;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;

public class JobHeaderUpdatePreProcess extends Process {
	private JobHeaderService jobHeaderService;

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String jobId = (String) request.getAttribute("jobId");

		HashMap<String, Object> jobHeader = jobHeaderService
				.getJobHeaderById(jobId);

		model.put("jobHeader", jobHeader);

		return new Result(this.getSuccessView(), model);
	}

}
