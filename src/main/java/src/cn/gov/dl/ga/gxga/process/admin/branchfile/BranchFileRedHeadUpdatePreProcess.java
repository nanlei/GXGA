package cn.gov.dl.ga.gxga.process.admin.branchfile;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class BranchFileRedHeadUpdatePreProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleId = (String) request.getAttribute("articleId");

		HashMap<String, Object> article = articleService
				.getArticleById(articleId);

		String redHeadConfig = (String) article.get("redHeadConfig");

		HashMap<String, String> config = JSONParser.parseJSON(redHeadConfig);

		model.put("branchFileNo", config.get("branchFileNo"));
		model.put("articleTitle", config.get("articleTitle"));
		model.put("bottomEnding", config.get("bottomEnding"));
		model.put("bottomDate", config.get("bottomDate"));

		model.put("article", article);

		return new Result(this.getSuccessView(), model);
	}

}