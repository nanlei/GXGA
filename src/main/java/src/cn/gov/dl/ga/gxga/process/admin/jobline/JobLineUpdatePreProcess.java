package cn.gov.dl.ga.gxga.process.admin.jobline;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobLineService;
import cn.gov.dl.ga.gxga.service.admin.VideoService;

public class JobLineUpdatePreProcess extends Process {
	private JobLineService jobLineService;
	private VideoService videoService;

	public void setJobLineService(JobLineService jobLineService) {
		this.jobLineService = jobLineService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String articleId = (String) request.getAttribute("articleId");

		HashMap<String, Object> article = jobLineService.getArticleById(articleId);

		int videoId = (Integer) article.get("videoId");

		if (videoId > 0) {
			model.put("video", videoService.getVideoById(videoId + ""));
		}

		model.put("article", article);

		return new Result(this.getSuccessView(), model);
	}

}
