package cn.gov.dl.ga.gxga.process.admin.duty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.DutyService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class DutyUpdateProcess extends Process {
	private DutyService dutyService;

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
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

		String dutyId = params.get("dutyId");

		Timestamp dutyDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd")
				.parse(params.get("dutyDate")).getTime());
		String dutyManager = params.get("dutyManager");
		String dutyLeader = params.get("dutyLeader");
		String dutyPolice = params.get("dutyPolice");

		Object[] parameters = new Object[] { dutyDate, dutyManager, dutyLeader,
				dutyPolice, createBy, createByName, createByIP, dutyId };

		dutyService.updateDuty(parameters);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
