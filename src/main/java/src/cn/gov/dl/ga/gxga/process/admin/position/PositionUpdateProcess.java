package cn.gov.dl.ga.gxga.process.admin.position;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.PositionService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class PositionUpdateProcess extends Process {
	private PositionService positionService;

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String positionName = params.get("positionName");
		String positionCategory = params.get("positionCategory");
		String positionType = params.get("positionType");
		String positionCode = params.get("positionCode");
		String positionOrder = params.get("positionOrder");
		String positionId = params.get("positionId");

		Object[] parameters = new Object[] { positionName, positionCategory,
				positionType, positionCode, positionOrder, positionId };

		positionService.updatePosition(parameters);

		SystemCache.getInstants().refresh(Constant.CACHE_POSITION);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
