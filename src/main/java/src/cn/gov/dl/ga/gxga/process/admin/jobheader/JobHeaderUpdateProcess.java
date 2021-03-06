package cn.gov.dl.ga.gxga.process.admin.jobheader;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.JobHeaderService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class JobHeaderUpdateProcess extends Process {
	private JobHeaderService jobHeaderService;

	public void setJobHeaderService(JobHeaderService jobHeaderService) {
		this.jobHeaderService = jobHeaderService;
	}

	@Override
	public Result process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String jobId = (String) request.getAttribute("jobId");

		HashMap<String, Object> jobHeader = jobHeaderService.getJobHeaderById(jobId);

		ServletContext servletContext = request.getSession().getServletContext();

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);

		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iterator = multiRequest.getFileNames();

			while (iterator.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iterator.next().toString());
				if (StringUtils.isNotEmpty(file.getOriginalFilename())) {
					if (file.getSize() == 0) {
						model.put("message", "文件无效，请重试");

						return new Result(this.getSuccessView(), model);
					} else {
						String path = servletContext.getContextPath() + "/file/jobheader/" + new Date().getTime() + "."
								+ CoreUtil.getFileExtensionName(file.getOriginalFilename());

						// 上传
						String realPath = servletContext.getRealPath(path);
						file.transferTo(new File(realPath));

						// DB更新
						jobHeaderService.updateHeader(request, path);

						// 原有文件删除
						File oldFile = new File(servletContext.getRealPath((String) jobHeader.get("jobImageUrl")));
						oldFile.delete();
					}
				} else {
					// Only DB
					jobHeaderService.updateHeader(request, null);
				}
			}
		}

		File tmpDir = new File(servletContext.getRealPath(servletContext.getContextPath() + "/file/tmp/"));
		for (File tmp : tmpDir.listFiles()) {
			tmp.delete();
		}

		model.put("message", "更新成功");

		return new Result(this.getSuccessView(), model);
	}

}
