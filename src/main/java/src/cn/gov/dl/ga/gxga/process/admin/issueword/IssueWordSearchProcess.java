package cn.gov.dl.ga.gxga.process.admin.issueword;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.IssueWordService;

public class IssueWordSearchProcess extends Process {
	private IssueWordService issueWordService;

	public void setIssueWordService(IssueWordService issueWordService) {
		this.issueWordService = issueWordService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList issueWords = issueWordService.searchIssueWord(request);

		model.put("total", issueWords.getRowCount());
		model.put("data", issueWords.getList());

		return new Result(this.getSuccessView(), model);
	}

}
