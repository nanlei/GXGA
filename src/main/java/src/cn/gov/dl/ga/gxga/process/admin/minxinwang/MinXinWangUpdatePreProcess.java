package cn.gov.dl.ga.gxga.process.admin.minxinwang;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinXinWangUpdatePreProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String transactionId = (String) request.getAttribute("transactionId");

		HashMap<String, Object> transaction = externalTransactionService
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
