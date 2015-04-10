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

public class IssueWordService extends BaseService {
	private static final String SQL_SEARCH_ISSUEWORD_PREFIX = "select iw.iwId, iw.filePath, date_format(iw.issueDate,'%Y-%m-%d') as issueDate, iw.createBy, iw.createByName, date_format(iw.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, iw.createByIP from doc_issue_word iw ";
	private static final String SQL_SEARCH_ISSUEWORD_SUFFIX = "order by iwId desc";

	public PagingList searchIssueWord(HttpServletRequest request)
			throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String issueDate = params.get("issueDate");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_ISSUEWORD_PREFIX,
				SQL_SEARCH_ISSUEWORD_SUFFIX);
		if (StringUtils.isNotEmpty(issueDate)) {
			helper.setParam(true, "date_format(iw.issueDate,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(issueDate)));
		}
		return helper;
	}

	public int createIssueWord(HttpServletRequest request, String filePath)
			throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);
		parameters.put("filePath", filePath);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_issue_word").usingGeneratedKeyColumns("iwId");

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

		String issueDate = (String) request.getAttribute("issueDate");
		Date d_issueDate = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("issueDate", new Timestamp(d_issueDate.getTime()));
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_DELETE_IW_BY_ID = "delete from doc_issue_word where iwId=?";

	public void deleteIssue(final String[] iwIds) {
		jt.batchUpdate(SQL_DELETE_IW_BY_ID, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(iwIds[i]));
			}

			@Override
			public int getBatchSize() {
				return iwIds.length;
			}
		});
	}

	// Extend

	private static final String SQL_GET_ISSUE_WORD_BY_IDS = "select iw.filePath from doc_issue_word iw where ";

	public List<Map<String, Object>> getIssueByIds(String[] iwIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_ISSUE_WORD_BY_IDS
				+ sqlHelper.buildWhereIn("iw.iwId", iwIds));
	}
}
