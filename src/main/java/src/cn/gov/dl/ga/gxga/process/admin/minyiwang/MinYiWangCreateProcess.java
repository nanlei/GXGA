package cn.gov.dl.ga.gxga.process.admin.minyiwang;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.MinYiWang;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinYiWangCreateProcess extends Process {
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

		String config_noticeNo = params.get("config_noticeNo");
		String config_noticeUnit = params.get("config_noticeUnit");
		String config_noticeDate = params.get("config_noticeDate");
		String config_noticeHost = params.get("config_noticeHost");
		String config_noticeContent = params.get("config_noticeContent");
		String config_noticeLeaderComment = params
				.get("config_noticeLeaderComment");
		String config_noticeReply = params.get("config_noticeReply");
		String config_noticeReplyTime = params.get("config_noticeReplyTime");
		String config_name = params.get("config_name");
		String config_contact = params.get("config_contact");
		String config_content = params.get("config_content");
		String config_reply = params.get("config_reply");

		String transactionConfig = JSONMaker.makeJson(new MinYiWang(
				config_noticeNo, config_noticeUnit, config_noticeDate,
				config_noticeHost, config_noticeContent,
				config_noticeLeaderComment, config_noticeReply,
				config_noticeReplyTime, config_name, config_contact,
				config_content, config_reply));

		externalTransactionService.createExternalTransaction(request,
				Constant.EXTERNALTRANSACTIONTYPE_MYW, transactionConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
