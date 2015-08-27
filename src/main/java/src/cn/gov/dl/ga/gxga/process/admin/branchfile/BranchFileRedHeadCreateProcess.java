package cn.gov.dl.ga.gxga.process.admin.branchfile;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.ReadHeadBranchFile;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class BranchFileRedHeadCreateProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String branchFileNo = params.get("branchFileNo");
		String articleTitle = params.get("articleTitle");
		String bottomEnding = params.get("bottomEnding");
		String bottomDate = params.get("bottomDate");

		String redHeadConfig = JSONMaker.makeJson(new ReadHeadBranchFile(
				branchFileNo, articleTitle, bottomEnding, bottomDate));

		articleService.createRedHeadArticle(request,
				Constant.ARTICLETYPE_BRANCHFILE, redHeadConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
