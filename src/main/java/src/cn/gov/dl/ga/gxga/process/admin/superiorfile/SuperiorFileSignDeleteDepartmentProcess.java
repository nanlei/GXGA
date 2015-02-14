package cn.gov.dl.ga.gxga.process.admin.superiorfile;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class SuperiorFileSignDeleteDepartmentProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		final String articleId = (String) request.getAttribute("articleId");

		final String[] departmentIds = ((String) request
				.getAttribute("departmentIds")).split(",");

		articleService.deleteSignDepartment(articleId, departmentIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
