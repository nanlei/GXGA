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

public class DocWordService extends BaseService {
	private static final String SQL_SEARCH_DOC_WORD_BY_TYPE_PREFIX = "select w.wordId, w.wordTitle, w.wordDate, w.filePath, w.wordOrder, w.createByName, date_format(w.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, w.createByIP from doc_word w ";
	private static final String SQL_SEARCH_DOC_WORD_BY_TYPE_SUFFIX = "order by ";

	// Search by type
	public PagingList searchWordByType(HttpServletRequest request,
			String wordType) throws Exception {
		QueryHelper helper = buildQueryConditionByWordType(request, wordType);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	@SuppressWarnings("unchecked")
	private QueryHelper buildQueryConditionByWordType(
			HttpServletRequest request, String wordType) throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String wordDate = params.get("wordDate");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int roleId = (Integer) loginUser.get("roleId");
		int userId = (Integer) loginUser.get("userId");
		int departmentId = (Integer) loginUser.get("departmentId");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "wordOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_DOC_WORD_BY_TYPE_PREFIX,
				SQL_SEARCH_DOC_WORD_BY_TYPE_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(true, "w.wordType=?", wordType);
		if (StringUtils.isNotEmpty(wordDate)) {
			helper.setParam(true, "date_format(w.wordDate,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(wordDate)));
		}
		if (roleId != 1) {// Not Super Admin
			helper.setParam(true, "w.departmentId=?", departmentId);
			helper.setParam(true, "w.createBy=?", userId);
		}

		return helper;
	}

	public int createDocWord(HttpServletRequest request, String filePath,
			String wordType) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request,
				wordType);
		parameters.put("filePath", filePath);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_word").usingGeneratedKeyColumns("wordId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request, String wordType) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		int departmentId = (Integer) loginUser.get("departmentId");

		String wordDate = (String) request.getAttribute("wordDate");
		Date d_wordDate = new SimpleDateFormat("yyyy-MM-dd").parse(wordDate);

		String wordTitle = (String) request.getAttribute("wordTitle");
		String wordOrder = (String) request.getAttribute("wordOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("wordType", wordType);
		parameters.put("wordTitle", wordTitle);
		parameters.put("wordDate", new Timestamp(d_wordDate.getTime()));
		parameters.put("wordOrder", wordOrder);
		parameters.put("departmentId", departmentId);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_DELETE_WORD_BY_ID = "delete from doc_word where wordId=?";

	public void deleteWord(final String[] wordIds) {
		jt.batchUpdate(SQL_DELETE_WORD_BY_ID,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(wordIds[i]));
					}

					@Override
					public int getBatchSize() {
						return wordIds.length;
					}
				});
	}

	// Extend
	private static final String SQL_GET_WORD_BY_IDS = "select w.filePath from doc_word w where ";

	public List<Map<String, Object>> getWordByIds(String[] wordIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_WORD_BY_IDS
				+ sqlHelper.buildWhereIn("w.wordId", wordIds));
	}
}
