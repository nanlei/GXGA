package cn.gov.dl.ga.gxga.process.admin.role;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.RoleService;

public class RoleUpdatePreProcess extends Process {
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String roleId = (String) request.getAttribute("roleId");

		HashMap<String, Object> role = roleService.getRoleById(roleId);

		model.put("role", role);

		return new Result(this.getSuccessView(), model);
	}

}
