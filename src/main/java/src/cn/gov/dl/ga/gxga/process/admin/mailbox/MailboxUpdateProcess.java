package cn.gov.dl.ga.gxga.process.admin.mailbox;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

public class MailboxUpdateProcess extends Process {
	private MailboxService mailboxService;

	public void setMailboxService(MailboxService mailboxService) {
		this.mailboxService = mailboxService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailId = params.get("mailId");
		String mailSubject = params.get("mailSubject");
		String mailContent = params.get("mailContent");
		int leaderId = Integer.parseInt(params.get("leaderId"));
		int deptAdminId = Integer.parseInt(params.get("deptAdminId"));
		String isPublic = params.get("isPublic");

		Timestamp dueDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("dueDate")).getTime());

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { mailSubject, mailContent, leaderId, deptAdminId, isPublic, dueDate,
				createBy, createByName, createByIP, mailId };

		Map<String, Object> mail = mailboxService.getMailById(mailId);

		if ("NEW".equals(mail.get("sts"))) {
			mailboxService.updateMailById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notnew");
		}

		return new Result(this.getSuccessView(), model);
	}

}
