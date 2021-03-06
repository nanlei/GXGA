package cn.gov.dl.ga.gxga.process.admin.emergencynotice;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.EmergencyNoticeService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class EmergencyNoticeUpdateProcess extends Process {
	private EmergencyNoticeService emergencyNoticeService;

	public void setEmergencyNoticeService(EmergencyNoticeService emergencyNoticeService) {
		this.emergencyNoticeService = emergencyNoticeService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String noticeMode = request.getParameter("noticeMode");

		if ("SELF".equals(noticeMode)) {

			ServletContext servletContext = request.getSession().getServletContext();

			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);

			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				// 将request变成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 获取multiRequest 中所有的文件名
				Iterator<String> iterator = multiRequest.getFileNames();

				String imagePath = null;
				String attachmentPath = null;

				String noticeId = (String) request.getAttribute("noticeId");
				Map<String, Object> notice = emergencyNoticeService.getEmergencyNoticeById(noticeId);

				while (iterator.hasNext()) {
					// 一次遍历所有文件
					String fileName = iterator.next();

					MultipartFile file = multiRequest.getFile(fileName);
					if (file != null && file.getSize() > 0) {
						if ("FdataImage".equals(fileName)) {
							// 删除老文件
							String oldImageRealPath = servletContext.getRealPath((String) notice.get("noticeImageUrl"));
							File oldImageFile = new File(oldImageRealPath);
							oldImageFile.delete();

							imagePath = servletContext.getContextPath() + "/file/notice/image/" + new Date().getTime()
									+ "." + CoreUtil.getFileExtensionName(file.getOriginalFilename());
							String imageRealPath = servletContext.getRealPath(imagePath);
							// 上传Image
							file.transferTo(new File(imageRealPath));
						} else if ("FdataAttachment".equals(fileName)) {
							// 删除老文件
							String oldAttachmentRealPath = servletContext
									.getRealPath((String) notice.get("noticeAttachmentUrl"));
							File oldAttachmentFile = new File(oldAttachmentRealPath);
							oldAttachmentFile.delete();

							attachmentPath = servletContext.getContextPath() + "/file/notice/attachment/"
									+ new Date().getTime() + "."
									+ CoreUtil.getFileExtensionName(file.getOriginalFilename());
							String attachmentRealPath = servletContext.getRealPath(attachmentPath);
							// 上传Attachment
							file.transferTo(new File(attachmentRealPath));
						}
					} else {
						model.put("message", "文件无效，请重试");
					}

				} // end of while

				// DB
				emergencyNoticeService.updateNoticeById(multiRequest, imagePath, attachmentPath);

			}

			File tmpDir = new File(servletContext.getRealPath(servletContext.getContextPath() + "/file/tmp/"));
			for (File tmp : tmpDir.listFiles()) {
				tmp.delete();
			}

		} else if ("LINK-JOB".equals(noticeMode)) {
			emergencyNoticeService.updateNoticeById(request, null, null);
		}

		model.put("message", "紧急通知修改成功");

		return new Result(this.getSuccessView(), model);
	}

}
