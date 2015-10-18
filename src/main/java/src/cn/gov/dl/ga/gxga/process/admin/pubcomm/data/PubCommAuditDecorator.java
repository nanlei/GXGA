package cn.gov.dl.ga.gxga.process.admin.pubcomm.data;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import cn.gov.dl.ga.gxga.core.Constant;

public class PubCommAuditDecorator {
	private static final String transactionNo = "编号";
	private static final String transactionTitle = "投诉标题";
	private static final String transactionStatus = "状态";
	private static final String transactionOrder = "排序";

	private static final String config_department = "投诉咨询对象";
	private static final String config_type = "投诉咨询类型";
	private static final String config_content = "内容";
	private static final String config_leaderComment = "领导审批";
	private static final String config_replyContent = "答复";
	private static final String config_reply = "答复人";
	private static final String config_date = "日期";

	private static final String STS_NEW = "待处理";
	private static final String STS_WAI = "处理中";
	private static final String STS_FIN = "办结";
	private static final String STS_OVT = "超时";

	private List<HashMap<String, String>> resultList;

	public PubCommAuditDecorator() {
		super();
	}

	public PubCommAuditDecorator(List<HashMap<String, String>> resultList) {
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

			if ("config_department".equals(fieldName)) {
				map.put("fieldName", config_department);
			}
			if ("config_type".equals(fieldName)) {
				map.put("fieldName", config_type);
			}
			if ("config_content".equals(fieldName)) {
				map.put("fieldName", config_content);
			}
			if ("config_leaderComment".equals(fieldName)) {
				map.put("fieldName", config_leaderComment);
			}
			if ("config_replyContent".equals(fieldName)) {
				map.put("fieldName", config_replyContent);
			}
			if ("config_reply".equals(fieldName)) {
				map.put("fieldName", config_reply);
			}
			if ("config_date".equals(fieldName)) {
				map.put("fieldName", config_date);
				map.put("oldValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("oldValue"))));
				map.put("newValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("newValue"))));
			}
		}
	}
}
