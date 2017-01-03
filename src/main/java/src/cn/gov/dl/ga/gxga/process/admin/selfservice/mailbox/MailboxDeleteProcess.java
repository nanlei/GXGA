package cn.gov.dl.ga.gxga.process.admin.selfservice.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;

public class MailboxDeleteProcess extends Process {
	private SelfService selfService;

	public void setSelfService(SelfService selfService) {
		this.selfService = selfService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		final String[] mailIds = ((String) request.getAttribute("mailIds")).split(",");

		int createBy = (Integer) loginUser.get("userId");

		boolean deleteFlag = false;

		for (int i = 0; i < mailIds.length; i++) {
			Map<String, Object> mail = selfService.getMailById(mailIds[i], createBy);

			if (createBy == (Integer) mail.get("createBy")) {
				deleteFlag = true;
			} else {
				deleteFlag = false;
			}
		}

		if (deleteFlag) {
			selfService.deleteMail(mailIds);
			model.put("status", "true");
		} else {
			model.put("status", "false");
		}

		return new Result(this.getSuccessView(), model);
	}

}
