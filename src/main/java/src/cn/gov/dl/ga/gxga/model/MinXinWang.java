package cn.gov.dl.ga.gxga.model;

/**
 * 民心网
 * 
 * @author Nan Lei
 * 
 */
public class MinXinWang {
	private String config_name;// 投诉人姓名
	private String config_phone;// 投诉人电话
	private String config_address;// 投诉人地址
	private String config_email;// 投诉人Mail
	private String config_time;// 投诉时间
	private String config_disposeTime;// 拟办时间
	private String config_content;// 投诉内容
	private String config_department;// 责任部门
	private String config_comment;// 拟办意见
	private String config_leader;// 分管领导
	private String config_leaderComment;// 领导意见

	public MinXinWang() {
		super();
	}

	public MinXinWang(String config_name, String config_phone,
			String config_address, String config_email, String config_time,
			String config_disposeTime, String config_content,
			String config_department, String config_comment,
			String config_leader, String config_leaderComment) {
		super();
		this.config_name = config_name;
		this.config_phone = config_phone;
		this.config_address = config_address;
		this.config_email = config_email;
		this.config_time = config_time;
		this.config_disposeTime = config_disposeTime;
		this.config_content = config_content;
		this.config_department = config_department;
		this.config_comment = config_comment;
		this.config_leader = config_leader;
		this.config_leaderComment = config_leaderComment;
	}

	public String getConfig_name() {
		return config_name;
	}

	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}

	public String getConfig_phone() {
		return config_phone;
	}

	public void setConfig_phone(String config_phone) {
		this.config_phone = config_phone;
	}

	public String getConfig_address() {
		return config_address;
	}

	public void setConfig_address(String config_address) {
		this.config_address = config_address;
	}

	public String getConfig_email() {
		return config_email;
	}

	public void setConfig_email(String config_email) {
		this.config_email = config_email;
	}

	public String getConfig_time() {
		return config_time;
	}

	public void setConfig_time(String config_time) {
		this.config_time = config_time;
	}

	public String getConfig_disposeTime() {
		return config_disposeTime;
	}

	public void setConfig_disposeTime(String config_disposeTime) {
		this.config_disposeTime = config_disposeTime;
	}

	public String getConfig_content() {
		return config_content;
	}

	public void setConfig_content(String config_content) {
		this.config_content = config_content;
	}

	public String getConfig_department() {
		return config_department;
	}

	public void setConfig_department(String config_department) {
		this.config_department = config_department;
	}

	public String getConfig_comment() {
		return config_comment;
	}

	public void setConfig_comment(String config_comment) {
		this.config_comment = config_comment;
	}

	public String getConfig_leader() {
		return config_leader;
	}

	public void setConfig_leader(String config_leader) {
		this.config_leader = config_leader;
	}

	public String getConfig_leaderComment() {
		return config_leaderComment;
	}

	public void setConfig_leaderComment(String config_leaderComment) {
		this.config_leaderComment = config_leaderComment;
	}

}
