package cn.gov.dl.ga.gxga.process.admin.minxinwang;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.MinXinWang;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinXinWangCreateProcess extends Process {
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

		String config_name = params.get("config_name");
		String config_phone = params.get("config_phone");
		String config_address = params.get("config_address");
		String config_email = params.get("config_email");
		String config_time = params.get("config_time");
		String config_disposeTime = params.get("config_disposeTime");
		String config_content = params.get("config_content");
		String config_department = params.get("config_department");
		String config_comment = params.get("config_comment");
		String config_leader = params.get("config_leader");
		String config_leaderComment = params.get("config_leaderComment");

		String transactionConfig = JSONMaker.makeJson(new MinXinWang(
				config_name, config_phone, config_address, config_email,
				config_time, config_disposeTime, config_content,
				config_department, config_comment, config_leader,
				config_leaderComment));

		externalTransactionService.createExternalTransaction(request,
				Constant.EXTERNALTRANSACTIONTYPE_MXW, transactionConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
