package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class JobCategoryService extends BaseService {
	private static final String SQL_SEARCH_JOBCATEGORY_BY_PREFIX = "select jc.jobCategoryId, jh.jobTitle, jc.jobCategoryTitle, jc.jobCategoryOrder, jc.createByName, date_format(jc.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, jc.createByIP from doc_job_category jc, doc_job_header jh ";
	private static final String SQL_SEARCH_JOBCATEGORY_BY_SUFFIX = "order by jc.jobId asc, ";

	public PagingList searchJobCategory(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String jobId = params.get("jobId");
		String jobCategoryTitle = params.get("jobCategoryTitle");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_JOBCATEGORY_BY_PREFIX,
				SQL_SEARCH_JOBCATEGORY_BY_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "jc.jobId=jh.jobId");
		helper.setParam(StringUtils.isNotEmpty(jobId), "jc.jobId=?", jobId);
		helper.setParam(StringUtils.isNotEmpty(jobCategoryTitle),
				"jh.jobCategoryTitle like concat('%',?,'%')", jobCategoryTitle);

		return helper;
	}

	public int createJobCategory(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_job_category").usingGeneratedKeyColumns("categoryId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String jobId = params.get("jobId");
		String jobCategoryTitle = params.get("jobCategoryTitle");
		String jobCategoryOrder = params.get("jobCategoryOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("jobId", jobId);
		parameters.put("jobCategoryTitle", jobCategoryTitle);
		parameters.put("jobCategoryOrder", jobCategoryOrder);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_JOB_CATEGORY_BY_ID = "select * from doc_job_category where jobCategoryId=?";

	public HashMap<String, Object> getJobCategoryById(String jobCategoryId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_JOB_CATEGORY_BY_ID, jobCategoryId);
	}

	private static final String SQL_UPDATE_JOB_CATEGORY_BY_ID = "update doc_job_category set jobId=?, jobCategoryTitle=?, jobCategoryOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where jobCategoryId=?";

	public int updateHeader(Object[] parameters) {
		return jt.update(SQL_UPDATE_JOB_CATEGORY_BY_ID, parameters);
	}

	private static final String SQL_DELETE_JOB_CATEGORY_BY_ID = "delete from doc_job_category where jobCategoryId=?";
	private static final String SQL_DELETE_ARTICLE_BY_JOB_CATEGORY_ID = "delete from doc_article where jobCategoryId=?";

	public void deleteJobCategoryById(final String[] jobCategoryIds) {
		jt.batchUpdate(SQL_DELETE_JOB_CATEGORY_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(jobCategoryIds[i]));
					}

					@Override
					public int getBatchSize() {
						return jobCategoryIds.length;
					}
				});

		jt.batchUpdate(SQL_DELETE_ARTICLE_BY_JOB_CATEGORY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(jobCategoryIds[i]));
					}

					@Override
					public int getBatchSize() {
						return jobCategoryIds.length;
					}
				});
	}

}
