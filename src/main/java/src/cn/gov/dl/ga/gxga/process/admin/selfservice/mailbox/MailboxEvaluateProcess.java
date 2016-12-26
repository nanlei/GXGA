package cn.gov.dl.ga.gxga.process.admin.selfservice.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MailboxEvaluateProcess extends Process {
	private SelfService selfService;

	public void setSelfService(SelfService selfService) {
		this.selfService = selfService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailId = params.get("mailId");
		String rank = params.get("rank");

		int createBy = (Integer) loginUser.get("userId");

		Object[] parameters = new Object[] { rank, mailId, createBy };

		Map<String, Object> mail = selfService.getMailById(mailId, createBy);

		if ("RUN".equals(mail.get("sts"))) {
			selfService.evaluateMailById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notrun");
		}

		return new Result(this.getSuccessView(), model);
	}

}
