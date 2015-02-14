package cn.gov.dl.ga.gxga.process.admin.video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.VideoService;

public class VideoAjaxProcess extends Process {
	private VideoService videoService;

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String categoryId = (String) request.getAttribute("categoryId");

		List<Map<String, Object>> data = videoService
				.getVideoForAjax(categoryId);

		model.put("data", data);

		return new Result(this.getSuccessView(), model);
	}

}
