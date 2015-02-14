package cn.gov.dl.ga.gxga.process.admin.dutyplan;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyPlanService;

public class DutyPlanSearchProcess extends Process {
	private DutyPlanService dutyPlanService;

	public void setDutyPlanService(DutyPlanService dutyPlanService) {
		this.dutyPlanService = dutyPlanService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList dutyPlans = dutyPlanService.searchDutyPlan(request);

		model.put("total", dutyPlans.getRowCount());
		model.put("data", dutyPlans.getList());

		return new Result(this.getSuccessView(), model);
	}

}
