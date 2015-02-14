package cn.gov.dl.ga.gxga.process.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;

public class AdminConstProcess extends Process {

	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		String constant = request.getParameter("constant");

		Object data = SystemCache.getInstants().getConstantMap().get(constant);

		model.put("data", data);

		return new Result(this.getSuccessView(), model);
	}

}
