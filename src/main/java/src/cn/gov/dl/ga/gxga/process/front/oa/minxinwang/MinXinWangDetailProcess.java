package cn.gov.dl.ga.gxga.process.front.oa.minxinwang;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinXinWangDetailProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String transactionId = (String) request.getAttribute("transactionId");

		HashMap<String, Object> transaction = indexService
				.getTransactionById(transactionId);

		String transactionConfig = (String) transaction
				.get("transactionConfig");

		HashMap<String, String> config = JSONParser
				.parseJSON(transactionConfig);

		model.put("transaction", transaction);
		model.put("config", config);

		return new Result(this.getSuccessView(), model);
	}

}
