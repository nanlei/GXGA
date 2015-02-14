package cn.gov.dl.ga.gxga.process.admin.mailbox;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MailboxService;

public class MailboxDeleteProcess extends Process {
	private MailboxService mailboxService;

	public void setMailboxService(MailboxService mailboxService) {
		this.mailboxService = mailboxService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] mailIds = ((String) request.getAttribute("mailIds"))
				.split(",");

		mailboxService.deleteMail(mailIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
