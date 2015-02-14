package cn.gov.dl.ga.gxga.process.front.informationsecurity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class InformationsecurityInfoProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String informationSecurityId = (String) request
				.getAttribute("informationSecurityId");

		Map<String, Object> informationSecurity = indexService
				.getArticleById(informationSecurityId);

		model.put("informationSecurity", informationSecurity);
		model.put("articleType", Constant.ARTICLETYPE_INFORMATIONSECURITY);

		return new Result(this.getSuccessView(), model);
	}

}
