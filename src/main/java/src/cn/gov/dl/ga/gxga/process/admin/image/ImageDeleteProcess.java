package cn.gov.dl.ga.gxga.process.admin.image;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ImageService;

public class ImageDeleteProcess extends Process {
	private ImageService imageService;

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		ServletContext servletContext = request.getSession()
				.getServletContext();

		final String[] imageIds = ((String) request.getAttribute("imageIds"))
				.split(",");

		List<Map<String, Object>> imageList = imageService
				.getImageByIds(imageIds);

		for (int i = 0; i < imageList.size(); i++) {
			Map<String, Object> image = imageList.get(i);

			String imageUrl = (String) image.get("imageUrl");

			new File(servletContext.getRealPath(imageUrl)).delete();
		}

		imageService.deleteImage(imageIds);

		model.put("status", "true");

		return new Result(this.getSuccessView(), model);
	}

}
