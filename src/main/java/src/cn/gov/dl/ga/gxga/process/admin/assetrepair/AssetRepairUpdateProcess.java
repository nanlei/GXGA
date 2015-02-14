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

public class AssetRepairUpdateProcess extends Process {
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
		String departmentId = params.get("departmentId");
		String assetName = params.get("assetName");
		String assetLocation = params.get("assetLocation");
		String remark = params.get("remark");

		int createBy = (Integer) loginUser.get("userId");
		String createByName = (String) loginUser.get("realName");
		String createByIP = CoreUtil.getIPAddr(request);

		Object[] parameters = new Object[] { departmentId, assetName,
				assetLocation, remark, createBy, createByName, createByIP, arId };

		Map<String, Object> assetRepair = assetRepairService
				.getAssetRepairById(arId);

		if ("NEW".equals(assetRepair.get("sts"))) {
			assetRepairService.updateAssetRepairById(parameters);
			model.put("status", "true");
		} else {
			model.put("status", "notnew");
		}

		return new Result(this.getSuccessView(), model);
	}

}
