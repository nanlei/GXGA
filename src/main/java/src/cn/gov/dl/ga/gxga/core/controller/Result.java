package cn.gov.dl.ga.gxga.core.controller;

import java.util.HashMap;

/**
 * Result返回结果定义
 * 
 * @author Nanlei
 * 
 */
public class Result {
	private String msgCode;
	private Object[] msgArgs;
	private String forward;
	private boolean isRedirect = false;
	private boolean isForward = false;

	private HashMap<String, Object> model = new HashMap<String, Object>();

	public Result(String forward, HashMap<String, Object> model) {
		super();
		this.msgCode = null;
		this.msgArgs = null;
		this.forward = forward;
		this.model = model;
	}

	public Result(String forward) {
		super();
		this.msgCode = null;
		this.msgArgs = null;
		this.forward = forward;
		this.isRedirect = true;
	}

	public Result(String msgCode, Object[] msgArgs, String forward,
			HashMap<String, Object> model) {
		super();
		this.msgCode = msgCode;
		this.msgArgs = msgArgs;
		this.forward = forward;
		this.model = model;
	}

	public Result(String msgCode, Object[] msgArgs, String forward,
			boolean isRedirect, HashMap<String, Object> model) {
		super();
		this.msgCode = msgCode;
		this.msgArgs = msgArgs;
		this.forward = forward;
		this.model = model;
		this.isRedirect = isRedirect;
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

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public HashMap<String, Object> getModel() {
		return model;
	}

	public void addAllProperty(HashMap<String, Object> model) {
		model.putAll(model);
	}

	public void addProperty(String key, Object value) {
		model.put(key, value);
	}

	public void removeProperty(String key) {
		model.remove(key);
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public boolean isForward() {
		return isForward;
	}

	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}
}
