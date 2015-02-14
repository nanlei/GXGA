package cn.gov.dl.ga.gxga.process.admin.jobcategory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobCategoryService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class JobCategoryUpdateProcess extends Process {
	private JobCategoryService jobCategoryService;

	public void setJobCategoryService(JobCategoryService jobCategoryService) {
		this.jobCategoryService = jobCategoryService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String jobId = params.get("jobId");
		String jobCategoryTitle = params.get("jobCategoryTitle");
		String jobCategoryOrder = params.get("jobCategoryOrder");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		String jobCategoryId = params.get("jobCategoryId");

		Object[] parameters = new Object[] { jobId, jobCategoryTitle,
				jobCategoryOrder, createBy, createByName, createByIP,
				jobCategoryId };

		jobCategoryService.updateHeader(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}
}
