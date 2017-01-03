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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.BaseService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;
import cn.gov.dl.ga.gxga.util.QueryHelper;

public class SelfService extends BaseService {
	// PASSWORD
	private static final String SQL_CHECK_USER_PASSWORD = "select count(*) from sys_user where userId=? and password=?";

	public int checkUserPassword(int userId, String password) {
		return jt.queryForObject(SQL_CHECK_USER_PASSWORD, Integer.class, new Object[] { userId, password });
	}

	private static final String SQL_UPDATE_USER_PASSWORD = "update sys_user set password=? where userId=?";

	public int updateUserPassword(int userId, String password) {
		return jt.update(SQL_UPDATE_USER_PASSWORD, new Object[] { password, userId });
	}

	// EMPLOYEE
	private static final String SQL_GET_EMPLOYEE_BY_USER_ID = "select e.*, u.userName, d.departmentName from hr_employee e, hr_department d, sys_user u where e.userId=u.userId and e.departmentId=d.departmentId and e.userId=?";

	public HashMap<String, Object> getEmployeeByUserId(int userId) {
		try {
			return (HashMap<String, Object>) jt.queryForMap(SQL_GET_EMPLOYEE_BY_USER_ID, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	private static final String SQL_UPDATE_EMPLOYEE_BY_USER_ID = "update hr_employee set employeeOrder=?, employeeName=?, employeeGender=?, employeeIdNo=?, employeePoliceNo=?, employeeBirth=?, employeePolitical=?, employeeNationality=?, employeeIdentity=?, employeeAddress=?, employeeFamily=?, employeeEducation=?, employeeSchool=?, employeeDegree=?, employeeMajor=?, employeeHighEducation=?, employeeHighSchool=?, employeeHighDegree=?, employeeHighMajor=?, employeeJobTitle=?, employeeOtherCertificate=?, employeePosition=?, employeePositionLevel=?, employeeStartTime=?, employeeJob=?, employeeWorkStartTime=?, employeePoliceStartTime=?, employeePoliceSpecialty=?, employeeOtherSpecialty=?, employeeRewards=? where userId=?";

	public int updateEmployee(Object[] parameters) {
		return jt.update(SQL_UPDATE_EMPLOYEE_BY_USER_ID, parameters);
	}

	// USERACTION
	private static final String SQL_GET_USER_ACTION_BY_USER_ID = "select actionId, actionType, refTable, refId, date_format(actionTime,'%Y-%m-%d %H:%i:%s') as actionTime from sys_user_action where userId=?";

	public List<Map<String, Object>> getUserActionListByUserId(int userId) {
		return jt.queryForList(SQL_GET_USER_ACTION_BY_USER_ID, userId);
	}

	public String buildRefForUserAction(String sql, Object[] parameters) {
		return jt.queryForObject(sql, String.class, parameters);
	}

	private static final String SQL_GET_USER_ACTION_BY_ID = "select * from sys_user_action where actionId=?";
	private static final String SQL_DELETE_USER_ACTION_BY_ID = "delete from sys_user_action where actionId=?";

	public void disposeUserActionById(HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String actionId = params.get("actionId");

		Map<String, Object> action = jt.queryForMap(SQL_GET_USER_ACTION_BY_ID, actionId);

		String refTable = (String) action.get("refTable");
		int refId = (Integer) action.get("refId");

		if ("doc_article_sign".equals(refTable)) {
			disposeArticleSign(request, refId);
		} else if ("fun_mailbox".equals(refTable)) {
			disposeMailbox(request, refId);
		}

		jt.update(SQL_DELETE_USER_ACTION_BY_ID, actionId);
	}

	private static final String SQL_DISPOSE_ARTICLE_SIGN = "update doc_article_sign set comment=?, status='RUN', signBy=?, signByName=?, signByTime=now(), signByIP=? where asId=?";

	@SuppressWarnings("unchecked")
	private void disposeArticleSign(HttpServletRequest request, int asId) {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String comment = params.get("comment");

		int signBy = (Integer) loginUser.get("userId");
		String signByName = (String) loginUser.get("realName");
		String signByIP = CoreUtil.getIPAddr(request);
		Object[] parameters = new Object[] { comment, signBy, signByName, signByIP, asId };

		jt.update(SQL_DISPOSE_ARTICLE_SIGN, parameters);
	}

	private static final String SQL_DISPOSE_MAILBOX = "update fun_mailbox set mailComment=?, sts='RUN', commentBy=?, commentByName=?, commentByTime=now(), commentByIP=? where mailId=?";

	@SuppressWarnings("unchecked")
	private void disposeMailbox(HttpServletRequest request, int mailId) {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailComment = params.get("comment");

		int commentBy = (Integer) loginUser.get("userId");
		String commentByName = (String) loginUser.get("realName");
		String commentByIP = CoreUtil.getIPAddr(request);
		Object[] parameters = new Object[] { mailComment, commentBy, commentByName, commentByIP, mailId };

		jt.update(SQL_DISPOSE_MAILBOX, parameters);

	}

	// MAILBOX
	private static final String SQL_SEARCH_MAIL_PREFIX = "select m.mailId, m.mailSubject, m.sts, c.constantName as statusName, case when m.isPublic='Y' then '是' else '否' end isPublic, createByName, date_format(m.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, m.createByIP, m.commentByName, date_format(m.commentByTime,'%Y-%m-%d %H:%i:%s') as commentByTime, m.commentByIP from fun_mailbox m, sys_constant c ";
	private static final String SQL_SEARCH_MAIL_SUFFIX = "order by ";

	public PagingList searchMail(HttpServletRequest request) throws Exception {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	@SuppressWarnings("unchecked")
	private QueryHelper buildQueryCondition(HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String createByTime = params.get("createByTime");
		String isPublic = params.get("isPublic");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "mailId" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_MAIL_PREFIX,
				SQL_SEARCH_MAIL_SUFFIX + sortField + " " + sortOrder);

		helper.setParam(true, "m.createBy=?", (Integer) loginUser.get("userId"));
		helper.setParam(true, "m.sts=c.constantValue and c.constantType='MAILSTATUS'");
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

		// add user action
		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		if (StringUtils.isNotEmpty(params.get("leaderId"))) {
			createUserAction(pk, params.get("leaderId"));
		} else if (StringUtils.isNotEmpty(params.get("deptAdminId"))) {
			createUserAction(pk, params.get("deptAdminId"));
		}

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(HttpServletRequest request) throws Exception {
		Map<String, Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String mailSubject = params.get("mailSubject");
		String mailContent = params.get("mailContent");
		Integer leaderId = StringUtils.isNotEmpty(params.get("leaderId")) ? Integer.parseInt(params.get("leaderId"))
				: null;
		Integer deptAdminId = StringUtils.isNotEmpty(params.get("deptAdminId"))
				? Integer.parseInt(params.get("deptAdminId")) : null;
		String isPublic = params.get("isPublic");
		String dueDate = params.get("dueDate");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		Date d_dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
		parameters.put("dueDate", new Timestamp(d_dueDate.getTime()));
		parameters.put("mailSubject", mailSubject);
		parameters.put("mailContent", mailContent);
		parameters.put("departmentId", loginUser.get("departmentId"));
		parameters.put("isPublic", isPublic);
		parameters.put("leaderId", leaderId);
		parameters.put("deptAdminId", deptAdminId);
		parameters.put("sts", Constant.STS_NEW);
		parameters.put("createBy", (Integer) loginUser.get("userId"));
		parameters.put("createByName", loginUser.get("realName"));
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private int createUserAction(int refId, String leaderId) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName("sys_user_action")
				.usingGeneratedKeyColumns("actionId");

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("actionType", "MAILBOX");
		params.put("userId", leaderId);
		params.put("departmentId", 21);// 21 for leader
		params.put("refTable", "fun_mailbox");
		params.put("refId", refId);
		params.put("sts", Constant.STS_NEW);
		params.put("actionTime", new Timestamp(new Date().getTime()));

		Number id = insert.executeAndReturnKey(params);

		return id.intValue();
	}

	private static final String SQL_GET_MAIL_BY_ID = "select * from fun_mailbox where mailId=? and createBy=?";

	public HashMap<String, Object> getMailById(String mailId, int createBy) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_MAIL_BY_ID, mailId, createBy);
	}

	private static final String SQL_UPDATE_MAIL_BY_ID = "update fun_mailbox set mailSubject=?, mailContent=?, isPublic=?, leaderId=?, deptAdminId=?, dueDate=? where mailId=? and createBy=?";

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

	private static final String SQL_EVALUATE_MAIL_BY_ID = "update fun_mailbox set sts='EVL', rank=? where mailId=? and createBy=?";

	public int evaluateMailById(Object[] parameters) {
		return jt.update(SQL_EVALUATE_MAIL_BY_ID, parameters);
	}

}
