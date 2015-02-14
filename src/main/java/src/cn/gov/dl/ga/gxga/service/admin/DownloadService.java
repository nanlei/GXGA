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

public class DownloadService extends BaseService {
	private static final String SQL_SEARCH_DOWNLOAD_PREFIX = "select d.downloadId, d.downloadUrl, d.downloadDescription, d.downloadOrder, d.createBy, d.createByName, date_format(d.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, d.createByIP from fun_download d ";
	private static final String SQL_SEARCH_DOWNLOAD_SUFFIX = "order by ";

	public PagingList searchDownload(HttpServletRequest request)
			throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String downloadDescription = params.get("downloadDescription");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "downloadOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_DOWNLOAD_PREFIX,
				SQL_SEARCH_DOWNLOAD_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(StringUtils.isNotEmpty(downloadDescription),
				"l.downloadDescription like concat('%',?,'%')",
				downloadDescription);

		return helper;
	}

	public int createDownload(HttpServletRequest request, String filePath) {
		HashMap<String, Object> parameters = buildInsertCondition(request);
		parameters.put("downloadUrl", filePath);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_download").usingGeneratedKeyColumns("downloadId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String downloadDescription = (String) request
				.getAttribute("downloadDescription");
		String downloadOrder = (String) request.getAttribute("downloadOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("downloadDescription", downloadDescription);
		parameters.put("downloadOrder", Integer.parseInt(downloadOrder));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_DOWNLOAD_BY_ID = "select * from fun_download where downloadId=?";

	public HashMap<String, Object> getDownloadById(String downloadId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_DOWNLOAD_BY_ID,
				downloadId);
	}

	private static final String SQL_UPDATE_DOWNLOAD_BY_ID = "update fun_download set downloadDescription=?, downloadUrl=?, downloadOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where downloadId=?";
	private static final String SQL_UPDATE_DOWNLOAD_NO_URL_BY_ID = "update fun_download set downloadDescription=?, downloadOrder=? where downloadId=?";

	public void updateDownloadById(HttpServletRequest request,
			String downloadUrl) {
		if (StringUtils.isEmpty(downloadUrl)) {
			Object[] parameters = buildUpdateCondition(request);
			jt.update(SQL_UPDATE_DOWNLOAD_NO_URL_BY_ID, parameters);
		} else {
			Object[] parameters = buildUpdateCondition(request, downloadUrl);
			jt.update(SQL_UPDATE_DOWNLOAD_BY_ID, parameters);
		}

	}

	private Object[] buildUpdateCondition(HttpServletRequest request) {
		String downloadId = (String) request.getAttribute("downloadId");
		String downloadDescription = (String) request
				.getAttribute("downloadDescription");
		String downloadOrder = (String) request.getAttribute("downloadOrder");

		return new Object[] { downloadDescription, downloadOrder, downloadId };
	}

	@SuppressWarnings("unchecked")
	private Object[] buildUpdateCondition(HttpServletRequest request,
			String downloadUrl) {
		String downloadId = (String) request.getAttribute("downloadId");
		String downloadDescription = (String) request
				.getAttribute("downloadDescription");
		String downloadOrder = (String) request.getAttribute("downloadOrder");

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		return new Object[] { downloadDescription, downloadUrl, downloadOrder,
				(Integer) loginUser.get("userId"), loginUser.get("realName"),
				CoreUtil.getIPAddr(request), downloadId };
	}

	private static final String SQL_DELETE_DOWNLOAD = "delete from fun_download where downloadId=?";

	public void deleteDownload(final String[] downloadIds) {
		jt.batchUpdate(SQL_DELETE_DOWNLOAD, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(downloadIds[i]));
			}

			@Override
			public int getBatchSize() {
				return downloadIds.length;
			}
		});
	}

	// Extend
	private static final String SQL_GET_DOWNLOAD_BY_IDS = "select downloadUrl from fun_download where ";

	public List<Map<String, Object>> getDownloadByIds(String[] downloadIds) {
		SqlHelper sqlHelper = new SqlHelper();
		return jt.queryForList(SQL_GET_DOWNLOAD_BY_IDS
				+ sqlHelper.buildWhereIn("downloadId", downloadIds));
	}
}
