package cn.gov.dl.ga.gxga.process.admin.overtimemeal;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.OvertimeMealService;

public class OvertimeMealDetailProcess extends Process {
	private OvertimeMealService overtimeMealService;

	public void setOvertimeMealService(OvertimeMealService overtimeMealService) {
		this.overtimeMealService = overtimeMealService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String omId = (String) request.getAttribute("omId");

		HashMap<String, Object> overtimeMeal = overtimeMealService
				.getOvertimeMealById(omId);

		model.put("overtimeMeal", overtimeMeal);

		return new Result(this.getSuccessView(), model);
	}

}
