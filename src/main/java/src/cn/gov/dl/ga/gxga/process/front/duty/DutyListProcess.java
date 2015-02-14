package cn.gov.dl.ga.gxga.process.front.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class DutyListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		List<Map<String, Object>> dutyList = indexService.getDuty();

		List<Map<String, Object>> dutyPlanList = indexService.getDutyPlan();

		model.put("dutyList", dutyList);

		model.put("dutyPlanList", dutyPlanList);

		return new Result(this.getSuccessView(), model);
	}

}
