package cn.gov.dl.ga.gxga.process.admin.rank;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.RankService;

public class RankSearchProcess extends Process {
	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList ranks = rankService.searchRank(request);

		model.put("total", ranks.getRowCount());
		model.put("data", ranks.getList());

		return new Result(this.getSuccessView(), model);
	}

}
