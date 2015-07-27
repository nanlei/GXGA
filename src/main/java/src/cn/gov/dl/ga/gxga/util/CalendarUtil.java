package cn.gov.dl.ga.gxga.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public class CalendarUtil {

	private CalendarUtil() {
		throw new Error("Don't instant " + getClass().getName());
	}

	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	public static String getFormattedCurrentMonth() {
		String month = String.valueOf(getCurrentMonth());
		return StringUtils.leftPad(month, 2, '0');
	}

	public static String getCurrentYearAndMonth() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year + getFormattedCurrentMonth();
	}

	public static String getOffsetYearAndMonth(String offset, String ts) {
		int year = Integer.valueOf(ts.substring(0, 4));
		int month = Integer.valueOf(ts.substring(4, 6));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.add(Calendar.MONTH, Integer.valueOf(offset));
		return new SimpleDateFormat("yyyyMM").format(cal.getTime());
	}

	public static void main(String[] args) {
		System.out.println(getOffsetYearAndMonth("12", "201507"));
	}
}
