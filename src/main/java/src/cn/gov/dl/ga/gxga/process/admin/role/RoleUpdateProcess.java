package cn.gov.dl.ga.gxga.process.admin.role;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.RoleService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class RoleUpdateProcess extends Process {
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String roleName = params.get("roleName");
		String roleDescription = params.get("roleDescription");
		String roleOrder = params.get("roleOrder");
		String roleId = params.get("roleId");

		Object[] parameters = new Object[] { roleName, roleDescription,
				roleOrder, roleId };

		roleService.updateRole(parameters);

		SystemCache.getInstants().refresh(Constant.CACHE_ROLE);
		SystemCache.getInstants().loadPermission();

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
