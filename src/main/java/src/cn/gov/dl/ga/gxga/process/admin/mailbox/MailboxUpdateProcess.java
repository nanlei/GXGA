package cn.gov.dl.ga.gxga.process.admin.mailbox;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MailboxService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MailboxUpdateProcess extends Process {
	private MailboxService mailboxService;

	public void setMailboxService(MailboxService mailboxService) {
		this.mailboxService = mailboxService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailId = params.get("mailId");
		String mailSubject = params.get("mailSubject");
		String mailContent = params.get("mailContent");
		Integer leaderId = StringUtils.isNotEmpty(params.get("leaderId")) ? Integer.parseInt(params.get("leaderId"))
				: null;
		Integer deptAdminId = StringUtils.isNotEmpty(params.get("deptAdminId"))
				? Integer.parseInt(params.get("deptAdminId")) : null;
		String isPublic = params.get("isPublic");

		Timestamp dueDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("dueDate")).getTime());

		Object[] parameters = new Object[] { mailSubject, mailContent, leaderId, deptAdminId, isPublic, dueDate,
				mailId };

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
