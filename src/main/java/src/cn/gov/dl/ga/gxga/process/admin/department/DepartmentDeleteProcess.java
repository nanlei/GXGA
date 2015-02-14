package cn.gov.dl.ga.gxga.process.admin.department;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentService;

public class DepartmentDeleteProcess extends Process {
	private DepartmentService departmentService;

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] departmentIds = ((String) request
				.getAttribute("departmentIds")).split(",");

		for (int i = 0; i < departmentIds.length; i++) {
			String deptId = departmentIds[i];
			if ("0".equals(deptId) || "1".equals(deptId) || "21".equals(deptId)) {
				departmentIds[i] = "-1";
			}
		}

		departmentService.deleteDepartment(departmentIds);

		SystemCache.getInstants().refresh(Constant.CACHE_DEPARTMENT);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
