package cn.gov.dl.ga.gxga.process.admin.permission;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionService;

public class PermissionDeleteProcess extends Process {
	private PermissionService permissionService;

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] permissionIds = ((String) request
				.getAttribute("permissionIds")).split(",");

		permissionService.deletePermission(permissionIds);
		
		SystemCache.getInstants().loadPermission();

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
