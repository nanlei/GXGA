package cn.gov.dl.ga.gxga.process.admin.image;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.ImageService;

public class ImageUpdatePreProcess extends Process {
	private ImageService imageService;

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String imageId = (String) request.getAttribute("imageId");

		HashMap<String, Object> image = imageService.getImageById(imageId);

		model.put("image", image);

		return new Result(this.getSuccessView(), model);
	}

}
