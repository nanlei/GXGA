package cn.gov.dl.ga.gxga.process.front.mailbox;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class MailboxListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		PagingList mailList = indexService.getMailList(request);

		model.put("mailList", mailList);

		return new Result(this.getSuccessView(), model);
	}

}
