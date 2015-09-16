package cn.gov.dl.ga.gxga.process.front.oa.officalmailbox;

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

public class OfficalMailBoxListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		request.setAttribute("paging", "front");

		PagingList officalMailBoxList = indexService.getOfficalMailBox(request);

		List<Map<String, Object>> dataList = officalMailBoxList.getList();

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> data = dataList.get(i);
			String transactionConfig = (String) data.get("transactionConfig");
			HashMap<String, String> config = JSONParser
					.parseJSON(transactionConfig);
			data.put("config_unit", config.get("config_unit"));
			data.put("config_date", config.get("config_date"));
		}

		model.put("officalMailBoxList", officalMailBoxList);

		return new Result(this.getSuccessView(), model);
	}

}
