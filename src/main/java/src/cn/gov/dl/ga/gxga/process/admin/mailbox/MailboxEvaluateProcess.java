package cn.gov.dl.ga.gxga.process.admin.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MailboxService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MailboxEvaluateProcess extends Process {
	private MailboxService mailboxService;

	public void setMailboxService(MailboxService mailboxService) {
		this.mailboxService = mailboxService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String mailId = params.get("mailId");
		String rank = params.get("rank");

		Object[] parameters = new Object[] { rank, mailId };

		Map<String, Object> mail = mailboxService.getMailById(mailId);

		if ("RUN".equals(mail.get("sts")) && loginUser.get("userId").equals(mail.get("createBy"))) {
			mailboxService.evaluateMailById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notrun");
		}

		return new Result(this.getSuccessView(), model);
	}

}
