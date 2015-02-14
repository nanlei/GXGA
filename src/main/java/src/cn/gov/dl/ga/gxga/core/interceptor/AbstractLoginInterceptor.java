package cn.gov.dl.ga.gxga.core.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录拦截器基类，处理后台管理请求
 * 
 * @author Nan Lei
 * 
 */
public abstract class AbstractLoginInterceptor extends
		HandlerInterceptorAdapter {
	private List<String> ignoreURLs;// 拦截器忽略的URL
	private String loginURL;// 登录URL
	private boolean exactMatch = true;

	public List<String> getIgnoreURLs() {
		return ignoreURLs;
	}

	public void setIgnoreURLs(List<String> ignoreURLs) {
		this.ignoreURLs = ignoreURLs;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!isIgnoreURL(request.getServletPath())) {
			if (!alreadyLogin(request, response)) {
				request.setAttribute(Constant.COMMAND, "");
				request.getRequestDispatcher(loginURL).forward(request,
						response);
				return false;
			}
		}
		return true;
	}

	private boolean isIgnoreURL(String sourceURL) {
		if (exactMatch) {
			return ignoreURLs.contains(sourceURL);
		} else {
			for (String url : ignoreURLs) {
				if (url.endsWith("/*")) {
					if (sourceURL.startsWith(url.replace("/*", ""))) {
						return true;
					}
				} else if (url.equals(sourceURL)) {
					return true;
				}
			}
		}
		return false;
	}

	public abstract boolean alreadyLogin(HttpServletRequest request,
			HttpServletResponse response);

}
