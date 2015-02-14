package cn.gov.dl.ga.gxga.process.admin.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.UserService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class UserCreateProcess extends Process {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		String isEmployee = objectMap.get("isEmployee");

		if ("Y".equals(isEmployee)) {
			userService.addUserAndEmployee(request);
		} else {
			userService.addUser(request);
		}

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
