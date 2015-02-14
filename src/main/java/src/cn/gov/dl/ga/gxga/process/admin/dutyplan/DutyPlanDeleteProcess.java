package cn.gov.dl.ga.gxga.process.admin.dutyplan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyPlanService;

public class DutyPlanDeleteProcess extends Process {
	private DutyPlanService dutyPlanService;

	public void setDutyPlanService(DutyPlanService dutyPlanService) {
		this.dutyPlanService = dutyPlanService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int user_departmentId = (Integer) loginUser.get("departmentId");

		String dpId = (String) request.getAttribute("dpId");

		HashMap<String, Object> dutyPlan = dutyPlanService
				.getDutyPlanById(dpId);

		int departmentId = (Integer) dutyPlan.get("departmentId");

		if (user_departmentId == departmentId) {
			dutyPlanService.deleteDutyById(dpId);
			model.put("status", "true");
		} else {
			model.put("status", "error");
		}

		return new Result(this.getSuccessView(), model);
	}

}
