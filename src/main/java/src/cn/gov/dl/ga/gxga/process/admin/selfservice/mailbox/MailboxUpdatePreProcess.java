package cn.gov.dl.ga.gxga.process.admin.selfservice.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;

public class MailboxUpdatePreProcess extends Process {
	private SelfService selfService;

	public void setSelfService(SelfService selfService) {
		this.selfService = selfService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String mailId = (String) request.getAttribute("mailId");

		HashMap<String, Object> mail = selfService.getMailById(mailId,
				(Integer) loginUser.get("userId"));

		model.put("mail", mail);

		return new Result(this.getSuccessView(), model);
	}

}
