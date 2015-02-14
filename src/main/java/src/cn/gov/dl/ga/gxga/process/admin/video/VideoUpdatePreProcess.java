package cn.gov.dl.ga.gxga.process.admin.video;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.VideoService;

public class VideoUpdatePreProcess extends Process {
	private VideoService videoService;

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String videoId = (String) request.getAttribute("videoId");

		HashMap<String, Object> video = videoService.getVideoById(videoId);

		model.put("video", video);

		return new Result(this.getSuccessView(), model);
	}

}
