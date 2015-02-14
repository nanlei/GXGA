package cn.gov.dl.ga.gxga.process.admin.position;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PositionService;

public class PositionUpdatePreProcess extends Process {
	private PositionService positionService;

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String positionId = (String) request.getAttribute("positionId");

		HashMap<String, Object> position = positionService
				.getPositionById(positionId);

		model.put("position", position);

		return new Result(this.getSuccessView(), model);
	}

}
