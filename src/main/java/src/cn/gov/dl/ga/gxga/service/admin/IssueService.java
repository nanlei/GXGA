package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

public class IssueService extends BaseService {
	private static final String SQL_SEARCH_ISSUE_PREFIX = "select i.issueId, i.issueContent, i.issueOrder, date_format(i.issueDate,'%Y-%m-%d') as issueDate, c.constantName as issueStatus, i.createBy, i.createByName, date_format(i.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, i.createByIP, d.departmentName from doc_issue i, hr_department d, sys_constant c ";
	private static final String SQL_SEARCH_ISSUE_SUFFIX = "order by i.issueDate desc, ";

	public PagingList searchIssue(HttpServletRequest request) throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String issueDate = params.get("issueDate");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "articleOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_ISSUE_PREFIX,
				SQL_SEARCH_ISSUE_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "i.departmentId=d.departmentId");
		helper.setParam(true,
				"i.issueStatus=c.constantValue and c.constantType='ISSUESTATUS'");
		if (StringUtils.isNotEmpty(issueDate)) {
			helper.setParam(true, "date_format(i.issueDate,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(issueDate)));
		}

		return helper;
	}

	public int createIssue(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_issue").usingGeneratedKeyColumns("issueId");

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

		int departmentId = (Integer) loginUser.get("departmentId");

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String issueDate = params.get("issueDate");
		String issueContent = params.get("issueContent");
		String issueOrder = params.get("issueOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Date d_issueDate = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);
		parameters.put("issueDate", new Timestamp(d_issueDate.getTime()));
		parameters.put("issueContent", issueContent);
		parameters.put("issueOrder", issueOrder);
		parameters.put("issueStatus", Constant.STS_NEW);
		parameters.put("departmentId", departmentId);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));
		return parameters;
	}

	private static final String SQL_GET_ISSUE_BY_ID = "select * from doc_issue where issueId=?";

	public HashMap<String, Object> getIssueById(String issueId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_ISSUE_BY_ID,
				issueId);
	}

	private static final String SQL_UPDATE_ISSUE_BY_ID = "update doc_issue set issueDate=?, issueContent=?, issueOrder=?, issueStatus=?, departmentId=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where issueId=?";

	public int updateIssue(Object[] parameters) {
		return jt.update(SQL_UPDATE_ISSUE_BY_ID, parameters);
	}

	private static final String SQL_DELETE_ISSUE_BY_ID = "delete from doc_issue where issueId=?";

	public void deleteIssue(final String[] issueIds) {
		jt.batchUpdate(SQL_DELETE_ISSUE_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(issueIds[i]));
					}

					@Override
					public int getBatchSize() {
						return issueIds.length;
					}
				});
	}

	// Extend

	private static final String SQL_GET_ISSUE_BY_IDS = "select i.issueContent from doc_issue i where ";

	public List<Map<String, Object>> getIssueByIds(String[] issueIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_ISSUE_BY_IDS
				+ sqlHelper.buildWhereIn("i.issueId", issueIds));
	}
}
