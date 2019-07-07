package cn.gov.dl.ga.gxga.service.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class JobLineService extends BaseService {
	private static final String SQL_SEARCH_JOB_LINE_BY_JOBID_PREFIX = "select a.articleId, a.articleTitle, a.articleOrder, jh.jobTitle, jc.jobCategoryTitle, c.constantName as articleStatus, a.createBy, a.createByName, date_format(a.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, a.createByIP, a.pageView from doc_article a, sys_constant c, doc_job_header jh, doc_job_category jc ";
	private static final String SQL_SEARCH_JOB_LINE_BY_JOBID_SUFFIX = "order by a.jobCategoryId asc, ";

	public PagingList searchArticlesByJobId(HttpServletRequest request) {
		QueryHelper helper = buildQueryConditionByDcId(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryConditionByDcId(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String jobId = params.get("jobId");
		String jobCategoryId = params.get("jobCategoryId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "articleOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_JOB_LINE_BY_JOBID_PREFIX,
				SQL_SEARCH_JOB_LINE_BY_JOBID_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "a.jobCategoryId=jc.jobCategoryId");
		helper.setParam(true, "jh.jobId=jc.jobId");
		helper.setParam(true, "a.articleStatus=c.constantValue and c.constantType='ARTICLESTATUS'");
		helper.setParam(StringUtils.isNotEmpty(jobCategoryId), "a.jobCategoryId=?", jobCategoryId);
		helper.setParam(StringUtils.isNotEmpty(jobId), "jh.jobId=?", jobId);

		return helper;
	}

	public int createArticleForJobLine(HttpServletRequest request, String videoId) {
		HashMap<String, Object> parameters = buildInsertCondition(request, videoId);

		parameters.put("articleBizType", Constant.ARTICLEBIZTYPE_NOR);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName("doc_article")
				.usingGeneratedKeyColumns("articleId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(HttpServletRequest request, String videoId) {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		int departmentId = 0;
		if (StringUtils.isNotEmpty(params.get("departmentId"))) {
			departmentId = Integer.parseInt(params.get("departmentId"));
		} else {
			departmentId = (Integer) loginUser.get("departmentId");
		}

		String articleTitle = params.get("articleTitle");
		String articleOrder = params.get("articleOrder");
		String jobCategoryId = params.get("jobCategoryId");

		String articleContent = (String) request.getAttribute("articleContent");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("articleType", "");

		parameters.put("articleCode", "");

		parameters.put("jobCategoryId", jobCategoryId);

		parameters.put("articleTitle", articleTitle);
		parameters.put("articleOrder", articleOrder);
		parameters.put("articleContent", articleContent);
		parameters.put("articleStatus", Constant.STS_NEW);
		parameters.put("departmentId", departmentId);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		parameters.put("pageView", 0);
		if (StringUtils.isNotEmpty(videoId)) {
			parameters.put("videoId", videoId);
		} else {
			parameters.put("videoId", "0");
		}
		return parameters;
	}

	private static final String SQL_GET_ARTICLE_BY_ID = "select * from doc_article where articleId=?";

	public HashMap<String, Object> getArticleById(String articleId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_ARTICLE_BY_ID, articleId);
	}

	private static final String SQL_UPDATE_ARTICLE_FOR_JOB_BY_ID = "update doc_article set articleTitle=?, articleContent=?, articleOrder=?, jobCategoryId=?, videoId=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where articleId=?";

	public int updateArtileForJobById(Object[] parameters) {
		return jt.update(SQL_UPDATE_ARTICLE_FOR_JOB_BY_ID, parameters);
	}

	// Extend
	private static final String SQL_GET_JOB_HEADER = "select jobId as id, jobTitle as text from doc_job_header order by jobOrder asc";

	public List<Map<String, Object>> getJobHeader() {
		return jt.queryForList(SQL_GET_JOB_HEADER);
	}

	private static final String GET_JOB_HEADER_ID = "select jobId from doc_job_category where jobCategoryId=?";

	public int getJobHeaderId(String jobCategoryId) {
		return jt.queryForObject(GET_JOB_HEADER_ID, Integer.class, jobCategoryId);
	}

	private static final String SQL_GET_JOB_CATEGORY = "select jobCategoryId as id, jobCategoryTitle as text from doc_job_category where jobId=? order by jobCategoryOrder asc";

	public List<Map<String, Object>> getJobCategory(String jobId) {
		return jt.queryForList(SQL_GET_JOB_CATEGORY, jobId);
	}

	public static final String SQL_GET_JOB_CATEGORY_LIST = "select jobCategoryId as id, jobCategoryTitle as text, jobId from doc_job_category where jobId=(select jobId from doc_job_category where jobCategoryId=?)";

	public List<Map<String, Object>> getJobCategoryList(String jobCategoryId) {
		return jt.queryForList(SQL_GET_JOB_CATEGORY_LIST, jobCategoryId);
	}
}
