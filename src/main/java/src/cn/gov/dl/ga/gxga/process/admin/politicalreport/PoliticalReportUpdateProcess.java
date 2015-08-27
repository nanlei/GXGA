package cn.gov.dl.ga.gxga.process.admin.politicalreport;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class PoliticalReportUpdateProcess extends Process {
	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String articleTitle = params.get("articleTitle");
		String articleContent = (String) request.getAttribute("articleContent");
		String articleOrder = params.get("articleOrder");
		String articleDate = params.get("articleDate");
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);
		String articleId = params.get("articleId");

		if (StringUtils.isNotEmpty(articleDate)) {
			articleDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("yyyy-MM-dd")
							.parse(articleDate));
		} else {
			articleDate = null;
		}

		Object[] parameters = new Object[] { articleTitle, articleContent,
				articleOrder, articleDate, createBy, createByName, createByIP,
				articleId };

		articleService.updateArtileById(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}
}
