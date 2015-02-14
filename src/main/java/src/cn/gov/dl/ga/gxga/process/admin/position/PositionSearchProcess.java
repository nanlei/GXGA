package cn.gov.dl.ga.gxga.process.admin.position;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PositionService;

public class PositionSearchProcess extends Process {
	private PositionService positionService;

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList positions = positionService.searchPosition(request);

		model.put("total", positions.getRowCount());
		model.put("data", positions.getList());

		return new Result(this.getSuccessView(), model);
	}

}
