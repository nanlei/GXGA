package cn.gov.dl.ga.gxga.process.admin.contact;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ContactService;

public class ContactUpdatePreProcess extends Process {
	private ContactService contactService;

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String contactId = (String) request.getAttribute("contactId");

		HashMap<String, Object> contact = contactService
				.getContactById(contactId);

		model.put("contact", contact);

		return new Result(this.getSuccessView(), model);
	}

}
