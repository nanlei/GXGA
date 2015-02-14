package cn.gov.dl.ga.gxga.process.admin.overtimemeal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.OvertimeMealService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class OvertimeMealUpdateProcess extends Process {
	private OvertimeMealService overtimeMealService;

	public void setOvertimeMealService(OvertimeMealService overtimeMealService) {
		this.overtimeMealService = overtimeMealService;
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

		String omId = params.get("omId");
		String departmentId = params.get("departmentId");
		String overtimeDate = params.get("overtimeDate");
		String remark = params.get("remark");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { departmentId, overtimeDate,
				remark, createBy, createByName, createByIP, omId };

		Map<String, Object> overtimeMeal = overtimeMealService
				.getOvertimeMealById(omId);

		if ("NEW".equals(overtimeMeal.get("sts"))) {
			overtimeMealService.updateOvertimeMealById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notnew");
		}

		return new Result(this.getSuccessView(), model);
	}

}
