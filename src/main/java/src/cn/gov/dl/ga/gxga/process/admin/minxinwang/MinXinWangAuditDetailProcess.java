package cn.gov.dl.ga.gxga.process.admin.minxinwang;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.process.admin.minxinwang.data.MinXinWangAuditDecorator;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinXinWangAuditDetailProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String auditId = request.getParameter("auditId");

		HashMap<String, Object> audit = externalTransactionService
				.getAuditById(auditId);

		List<HashMap<String, String>> auditConfig = JSONParser
				.parseJSONToList((String) audit.get("auditConfig"));

		new MinXinWangAuditDecorator(auditConfig).decorate();

		model.put("auditConfig", auditConfig);

		return new Result(this.getSuccessView(), model);
	}
}
