package cn.gov.dl.ga.gxga.process.admin.memorabiliaindex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MemorabiliaIndexService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MemorabiliaIndexUpdateProcess extends Process {
	private MemorabiliaIndexService memorabiliaIndexService;

	public void setMemorabiliaIndexService(
			MemorabiliaIndexService memorabiliaIndexService) {
		this.memorabiliaIndexService = memorabiliaIndexService;
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

		String memorabiliaId = params.get("memorabiliaId");
		String memorabiliaContent = params.get("memorabiliaContent");
		String memorabiliaOrder = params.get("memorabiliaOrder");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { memorabiliaContent,
				memorabiliaOrder, createBy, createByName, createByIP,
				memorabiliaId };

		memorabiliaIndexService.updateMemorabiliaIndexById(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
