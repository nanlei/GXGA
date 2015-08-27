package cn.gov.dl.ga.gxga.process.front.talent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class TalentSubListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");
		
		String articleCode = (String) request.getAttribute("category");

		PagingList subList = indexService.getArticleSubListByTypeAndCode(
				request, Constant.ARTICLETYPE_TALENT, articleCode);

		List<Map<String, Object>> categoryList = indexService
				.getArticleCategoryByType(Constant.ARTICLETYPE_TALENT);

		model.put("subList", subList);
		model.put("categoryList", categoryList);
		model.put("articleCode", articleCode);

		return new Result(this.getSuccessView(), model);
	}

}
