package cn.gov.dl.ga.gxga.process.admin.overtimemeal;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.OvertimeMealService;

public class OvertimeMealDeleteProcess extends Process {
	private OvertimeMealService overtimeMealService;

	public void setOvertimeMealService(OvertimeMealService overtimeMealService) {
		this.overtimeMealService = overtimeMealService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] omIds = ((String) request.getAttribute("omIds"))
				.split(",");

		overtimeMealService.deleteAssetRepair(omIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
