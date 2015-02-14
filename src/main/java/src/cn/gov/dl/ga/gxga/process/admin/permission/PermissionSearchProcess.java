package cn.gov.dl.ga.gxga.process.admin.permission;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionService;

public class PermissionSearchProcess extends Process {
	private PermissionService permissionService;

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList permissions = permissionService.searchPermission(request);

		model.put("total", permissions.getRowCount());
		model.put("data", permissions.getList());

		return new Result(this.getSuccessView(), model);
	}

}
