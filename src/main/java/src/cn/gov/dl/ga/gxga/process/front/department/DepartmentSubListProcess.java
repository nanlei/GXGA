package cn.gov.dl.ga.gxga.process.front.department;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class DepartmentSubListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		String dcId = (String) request.getAttribute("dcId");

		String dcName = indexService.getDepartmentCategoryNameById(dcId);

		PagingList articleList = indexService.getArticleListByDcId(request,
				dcId);

		model.put("dcName", dcName);
		model.put("dcCode",
				(String) request.getAttribute("departmentCode"));
		model.put("articleList", articleList);

		return new Result(this.getSuccessView(), model);
	}

}
