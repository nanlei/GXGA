package cn.gov.dl.ga.gxga.process.admin.role;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionGroupService;
import cn.gov.dl.ga.gxga.service.admin.RoleService;

public class RoleUnselectedSearchProcess extends Process {
	private PermissionGroupService permissionGroupService;
	private RoleService roleService;

	public void setPermissionGroupService(
			PermissionGroupService permissionGroupService) {
		this.permissionGroupService = permissionGroupService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String roleId = (String) request.getAttribute("roleId");

		HashSet<Object> permissionGroupIds = roleService
				.getPermissionGroupFromRP(roleId);

		PagingList unseletedPermissions = permissionGroupService
				.getPermissionGroupUnselectedForRole(request,
						permissionGroupIds);

		model.put("total", unseletedPermissions.getRowCount());
		model.put("data", unseletedPermissions.getList());

		return new Result(this.getSuccessView(), model);
	}

}
