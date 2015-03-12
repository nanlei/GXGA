package cn.gov.dl.ga.gxga.process.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class ArticleListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		String articleType = (String) request.getAttribute("type");

		PagingList articleList = null;

		if ("ISSUEWORD".equals(articleType)) {
			articleList = indexService.getIssueWordList(request);
		} else {
			articleList = indexService.getArticleListByType(request,
					articleType);
		}

		List<Map<String, Object>> rankList = indexService.getRank();

		model.put("articleType", articleType);
		model.put("articleList", articleList);

		model.put("rankList", rankList);

		return new Result(this.getSuccessView(), model);
	}

}
