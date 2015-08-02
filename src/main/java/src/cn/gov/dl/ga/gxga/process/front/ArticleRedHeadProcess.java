package cn.gov.dl.ga.gxga.process.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class ArticleRedHeadProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleId = (String) request.getAttribute("articleId");

		Map<String, Object> article = indexService.getArticleById(articleId);

		String redHeadConfig = (String) article.get("redHeadConfig");

		HashMap<String, String> config = JSONParser.parseJSON(redHeadConfig);

		if (Constant.ARTICLETYPE_BRANCHFILE.equals(article.get("articleType"))) {
			model.put("branchFileNo", config.get("branchFileNo"));
			model.put("articleTitle", config.get("articleTitle"));
			model.put("bottomEnding", config.get("bottomEnding"));
			model.put("bottomDate", config.get("bottomDate"));
		} else if (Constant.ARTICLETYPE_NOTICE.equals(article
				.get("articleType"))) {
			model.put("topUnit", config.get("topUnit"));
			model.put("noticeNo", config.get("noticeNo"));
			model.put("topDate", config.get("topDate"));
			model.put("noticeHost", config.get("noticeHost"));
			model.put("noticeContact", config.get("noticeContact"));
			model.put("noticePhone", config.get("noticePhone"));
			model.put("bottomDate", config.get("bottomDate"));
		}

		model.put("article", article);

		return new Result(this.getSuccessView(), model);
	}

}
