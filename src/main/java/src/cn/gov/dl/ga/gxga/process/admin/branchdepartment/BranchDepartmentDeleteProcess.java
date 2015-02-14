package cn.gov.dl.ga.gxga.process.admin.branchdepartment;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class BranchDepartmentDeleteProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String[] articleIds = ((String) request
				.getAttribute("articleIds")).split(",");

		articleService.deleteArticleById(articleIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
