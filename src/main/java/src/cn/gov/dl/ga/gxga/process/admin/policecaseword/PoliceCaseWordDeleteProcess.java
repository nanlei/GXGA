package cn.gov.dl.ga.gxga.process.admin.policecaseword;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DocWordService;

public class PoliceCaseWordDeleteProcess extends Process {
	private DocWordService docWordService;

	public void setDocWordService(DocWordService docWordService) {
		this.docWordService = docWordService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		String[] wordIds = ((String) request.getAttribute("wordIds"))
				.split(",");

		List<Map<String, Object>> iwList = docWordService.getWordByIds(wordIds);

		for (int i = 0; i < iwList.size(); i++) {
			Map<String, Object> iw = iwList.get(i);

			String filePath = (String) iw.get("filePath");

			new File(servletContext.getRealPath(filePath)).delete();
		}

		// DB
		docWordService.deleteWord(wordIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
