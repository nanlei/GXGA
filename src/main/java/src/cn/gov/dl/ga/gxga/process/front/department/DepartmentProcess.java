package cn.gov.dl.ga.gxga.process.front.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class DepartmentProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentCode = (String) request.getAttribute("departmentCode");

		String departmentId = indexService
				.getDepartmentIdByCode(departmentCode);

		List<Map<String, Object>> categoryList = indexService
				.getDepartmentCategoryByDeparmentId(departmentId);

		model.put("categoryList", categoryList);

		return new Result(this.getSuccessView(), model);
	}
}
