package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import cn.gov.dl.ga.gxga.util.SqlHelper;

public class JobHeaderService extends BaseService {
	private static final String SQL_SEARCH_JOBHEADER_BY_PREFIX = "select jh.jobId, jh.jobTitle, jh.jobOrder, jh.jobImageUrl, jh.createByName, date_format(jh.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, jh.createByIP from doc_job_header jh ";
	private static final String SQL_SEARCH_JOBHEADER_BY_SUFFIX = "order by ";

	public PagingList searchJobHeader(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String jobTitle = params.get("jobTitle");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_JOBHEADER_BY_PREFIX,
				SQL_SEARCH_JOBHEADER_BY_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(StringUtils.isNotEmpty(jobTitle),
				"jh.jobTitle like concat('%',?,'%')", jobTitle);

		return helper;
	}

	public int createJobHeader(HttpServletRequest request, String jobImageUrl)
			throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				jobImageUrl);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_job_header").usingGeneratedKeyColumns("jobId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String jobImageUrl) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String jobTitle = (String) request.getAttribute("jobTitle");
		String jobOrder = (String) request.getAttribute("jobOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("jobTitle", jobTitle);
		parameters.put("jobImageUrl", jobImageUrl);
		parameters.put("jobOrder", jobOrder);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_JOB_HEADER_BY_ID = "select * from doc_job_header where jobId=?";

	public HashMap<String, Object> getJobHeaderById(String jobId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_JOB_HEADER_BY_ID, jobId);
	}

	private static final String SQL_UPDATE_JOB_HEADER_BY_ID = "update doc_job_header set jobTitle=?, jobOrder=?, jobImageUrl=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where jobId=?";
	private static final String SQL_UPDATE_JOB_HEADER_NO_URL_BY_ID = "update doc_job_header set jobTitle=?, jobOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where jobId=?";

	public int updateHeader(HttpServletRequest request, String jobImageUrl) {
		if (StringUtils.isEmpty(jobImageUrl)) {
			Object[] parameters = buildUpdateCondition(request, null);
			return jt.update(SQL_UPDATE_JOB_HEADER_NO_URL_BY_ID, parameters);
		} else {
			Object[] parameters = buildUpdateCondition(request, jobImageUrl);
			return jt.update(SQL_UPDATE_JOB_HEADER_BY_ID, parameters);
		}
	}

	@SuppressWarnings("unchecked")
	private Object[] buildUpdateCondition(HttpServletRequest request,
			String jobImageUrl) {
		String jobId = (String) request.getAttribute("jobId");
		String jobTitle = (String) request.getAttribute("jobTitle");
		String jobOrder = (String) request.getAttribute("jobOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		if (StringUtils.isEmpty(jobImageUrl)) {
			return new Object[] { jobTitle, jobOrder, createBy, createByName,
					createByIP, jobId };
		} else {
			return new Object[] { jobTitle, jobOrder, jobImageUrl, createBy,
					createByName, createByIP, jobId };
		}
	}

	private static final String SQL_DELETE_JOB_HEADER_BY_ID = "delete from doc_job_header where jobId=?";
	private static final String SQL_DELETE_JOB_CATEGORY_BY_JOB_ID = "delete from doc_job_category where jobId=?";
	private static final String SQL_GET_JOB_CATEGORY_BY_JOB_ID = "select jobCategoryId from doc_job_category where ";
	private static final String SQL_DELETE_ARTICLE_BY_JOB_CATEGORY_ID = "delete from doc_article where jobCategoryId=?";

	public void deleteJobHeaderById(final String[] jobIds) {

		SqlHelper helper = new SqlHelper();
		String whereIn = helper.buildWhereIn("jobId", jobIds);
		List<Map<String, Object>> jobCategoryIdList = jt
				.queryForList(SQL_GET_JOB_CATEGORY_BY_JOB_ID + whereIn);
		for (int i = 0; i < jobCategoryIdList.size(); i++) {
			int jobCategoryId = (Integer) jobCategoryIdList.get(i).get(
					"jobCategoryId");
			jt.update(SQL_DELETE_ARTICLE_BY_JOB_CATEGORY_ID, jobCategoryId);
		}

		jt.batchUpdate(SQL_DELETE_JOB_CATEGORY_BY_JOB_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(jobIds[i]));
					}

					@Override
					public int getBatchSize() {
						return jobIds.length;
					}
				});

		jt.batchUpdate(SQL_DELETE_JOB_HEADER_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(jobIds[i]));
					}

					@Override
					public int getBatchSize() {
						return jobIds.length;
					}
				});

	}

	// Extend
	private static final String SQL_GET_JOB_HEADER = "select jh.jobId as id, jh.jobTitle as text from doc_job_header jh order by jh.jobOrder asc";

	public List<Map<String, Object>> getJobHeader() {
		return jt.queryForList(SQL_GET_JOB_HEADER);
	}

	private static final String SQL_GET_JOB_HEADER_LIST_BY_IDS = "select jh.jobId, jh.jobImageUrl from doc_job_header jh where ";

	public List<Map<String, Object>> getJobHeaderListByIds(final String[] jobIds) {
		SqlHelper helper = new SqlHelper();
		String whereIn = helper.buildWhereIn("jobId", jobIds);
		return jt.queryForList(SQL_GET_JOB_HEADER_LIST_BY_IDS + whereIn);
	}
}
