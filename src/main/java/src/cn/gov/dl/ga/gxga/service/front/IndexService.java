package cn.gov.dl.ga.gxga.service.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class IndexService extends BaseService {
	private static final String SQL_GET_ARTICLE_BY_TYPE = "select articleId, articleTitle, articleBizType, articleDate from doc_article where articleType=? order by articleDate desc limit 0,8";

	public List<Map<String, Object>> getArticleByType(String articleType) {
		return jt.queryForList(SQL_GET_ARTICLE_BY_TYPE, articleType);
	}

	private static final String SQL_GET_ARTICLE_LIST_BY_TYPE = "select articleId, articleType, articleTitle, articleBizType, date_format(articleDate,'%Y-%m-%d') as articleDate, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where articleType=? order by articleDate desc, createByTime desc";

	public PagingList getArticleListByType(HttpServletRequest request, String articleType) {
		return getPagingList(SQL_GET_ARTICLE_LIST_BY_TYPE, request, 30, new Object[] { articleType });
	}

	private static final String SQL_GET_ISSUE_WORD_LIST = "select 'ISSUEWORD' as articleType,filePath, date_format(issueDate,'%Y-%m-%d') as issueDate from doc_issue_word order by issueDate desc";

	public PagingList getIssueWordList(HttpServletRequest request) {
		return getPagingList(SQL_GET_ISSUE_WORD_LIST, request, 30);
	}

	private static final String SQL_GET_ARTICLE_BY_TYPE_AND_CODE = "select articleTitle, articleCode, articleContent from doc_article where articleType=? and articleCode=?";

	public Map<String, Object> getArticleByTypeAndCode(String articleType, String articleCode) {
		return jt.queryForMap(SQL_GET_ARTICLE_BY_TYPE_AND_CODE, new Object[] { articleType, articleCode });
	}

	private static final String SQL_GET_ARTICLE_BY_TYPE_AND_CODE_FOR_LIST = "select articleId, articleTitle, articleType, articleCode, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where articleType=? and articleCode=? order by articleOrder asc limit 0,8";

	public List<Map<String, Object>> getArticleListByTypeAndCode(String articleType, String articleCode) {
		return jt.queryForList(SQL_GET_ARTICLE_BY_TYPE_AND_CODE_FOR_LIST, articleType, articleCode);
	}

	private static final String SQL_GET_ARTICLE_BY_TYPE_AND_CODE_FOR_SUB_LIST = "select articleId, articleTitle, articleType, articleCode, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where articleType=? and articleCode=? order by articleOrder asc";

	public PagingList getArticleSubListByTypeAndCode(HttpServletRequest request, String articleType,
			String articleCode) {
		return getPagingList(SQL_GET_ARTICLE_BY_TYPE_AND_CODE_FOR_SUB_LIST, request, 30,
				new Object[] { articleType, articleCode });
	}

	private static final String SQL_GET_ARTICLE_BY_DCID_FOR_LIST = "select articleId, articleTitle, articleType, articleCode,date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where dcId=? order by createByTime desc limit 0,8";

	public List<Map<String, Object>> getArticleListByDcId(String dcId) {
		return jt.queryForList(SQL_GET_ARTICLE_BY_DCID_FOR_LIST, dcId);
	}

	private static final String SQL_GET_ARTICLE_BY_ID = "select articleId, articleType, articleCode, articleTitle, articleContent, articleBizType, redHeadConfig, videoId, date_format(articleDate,'%Y-%m-%d') as articleDate, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where articleId=?";

	public Map<String, Object> getArticleById(String articleId) {
		return jt.queryForMap(SQL_GET_ARTICLE_BY_ID, new Object[] { articleId });
	}

	private static final String SQL_GET_RANK = "select d.departmentName as departmentName, r.rankValue as rankValue from fun_rank r, hr_department d where r.departmentId=d.departmentId order by r.rankOrder asc";

	public List<Map<String, Object>> getRank() {
		return jt.queryForList(SQL_GET_RANK);
	}

	private static final String SQL_GET_LINK_BY_TYPE = "select linkUrl, linkDescription from fun_link where linkType=? order by linkOrder asc";

	public List<Map<String, Object>> getLinkByType(String linkType) {
		return jt.queryForList(SQL_GET_LINK_BY_TYPE, linkType);
	}

	private static final String SQL_GET_LATEST_ARTICLES = "select articleId, articleTitle, articleType, date_format(articleDate,'%Y-%m-%d') as articleDate, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where length(articleCode)=0 order by articleDate desc, createByTime desc limit 0,20";
	private static final String SQL_GET_LATEST = "select articleId, articleTitle, articleType, date_format(articleDate,'%Y-%m-%d') as articleDate, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where length(articleCode)=0 order by articleDate desc, createByTime desc";

	public List<Map<String, Object>> getLatestArticles() {
		return jt.queryForList(SQL_GET_LATEST_ARTICLES);
	}

	public PagingList getLatest(HttpServletRequest request) {
		return getPagingList(SQL_GET_LATEST, request, 30);
	}

	private static final String SQL_GET_ISSUE_WORD = "select filePath, date_format(issueDate,'%Y-%m-%d') as issueDate from doc_issue_word order by issueDate desc limit 0,5";

	public List<Map<String, Object>> getIssueWordList() {
		return jt.queryForList(SQL_GET_ISSUE_WORD);
	}

	private static final String SQL_GET_DOWNLOAD = "select downloadId, downloadDescription,date_format(createByTime,'%Y-%m-%d') as createByTime from fun_download order by downloadOrder asc";

	public PagingList getDownlodList(HttpServletRequest request) {
		return getPagingList(SQL_GET_DOWNLOAD, request, 30);
	}

	private static final String SQL_GET_ARTICLE_CATEGORY_BY_TYPE = "select articleCode,c.constantName as articleCodeName from doc_article a, sys_constant c where a.articleType=? and a.articleCode=c.constantValue and c.constantType=? group by a.articleCode order by c.constantOrder asc";

	public List<Map<String, Object>> getArticleCategoryByType(String articleType) {
		return jt.queryForList(SQL_GET_ARTICLE_CATEGORY_BY_TYPE, articleType, articleType);
	}

	private static final String SQL_GET_CONTACT = "select c.contactId, d.departmentName as departmentName, c.subDepartment, c.name, p.positionName, c.internalNo, c.externalNo, c.mobile, c.virtualNo, c.unicomNo, c.unicomVirtualNo, c.officeNo, c.homePhone from fun_contact c, hr_department d, hr_position p where c.departmentId=d.departmentId and c.positionId=p.positionId and c.departmentId=? order by c.contactOrder asc";

	public List<Map<String, Object>> getContact(String departmentId) {
		return jt.queryForList(SQL_GET_CONTACT, departmentId);
	}

	// DUTY
	private static final String SQL_GET_DUTY_FOR_INDEX = "select date_format(dutyDate,'%Y-%m-%d') as dutyDate, dutyManager, dutyLeader, dutyPolice from fun_duty d where d.dutyDate>=date_format(now(),'%Y-%m-%d') order by d.dutyDate limit 0,3";

	public List<Map<String, Object>> getDutyForIndex() {
		return jt.queryForList(SQL_GET_DUTY_FOR_INDEX);
	}

	private static final String SQL_GET_DUTY_LIST = "select date_format(dutyDate,'%Y-%m-%d') as dutyDate, dutyManager, dutyLeader, dutyPolice from fun_duty d where date_format(d.dutyDate,'%Y%m')=date_format(now(),'%Y%m') order by d.dutyDate";

	public List<Map<String, Object>> getDuty() {
		return jt.queryForList(SQL_GET_DUTY_LIST);
	}

	private static final String SQL_GET_DUTY_LIST_FOR_YEAR_AND_MONTH = "select date_format(dutyDate,'%Y-%m-%d') as dutyDate, dutyManager, dutyLeader, dutyPolice from fun_duty d where date_format(d.dutyDate,'%Y%m')=date_format(?,'%Y%m') order by d.dutyDate";

	public List<Map<String, Object>> getDuty(String yearAndMonth) {
		return jt.queryForList(SQL_GET_DUTY_LIST_FOR_YEAR_AND_MONTH, yearAndMonth + "01");
	}

	private static final String SQL_GET_DUTY_PLAN_LIST = "select d.departmentName, dp.dpId, dp.dpUrl,date_format(dp.createByTime,'%Y-%m-%d') as createByTime from hr_department d, fun_duty_plan dp where d.departmentId=dp.departmentId";

	public List<Map<String, Object>> getDutyPlan() {
		return jt.queryForList(SQL_GET_DUTY_PLAN_LIST);
	}

	// Top News
	private static final String SQL_GET_TOP_NEWS = "select articleId, articleTitle from doc_article where articleType=? order by articleDate desc, articleOrder desc limit 0,1";

	public Map<String, Object> getTopNews(String articleType) {
		List<Map<String, Object>> list = jt.queryForList(SQL_GET_TOP_NEWS, articleType);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	// Image News
	private static final String SQL_GET_IMAGE_ARTICLE = "select a.articleId from doc_article a where a.articleType=? order by articleDate desc, articleOrder desc limit 1,15";
	private static final String SQL_GET_IMAGE_ARTICLE_ID = "select ai.articleId, ai.imageId from doc_article_image ai, doc_article a where ai.articleId=a.articleId and ";
	private static final String SQL_GET_IMAGE_NEWS_FOR_INDEX_NEWS = "select a.articleId, a.articleTitle from doc_article a where a.articleType=? and a.articleId=?";
	private static final String SQL_GET_IMAGE_NEWS_FOR_INDEX_IMAGE = "select imageUrl from doc_image i where i.imageId=?";

	public List<Map<String, Object>> getImageNewsForIndex(String articleType) {
		List<Map<String, Object>> list = jt.queryForList(SQL_GET_IMAGE_ARTICLE, articleType);

		if (list.size() == 0) {
			return null;
		} else {
			SqlHelper sqlHelper = new SqlHelper();

			String where = sqlHelper.buildWhereIn("ai.articleId", "articleId", list);

			List<Map<String, Object>> aiList = jt.queryForList(SQL_GET_IMAGE_ARTICLE_ID + where
					+ " group by ai.articleId order by a.articleDate desc, a.articleOrder desc");

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < aiList.size(); i++) {
				int articleId = (Integer) aiList.get(i).get("articleId");
				int imageId = (Integer) aiList.get(i).get("imageId");
				Map<String, Object> news = jt.queryForList(SQL_GET_IMAGE_NEWS_FOR_INDEX_NEWS, articleType, articleId)
						.get(0);
				String imageUrl = jt.queryForObject(SQL_GET_IMAGE_NEWS_FOR_INDEX_IMAGE, String.class, imageId);

				Map<String, Object> resultMap = new HashMap<String, Object>();

				resultMap.put("articleId", news.get("articleId"));
				resultMap.put("articleTitle", news.get("articleTitle"));
				resultMap.put("imageUrl", imageUrl);

				resultList.add(resultMap);
			}

			return resultList;
		}

	}

	// Memorabilia Index
	private static final String SQL_GET_MEMORABILIA_INDEX = "select memorabiliaContent from doc_memorabilia_index order by memorabiliaOrder asc";

	public List<Map<String, Object>> getMemorabiliaIndex() {
		return jt.queryForList(SQL_GET_MEMORABILIA_INDEX);
	}

	// Extend
	private static final String SQL_GET_ATTACHMENT_BY_ARTICLEID = "select a.attachmentId, a.attachmentName from doc_attachment a, doc_article_attachment aa where a.attachmentId=aa.attachmentId and aa.articleId=?";

	public List<Map<String, Object>> getAttachmentByArticleId(String articleId) {
		return jt.queryForList(SQL_GET_ATTACHMENT_BY_ARTICLEID, articleId);
	}

	private static final String SQL_GET_ATTACHMENT_BY_ID = "select attachmentName, attachmentUrl from doc_attachment where attachmentId=?";

	public Map<String, Object> getAttachmentById(String attachmentId) {
		return jt.queryForMap(SQL_GET_ATTACHMENT_BY_ID, attachmentId);
	}

	private static final String SQL_GET_IMAGE_BY_ARTICLEID = "select i.imageId, i.imageName, i.imageUrl from doc_image i, doc_article_image ai where i.imageId=ai.imageId and ai.articleId=?";

	public List<Map<String, Object>> getImageByArticleId(String articleId) {
		return jt.queryForList(SQL_GET_IMAGE_BY_ARTICLEID, articleId);
	}

	// Department
	private static final String SQL_GET_DEPARTMENTID_BY_CODE = "select departmentId from hr_department where departmentCode=?";

	public String getDepartmentIdByCode(String departmentCode) {
		return jt.queryForObject(SQL_GET_DEPARTMENTID_BY_CODE, new Object[] { departmentCode }, String.class);
	}

	private static final String SQL_GET_DEPARTMENT_LIST = "select departmentId, departmentName, departmentCode from hr_department where departmentId>1 order by departmentOrder asc";

	public List<Map<String, Object>> getDepartmentList() {
		return jt.queryForList(SQL_GET_DEPARTMENT_LIST);
	}

	private static final String SQL_GET_DEPARTMENT_CATEGORY_BY_DEPARTMENTID = "select dcId, dcName from doc_department_category where departmentId=? order by dcOrder asc";

	public List<Map<String, Object>> getDepartmentCategoryByDeparmentId(String departmentId) {
		return jt.queryForList(SQL_GET_DEPARTMENT_CATEGORY_BY_DEPARTMENTID, departmentId);
	}

	private static final String SQL_GET_DEPARTMENT_CATEGORY_NAME_BY_ID = "select dcName from doc_department_category where dcId=?";

	public String getDepartmentCategoryNameById(String dcId) {
		return jt.queryForObject(SQL_GET_DEPARTMENT_CATEGORY_NAME_BY_ID, new Object[] { dcId }, String.class);
	}

	private static final String SQL_GET_ARTICLE_LIST_BY_DCID = "select articleId, articleTitle, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where dcId>0 and dcId=? order by createByTime desc";

	public PagingList getArticleListByDcId(HttpServletRequest request, String dcId) {
		return getPagingList(SQL_GET_ARTICLE_LIST_BY_DCID, request, 30, new Object[] { dcId });
	}

	private static final String SQL_GET_DC_NAME_AND_CODE_BY_ARTICLEID = "select dc.dcName from doc_department_category dc, doc_article a where dc.dcId=a.dcId and a.articleId=?";

	public Map<String, Object> getDcNameByArticleId(String articleId) {
		return jt.queryForMap(SQL_GET_DC_NAME_AND_CODE_BY_ARTICLEID, articleId);
	}

	// Sign
	private static final String SQL_GET_SIGN_LIST_BY_ARTICLE_ID = "select d.departmentName from doc_article_sign a, hr_department d where a.departmentId=d.departmentId and a.STATUS='RUN' and a.articleId=?";

	public List<Map<String, Object>> getSignListByArticleId(String articleId) {
		return jt.queryForList(SQL_GET_SIGN_LIST_BY_ARTICLE_ID, articleId);
	}

	// Mailbox
	private static final String SQL_GET_MAIL_LIST = "select m.mailId, m.mailSubject, date_format(m.createByTime,'%Y-%m-%d') as createByTime, c.constantName as sts, m.commentByName from fun_mailbox m, sys_constant c where m.sts=c.constantValue and c.constantType='MAILSTATUS' and m.isPublic='Y' order by m.createByTime desc";

	public PagingList getMailList(HttpServletRequest request) {
		return getPagingList(SQL_GET_MAIL_LIST, request, 30);
	}

	private static final String SQL_GET_MAIL_BY_ID = "select * from fun_mailbox where mailId=?";

	public Map<String, Object> getMailById(String mailId) {
		return jt.queryForMap(SQL_GET_MAIL_BY_ID, mailId);
	}

	// Birthday
	private static final String SQL_GET_BIRTHDAY_LIST = "select e.employeeName, d.departmentName from hr_employee e, hr_department d where e.departmentId=d.departmentId and date_format(employeeBirth,'%m-%d')=date_format(now(),'%m-%d')";

	public List<Map<String, Object>> getBirthdayList() {
		return jt.queryForList(SQL_GET_BIRTHDAY_LIST);
	}

	// Emergency Notice
	private static final String SQL_GET_EMERGENCY_NOTICE_LIST = "select noticeTitle, noticeImageUrl, noticeAttachmentUrl, linkModule, linkId, j.jobImageUrl from fun_emergency_notice n left join doc_job_header j on n.linkId=j.jobid and n.linkModule='LINK-JOB' where noticeStatus='RUN' order by noticeOrder asc";

	public List<Map<String, Object>> getEmergencyNoticeList() {
		return jt.queryForList(SQL_GET_EMERGENCY_NOTICE_LIST);
	}

	// Meeting
	private static final String SQL_GET_MEETING_LIST = "select m.meetingSubject, m.meetingRoom, d.departmentName,date_format(m.meetingStartTime,'%Y-%m-%d %H:%i') as meetingStartTime, date_format(m.meetingEndTime,'%Y-%m-%d %H:%i') as meetingEndTime from oa_meeting m, hr_department d where m.departmentId=d.departmentId order by meetingStartTime desc";

	public PagingList getMeetingList(HttpServletRequest request) {
		return getPagingList(SQL_GET_MEETING_LIST, request, 30);
	}

	// Asset Repair
	private static final String SQL_GET_ASSET_REPAIR_LIST = "select ar.arCode, ar.assetName, ar.assetLocation, d.departmentName, date_format(ar.createByTime,'%Y-%m-%d') as createByTime, c.constantName as sts from oa_asset_repair ar, hr_department d, sys_constant c where ar.departmentId=d.departmentId and ar.sts=c.constantValue and c.constantType='ASSETREPAIRSTATUS' order by ar.createByTime desc";

	public PagingList getAssetRepairList(HttpServletRequest request) {
		return getPagingList(SQL_GET_ASSET_REPAIR_LIST, request, 30);
	}

	// Overtime Meal
	private static final String SQL_GET_OVERTIME_MEAL_LIST = "select  d.departmentName, date_format(om.overtimeDate,'%Y-%m-%d') as overtimeDate, c.constantName as sts from oa_overtime_meal om, hr_department d, sys_constant c where om.departmentId=d.departmentId and om.sts=c.constantValue and c.constantType='OVERTIMEMEALSTATUS' order by om.createByTime desc";

	public PagingList getOvertimeMealList(HttpServletRequest request) {
		return getPagingList(SQL_GET_OVERTIME_MEAL_LIST, request, 30);
	}

	// Job
	private static final String SQL_GET_JOB_LIST = "select jh.jobId, jh.jobTitle, jh.jobImageUrl from doc_job_header jh order by jh.jobOrder";

	public List<Map<String, Object>> getJobList() {
		return jt.queryForList(SQL_GET_JOB_LIST);
	}

	private static final String SQL_GET_JOB_CATEGORY_LIST = "select jc.jobCategoryId, jc.jobCategoryTitle from doc_job_category jc where jc.jobId=? order by jc.jobCategoryOrder asc";

	public List<Map<String, Object>> getJobCategoryList(String jobId) {
		return jt.queryForList(SQL_GET_JOB_CATEGORY_LIST, jobId);
	}

	private static final String SQL_GET_ARTILE_LIST_BY_JOB_CATEGORY_ID = "select articleId, articleTitle, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where jobCategoryId=? order by articleId desc limit 0,8";

	public List<Map<String, Object>> getArticleListByJobCategoryId(String jobCategoryId) {
		return jt.queryForList(SQL_GET_ARTILE_LIST_BY_JOB_CATEGORY_ID, jobCategoryId);
	}

	private static final String SQL_GET_JOB_ARTICLES = "select articleId, articleTitle, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where jobCategoryId>0 and jobCategoryId=? order by createByTime desc";

	public PagingList getJobArticles(HttpServletRequest request, String jobCategoryId) {
		return getPagingList(SQL_GET_JOB_ARTICLES, request, 30, new Object[] { jobCategoryId });
	}

	// Video
	private static final String SQL_GET_VIDEO_BY_ID = "select videoId, videoName, videoUrl from doc_video where videoId=?";

	public Map<String, Object> getVideoById(int videoId) {
		return jt.queryForMap(SQL_GET_VIDEO_BY_ID, videoId);
	}

	// Search
	private static final String SQL_SEARCH_FOR_INDEX = "select a.articleId, a.articleType, a.articleCode, a.articleTitle, a.dcId, a.jobCategoryId, date_format(a.createByTime,'%Y-%m-%d') as createByTime, d.departmentCode, jc.jobId from doc_article a left outer join doc_job_category jc on a.jobCategoryId=jc.jobCategoryId, hr_department d where a.departmentId=d.departmentId and a.articleTitle like concat('%',?,'%') order by a.createByTime desc";

	public PagingList searchArticlesForIndex(HttpServletRequest request) {
		String articleTitle = (String) request.getAttribute("searchString");
		return getPagingList(SQL_SEARCH_FOR_INDEX, request, 30, new Object[] { articleTitle });
	}

	// PoliceCase
	private static final String SQL_GET_POLICE_CASE = "select * from ("
			+ "select articleId as articleId, articleTitle as articleTitle, '' as filePath, 'POLICECASE' as articleType, 'ARTICLE' as type, articleDate, date_format(createByTime,'%Y-%m-%d') as createByTime from doc_article where articleType=?"
			+ " union "
			+ "select wordId as articleId, wordTitle as articleTitle, filePath, 'POLICECASE' as articleType, 'WORD' as type, wordDate as articleDate, wordDate as createByTime from doc_word where wordType=?) result "
			+ "order by articleDate desc, createByTime desc";

	public List<Map<String, Object>> getPoliceCase() {
		return jt.queryForList(SQL_GET_POLICE_CASE + INDEX_LIST_LIMT, Constant.ARTICLETYPE_POLICECASE,
				Constant.DOCWORD_POLICECASE);
	}

	public PagingList getPoliceCaseList(HttpServletRequest request) {
		return getPagingList(SQL_GET_POLICE_CASE, request, 30,
				new Object[] { Constant.ARTICLETYPE_POLICECASE, Constant.DOCWORD_POLICECASE });
	}

	// External Transaction
	private static final String SQL_GET_EXTERNAL_TRANSACTION_LIST = "select transactionId, transactionNo, transactionTitle, transactionConfig, transactionStatus, date_format(createByTime,'%Y-%m-%d') as createByTime from oa_external_transaction where transactionType=?";

	private static final String SQL_GET_EXTERNAL_TRANSACTION_BY_ID = "select * from oa_external_transaction where transactionId=?";

	// MinXinWang
	public PagingList getMinXinWangList(HttpServletRequest request) {
		return getPagingList(SQL_GET_EXTERNAL_TRANSACTION_LIST, request, 30,
				new Object[] { Constant.EXTERNALTRANSACTIONTYPE_MXW });
	}

	// MinYiWang
	public PagingList getMinYiWangList(HttpServletRequest request) {
		return getPagingList(SQL_GET_EXTERNAL_TRANSACTION_LIST, request, 30,
				new Object[] { Constant.EXTERNALTRANSACTIONTYPE_MYW });
	}

	// PubComm
	public PagingList getPubCommList(HttpServletRequest request) {
		return getPagingList(SQL_GET_EXTERNAL_TRANSACTION_LIST, request, 30,
				new Object[] { Constant.EXTERNALTRANSACTIONTYPE_PUBCOMM });
	}

	// OfficalMailBox
	public PagingList getOfficalMailBox(HttpServletRequest request) {
		return getPagingList(SQL_GET_EXTERNAL_TRANSACTION_LIST, request, 30,
				new Object[] { Constant.EXTERNALTRANSACTIONTYPE_OFFICALMAILBOX });
	}

	public HashMap<String, Object> getTransactionById(String transactionId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_EXTERNAL_TRANSACTION_BY_ID, transactionId);
	}

	// Monthly Star
	private static final String SQL_GET_MONTHLY_STAR_ARTICLE = "select articleId, articleTitle from doc_article "
			+ "where articleType='MONTHLYSTAR' order by articleDate desc, articleOrder desc limit 1";

	private static final String SQL_GET_MONTHLY_STAR_IMAGE = "select i.imageName, i.imageUrl from doc_article a, doc_article_image ai, doc_image i "
			+ "where a.articleId = ai.articleId and ai.imageId = i.imageId and a.articleId=? "
			+ "order by i.imageId asc";

	public Map<String, Object> getMonthlyStar() {
		return jt.queryForMap(SQL_GET_MONTHLY_STAR_ARTICLE);
	}

	public List<Map<String, Object>> getMonthlyStarImages(String articleId) {
		return jt.queryForList(SQL_GET_MONTHLY_STAR_IMAGE, articleId);
	}
}
