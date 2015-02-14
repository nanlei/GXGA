package cn.gov.dl.ga.gxga.process.admin.duty;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyService;

public class DutyCreateProcess extends Process {
	private DutyService dutyService;

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		dutyService.createDuty(request);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
