package cn.gov.dl.ga.gxga.process.admin.notice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class NoticeRedHeadUpdatePreProcess extends Process {
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

		model.put("topUnit", config.get("topUnit"));
		model.put("noticeNo", config.get("noticeNo"));
		model.put("topDate", config.get("topDate"));
		model.put("noticeHost", config.get("noticeHost"));
		model.put("noticeContact", config.get("noticeContact"));
		model.put("noticePhone", config.get("noticePhone"));
		model.put("bottomDate", config.get("bottomDate"));

		model.put("article", article);

		return new Result(this.getSuccessView(), model);
	}

}
