package cn.gov.dl.ga.gxga.process.admin.video;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.PagingList;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.VideoService;

public class VideoSearchProcess extends Process {
	private VideoService videoService;

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		PagingList videos = videoService.searchVideo(request);

		model.put("total", videos.getRowCount());
		model.put("data", videos.getList());

		return new Result(this.getSuccessView(), model);
	}

}
