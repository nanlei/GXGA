package cn.gov.dl.ga.gxga.process.admin.role;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.RoleService;

public class RoleCreateProcess extends Process {
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		roleService.addRole(request);

		SystemCache.getInstants().refresh(Constant.CACHE_ROLE);
		SystemCache.getInstants().loadPermission();

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
