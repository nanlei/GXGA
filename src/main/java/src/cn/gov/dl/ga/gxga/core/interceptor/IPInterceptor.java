package cn.gov.dl.ga.gxga.core.interceptor;

import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.util.CoreUtil;

public class IPInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(IPInterceptor.class);

	@Override
	public boolean intercept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashSet<String> ipSet = SystemCache.getInstants().getIpSet();

		String clientIP = CoreUtil.getIPAddr(request);

		if (ipSet.contains(clientIP)) {
			logger.info("{} {} {}", clientIP, "access", request.getRequestURI());
			return true;
		} else {
			logger.info("{} {} {}", clientIP, "cannot access",
					request.getRequestURI());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
			builder.append("alert(\"您的IP " + clientIP
					+ " 不在系统允许的范围内，请与网站管理员联系\");");
			builder.append("window.top.location.href='/index.do';");
			builder.append("</script>");
			out.print(builder.toString());
			out.close();

			return false;
		}

	}

}
