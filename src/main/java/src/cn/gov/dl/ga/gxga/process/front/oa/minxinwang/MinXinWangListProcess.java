package cn.gov.dl.ga.gxga.process.front.oa.minxinwang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class MinXinWangListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		PagingList minxinwangList = indexService.getMinXinWangList(request);

		List<Map<String, Object>> dataList = minxinwangList.getList();

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> data = dataList.get(i);
			String transactionConfig = (String) data.get("transactionConfig");
			HashMap<String, String> config = JSONParser
					.parseJSON(transactionConfig);
			data.put("config_department", config.get("config_department"));
			data.put("config_time", config.get("config_time"));
			data.put("config_disposeTime", config.get("config_disposeTime"));
		}

		model.put("minxinwangList", minxinwangList);

		return new Result(this.getSuccessView(), model);
	}

}
