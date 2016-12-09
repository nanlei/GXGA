package cn.gov.dl.ga.gxga.process.admin.externalip;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ExternalIPService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class ExternalIPUpdateProcess extends Process {
	private ExternalIPService externalIPService;

	public void setExternalIPService(ExternalIPService externalIPService) {
		this.externalIPService = externalIPService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String externalIP = params.get("externalIP");
		String externalIPName = params.get("externalIPName");
		String externalIPDescription = params.get("externalIPDescription");
		String externalIPOrder = params.get("externalIPOrder");
		String isLock = params.get("isLock");
		String externalIPId = params.get("externalIPId");

		Object[] parameters = new Object[] { externalIP, externalIPName,
				externalIPDescription, externalIPOrder, isLock, externalIPId };

		externalIPService.updateExternalIP(parameters);
		
		SystemCache.getInstants().refreshIP();

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
