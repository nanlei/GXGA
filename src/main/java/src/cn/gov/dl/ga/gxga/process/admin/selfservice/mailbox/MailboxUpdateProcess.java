package cn.gov.dl.ga.gxga.process.admin.selfservice.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MailboxUpdateProcess extends Process {
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

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailId = params.get("mailId");
		String mailSubject = params.get("mailSubject");
		String mailContent = params.get("mailContent");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { mailSubject, mailContent,
				createBy, createByName, createByIP, mailId, createBy };

		Map<String, Object> mail = selfService.getMailById(mailId, createBy);

		if ("NEW".equals(mail.get("sts"))) {
			selfService.updateMailById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notnew");
		}

		return new Result(this.getSuccessView(), model);
	}

}
