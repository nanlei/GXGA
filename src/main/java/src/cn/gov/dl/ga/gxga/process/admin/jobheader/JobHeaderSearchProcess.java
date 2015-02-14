package cn.gov.dl.ga.gxga.process.admin.jobheader;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;

public class JobHeaderSearchProcess extends Process {
	private JobHeaderService jobHeaderService;

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList jobHeaders = jobHeaderService.searchJobHeader(request);

		model.put("total", jobHeaders.getRowCount());
		model.put("data", jobHeaders.getList());

		return new Result(this.getSuccessView(), model);
	}

}
