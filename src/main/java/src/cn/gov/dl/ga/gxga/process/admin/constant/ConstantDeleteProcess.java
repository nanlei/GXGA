package cn.gov.dl.ga.gxga.process.admin.constant;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ConstantService;

public class ConstantDeleteProcess extends Process {
	private ConstantService constantService;

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] constantIds = ((String) request
				.getAttribute("constantIds")).split(",");

		constantService.deleteConstant(constantIds);
		
		SystemCache.getInstants().refresh(Constant.CACHE_CONSTANT);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
