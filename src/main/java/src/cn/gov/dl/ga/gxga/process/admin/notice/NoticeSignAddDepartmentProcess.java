package cn.gov.dl.ga.gxga.process.admin.notice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;

public class NoticeSignAddDepartmentProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		int flag = articleService.checkSignList(request);

		String leaderId = (String) request.getAttribute("leaderId");

		if (flag > 0) {
			model.put("status", "exists");
		} else {
			articleService.addSignDepartment(request, leaderId);

			model.put("status", "true");
		}

		return new Result(this.getSuccessView(), model);
	}
}
