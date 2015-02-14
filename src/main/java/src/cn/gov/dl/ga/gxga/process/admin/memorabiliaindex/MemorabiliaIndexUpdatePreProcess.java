package cn.gov.dl.ga.gxga.process.admin.memorabiliaindex;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MemorabiliaIndexService;

public class MemorabiliaIndexUpdatePreProcess extends Process {
	private MemorabiliaIndexService memorabiliaIndexService;

	public void setMemorabiliaIndexService(
			MemorabiliaIndexService memorabiliaIndexService) {
		this.memorabiliaIndexService = memorabiliaIndexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String memorabiliaId = (String) request.getAttribute("memorabiliaId");

		HashMap<String, Object> memorabiliaIndex = memorabiliaIndexService
				.getMemorabiliaIndexById(memorabiliaId);

		model.put("memorabiliaIndex", memorabiliaIndex);

		return new Result(this.getSuccessView(), model);
	}

}
