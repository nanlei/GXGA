package cn.gov.dl.ga.gxga.util;

import javax.servlet.http.HttpServletRequest;

public class CoreUtil {
	// 多级代理取真实ip
	public static String getIPAddr(HttpServletRequest request) {
		String strClientIP = request.getHeader("x-forwarded-for");
		request.getRemoteHost();
		if (strClientIP == null || strClientIP.length() == 0
				|| "unknown".equalsIgnoreCase(strClientIP)) {
			strClientIP = request.getRemoteAddr();
		} else {
			String ipList[] = null;
			ipList = strClientIP.split(","); // 拆分字符串，可直接用String.plit方法
			String strIP = new String();
			for (int index = 0; index < ipList.length; index++) {
				strIP = (String) ipList[index];
				if (!("unknown".equalsIgnoreCase(strIP))) {
					strClientIP = strIP;
					break;
				}
			}
		}

		return strClientIP;
	}

	public static String getFileExtensionName(String fileName) {
		int split = fileName.lastIndexOf(".");

		if (split == -1) {
			return "txt";
		}

		return fileName.substring(split + 1, fileName.length());
	}
}
