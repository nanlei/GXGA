package cn.gov.dl.ga.gxga.process.front.department;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class DepartmentArticleProcess extends Process {
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

		Map<String, Object> dc = indexService.getDcNameByArticleId(articleId);
		dc.put("dcCode", (String) request.getAttribute("departmentCode"));

		int videoId = (Integer) article.get("videoId");

		if (videoId > 0) {
			model.put("video", indexService.getVideoById(videoId));
		}

		model.put("article", article);
		model.put("dc", dc);

		model.put("UserAgent", request.getHeader("User-Agent"));

		return new Result(this.getSuccessView(), model);
	}

}
