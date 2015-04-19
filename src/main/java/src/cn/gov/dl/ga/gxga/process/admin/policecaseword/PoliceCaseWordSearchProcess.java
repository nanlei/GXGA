package cn.gov.dl.ga.gxga.process.admin.policecaseword;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DocWordService;

public class PoliceCaseWordSearchProcess extends Process {
	private DocWordService docWordService;

	public void setDocWordService(DocWordService docWordService) {
		this.docWordService = docWordService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList policeCaseWords = docWordService.searchWordByType(request,
				Constant.DOCWORD_POLICECASE);

		model.put("total", policeCaseWords.getRowCount());
		model.put("data", policeCaseWords.getList());

		return new Result(this.getSuccessView(), model);
	}

}
