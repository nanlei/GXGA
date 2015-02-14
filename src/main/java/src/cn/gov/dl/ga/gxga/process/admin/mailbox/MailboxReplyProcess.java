package cn.gov.dl.ga.gxga.process.admin.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MailboxService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MailboxReplyProcess extends Process {
	private MailboxService mailboxService;

	public void setMailboxService(MailboxService mailboxService) {
		this.mailboxService = mailboxService;
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

		String mailId = params.get("mailId");
		String mailComment = params.get("mailComment");

		int commentBy = (Integer) loginUser.get("userId");
		String commentByName = (String) loginUser.get("realName");
		String commentByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { mailComment, commentBy,
				commentByName, commentByIP, mailId };

		Map<String, Object> mail = mailboxService.getMailById(mailId);

		if (Constant.STS_RUN.equals(mail.get("sts"))) {
			model.put("status", "run");
		} else {
			mailboxService.replyMail(parameters);
			model.put("status", "true");
		}
		return new Result(this.getSuccessView(), model);
	}

}
