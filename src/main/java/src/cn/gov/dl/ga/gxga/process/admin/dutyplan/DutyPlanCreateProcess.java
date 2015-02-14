package cn.gov.dl.ga.gxga.process.admin.dutyplan;

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
import cn.gov.dl.ga.gxga.service.admin.DutyPlanService;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class DutyPlanCreateProcess extends Process {
	private DutyPlanService dutyPlanService;

	public void setDutyPlanService(DutyPlanService dutyPlanService) {
		this.dutyPlanService = dutyPlanService;
	}

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String departmentId = (String) request.getAttribute("departmentId");

		int exists = dutyPlanService.checkDutyPlanByDepartmentId(departmentId);

		if (exists > 0) {
			model.put("message", "部门值班表已经存在，请删除后重传");
		} else {
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
								+ "/file/duty/"
								+ new Date().getTime()
								+ "."
								+ CoreUtil.getFileExtensionName(file
										.getOriginalFilename());

						// DB
						dutyPlanService.createDutyPlan(multiRequest, path);

						// 上传
						String realPath = servletContext.getRealPath(path);
						file.transferTo(new File(realPath));
					} else {
						model.put("message", "文件无效，请重试");
					}

				}

			}

			model.put("message", "文件上传成功");
		}// end of if(exists>0)

		return new Result(this.getSuccessView(), model);
	}
}
