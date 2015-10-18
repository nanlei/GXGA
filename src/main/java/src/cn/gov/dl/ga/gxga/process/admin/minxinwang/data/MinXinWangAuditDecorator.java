package cn.gov.dl.ga.gxga.process.admin.minxinwang.data;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import cn.gov.dl.ga.gxga.core.Constant;

public class MinXinWangAuditDecorator {
	private static final String transactionNo = "编号";
	private static final String transactionTitle = "投诉标题";
	private static final String transactionStatus = "状态";
	private static final String transactionOrder = "排序";

	private static final String config_name = "投诉人姓名";
	private static final String config_phone = "投诉人电话";
	private static final String config_address = "投诉人地址";
	private static final String config_email = "投诉人Mail";
	private static final String config_time = "投诉时间";
	private static final String config_disposeTime = "拟办时间";
	private static final String config_content = "投诉内容";
	private static final String config_department = "责任部门";
	private static final String config_comment = "拟办意见";
	private static final String config_leader = "分管领导";
	private static final String config_leaderComment = "领导意见";

	private static final String STS_NEW = "待处理";
	private static final String STS_WAI = "处理中";
	private static final String STS_FIN = "办结";
	private static final String STS_OVT = "超时";

	private List<HashMap<String, String>> resultList;

	public MinXinWangAuditDecorator() {
		super();
	}

	public MinXinWangAuditDecorator(List<HashMap<String, String>> resultList) {
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

			if ("config_name".equals(fieldName)) {
				map.put("fieldName", config_name);
			}
			if ("config_phone".equals(fieldName)) {
				map.put("fieldName", config_phone);
			}
			if ("config_address".equals(fieldName)) {
				map.put("fieldName", config_address);
			}
			if ("config_email".equals(fieldName)) {
				map.put("fieldName", config_email);
			}
			if ("config_time".equals(fieldName)) {
				map.put("fieldName", config_time);
				map.put("oldValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("oldValue"))));
				map.put("newValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("newValue"))));
			}
			if ("config_disposeTime".equals(fieldName)) {
				map.put("fieldName", config_disposeTime);
				map.put("oldValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("oldValue"))));
				map.put("newValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("newValue"))));
			}
			if ("config_content".equals(fieldName)) {
				map.put("fieldName", config_content);
			}
			if ("config_department".equals(fieldName)) {
				map.put("fieldName", config_department);
			}
			if ("config_comment".equals(fieldName)) {
				map.put("fieldName", config_comment);
			}
			if ("config_leader".equals(fieldName)) {
				map.put("fieldName", config_leader);
			}
			if ("config_leaderComment".equals(fieldName)) {
				map.put("fieldName", config_leaderComment);
			}

		}
	}
}
