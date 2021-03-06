package cn.gov.dl.ga.gxga.process.admin.minyiwang;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;

public class MinYiWangAuditSearchProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList audits = externalTransactionService
				.searchAuditByTransactionId(request);

		model.put("total", audits.getRowCount());
		model.put("data", audits.getList());

		return new Result(this.getSuccessView(), model);
	}

}
