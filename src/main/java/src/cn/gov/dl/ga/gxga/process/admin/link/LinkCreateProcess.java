package cn.gov.dl.ga.gxga.process.admin.link;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.LinkService;

public class LinkCreateProcess extends Process {
	private LinkService linkService;

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		linkService.createLink(request);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
