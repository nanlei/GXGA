package cn.gov.dl.ga.gxga.process.admin.notice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.model.RedHeadNotice;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.JSONMaker;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class NoticeRedHeadCreateProcess extends Process {
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

		String topUnit = params.get("topUnit");
		String noticeNo = params.get("noticeNo");
		String topDate = params.get("topDate");
		String noticeHost = params.get("noticeHost");
		String noticeContact = params.get("noticeContact");
		String noticePhone = params.get("noticePhone");
		String bottomDate = params.get("bottomDate");

		String redHeadConfig = JSONMaker.makeJson(new RedHeadNotice(topUnit,
				noticeNo, topDate, noticeHost, noticeContact, noticePhone,
				bottomDate));

		articleService.createRedHeadArticle(request,
				Constant.ARTICLETYPE_NOTICE, redHeadConfig);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
