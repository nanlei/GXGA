package cn.gov.dl.ga.gxga.process.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmployeeService;
import cn.gov.dl.ga.gxga.service.admin.UserService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.MD5;

public class AdminLogonProcess extends Process {
	private UserService userService;
	private EmployeeService employeeService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = request.getParameter("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String userName = params.get("userName");
		String password = params.get("password");

		String md5Password = MD5.encode(password);

		Map<String, Object> user = userService.checkLogon(userName);

		if (user == null) {
			model.put("status", "null");
		} else {
			if ("Y".equals(user.get("isLock"))) {
				model.put("status", "locked");
			} else if (user != null && user.get("password").equals(md5Password)) {
				String isEmployee = (String) user.get("isEmployee");

				if ("Y".equals(isEmployee)) {
					int employeeId = (Integer) user.get("employeeId");
					int departmentId = employeeService
							.getDepartmentIdByEmployeeId(employeeId);
					user.put("departmentId", departmentId);
				} else {
					user.put("departmentId", 0);
				}

				request.getSession().invalidate();
				request.getSession().setAttribute(Constant.LOGIN_USER, user);
				request.getSession().setAttribute("CKFinder_UserRole", "admin");

				int userId = (Integer) user.get("userId");

				String ip = CoreUtil.getIPAddr(request);

				userService.updateUserLogon(new Object[] { ip, userId });

				model.put("status", "true");
			} else {
				model.put("status", "invalid");
			}
		}// eof - user==null

		return new Result(this.getSuccessView(), model);
	}

}