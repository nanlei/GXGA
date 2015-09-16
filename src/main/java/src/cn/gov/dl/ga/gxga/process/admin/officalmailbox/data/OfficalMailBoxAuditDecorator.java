package cn.gov.dl.ga.gxga.process.admin.officalmailbox.data;

import java.util.HashMap;
import java.util.List;

import cn.gov.dl.ga.gxga.core.Constant;

public class OfficalMailBoxAuditDecorator {
	private static final String transactionNo = "编号";
	private static final String transactionTitle = "投诉标题";
	private static final String transactionStatus = "状态";
	private static final String transactionOrder = "排序";

	private static final String config_no = "单号";
	private static final String config_unit = "单位";
	private static final String config_date = "日期";
	private static final String config_content = "办理事项";
	private static final String config_comment = "办公室意见";
	private static final String config_leaderComment = "领导批示";
	private static final String config_result = "办理结果";

	private static final String STS_NEW = "待处理";
	private static final String STS_WAI = "处理中";
	private static final String STS_FIN = "办结";
	private static final String STS_OVT = "超时";

	private List<HashMap<String, String>> resultList;

	public OfficalMailBoxAuditDecorator() {
		super();
	}

	public OfficalMailBoxAuditDecorator(List<HashMap<String, String>> resultList) {
		super();
		this.resultList = resultList;
	}

	public void decorate() throws Exception {
		for (int i = 0; i < resultList.size(); i++) {
			HashMap<String, String> map = resultList.get(i);
			String fieldName = map.get("fieldName");
			if ("transactionNo".equals(fieldName)) {
				map.put("fieldName", transactionNo);
			}
			if ("transactionTitle".equals(fieldName)) {
				map.put("fieldName", transactionTitle);
			}
			if ("transactionStatus".endsWith(fieldName)) {
				map.put("fieldName", transactionStatus);
				if (Constant.STS_NEW.equals(map.get("oldValue"))) {
					map.put("oldValue", STS_NEW);
				} else if (Constant.STS_WAI.equals(map.get("oldValue"))) {
					map.put("oldValue", STS_WAI);
				} else if (Constant.STS_FIN.equals(map.get("oldValue"))) {
					map.put("oldValue", STS_FIN);
				} else if (Constant.STS_OVT.equals(map.get("oldValue"))) {
					map.put("oldValue", STS_OVT);
				}

				if (Constant.STS_NEW.equals(map.get("newValue"))) {
					map.put("newValue", STS_NEW);
				} else if (Constant.STS_WAI.equals(map.get("newValue"))) {
					map.put("newValue", STS_WAI);
				} else if (Constant.STS_FIN.equals(map.get("newValue"))) {
					map.put("newValue", STS_FIN);
				} else if (Constant.STS_OVT.equals(map.get("newValue"))) {
					map.put("newValue", STS_OVT);
				}
			}
			if ("transactionOrder".equals(fieldName)) {
				map.put("fieldName", transactionOrder);
			}

			if ("config_no".equals(fieldName)) {
				map.put("fieldName", config_no);
			}
			if ("config_unit".equals(fieldName)) {
				map.put("fieldName", config_unit);
			}
			if ("config_date".equals(fieldName)) {
				map.put("fieldName", config_date);
			}
			if ("config_content".equals(fieldName)) {
				map.put("fieldName", config_content);
			}
			if ("config_comment".equals(fieldName)) {
				map.put("fieldName", config_comment);
			}
			if ("config_leaderComment".equals(fieldName)) {
				map.put("fieldName", config_leaderComment);
			}
			if ("config_result".equals(fieldName)) {
				map.put("fieldName", config_result);
			}
		}
	}
}
