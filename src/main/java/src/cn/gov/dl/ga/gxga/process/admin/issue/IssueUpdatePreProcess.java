package cn.gov.dl.ga.gxga.process.admin.issue;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.IssueService;

public class IssueUpdatePreProcess extends Process {
	private IssueService issueService;

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String issueId = (String) request.getAttribute("issueId");

		HashMap<String, Object> issue = issueService.getIssueById(issueId);

		model.put("issue", issue);

		return new Result(this.getSuccessView(), model);
	}

}
