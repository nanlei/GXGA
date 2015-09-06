package cn.gov.dl.ga.gxga.process.admin.pubcomm;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.PubComm;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class PubCommCreateProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String config_department = params.get("config_department");
		String config_type = params.get("config_type");
		String config_content = params.get("config_content");
		String config_leaderComment = params.get("config_leaderComment");
		String config_replyContent = params.get("config_replyContent");
		String config_reply = params.get("config_reply");
		String config_date = params.get("config_date");

		String transactionConfig = JSONMaker.makeJson(new PubComm(
				config_department, config_type, config_content,
				config_leaderComment, config_replyContent, config_reply,
				config_date));

		externalTransactionService.createExternalTransaction(request,
				Constant.EXTERNALTRANSACTIONTYPE_PUBCOMM, transactionConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
