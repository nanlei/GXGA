package cn.gov.dl.ga.gxga.process.front.talent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class TalentInfoProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String talentId = (String) request.getAttribute("talentId");

		Map<String, Object> talent = indexService.queryArticleById(talentId);

		List<Map<String, Object>> categoryList = indexService
				.getArticleCategoryByType(Constant.ARTICLETYPE_TALENT);

		model.put("talent", talent);
		model.put("articleType", Constant.ARTICLETYPE_TALENT);
		model.put("articleCode", (String) talent.get("articleCode"));
		model.put("categoryList", categoryList);

		return new Result(this.getSuccessView(), model);
	}

}
