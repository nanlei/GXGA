package cn.gov.dl.ga.gxga.model;

/**
 * 公众交流平台
 * 
 * @author Nan Lei
 * 
 */
public class PubComm {
	private String config_department;// 投诉咨询对象
	private String config_type;// 投诉咨询类型
	private String config_content;// 内容
	private String config_leaderComment;// 领导审批
	private String config_replyContent;// 答复
	private String config_reply;// 答复人
	private String config_date;// 日期

	public PubComm() {
		super();
	}

	public PubComm(String config_department, String config_type,
			String config_content, String config_leaderComment,
			String config_replyContent, String config_reply, String config_date) {
		super();
		this.config_department = config_department;
		this.config_type = config_type;
		this.config_content = config_content;
		this.config_leaderComment = config_leaderComment;
		this.config_replyContent = config_replyContent;
		this.config_reply = config_reply;
		this.config_date = config_date;
	}

	public String getConfig_department() {
		return config_department;
	}

	public void setConfig_department(String config_department) {
		this.config_department = config_department;
	}

	public String getConfig_type() {
		return config_type;
	}

	public void setConfig_type(String config_type) {
		this.config_type = config_type;
	}

	public String getConfig_content() {
		return config_content;
	}

	public void setConfig_content(String config_content) {
		this.config_content = config_content;
	}

	public String getConfig_leaderComment() {
		return config_leaderComment;
	}

	public void setConfig_leaderComment(String config_leaderComment) {
		this.config_leaderComment = config_leaderComment;
	}

	public String getConfig_replyContent() {
		return config_replyContent;
	}

	public void setConfig_replyContent(String config_replyContent) {
		this.config_replyContent = config_replyContent;
	}

	public String getConfig_reply() {
		return config_reply;
	}

	public void setConfig_reply(String config_reply) {
		this.config_reply = config_reply;
	}

	public String getConfig_date() {
		return config_date;
	}

	public void setConfig_date(String config_date) {
		this.config_date = config_date;
	}

}
