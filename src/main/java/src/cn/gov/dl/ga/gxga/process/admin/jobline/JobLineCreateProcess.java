package cn.gov.dl.ga.gxga.process.admin.jobline;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobLineService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class JobLineCreateProcess extends Process {
	private JobLineService jobLineService;

	public void setJobLineService(JobLineService jobLineService) {
		this.jobLineService = jobLineService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String videoId = params.get("videoId");

		jobLineService.createArticleForJobLine(request, videoId);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
