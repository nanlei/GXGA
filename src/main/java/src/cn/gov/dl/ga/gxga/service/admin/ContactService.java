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

public class ContactService extends BaseService {
	private static final String SQL_SEARCH_CONTACT_PREFIX = "select c.contactId, d.departmentName as departmentName, c.subDepartment, c.name, p.positionName, c.internalNo, c.externalNo, c.mobile, c.virtualNo, c.unicomNo, c.unicomVirtualNo, c.officeNo, c.homePhone, c.contactOrder, c.createBy, c.createByName, date_format(c.createByTime,'%Y-%m-%d %H:%i:%s') as createByTime, c.createByIP from fun_contact c, hr_department d, hr_position p ";
	private static final String SQL_SEARCH_CONTACT_SUFFIX = "order by ";

	public PagingList searchContact(HttpServletRequest request) {
		QueryHelper helper = buildQueryCondition(request);
		return getPagingList(helper.getQuerySql(), request, helper.getParams());
	}

	private QueryHelper buildQueryCondition(HttpServletRequest request) {
		String condition = (String) request.getAttribute("condition");
		HashMap<String, String> params = JSONParser.parseJSON(condition);

		String departmentId = params.get("departmentId");
		String subDepartment = params.get("subDepartment");
		String name = params.get("name");
		String positionId = params.get("positionId");
		String internalNo = params.get("internalNo");
		String externalNo = params.get("externalNo");
		String mobile = params.get("mobile");
		String virtualNo = params.get("virtualNo");
		String unicomNo = params.get("unicomNo");
		String unicomVirtualNo = params.get("unicomVirtualNo");
		String officeNo = params.get("officeNo");
		String homePhone = params.get("homePhone");

		String sortField = (String) request.getAttribute("sortField");
		String sortOrder = (String) request.getAttribute("sortOrder");

		sortField = StringUtils.isEmpty(sortField) ? "contactOrder" : sortField;
		sortOrder = StringUtils.isEmpty(sortOrder) ? "asc" : sortOrder;

		QueryHelper helper = new QueryHelper(SQL_SEARCH_CONTACT_PREFIX,
				SQL_SEARCH_CONTACT_SUFFIX + sortField + " " + sortOrder);
		helper.setParam(true, "c.departmentId=d.departmentId");
		helper.setParam(true, "c.positionId=p.positionId");
		helper.setParam(StringUtils.isNotEmpty(departmentId),
				"c.departmentId=?", departmentId);
		helper.setParam(StringUtils.isNoneEmpty(subDepartment),
				"c.subDepartment like concat('%',?,'%')", subDepartment);
		helper.setParam(StringUtils.isNotEmpty(name),
				"c.name like concat('%',?,'%')", name);
		helper.setParam(StringUtils.isNotEmpty(positionId), "c.positionId=?",
				positionId);
		helper.setParam(StringUtils.isNotEmpty(internalNo),
				"c.internalNo like concat('%',?,'%')", internalNo);
		helper.setParam(StringUtils.isNotEmpty(externalNo),
				"c.externalNo like concat('%',?,'%')", externalNo);
		helper.setParam(StringUtils.isNotEmpty(mobile),
				"c.mobile like concat('%',?,'%')", mobile);
		helper.setParam(StringUtils.isNotEmpty(virtualNo),
				"c.virtualNo like concat('%',?,'%')", virtualNo);
		helper.setParam(StringUtils.isNotEmpty(unicomNo),
				"c.unicomNo like concat('%',?,'%')", unicomNo);
		helper.setParam(StringUtils.isNotEmpty(unicomVirtualNo),
				"c.unicomVirtualNo like concat('%',?,'%')", unicomVirtualNo);
		helper.setParam(StringUtils.isNotEmpty(officeNo),
				"c.officeNo like concat('%',?,'%')", officeNo);
		helper.setParam(StringUtils.isNotEmpty(homePhone),
				"c.homePhone like concat('%',?,'%')", homePhone);

		return helper;
	}

	public int addContact(HttpServletRequest request) {
		HashMap<String, Object> parameters = buildInsertCondition(request);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(jt).withTableName(
				"fun_contact").usingGeneratedKeyColumns("contactId");

		Number id = insert.executeAndReturnKey(parameters);

		int pk = id.intValue();

		return pk;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> buildInsertCondition(
			HttpServletRequest request) {
		String object = (String) request.getAttribute("object");
		HashMap<String, String> objectMap = JSONParser.parseJSON(object);

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");

		String name = objectMap.get("name");
		String departmentId = objectMap.get("departmentId");
		String subDepartment = objectMap.get("subDepartment");
		String positionId = objectMap.get("positionId");
		String contactOrder = objectMap.get("contactOrder");
		String internalNo = objectMap.get("internalNo");
		String externalNo = objectMap.get("externalNo");
		String mobile = objectMap.get("mobile");
		String virtualNo = objectMap.get("virtualNo");
		String unicomNo = objectMap.get("unicomNo");
		String unicomVirtualNo = objectMap.get("unicomVirtualNo");
		String officeNo = objectMap.get("officeNo");
		String homePhone = objectMap.get("homePhone");

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("name", name);
		parameters.put("departmentId", departmentId);
		parameters.put("subDepartment", subDepartment);
		parameters.put("positionId", positionId);
		parameters.put("contactOrder", contactOrder);
		parameters.put("internalNo", internalNo);
		parameters.put("externalNo", externalNo);
		parameters.put("mobile", mobile);
		parameters.put("virtualNo", virtualNo);
		parameters.put("unicomNo", unicomNo);
		parameters.put("unicomVirtualNo", unicomVirtualNo);
		parameters.put("officeNo", officeNo);
		parameters.put("homePhone", homePhone);
		parameters.put("createBy", createBy);
		parameters.put("createByName", createByName);
		parameters.put("createByTime", new Timestamp(new Date().getTime()));
		parameters.put("createByIP", CoreUtil.getIPAddr(request));

		return parameters;
	}

	private static final String SQL_GET_CONTACT_BY_ID = "select * from fun_contact where contactId=?";

	public HashMap<String, Object> getContactById(String contactId) {
		return (HashMap<String, Object>) jt.queryForMap(SQL_GET_CONTACT_BY_ID,
				contactId);
	}

	private static final String SQL_UPDATE_CONTACT = "update fun_contact set departmentId=?, subDepartment=?, name=?, positionId=?, contactOrder=?, internalNo=?, externalNo=?, mobile=?, virtualNo=?, unicomNo=?, unicomVirtualNo=?, officeNo=?, homePhone=?, createBy=?, createByName=?, createByTime=now(), createByIP=? where contactId=?";

	public int updateContact(Object[] parameters) {
		return jt.update(SQL_UPDATE_CONTACT, parameters);
	}

	private static final String SQL_DELETE_CONTACT = "delete from fun_contact where contactId=?";

	public void deleteContact(final String[] contactIds) {
		jt.batchUpdate(SQL_DELETE_CONTACT, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(contactIds[i]));
			}

			@Override
			public int getBatchSize() {
				return contactIds.length;
			}
		});
	}
}
