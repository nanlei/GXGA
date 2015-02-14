package cn.gov.dl.ga.gxga.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验器基类
 * 
 * @author Nanlei
 * 
 */
public abstract class Validator {
	private String fieldName;
	private String msgCode;
	private Object[] msgArgs;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Object[] getMsgArgs() {
		return msgArgs;
	}

	public void setMsgArgs(Object[] msgArgs) {
		this.msgArgs = msgArgs;
	}

	public abstract boolean validate(HttpServletRequest request,
			HttpServletResponse response);
}
