package cn.gov.dl.ga.gxga.process.front.monthlystar;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class MonthlyStarInfoProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String id = (String) request.getAttribute("id");

		Map<String, Object> monthlyStar = indexService.queryArticleById(id);

		model.put("monthlyStar", monthlyStar);
		model.put("articleType", Constant.ARTICLETYPE_MONTHLYSTAR);

		return new Result(this.getSuccessView(), model);
	}

}
