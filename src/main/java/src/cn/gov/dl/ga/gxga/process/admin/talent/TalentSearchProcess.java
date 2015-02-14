package cn.gov.dl.ga.gxga.process.admin.talent;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class TalentSearchProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleCode = (String) request.getAttribute("articleCode");

		PagingList articles = articleService.searchArticlesByTypeAndCode(
				request, Constant.ARTICLETYPE_TALENT, articleCode);

		model.put("total", articles.getRowCount());
		model.put("data", articles.getList());

		return new Result(this.getSuccessView(), model);
	}

}
