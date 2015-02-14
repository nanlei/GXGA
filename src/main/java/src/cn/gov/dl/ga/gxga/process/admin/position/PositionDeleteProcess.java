package cn.gov.dl.ga.gxga.process.admin.position;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PositionService;

public class PositionDeleteProcess extends Process {
	private PositionService positionService;

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] positionIds = ((String) request
				.getAttribute("positionIds")).split(",");

		positionService.deletePosition(positionIds);

		SystemCache.getInstants().refresh(Constant.CACHE_POSITION);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
