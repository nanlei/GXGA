package cn.gov.dl.ga.gxga.process.admin.officalmailbox;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.OfficalMailBox;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class OfficalMailBoxCreateProcess extends Process {
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

		String config_no = params.get("config_no");
		String config_unit = params.get("config_unit");
		String config_date = params.get("config_date");
		String config_content = params.get("config_content");
		String config_comment = params.get("config_comment");
		String config_leaderComment = params.get("config_leaderComment");
		String config_result = params.get("config_result");

		String transactionConfig = JSONMaker.makeJson(new OfficalMailBox(
				config_no, config_unit, config_date, config_content,
				config_comment, config_leaderComment, config_result));

		externalTransactionService.createExternalTransaction(request,
				Constant.EXTERNALTRANSACTIONTYPE_OFFICALMAILBOX,
				transactionConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
