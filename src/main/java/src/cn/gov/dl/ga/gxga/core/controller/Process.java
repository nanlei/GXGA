package cn.gov.dl.ga.gxga.core.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Process基类，抽象的功能模块基类必须继承本类
 * 
 * @author Nan Lei
 * 
 */
public abstract class Process {
	private String validatedFailedForward;
	private String successView;
	private String failureView;
	private Validator[] validators;

	public String getValidatedFailedForward() {
		return validatedFailedForward;
	}

	public void setValidatedFailedForward(String validatedFailedForward) {
		this.validatedFailedForward = validatedFailedForward;
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public String getFailureView() {
		return failureView;
	}

	public void setFailureView(String failureView) {
		this.failureView = failureView;
	}

	public Validator[] getValidators() {
		return validators;
	}

	public void setValidators(Validator[] validators) {
		this.validators = validators;
	}

	public Result validatAndProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Result result = null;
		if (validators != null && validators.length != 0) {
			for (int i = 0; i < validators.length; i++) {
				Validator validator = validators[i];
				if (!validator.validate(request, response)) {
					return new Result(validator.getMsgCode(), validator
							.getMsgArgs(), validatedFailedForward,
							new HashMap<String, Object>());
				}
			}
		}
		result = process(request, response);
		return result;
	}

	public abstract Result process(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
