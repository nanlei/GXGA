package cn.gov.dl.ga.gxga.process.admin.officalmailbox.data;

import java.util.List;
import java.util.Map;

import cn.gov.dl.ga.gxga.core.Constant;

public class OfficalMailBoxDecorator {
	private static final String STS_NEW = "待处理";
	private static final String STS_WAI = "处理中";
	private static final String STS_FIN = "办结";
	private static final String STS_OVT = "超时";

	private List<Map<String, Object>> resultList;

	private Map<String, Object> resultMap;

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public OfficalMailBoxDecorator() {
		super();
	}

	public OfficalMailBoxDecorator(List<Map<String, Object>> resultList) {
		super();
		this.resultList = resultList;
	}

	public OfficalMailBoxDecorator(Map<String, Object> resultMap) {
		super();
		this.resultMap = resultMap;
	}

	public void decorate() {
		if (resultList != null) {
			decorateList();
		} else if (resultMap != null) {
			decorateMap();
		}
	}

	private void decorateList() {
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> map = resultList.get(i);
			String transactionStatus = (String) map.get("transactionStatus");
			if (Constant.STS_NEW.equals(transactionStatus)) {
				map.put("transactionStatus", STS_NEW);
			} else if (Constant.STS_WAI.equals(transactionStatus)) {
				map.put("transactionStatus", STS_WAI);
			} else if (Constant.STS_FIN.equals(transactionStatus)) {
				map.put("transactionStatus", STS_FIN);
			} else if (Constant.STS_OVT.equals(transactionStatus)) {
				map.put("transactionStatus", STS_OVT);
			}
		}
	}

	private void decorateMap() {
		String transactionStatus = (String) resultMap.get("transactionStatus");
		if (Constant.STS_NEW.equals(transactionStatus)) {
			resultMap.put("transactionStatus", STS_NEW);
		} else if (Constant.STS_WAI.equals(transactionStatus)) {
			resultMap.put("transactionStatus", STS_WAI);
		} else if (Constant.STS_FIN.equals(transactionStatus)) {
			resultMap.put("transactionStatus", STS_FIN);
		} else if (Constant.STS_OVT.equals(transactionStatus)) {
			resultMap.put("transactionStatus", STS_OVT);
		}
	}
}
