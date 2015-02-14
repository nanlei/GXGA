package cn.gov.dl.ga.gxga.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.gov.dl.ga.gxga.core.Constant;

public class QueryUtil {
	/**
	 * 将请求参数还原为key=value的形式
	 */
	@SuppressWarnings("rawtypes")
	public static String getQueryString(Map params) {
		StringBuffer queryString = new StringBuffer(256);
		Iterator it = params.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] param = (String[]) params.get(key);
			for (int i = 0; i < param.length; i++) {
				if (count == 0) {
					count++;
				} else {
					queryString.append("&");
				}
				queryString.append(key);
				queryString.append("=");
				try {
					queryString.append(URLEncoder.encode((String) param[i],
							Constant.ENCODING));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return queryString.toString();
	}

	/**
	 * 对传入的url进行编码
	 */
	public static String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, Constant.ENCODING);
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	/**
	 * 获得请求的路径及参数
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer originalURL = new StringBuffer(request.getServletPath());
		Map parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			originalURL.append(getQueryString(parameters));
		}
		return originalURL.toString();
	}
}
