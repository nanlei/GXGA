package cn.gov.dl.ga.gxga.process.admin.contact;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ContactService;

public class ContactSearchProcess extends Process {
	private ContactService contactService;

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList contacts = contactService.searchContact(request);

		model.put("total", contacts.getRowCount());
		model.put("data", contacts.getList());

		return new Result(this.getSuccessView(), model);
	}

}
