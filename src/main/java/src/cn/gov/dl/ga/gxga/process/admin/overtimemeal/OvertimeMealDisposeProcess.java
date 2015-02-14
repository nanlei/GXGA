package cn.gov.dl.ga.gxga.process.admin.overtimemeal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.OvertimeMealService;

public class OvertimeMealDisposeProcess extends Process {
	private OvertimeMealService overtimeMealService;

	public void setOvertimeMealService(OvertimeMealService overtimeMealService) {
		this.overtimeMealService = overtimeMealService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String omId = (String) request.getAttribute("omId");

		Map<String, Object> overtimeMeal = overtimeMealService
				.getOvertimeMealById(omId);

		if (Constant.STS_NEW.equals(overtimeMeal.get("sts"))) {
			overtimeMealService.disposeOvertimeMeal(omId);
			model.put("status", "true");
		} else {
			model.put("status", "notnew");
		}

		return new Result(this.getSuccessView(), model);
	}

}
