package cn.gov.dl.ga.gxga.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.gov.dl.ga.gxga.common.SystemCache;
import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.service.admin.ConstantService;
import cn.gov.dl.ga.gxga.service.front.IndexService;

public class FtlUtil {
	private ConstantService constantService;
	private IndexService indexService;

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public static String getSystemMode() {
		return SystemCache.getInstants().getMode();
	}

	@SuppressWarnings("unchecked")
	public static boolean hasPermission(String actionName, String roleId) {
		HashMap<String, Object> permissions = (HashMap<String, Object>) SystemCache.getInstants().getCacheMap()
				.get(Constant.CACHE_PERMISSION);

		if (null == permissions.get(actionName)) {
			return false;
		}
		String roles = (String) permissions.get(actionName);

		if (StringUtils.isEmpty(roles))
			return false;

		return roles.contains("#" + roleId + "#");

	}

	public static String getIP() {
		return CoreUtil
				.getIPAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}

	public String getConstantNameByTypeAndValue(String constantType, String constantValue) {
		return constantService.getConstantNameByTypeAndValue(constantType, constantValue);
	}

	public String getToday() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	public int getDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	public List<Map<String, Object>> getRankList() {
		return indexService.getRank();
	}

	public List<Map<String, Object>> getArticleListByTypeAndCode(String articleType, String articleCode) {
		return indexService.getArticleListByTypeAndCode(articleType, articleCode);
	}

	public List<Map<String, Object>> getArticleListByDcId(String dcId) {
		return indexService.getArticleListByDcId(dcId);
	}

	public List<Map<String, Object>> getAttachmentByArticleId(String articleId) {
		return indexService.getAttachmentByArticleId(articleId);
	}

	public List<Map<String, Object>> getImageByArticleId(String articleId) {
		return indexService.getImageByArticleId(articleId);
	}

	public List<Map<String, Object>> getDutyForIndex() {
		return indexService.getDutyForIndex();
	}

	public List<Map<String, Object>> getSignByArticleId(String articleId) {
		return indexService.getSignListByArticleId(articleId);
	}

	public List<Map<String, Object>> getJobList() {
		return indexService.getJobList();
	}

	public List<Map<String, Object>> getArticleListByJobCategoryId(String jobCategoryId) {
		return indexService.getArticleListByJobCategoryId(jobCategoryId);
	}

	// Paging
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getQueryStringWithoutPageNum() {
		Map m = new HashMap(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getParameterMap());
		m.remove("pageIndex");
		return QueryUtil.getQueryString(m);
	}

	public String getFullUrlWithoutPageNum() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletPath()
				+ "?" + getQueryStringWithoutPageNum();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getQueryStringWithoutPageInfo() {
		Map m = new HashMap(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getParameterMap());
		m.remove("pageIndex");
		m.remove("pageSize");
		return QueryUtil.getQueryString(m);
	}

	public String getFullUrlWithoutPageInfo() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletPath()
				+ "?" + getQueryStringWithoutPageInfo();
	}

	public static String substring(String str, int toCount, String suffix) {
		int reInt = 0;
		StringBuffer reStr = new StringBuffer();
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			if (toCount >= reInt) {
				reStr.append(tempChar[kk]);
			}
		}
		if (suffix != null && !"".equals(suffix)) {
			if (toCount == reInt || (toCount == reInt - 1))
				reStr.append(suffix);
		}
		return reStr.toString();
	}

	public static int getDaysOffset(String date) {
		return CalendarUtil.getDaysOffset(date);
	}
}
