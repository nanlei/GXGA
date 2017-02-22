package cn.gov.dl.ga.gxga.process.front.issue;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class IssueListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		PagingList issueList = indexService.getIssueWordList(request);

		model.put("issueList", issueList);
		model.put("articleType", Constant.ARTICLETYPE_ISSUEWORD);

		return new Result(this.getSuccessView(), model);
	}

}
