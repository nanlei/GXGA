package cn.gov.dl.ga.gxga.process.admin.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.UserService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class UserUpdateProcess extends Process {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String realName = params.get("realName");
		String bindIP = params.get("bindIP");
		String userOrder = params.get("userOrder");
		String roleId = params.get("roleId");
		String isLock = params.get("isLock");
		String isSignRole = params.get("isSignRole");
		String userId = params.get("userId");

		Object[] parameters = new Object[] { realName, bindIP, userOrder,
				roleId, isLock, isSignRole, userId };

		userService.updateUser(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
