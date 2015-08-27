package cn.gov.dl.ga.gxga.process.front.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.front.IndexService;
import cn.gov.dl.ga.gxga.util.CalendarUtil;

public class DutyListProcess extends Process {
	private IndexService indexService;

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		List<Map<String, Object>> dutyList = null;

		String offset = request.getParameter("offset");
		String ts = request.getParameter("ts");

		if (StringUtils.isNotEmpty(offset) && StringUtils.isNotEmpty(ts)) {
			String yearAndMonth = CalendarUtil
					.getOffsetYearAndMonth(offset, ts);
			dutyList = indexService.getDuty(yearAndMonth);

			model.put("sessionYearMonth", yearAndMonth);
		} else {
			dutyList = indexService.getDuty();

			model.put("sessionYearMonth", CalendarUtil.getCurrentYearAndMonth());
		}

		List<Map<String, Object>> dutyPlanList = indexService.getDutyPlan();

		model.put("dutyList", dutyList);

		model.put("dutyPlanList", dutyPlanList);

		return new Result(this.getSuccessView(), model);
	}

}
