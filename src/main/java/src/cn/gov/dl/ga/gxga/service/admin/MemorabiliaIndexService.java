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

public class MemorabiliaIndexService extends BaseService {
	private static final String SQL_SEARCH_MEMORABILIAINDEX_PREFIX = "select memorabiliaId, memorabiliaContent, memorabiliaOrder, createBy, createByName, date_format(createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, createByIP from doc_memorabilia_index ";
	private static final String SQL_SEARCH_MEMORABILIAINDEX_SUFFIX = "order by ";

	public PagingList searchMemorabiliaIndex(HttpServletRequest request)
			throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String memorabiliaContent = params.get("memorabiliaContent");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "constantOrder"
				: sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(
				SQL_SEARCH_MEMORABILIAINDEX_PREFIX,
				SQL_SEARCH_MEMORABILIAINDEX_SUFFIX + sortField + " "
						+ sortOrder);

		helper.setParam(StringUtils.isNotEmpty(memorabiliaContent),
				"memorabiliaContent like concat('%',?,'%')", memorabiliaContent);

		return helper;
	}

	public int createMemorabiliaIndex(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"doc_memorabilia_index").usingGeneratedKeyColumns(
				"memorabiliaId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String memorabiliaContent = params.get("memorabiliaContent");
		String memorabiliaOrder = params.get("memorabiliaOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("memorabiliaContent", memorabiliaContent);
		parameters.put("memorabiliaOrder", memorabiliaOrder);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_MEMORABILIAINDEX_BY_ID = "select * from doc_memorabilia_index where memorabiliaId=?";

	public HashMap<String, Object> getMemorabiliaIndexById(String memorabiliaId) {
		return (HashMap<String, Object>) jt.queryForMap(
				SQL_GET_MEMORABILIAINDEX_BY_ID, memorabiliaId);
	}

	private static final String SQL_UPDATE_MEMORABILIAINDEX_BY_ID = "update doc_memorabilia_index set memorabiliaContent=?, memorabiliaOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where memorabiliaId=?";

	public int updateMemorabiliaIndexById(Object[] parameters) {
		return jt.update(SQL_UPDATE_MEMORABILIAINDEX_BY_ID, parameters);
	}

	private static final String SQL_DELETE_MEMORABILIAINDEX = "delete from doc_memorabilia_index where memorabiliaId=?";

	public void deleteMemorabiliaIndex(final String[] memorabiliaIds) {
		jt.batchUpdate(SQL_DELETE_MEMORABILIAINDEX,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, Integer.parseInt(memorabiliaIds[i]));
					}

					@Override
					public int getBatchSize() {
						return memorabiliaIds.length;
					}
				});
	}
}
