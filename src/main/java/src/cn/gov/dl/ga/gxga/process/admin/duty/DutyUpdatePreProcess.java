package cn.gov.dl.ga.gxga.process.admin.duty;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyService;

public class DutyUpdatePreProcess extends Process {
	private DutyService dutyService;

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String dutyId = (String) request.getAttribute("dutyId");

		HashMap<String, Object> duty = dutyService.getDutyById(dutyId);

		model.put("duty", duty);

		return new Result(this.getSuccessView(), model);
	}

}
