package cn.gov.dl.ga.gxga.process.admin.contact;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ContactService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class ContactUpdateProcess extends Process {
	private ContactService contactService;

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);
		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String contactId = params.get("contactId");

		String departmentId = params.get("departmentId");
		String subDepartment = params.get("subDepartment");
		String name = params.get("name");
		String positionId = params.get("positionId");
		String contactOrder = params.get("contactOrder");
		String internalNo = params.get("internalNo");
		String externalNo = params.get("externalNo");
		String mobile = params.get("mobile");
		String virtualNo = params.get("virtualNo");
		String unicomNo = params.get("unicomNo");
		String unicomVirtualNo = params.get("unicomVirtualNo");
		String officeNo = params.get("officeNo");
		String homePhone = params.get("homePhone");

		Object[] parameters = new Object[] { departmentId, subDepartment, name,
				positionId, contactOrder, internalNo, externalNo, mobile,
				virtualNo, unicomNo, unicomVirtualNo, officeNo, homePhone,
				createBy, createByName, createByIP, contactId };

		contactService.updateContact(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
