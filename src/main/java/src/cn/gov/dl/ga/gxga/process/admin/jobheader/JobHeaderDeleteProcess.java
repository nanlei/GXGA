package cn.gov.dl.ga.gxga.process.admin.jobheader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;

public class JobHeaderDeleteProcess extends Process {
	private JobHeaderService jobHeaderService;

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] jobIds = ((String) request.getAttribute("jobIds"))
				.split(",");

		List<Map<String, Object>> jobHeaderList = jobHeaderService
				.getJobHeaderListByIds(jobIds);

		for (int i = 0; i < jobHeaderList.size(); i++) {
			String jobImageUrl = (String) jobHeaderList.get(i).get(
					"jobImageUrl");
			new File(servletContext.getRealPath(jobImageUrl)).delete();
		}

		jobHeaderService.deleteJobHeaderById(jobIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
