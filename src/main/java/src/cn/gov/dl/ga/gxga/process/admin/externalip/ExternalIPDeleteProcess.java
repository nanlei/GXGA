package cn.gov.dl.ga.gxga.process.admin.externalip;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ExternalIPService;

public class ExternalIPDeleteProcess extends Process {
	private ExternalIPService externalIPService;

	public void setExternalIPService(ExternalIPService externalIPService) {
		this.externalIPService = externalIPService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] externalIPIds = ((String) request
				.getAttribute("externalIPIds")).split(",");

		externalIPService.deleteExternalIP(externalIPIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
