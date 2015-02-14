package cn.gov.dl.ga.gxga.process.admin.overtimemeal;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.OvertimeMealService;

public class OvertimeMealSearchProcess extends Process {
	private OvertimeMealService overtimeMealService;

	public void setOvertimeMealService(OvertimeMealService overtimeMealService) {
		this.overtimeMealService = overtimeMealService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList overtimeMeals = overtimeMealService
				.searchOvertimeMeal(request);

		model.put("total", overtimeMeals.getRowCount());
		model.put("data", overtimeMeals.getList());

		return new Result(this.getSuccessView(), model);
	}

}
