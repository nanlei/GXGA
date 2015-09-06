package cn.gov.dl.ga.gxga.process.front.oa.pubcomm;

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

public class PubCommListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		PagingList pubCommList = indexService.getPubCommList(request);

		List<Map<String, Object>> dataList = pubCommList.getList();

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> data = dataList.get(i);
			String transactionConfig = (String) data.get("transactionConfig");
			HashMap<String, String> config = JSONParser
					.parseJSON(transactionConfig);
			data.put("config_department", config.get("config_department"));
			data.put("config_date", config.get("config_date"));
		}

		model.put("pubCommList", pubCommList);

		return new Result(this.getSuccessView(), model);
	}

}
