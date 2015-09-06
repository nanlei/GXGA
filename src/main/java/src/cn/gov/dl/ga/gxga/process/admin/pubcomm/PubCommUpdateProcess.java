package cn.gov.dl.ga.gxga.process.admin.pubcomm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.Audit;
import cn.gov.dl.ga.gxga.model.PubComm;
import cn.gov.dl.ga.gxga.process.admin.pubcomm.data.PubCommAudit;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class PubCommUpdateProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String transactionId = params.get("transactionId");
		String transactionNo = params.get("transactionNo");
		String transactionTitle = params.get("transactionTitle");
		String transactionOrder = params.get("transactionOrder");
		String transactionStatus = params.get("transactionStatus");

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

		int modifiedBy = (Integer) loginUser.get("userId");
		String modifiedByName = (String) loginUser.get("realName");
		String modifiedByIP = CoreUtil.getIPAddr(request);

		HashMap<String, Object> transaction = externalTransactionService
				.getTransactionById(transactionId);

		List<Audit> auditConfig = new PubCommAudit(transaction, transactionNo,
				transactionTitle, transactionOrder, transactionStatus,
				transactionConfig).audit();

		externalTransactionService.updateTransactionById(new Object[] {
				transactionNo, transactionTitle, transactionConfig,
				transactionOrder, transactionStatus, modifiedBy,
				modifiedByName, modifiedByIP, transactionId });

		externalTransactionService.createExternalTransactionAudit(request,
				transactionId, JSONMaker.makeJson(auditConfig));

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
