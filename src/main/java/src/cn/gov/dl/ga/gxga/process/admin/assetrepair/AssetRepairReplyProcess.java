package cn.gov.dl.ga.gxga.process.admin.assetrepair;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.AssetRepairService;
import cn.gov.dl.ga.gxga.util.CoreUtil;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class AssetRepairReplyProcess extends Process {
	private AssetRepairService assetRepairService;

	public void setAssetRepairService(AssetRepairService assetRepairService) {
		this.assetRepairService = assetRepairService;
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

		String arId = params.get("arId");
		String feedback = params.get("feedback");

		int feedbackBy = (Integer) loginUser.get("userId");
		String feedbackByName = (String) loginUser.get("realName");
		String feedbackByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { feedback, feedbackBy,
				feedbackByName, feedbackByIP, arId };

		Map<String, Object> assetRepair = assetRepairService
				.getAssetRepairById(arId);

		if (Constant.STS_RUN.equals(assetRepair.get("sts"))) {
			model.put("status", "run");
		} else {
			assetRepairService.replyAssetRepair(parameters);
			model.put("status", "true");
		}
		return new Result(this.getSuccessView(), model);
	}

}
