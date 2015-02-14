package cn.gov.dl.ga.gxga.process.admin.assetrepair;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.AssetRepairService;

public class AssetRepairReplyPreProcess extends Process {
	private AssetRepairService assetRepairService;

	public void setAssetRepairService(AssetRepairService assetRepairService) {
		this.assetRepairService = assetRepairService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String arId = (String) request.getAttribute("arId");

		HashMap<String, Object> assetRepair = assetRepairService
				.getAssetRepairById(arId);

		model.put("assetRepair", assetRepair);

		return new Result(this.getSuccessView(), model);
	}

}
