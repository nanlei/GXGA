package cn.gov.dl.ga.gxga.process.admin.department;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class DepartmentUpdateProcess extends Process {
	private DepartmentService departmentService;

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String departmentName = params.get("departmentName");
		String departmentType = params.get("departmentType");
		String departmentCode = params.get("departmentCode");
		String upperId = params.get("upperId");
		String isLeaf = params.get("isLeaf");
		String departmentOrder = params.get("departmentOrder");
		String departmentId = params.get("departmentId");

		Object[] parameters = new Object[] { departmentName, departmentType,
				departmentCode, upperId, isLeaf, departmentOrder, departmentId };

		departmentService.updateDepartment(parameters);

		SystemCache.getInstants().refresh(Constant.CACHE_DEPARTMENT);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}
}
