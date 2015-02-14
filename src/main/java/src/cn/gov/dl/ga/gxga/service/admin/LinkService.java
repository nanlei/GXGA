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

public class LinkService extends BaseService {
	private static final String SQL_SEARCH_LINK_PREFIX = "select l.linkId, l.linkUrl, l.linkDescription, l.linkOrder, c.constantName as linkType, l.createBy, l.createByName, date_format(l.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, l.createByIP from fun_link l, sys_constant c ";
	private static final String SQL_SEARCH_LINK_SUFFIX = "order by l.linkOrder asc";

	public PagingList searchLink(HttpServletRequest request) throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request)
			throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String linkUrl = params.get("linkUrl");
		String linkDescription = params.get("linkDescription");
		String linkType = params.get("linkType");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_LINK_PREFIX,
				SQL_SEARCH_LINK_SUFFIX);

		helper.setParam(true,
				"l.linkType=c.constantValue and c.constantType='LINKTYPE'");
		helper.setParam(StringUtils.isNotEmpty(linkUrl),
				"l.linkUrl like concat('%',?,'%')", linkUrl);
		helper.setParam(StringUtils.isNotEmpty(linkDescription),
				"l.linkDescription like concat('%',?,'%')", linkDescription);
		helper.setParam(StringUtils.isNotEmpty(linkType), "l.linkType=?",
				linkType);

		return helper;
	}

	public int createLink(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_link").usingGeneratedKeyColumns("linkId");

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

		String linkType = params.get("linkType");
		String linkUrl = params.get("linkUrl");
		String linkDescription = params.get("linkDescription");
		String linkOrder = params.get("linkOrder");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("linkType", linkType);
		parameters.put("linkUrl", linkUrl);
		parameters.put("linkDescription", linkDescription);
		parameters.put("linkOrder", Integer.parseInt(linkOrder));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_LINK_BY_ID = "select * from fun_link where linkId=?";

	public HashMap<String, Object> getLinkById(String linkId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_LINK_BY_ID,
				linkId);
	}

	private static final String SQL_UPDATE_LINK_BY_ID = "update fun_link set linkType=?, linkUrl=?, linkDescription=?, linkOrder=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where linkId=?";

	public int updateLinkById(Object[] parameters) {
		return jt.update(SQL_UPDATE_LINK_BY_ID, parameters);
	}

	private static final String SQL_DELETE_LINK = "delete from fun_link where linkId=?";

	public void deleteLink(final String[] linkIds) {
		jt.batchUpdate(SQL_DELETE_LINK, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(linkIds[i]));
			}

			@Override
			public int getBatchSize() {
				return linkIds.length;
			}
		});
	}
}
