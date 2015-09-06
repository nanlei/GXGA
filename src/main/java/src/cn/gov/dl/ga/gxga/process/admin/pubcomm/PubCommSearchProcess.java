package cn.gov.dl.ga.gxga.process.admin.pubcomm;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.process.admin.pubcomm.data.PubCommDecorator;
import cn.gov.dl.ga.gxga.service.admin.ExternalTransactionService;

public class PubCommSearchProcess extends Process {
	private ExternalTransactionService externalTransactionService;

	public void setExternalTransactionService(
			ExternalTransactionService externalTransactionService) {
		this.externalTransactionService = externalTransactionService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList transactions = externalTransactionService
				.searchExternalTransactionByType(request,
						Constant.EXTERNALTRANSACTIONTYPE_PUBCOMM);

		new PubCommDecorator(transactions.getList()).decorate();

		model.put("total", transactions.getRowCount());
		model.put("data", transactions.getList());

		return new Result(this.getSuccessView(), model);
	}

}
