package cn.gov.dl.ga.gxga.process.admin.memorabiliaindex;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.MemorabiliaIndexService;

public class MemorabiliaIndexSearchProcess extends Process {
	private MemorabiliaIndexService memorabiliaIndexService;

	public void setMemorabiliaIndexService(
			MemorabiliaIndexService memorabiliaIndexService) {
		this.memorabiliaIndexService = memorabiliaIndexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList memorabiliaIndex = memorabiliaIndexService
				.searchMemorabiliaIndex(request);

		model.put("total", memorabiliaIndex.getRowCount());
		model.put("data", memorabiliaIndex.getList());

		return new Result(this.getSuccessView(), model);
	}

}
