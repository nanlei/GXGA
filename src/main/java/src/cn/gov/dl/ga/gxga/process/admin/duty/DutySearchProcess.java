package cn.gov.dl.ga.gxga.process.admin.duty;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyService;

public class DutySearchProcess extends Process {
	private DutyService dutyService;

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList duties = dutyService.searchDuty(request);

		model.put("total", duties.getRowCount());
		model.put("data", duties.getList());

		return new Result(this.getSuccessView(), model);
	}

}
