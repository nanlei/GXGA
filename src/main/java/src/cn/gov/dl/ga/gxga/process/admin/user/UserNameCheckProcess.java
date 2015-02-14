package cn.gov.dl.ga.gxga.process.admin.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.UserService;

public class UserNameCheckProcess extends Process {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String userName = (String) request.getAttribute("userName");

		int userExists = userService.checkUserName(userName);

		if (userExists == 0) {
			model.put("status", "true");
		} else {
			model.put("status", "false");
		}

		return new Result(this.getSuccessView(), model);
	}

}
