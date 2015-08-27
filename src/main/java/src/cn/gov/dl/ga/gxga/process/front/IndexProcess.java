package cn.gov.dl.ga.gxga.process.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class IndexProcess extends Process {
	private static final Logger logger = LoggerFactory
			.getLogger(IndexProcess.class);

	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("{} {}", CoreUtil.getIPAddr(request), "access index page");

		// Top News
		Map<String, Object> topNews = indexService
				.getTopNews(Constant.ARTICLETYPE_IMAGENEWS);

		// Image News
		List<Map<String, Object>> imageNewsList = indexService
				.getImageNewsForIndex(Constant.ARTICLETYPE_IMAGENEWS);

		// Article
		List<Map<String, Object>> branchFileList = indexService
				.getArticleByType(Constant.ARTICLETYPE_BRANCHFILE);
		List<Map<String, Object>> superiorFileList = indexService
				.getArticleByType(Constant.ARTICLETYPE_SUPERIORFILE);
		List<Map<String, Object>> noticeList = indexService
				.getArticleByType(Constant.ARTICLETYPE_NOTICE);
		List<Map<String, Object>> workReportList = indexService
				.getArticleByType(Constant.ARTICLETYPE_WORKREPORT);
		List<Map<String, Object>> politicalReportList = indexService
				.getArticleByType(Constant.ARTICLETYPE_POLITICALREPORT);
		List<Map<String, Object>> legalList = indexService
				.getArticleByType(Constant.ARTICLETYPE_LEGAL);
		List<Map<String, Object>> disciplineList = indexService
				.getArticleByType(Constant.ARTICLETYPE_DISCIPLINE);
		List<Map<String, Object>> policeCaseList = indexService.getPoliceCase();
		List<Map<String, Object>> experienceList = indexService
				.getArticleByType(Constant.ARTICLETYPE_EXPERIENCE);
		List<Map<String, Object>> policeCultureList = indexService
				.getArticleByType(Constant.ARTICLETYPE_POLICECULTURE);
		List<Map<String, Object>> evaluationList = indexService
				.getArticleByType(Constant.ARTICLETYPE_EVALUATION);

		// Link
		List<Map<String, Object>> qgkList = indexService
				.getLinkByType(Constant.LINKTYPE_QGK);
		List<Map<String, Object>> stkList = indexService
				.getLinkByType(Constant.LINKTYPE_STK);
		List<Map<String, Object>> sjkList = indexService
				.getLinkByType(Constant.LINKTYPE_SJK);
		List<Map<String, Object>> fjkList = indexService
				.getLinkByType(Constant.LINKTYPE_FJK);
		List<Map<String, Object>> qgdhList = indexService
				.getLinkByType(Constant.LINKTYPE_QGDH);
		List<Map<String, Object>> sndhList = indexService
				.getLinkByType(Constant.LINKTYPE_SNDH);
		List<Map<String, Object>> sjdhList = indexService
				.getLinkByType(Constant.LINKTYPE_SJDH);
		List<Map<String, Object>> sjzsbmdhList = indexService
				.getLinkByType(Constant.LINKTYPE_SJZSBMDH);
		List<Map<String, Object>> qxfjdhList = indexService
				.getLinkByType(Constant.LINKTYPE_QXFJDH);

		// MEMORABILIA INDEX
		List<Map<String, Object>> memorabiliaIndexList = indexService
				.getMemorabiliaIndex();

		// Rank
		List<Map<String, Object>> rankList = indexService.getRank();

		// Latest
		List<Map<String, Object>> latestArticleList = indexService
				.getLatestArticles();

		// IssueWord
		List<Map<String, Object>> issueWordList = indexService
				.getIssueWordList();

		// Birthday
		List<Map<String, Object>> birthdayList = indexService.getBirthdayList();

		// Emergency Notice
		List<Map<String, Object>> emergencyNoticeList = indexService
				.getEmergencyNoticeList();

		// Job
		List<Map<String, Object>> jobList = indexService.getJobList();

		HashMap<String, Object> model = new HashMap<String, Object>();

		model.put("topNews", topNews);

		model.put("imageNewsList", imageNewsList);

		model.put("branchFileList", branchFileList);
		model.put("superiorFileList", superiorFileList);
		model.put("noticeList", noticeList);
		model.put("workReportList", workReportList);
		model.put("politicalReportList", politicalReportList);
		model.put("legalList", legalList);
		model.put("disciplineList", disciplineList);
		model.put("policeCaseList", policeCaseList);
		model.put("experienceList", experienceList);
		model.put("policeCultureList", policeCultureList);
		model.put("evaluationList", evaluationList);

		model.put("qgkList", qgkList);
		model.put("stkList", stkList);
		model.put("sjkList", sjkList);
		model.put("fjkList", fjkList);
		model.put("qgdhList", qgdhList);
		model.put("sndhList", sndhList);
		model.put("sjdhList", sjdhList);
		model.put("sjzsbmdhList", sjzsbmdhList);
		model.put("qxfjdhList", qxfjdhList);

		model.put("memorabiliaIndexList", memorabiliaIndexList);

		model.put("rankList", rankList);

		model.put("latestArticleList", latestArticleList);

		model.put("issueWordList", issueWordList);

		model.put("birthdayList", birthdayList);

		model.put("emergencyNoticeList", emergencyNoticeList);

		model.put("jobList", jobList);

		return new Result(this.getSuccessView(), model);
	}
}
