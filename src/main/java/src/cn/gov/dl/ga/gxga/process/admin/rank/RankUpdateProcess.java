package cn.gov.dl.ga.gxga.process.admin.rank;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.RankService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class RankUpdateProcess extends Process {
	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String rankValue = params.get("rankValue");
		String rankOrder = params.get("rankOrder");
		String rankId = params.get("rankId");

		Object[] parameters = new Object[] { rankValue, rankOrder, rankId };

		rankService.updateRank(parameters);
		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
