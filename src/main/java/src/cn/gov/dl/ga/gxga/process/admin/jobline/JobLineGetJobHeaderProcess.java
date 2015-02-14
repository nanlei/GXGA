package cn.gov.dl.ga.gxga.process.admin.jobline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobLineService;

public class JobLineGetJobHeaderProcess extends Process {
	private JobLineService jobLineService;

	public void setJobLineService(JobLineService jobLineService) {
		this.jobLineService = jobLineService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String jcid = (String) request.getAttribute("jcid");

		if (StringUtils.isNotEmpty(jcid)) {
			int jobId = jobLineService.getJobHeaderId(jcid);

			model.put("jobId", jobId);
			model.put("status", "true");
		} else {

			List<Map<String, Object>> data = jobLineService.getJobHeader();

			model.put("data", data);
		}

		return new Result(this.getSuccessView(), model);
	}

}
