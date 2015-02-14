package cn.gov.dl.ga.gxga.process.admin.article;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class ArticleDeleteImageProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleId = (String) request.getAttribute("articleId");

		int i_articleId = 0;

		if (StringUtils.isEmpty(articleId)) {

			String articleType = (String) request.getAttribute("articleType");
			String articleCode = (String) request.getAttribute("articleCode");

			HashMap<String, Object> article = articleService
					.getArticleByTypeAndCode(articleType, articleCode);

			i_articleId = (Integer) article.get("articleId");
		} else {
			i_articleId = Integer.parseInt(articleId);
		}

		final String[] imageIds = ((String) request.getAttribute("imageIds"))
				.split(",");

		articleService.deleteImagesFromArticle(i_articleId, imageIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
