package cn.gov.dl.ga.gxga.process.admin.branchoverview;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class GetLeaderProcess extends Process {
	private static final String ARTICLE_TYPE = "FJGK";// 分局概况
	private static final String ARTICLE_CODE = "LDBZ";// 领导班子

	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		HashMap<String, Object> leader = articleService
				.getArticleByTypeAndCode(ARTICLE_TYPE, ARTICLE_CODE);

		model.put("leader", leader);

		return new Result(this.getSuccessView(), model);
	}

}
