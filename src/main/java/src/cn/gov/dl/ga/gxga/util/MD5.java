package cn.gov.dl.ga.gxga.util;

import java.security.MessageDigest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密工具类
 * 
 * @author Nan Lei
 * 
 */
public class MD5 {
	private static final Logger logger = LoggerFactory.getLogger(MD5.class);

	/**
	 * 获得MD5加密密码的结果
	 * 
	 * @param string
	 * @return
	 */
	public static String encode(String string) {
		String md5Code = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(string.getBytes());
			md5Code = byteArrayToHexString(bytes);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return md5Code;
	}

	/**
	 * 重载方法，用于多次加密
	 * 
	 * @param string
	 * @param times
	 * @return
	 */
	public static String encode(String string, int times) {
		String md5Code = encode(string);
		for (int i = 0; i < times; i++) {
			md5Code = encode(md5Code);
		}
		return md5Code;
	}

	/**
	 * 字节数组转为十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			sb.append(byteToHexString(b));
		}
		return sb.toString();
	}

	/**
	 * 字节转为十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		String hexStr = null;
		int n = b;
		if (n < 0) {
			// 标准移位算法
			n = b & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr.toUpperCase();
	}

	/**
	 * MD5密码验证方法
	 * 
	 * @param inputString
	 * @param MD5Code
	 * @return
	 */
	public static boolean verifyPassword(String inputString, String MD5Code) {
		return encode(inputString).equals(MD5Code);
	}

	/**
	 * 多次加密时，MD5密码验证方法
	 * 
	 * @param inputString
	 * @param MD5Code
	 * @param times
	 * @return
	 */
	public static boolean verifyPassword(String inputString, String MD5Code,
			int times) {
		return encode(inputString, times).equals(MD5Code);
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("123:" + encode("123"));
		System.out.println("123456:" + encode("123456"));
		System.out.println("hztraining:" + encode("hztraining"));
		System.out.println(encode("123", 4));
		System.out.println(verifyPassword("123",
				"D170DC71FFAAB788855B7758A156E5F2", 4));
	}

}
