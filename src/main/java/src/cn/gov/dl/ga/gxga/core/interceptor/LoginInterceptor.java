package cn.gov.dl.ga.gxga.core.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gov.dl.ga.gxga.core.Constant;

public class LoginInterceptor extends AbstractLoginInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginInterceptor.class);

	@Override
	@SuppressWarnings("unchecked")
	public boolean alreadyLogin(HttpServletRequest request,
			HttpServletResponse response) {
		Object authUser = request.getSession()
				.getAttribute(Constant.LOGIN_USER);
		if (authUser != null) {
			HashMap<String, Object> user = (HashMap<String, Object>) authUser;
			logger.info("{} {}", user.get("USERNAME"), " login admin");
			return true;
		}
		return false;
	}
}
