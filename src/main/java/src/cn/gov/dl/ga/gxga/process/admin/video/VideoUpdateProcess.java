package cn.gov.dl.ga.gxga.process.admin.video;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.VideoService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class VideoUpdateProcess extends Process {
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

		ServletContext servletContext = request.getSession()
				.getServletContext();

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				servletContext);

		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iterator = multiRequest.getFileNames();

			while (iterator.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iterator.next()
						.toString());
				if (file != null && file.getSize() > 0) {
					String path = servletContext.getContextPath()
							+ "/file/video/"
							+ new Date().getTime()
							+ "."
							+ CoreUtil.getFileExtensionName(file
									.getOriginalFilename());
					// 上传
					String realPath = servletContext.getRealPath(path);
					file.transferTo(new File(realPath));

					// DB更新
					videoService.updateVideoById(multiRequest, path);

					// 原有文件删除
					File oldFile = new File(
							servletContext.getRealPath((String) video
									.get("videoUrl")));
					oldFile.delete();
				}
			}
		}

		// DB
		videoService.updateVideoById(request, null);

		File tmpDir = new File(servletContext.getRealPath(servletContext
				.getContextPath() + "/file/tmp/"));
		for (File tmp : tmpDir.listFiles()) {
			tmp.delete();
		}

		model.put("message", "视频更新成功");

		return new Result(this.getSuccessView(), model);
	}

}
