package cn.gov.dl.ga.gxga.process.admin.officalmailbox.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.gov.dl.ga.gxga.model.Audit;
import cn.gov.dl.ga.gxga.model.OfficalMailBox;
import cn.gov.dl.ga.gxga.util.JSONParser;

public class OfficalMailBoxAudit {
	private HashMap<String, Object> transaction;

	private String transactionNo;
	private String transactionTitle;
	private String transactionOrder;
	private String transactionStatus;
	private String transactionConfig;

	public HashMap<String, Object> getTransaction() {
		return transaction;
	}

	public void setTransaction(HashMap<String, Object> transaction) {
		this.transaction = transaction;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionTitle() {
		return transactionTitle;
	}

	public void setTransactionTitle(String transactionTitle) {
		this.transactionTitle = transactionTitle;
	}

	public String getTransactionOrder() {
		return transactionOrder;
	}

	public void setTransactionOrder(String transactionOrder) {
		this.transactionOrder = transactionOrder;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionConfig() {
		return transactionConfig;
	}

	public void setTransactionConfig(String transactionConfig) {
		this.transactionConfig = transactionConfig;
	}

	public OfficalMailBoxAudit() {
		super();
	}

	public OfficalMailBoxAudit(HashMap<String, Object> transaction,
			String transactionNo, String transactionTitle,
			String transactionOrder, String transactionStatus,
			String transactionConfig) {
		super();
		this.transaction = transaction;
		this.transactionNo = transactionNo;
		this.transactionTitle = transactionTitle;
		this.transactionOrder = transactionOrder;
		this.transactionStatus = transactionStatus;
		this.transactionConfig = transactionConfig;
	}

	public List<Audit> audit() {
		List<Audit> auditConfig = new ArrayList<Audit>();

		String dbTransactionNo = (String) transaction.get("transactionNo");
		if (!StringUtils.equals(transactionNo, dbTransactionNo)) {
			auditConfig.add(new Audit("transactionNo", dbTransactionNo,
					transactionNo));
		}

		String dbTransactionTitle = (String) transaction
				.get("transactionTitle");
		if (!StringUtils.equals(transactionTitle, dbTransactionTitle)) {
			auditConfig.add(new Audit("transactionTitle", dbTransactionTitle,
					transactionTitle));
		}

		String dbTransactionOrder = ((Integer) transaction
				.get("transactionOrder")).toString();
		if (!StringUtils.equals(transactionOrder, dbTransactionOrder)) {
			auditConfig.add(new Audit("transactionOrder", dbTransactionOrder,
					transactionOrder));
		}

		String dbTransactionStatus = (String) transaction
				.get("transactionStatus");
		if (!StringUtils.equals(transactionStatus, dbTransactionStatus)) {
			auditConfig.add(new Audit("transactionStatus", dbTransactionStatus,
					transactionStatus));
		}

		auditTractionConfig(auditConfig);

		return auditConfig;

	}

	public void auditTractionConfig(List<Audit> auditConfig) {
		String dbTransactionConfig = (String) transaction
				.get("transactionConfig");
		HashMap<String, String> dbTransactionConfigMap = JSONParser
				.parseJSON(dbTransactionConfig);

		HashMap<String, String> transactionConfigMap = JSONParser
				.parseJSON(transactionConfig);

		Field[] fields = OfficalMailBox.class.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if ("config_content".equals(fieldName)) {
				if (!StringUtils.equals(dbTransactionConfigMap.get(fieldName),
						transactionConfigMap.get(fieldName))) {
					auditConfig.add(new Audit(fieldName, "内容有变动", "内容有变动"));
				}
			} else if (!StringUtils.equals(
					dbTransactionConfigMap.get(fieldName),
					transactionConfigMap.get(fieldName))) {
				auditConfig.add(new Audit(fieldName, dbTransactionConfigMap
						.get(fieldName), transactionConfigMap.get(fieldName)));
			}
		}

		if (!StringUtils.equals(dbTransactionConfigMap.get(""),
				transactionConfigMap.get(""))) {
		}

	}
}
