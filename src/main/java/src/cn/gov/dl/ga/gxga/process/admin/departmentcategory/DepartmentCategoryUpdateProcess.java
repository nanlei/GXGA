package cn.gov.dl.ga.gxga.process.admin.departmentcategory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DepartmentCategoryService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class DepartmentCategoryUpdateProcess extends Process {
	private DepartmentCategoryService departmentCategoryService;

	public void setDepartmentCategoryService(
			DepartmentCategoryService departmentCategoryService) {
		this.departmentCategoryService = departmentCategoryService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String dcName = params.get("dcName");
		String dcOrder = params.get("dcOrder");

		String dcId = params.get("dcId");
		String departmentId = params.get("departmentId");

		Object[] parameters = new Object[] { dcName, dcOrder, createBy,
				createByName, createByIP, dcId, departmentId };

		departmentCategoryService.updateCategoryById(parameters);
		
		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
