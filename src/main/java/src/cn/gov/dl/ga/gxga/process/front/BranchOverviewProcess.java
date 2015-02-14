package cn.gov.dl.ga.gxga.process.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class BranchOverviewProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleCode = (String) request.getAttribute("code");

		Map<String, Object> article = null;

		if ("JGSZ".equals(articleCode)) {
			article = indexService.getArticleByTypeAndCode("FJGK", "JGSZ");
		} else if ("LDBZ".equals(articleCode)) {
			article = indexService.getArticleByTypeAndCode("FJGK", "LDBZ");
		} else if ("FJRYB".equals(articleCode)) {
			article = indexService.getArticleByTypeAndCode("FJGK", "FJRYB");
		} else if ("FJDSJ".equals(articleCode)) {
			article = indexService.getArticleByTypeAndCode("FJGK", "FJDSJ");
		} else {
			article = indexService.getArticleByTypeAndCode("FJGK", "JGSZ");
		}

		model.put("article", article);

		return new Result(this.getSuccessView(), model);
	}

}
