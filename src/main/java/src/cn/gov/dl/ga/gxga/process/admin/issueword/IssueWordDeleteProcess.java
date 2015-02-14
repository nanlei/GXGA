package cn.gov.dl.ga.gxga.process.admin.issueword;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.IssueWordService;

public class IssueWordDeleteProcess extends Process {
	private IssueWordService issueWordService;

	public void setIssueWordService(IssueWordService issueWordService) {
		this.issueWordService = issueWordService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		String[] iwIds = ((String) request.getAttribute("iwIds")).split(",");

		List<Map<String, Object>> iwList = issueWordService
				.getIssueByIds(iwIds);

		for (int i = 0; i < iwList.size(); i++) {
			Map<String, Object> iw = iwList.get(i);

			String filePath = (String) iw.get("filePath");

			new File(servletContext.getRealPath(filePath)).delete();
		}

		// DB
		issueWordService.deleteIssue(iwIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
