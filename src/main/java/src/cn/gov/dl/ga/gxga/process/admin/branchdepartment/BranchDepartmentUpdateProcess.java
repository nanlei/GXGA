package cn.gov.dl.ga.gxga.process.admin.branchdepartment;

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

public class BranchDepartmentUpdateProcess extends Process {
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
		String dcId = params.get("dcId");
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);
		String articleId = params.get("articleId");

		String videoId = params.get("videoId");

		if (StringUtils.isEmpty(videoId)) {
			videoId = "0";
		}

		Object[] parameters = new Object[] { articleTitle, articleContent,
				articleOrder, dcId, videoId, createBy, createByName,
				createByIP, articleId };

		articleService.updateArtileForDepartmentById(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
