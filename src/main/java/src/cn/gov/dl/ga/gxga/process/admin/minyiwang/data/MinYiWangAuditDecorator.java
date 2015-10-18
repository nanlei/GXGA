package cn.gov.dl.ga.gxga.process.admin.minyiwang.data;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import cn.gov.dl.ga.gxga.core.Constant;

public class MinYiWangAuditDecorator {
	private static final String transactionNo = "编号";
	private static final String transactionTitle = "投诉标题";
	private static final String transactionStatus = "状态";
	private static final String transactionOrder = "排序";

	private static final String config_noticeNo = "单号";
	private static final String config_noticeUnit = "签发单位";
	private static final String config_noticeDate = "签发日期";
	private static final String config_noticeHost = "承办单位";
	private static final String config_noticeContent = "转办事由";
	private static final String config_noticeLeaderComment = "部门领导审批";
	private static final String config_noticeReply = "答复人";
	private static final String config_noticeReplyTime = "答复时间";
	private static final String config_name = "网友姓名";
	private static final String config_contact = "联系方式";
	private static final String config_content = "内容";
	private static final String config_reply = "答复";

	private static final String STS_NEW = "待处理";
	private static final String STS_WAI = "处理中";
	private static final String STS_FIN = "办结";
	private static final String STS_OVT = "超时";

	private List<HashMap<String, String>> resultList;

	public MinYiWangAuditDecorator() {
		super();
	}

	public MinYiWangAuditDecorator(List<HashMap<String, String>> resultList) {
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

			if ("config_noticeNo".equals(fieldName)) {
				map.put("fieldName", config_noticeNo);
			}
			if ("config_noticeUnit".equals(fieldName)) {
				map.put("fieldName", config_noticeUnit);
			}
			if ("config_noticeDate".equals(fieldName)) {
				map.put("fieldName", config_noticeDate);
				map.put("oldValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("oldValue"))));
				map.put("newValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("newValue"))));
			}
			if ("config_noticeHost".equals(fieldName)) {
				map.put("fieldName", config_noticeHost);
			}
			if ("config_noticeContent".equals(fieldName)) {
				map.put("fieldName", config_noticeContent);
			}
			if ("config_noticeLeaderComment".equals(fieldName)) {
				map.put("fieldName", config_noticeLeaderComment);
			}
			if ("config_noticeReply".equals(fieldName)) {
				map.put("fieldName", config_noticeReply);
			}
			if ("config_noticeReplyTime".equals(fieldName)) {
				map.put("fieldName", config_noticeReplyTime);
				map.put("oldValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("oldValue"))));
				map.put("newValue", new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(map
								.get("newValue"))));
			}
			if ("config_name".equals(fieldName)) {
				map.put("fieldName", config_name);
			}
			if ("config_contact".equals(fieldName)) {
				map.put("fieldName", config_contact);
			}
			if ("config_content".equals(fieldName)) {
				map.put("fieldName", config_content);
			}
			if ("config_reply".equals(fieldName)) {
				map.put("fieldName", config_reply);
			}
		}
	}
}
