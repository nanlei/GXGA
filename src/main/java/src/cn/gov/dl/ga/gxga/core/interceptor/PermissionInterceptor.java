package cn.gov.dl.ga.gxga.core.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;

public class PermissionInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionInterceptor.class);

	@Override
	@SuppressWarnings("unchecked")
	public boolean intercept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String permissionURL = "";

		if (StringUtils.isEmpty(request.getParameter(Constant.COMMAND))) {
			permissionURL = request.getRequestURI();
		} else {
			permissionURL = request.getRequestURI() + "?" + Constant.COMMAND
					+ "=" + request.getParameter(Constant.COMMAND);
		}

		Map<String, Object> user = (Map<String, Object>) request.getSession()
				.getAttribute(Constant.LOGIN_USER);

		String roleId = String.valueOf(user.get("roleId"));

		boolean hasPermission = hasPermission(permissionURL, roleId);

		logger.info("{} - {}: {}", "PermissionInterceptor", permissionURL,
				hasPermission);

		if (!hasPermission) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
			builder.append("alert(\"您没有执行该操作的权限，请与网站管理员联系\");");
			builder.append("window.top.location.href='/index.do';");
			builder.append("</script>");
			out.print(builder.toString());
			out.close();
		}

		return hasPermission;
	}

	@SuppressWarnings("unchecked")
	private boolean hasPermission(String permissionURL, String roleId) {
		HashMap<String, Object> permissions = (HashMap<String, Object>) SystemCache
				.getInstants().getCacheMap().get(Constant.CACHE_PERMISSION);

		if (null == permissions.get(permissionURL)) {
			return false;
		}
		String roles = (String) permissions.get(permissionURL);

		if (StringUtils.isEmpty(roles))
			return false;

		return roles.contains("#" + roleId + "#");
	}

}
