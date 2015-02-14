package cn.gov.dl.ga.gxga.process.admin.constant;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ConstantService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class ConstantUpdateProcess extends Process {
	private ConstantService constantService;

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String constantType = params.get("constantType");
		String constantValue = params.get("constantValue");
		String constantName = params.get("constantName");
		String constantOrder = params.get("constantOrder");
		String isLock = params.get("isLock");
		String constantId = params.get("constantId");

		Object[] parameters = new Object[] { constantType, constantValue,
				constantName, constantOrder, isLock, constantId };

		constantService.updateConstant(parameters);

		SystemCache.getInstants().refresh(Constant.CACHE_CONSTANT);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
