package cn.gov.dl.ga.gxga.process.admin.link;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.LinkService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class LinkUpdateProcess extends Process {
	private LinkService linkService;

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		String object = (String) request.getAttribute("object");
		HashMap<String, String> params = JSONParser.parseJSON(object);

		String linkId = params.get("linkId");
		String linkType = params.get("linkType");
		String linkUrl = params.get("linkUrl");
		String linkDescription = params.get("linkDescription");
		String linkOrder = params.get("linkOrder");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { linkType, linkUrl,
				linkDescription, linkOrder, createBy, createByName, createByIP,
				linkId };

		linkService.updateLinkById(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
