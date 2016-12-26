package cn.gov.dl.ga.gxga.service.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

public class MailboxService extends BaseService {
	private static final String SQL_SEARCH_MAIL_PREFIX = "select m.mailId, m.mailSubject, m.sts, c.constantName as statusName, createByName, date_format(m.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, m.createByIP, m.commentByName, date_format(m.commentByTime,'%Y-%m-%d %H:%i:%s') as commentByTime, m.commentByIP from fun_mailbox m, sys_constant c ";
	private static final String SQL_SEARCH_MAIL_SUFFIX = "order by ";

	public PagingList searchMail(HttpServletRequest request) throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	@SuppressWarnings("unchecked")
	private QueryHelper buildQueryCondition(HttpServletRequest request) throws Exception {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String createByName = params.get("createByName");
		String createByTime = params.get("createByTime");
		String isPublic = params.get("isPublic");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "mailId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		int roleId = (Integer) loginUser.get("roleId");
		int departmentId = (Integer) loginUser.get("departmentId");

		QueryHelper helper = new QueryHelper(SQL_SEARCH_MAIL_PREFIX,
				SQL_SEARCH_MAIL_SUFFIX + sortField + " " + sortOrder);

		if (roleId != 1) {// Not Super Admin
			helper.setParam(true, "m.departmentId=?", departmentId);
		}
		helper.setParam(true, "m.sts=c.constantValue and c.constantType='MAILSTATUS'");
		helper.setParam(StringUtils.isNotEmpty(createByName), "m.createByName like concat('%',?,'%')", createByName);
		if (StringUtils.isNotEmpty(createByTime)) {
			helper.setParam(true, "date_format(m.createByTime,'%Y-%m-%d')=?",
					new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(createByTime)));
		} else if (StringUtils.isNotEmpty(isPublic)) {
			helper.setParam(true, "m.isPublic=?", isPublic);
		}
		return helper;
	}

	public int createMail(HttpServletRequest request) throws Exception {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName("fun_mailbox")
				.usingGeneratedKeyColumns("mailId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailSubject = params.get("mailSubject");
		String mailContent = params.get("mailContent");
		int leaderId = Integer.parseInt(params.get("leaderId"));
		int deptAdminId = Integer.parseInt(params.get("deptAdminId"));
		String dueDate = params.get("dueDate");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		Date d_dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
		parameters.put("dueDate", new Timestamp(d_dueDate.getTime()));
		parameters.put("mailSubject", mailSubject);
		parameters.put("mailContent", mailContent);
		parameters.put("sts", Constant.STS_NEW);
		parameters.put("public", "1"); // 1 for public mail
		parameters.put("leaderId", leaderId);
		parameters.put("deptAdminId", deptAdminId);
		parameters.put("departmentId", (Integer) loginUser.get("departmentId"));
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_MAIL_BY_ID = "select * from fun_mailbox where mailId=?";

	public HashMap<String, Object> getMailById(String mailId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_MAIL_BY_ID, mailId);
	}

	private static final String SQL_UPDATE_MAIL_BY_ID = "update fun_mailbox set mailSubject=?, mailContent=?, leaderId=?, deptAdminId=?, isPublic=?, dueDate=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where mailId=?";

	public int updateMailById(Object[] parameters) {
		return jt.update(SQL_UPDATE_MAIL_BY_ID, parameters);
	}

	private static final String SQL_DELETE_MAIL = "delete from fun_mailbox where mailId=?";

	public void deleteMail(final String[] mailIds) {
		jt.batchUpdate(SQL_DELETE_MAIL, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(mailIds[i]));
			}

			@Override
			public int getBatchSize() {
				return mailIds.length;
			}
		});
	}

	// Extend
	private static final String SQL_DISPOSE_MAIL_BY_ID = "update fun_mailbox set sts='WAI' where mailId=?";

	public void disposeMail(String mailId) {
		jt.update(SQL_DISPOSE_MAIL_BY_ID, mailId);
	}

	private static final String SQL_REPLY_MAIL_BY_ID = "update fun_mailbox set sts='RUN',mailComment=?, commentBy=?, commentByName=?, commentByTime=now(), commentByIP=? where mailId=?";

	public int replyMail(Object[] parameters) {
		return jt.update(SQL_REPLY_MAIL_BY_ID, parameters);
	}

	private static final String SQL_EVALUATE_MAIL_BY_ID = "update fun_mailbox set sts='EVL', rank=? where mailId=?";

	public int evaluateMailById(Object[] parameters) {
		return jt.update(SQL_EVALUATE_MAIL_BY_ID, parameters);
	}

}
