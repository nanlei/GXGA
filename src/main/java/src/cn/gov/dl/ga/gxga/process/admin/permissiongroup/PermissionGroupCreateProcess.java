package cn.gov.dl.ga.gxga.process.admin.permissiongroup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PermissionGroupService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class PermissionGroupCreateProcess extends Process {
	private PermissionGroupService permissionGroupService;

	public void setPermissionGroupService(
			PermissionGroupService permissionGroupService) {
		this.permissionGroupService = permissionGroupService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String permissionGroupName = params.get("permissionGroupName");
		String permissionGroupDescription = params
				.get("permissionGroupDescription");
		String permissionGroupOrder = params.get("permissionGroupOrder");
		String isInit = params.get("isInit");

		Object[] parameters = new Object[] { permissionGroupName,
				permissionGroupDescription, permissionGroupOrder, isInit };

		permissionGroupService.addPermissionGroup(parameters);
		
		SystemCache.getInstants().loadPermission();

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
