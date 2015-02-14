package cn.gov.dl.ga.gxga.process.admin.selfservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.dl.ga.gxga.core.Constant;
import cn.gov.dl.ga.gxga.core.controller.Process;
import cn.gov.dl.ga.gxga.core.controller.Result;
import cn.gov.dl.ga.gxga.service.admin.SelfService;

public class UserActionSearchProcess extends Process {
	private SelfService selfService;

	public void setSelfService(SelfService selfService) {
		this.selfService = selfService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Map<String, Object> loginUser = (Map<String, Object>) request
				.getSession().getAttribute(Constant.LOGIN_USER);

		int userId = (Integer) loginUser.get("userId");

		List<Map<String, Object>> userActionList = selfService
				.getUserActionListByUserId(userId);

		for (int i = 0; i < userActionList.size(); i++) {
			Map<String, Object> actionItem = userActionList.get(i);
			int actionId = (Integer) actionItem.get("actionId");
			String actionType = (String) actionItem.get("actionType");
			String refTable = (String) actionItem.get("refTable");
			int refId = (Integer) actionItem.get("refId");

			Map<String, Object> resultItem = new HashMap<String, Object>();
			resultItem.put("actionId", actionId);
			resultItem.put("actionType", buildActionTypeName(actionType));
			resultItem.put("ref", buildRef(refTable, refId));
			resultItem.put("actionTime", actionItem.get("actionTime"));

			resultList.add(resultItem);
		}

		model.put("total", resultList.size());
		model.put("data", resultList);

		return new Result(this.getSuccessView(), model);
	}

	private String buildActionTypeName(String actionType) {
		if ("SIGN".equals(actionType)) {
			return "文件签收";
		} else if ("MAILBOX".equals(actionType)) {
			return "局长信箱";
		}
		return "";
	}

	private String buildRef(String refTable, int refId) {
		if ("doc_article_sign".equals(refTable)) {
			String sql = "select a.articleTitle from doc_article a, doc_article_sign asign where a.articleId=asign.articleId and asign.asId=?";
			Object[] parameters = new Object[] { refId };
			return selfService.buildRefForUserAction(sql, parameters);
		} else if ("fun_mailbox".equals(refTable)) {
			String sql = "select m.mailSubject from fun_mailbox m where m.mailId=?";
			Object[] parameters = new Object[] { refId };
			return selfService.buildRefForUserAction(sql, parameters);
		}
		return "";
	}

}
