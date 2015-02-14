package cn.gov.dl.ga.gxga.process.admin.issue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.IssueService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class IssueUpdateProcess extends Process {
	private IssueService issueService;

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
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

		Timestamp issueDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd")
				.parse(params.get("issueDate")).getTime());
		String issueContent = params.get("issueContent");
		String issueOrder = params.get("issueOrder");
		String issueStatus = Constant.STS_NEW;

		int departmentId = (Integer) loginUser.get("departmentId");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		String issueId = params.get("issueId");

		Object[] parameters = new Object[] { issueDate, issueContent,
				issueOrder, issueStatus, departmentId, createBy, createByName,
				createByIP, issueId };

		issueService.updateIssue(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
