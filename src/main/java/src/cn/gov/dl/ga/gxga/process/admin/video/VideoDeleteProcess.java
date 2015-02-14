package cn.gov.dl.ga.gxga.process.admin.video;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.VideoService;

public class VideoDeleteProcess extends Process {
	private VideoService videoService;

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] videoIds = ((String) request.getAttribute("videoIds"))
				.split(",");

		List<Map<String, Object>> videoList = videoService
				.getVideoByIds(videoIds);

		for (int i = 0; i < videoList.size(); i++) {
			Map<String, Object> video = videoList.get(i);

			String videoUrl = (String) video.get("videoUrl");

			new File(servletContext.getRealPath(videoUrl)).delete();
		}

		videoService.deleteVideo(videoIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
