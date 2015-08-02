package cn.gov.dl.ga.gxga.process.admin.branchfile.data;

import java.util.List;
import java.util.Map;

import cn.gov.dl.ga.gxga.core.Constant;

public class BranchFileDecorator {
	private static final String BIZTTYPE_NOR = "普通文章";
	private static final String BIZTYPE_RED = "红头文件";

	private List<Map<String, Object>> resultList;

	public BranchFileDecorator(List<Map<String, Object>> resultList) {
		super();
		this.resultList = resultList;
	}

	public void decorate() {
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> map = resultList.get(i);
			String articleBizType = (String) map.get("articleBizType");
			if (Constant.ARTICLEBIZTYPE_NOR.equals(articleBizType)) {
				map.put("articleBizType", BIZTTYPE_NOR);
			} else if (Constant.ARTICLEBIZTYPE_RED.equals(articleBizType)) {
				map.put("articleBizType", BIZTYPE_RED);
			}
		}
	}
}
