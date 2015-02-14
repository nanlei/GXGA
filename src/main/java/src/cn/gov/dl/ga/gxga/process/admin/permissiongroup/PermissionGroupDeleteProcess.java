package cn.gov.dl.ga.gxga.process.admin.permissiongroup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionGroupService;

public class PermissionGroupDeleteProcess extends Process {
	private PermissionGroupService permissionGroupService;

	public void setPermissionGroupService(
			PermissionGroupService permissionGroupService) {
		this.permissionGroupService = permissionGroupService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] permissionGroupIds = ((String) request
				.getAttribute("permissionGroupIds")).split(",");

		permissionGroupService.deletePermissionGroup(permissionGroupIds);

		SystemCache.getInstants().loadPermission();
		
		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
