package cn.gov.dl.ga.gxga.process.front.contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class ContactListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentCode = (String) request.getParameter("departmentCode");

		String departmentId = indexService
				.getDepartmentIdByCode(departmentCode);

		List<Map<String, Object>> contactList = indexService
				.getContact(departmentId);

		String departmentName = contactList.size() > 0 ? (String) contactList
				.get(0).get("departmentName") : "";

		model.put("departmentName", departmentName);
		model.put("contactList", contactList);

		return new Result(this.getSuccessView(), model);
	}

}
