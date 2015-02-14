package cn.gov.dl.ga.gxga.process.admin.permissiongroup;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionGroupService;
import cn.gov.dl.ga.gxga.service.admin.PermissionService;

public class PermissionGroupUnselectedSearchProcess extends Process {
	private PermissionService permissionService;
	private PermissionGroupService permissionGroupService;

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public void setPermissionGroupService(
			PermissionGroupService permissionGroupService) {
		this.permissionGroupService = permissionGroupService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String permissionGroupId = (String) request
				.getAttribute("permissionGroupId");

		HashSet<Object> permissionIds = permissionGroupService
				.getPermissionFromPP(permissionGroupId);

		PagingList unseletedPermissions = permissionService
				.getPermissionUnselectedForPermissionGroup(request,
						permissionIds);

		model.put("total", unseletedPermissions.getRowCount());
		model.put("data", unseletedPermissions.getList());

		return new Result(this.getSuccessView(), model);
	}

}
