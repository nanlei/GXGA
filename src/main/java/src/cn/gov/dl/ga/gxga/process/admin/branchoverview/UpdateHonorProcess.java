package cn.gov.dl.ga.gxga.process.admin.branchoverview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ArticleService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class UpdateHonorProcess extends Process {
	private static final String ARTICLE_TYPE = "FJGK";// 分局概况
	private static final String ARTICLE_CODE = "FJRYB";// 分局荣誉榜
	
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

		String articleContent = (String) request.getAttribute("articleContent");
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { articleContent, createBy,
				createByName, createByIP, ARTICLE_TYPE, ARTICLE_CODE };

		articleService.updateArtileByTypeAndCode(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
