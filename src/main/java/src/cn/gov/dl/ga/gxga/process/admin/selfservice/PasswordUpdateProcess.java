package cn.gov.dl.ga.gxga.process.admin.selfservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.MD5;

public class PasswordUpdateProcess extends Process {
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
		int userId = (Integer) loginUser.get("userId");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String oldPassword = params.get("oldPassword");
		String newPassword = params.get("newPassword");
		String newPasswordConfirm = params.get("newPasswordConfirm");

		int exists = selfService.checkUserPassword(userId,
				MD5.encode(oldPassword));

		if (exists == 1) {
			if (newPassword.equals(newPasswordConfirm)) {
				selfService.updateUserPassword(userId, MD5.encode(newPassword));
				model.put("status", "true");
			} else {
				model.put("status", "pwdNotEqual");
			}
		} else {
			model.put("status", "oldPwdWrong");
		}

		return new Result(this.getSuccessView(), model);
	}
}
