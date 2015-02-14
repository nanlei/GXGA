package cn.gov.dl.ga.gxga.process.admin.constant;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ConstantService;

public class ConstantUpdatePreProcess extends Process {
	private ConstantService constantService;

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String constantId = (String) request.getAttribute("constantId");

		HashMap<String, Object> constant = constantService
				.getConstantById(constantId);

		model.put("constant", constant);

		return new Result(this.getSuccessView(), model);
	}

}
