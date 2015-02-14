package cn.gov.dl.ga.gxga.process.admin.imagenews;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class ImageNewsCreateProcess extends Process {
	private static final String ARTICLE_TYPE = "IMAGENEWS";// 图片新闻
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		articleService.createArticle(request, ARTICLE_TYPE);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
