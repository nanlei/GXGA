package cn.gov.dl.ga.gxga.process.admin.issueword;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.IssueService;
import cn.gov.dl.ga.gxga.service.admin.IssueWordService;

public class IssueWordGenerateProcess extends Process {
	private IssueService issueService;
	private IssueWordService issueWordService;

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public void setIssueWordService(IssueWordService issueWordService) {
		this.issueWordService = issueWordService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String[] issueIds = ((String) request.getAttribute("issueIds"))
				.split(",");

		List<Map<String, Object>> issueList = issueService
				.getIssueByIds(issueIds);

		XWPFDocument doc = new XWPFDocument();

		for (int i = 0; i < issueList.size(); i++) {
			Map<String, Object> issue = issueList.get(i);

			String issueContent = (String) issue.get("issueContent");

			XWPFParagraph paragraph = doc.createParagraph();

			paragraph.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			run.setFontSize(18);
			run.setText(issueContent);

			XWPFParagraph emptyParagraph = doc.createParagraph();
			emptyParagraph.createRun();
		}

		ServletContext servletContext = request.getSession()
				.getServletContext();

		String path = servletContext.getContextPath() + "/file/word/issue/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ ".docx";

		String realPath = servletContext.getRealPath(path);

		FileOutputStream fos = new FileOutputStream(new File(realPath));

		doc.write(fos);
		fos.flush();
		fos.close();

		// DB
		issueWordService.createIssueWord(request, path);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
