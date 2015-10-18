package cn.gov.dl.ga.gxga.model;

/**
 * 民意网
 * 
 * @author Nan Lei
 * 
 */
public class MinYiWang {
	private String config_noticeNo;// 单号
	private String config_noticeUnit;// 签发单位
	private String config_noticeDate;// 签发日期
	private String config_noticeHost;// 承办单位
	private String config_noticeContent;// 转办事由
	private String config_noticeLeaderComment;// 部门领导审批
	private String config_noticeReply;// 答复人
	private String config_noticeReplyTime;// 答复时间
	private String config_name;// 网友姓名
	private String config_contact;// 联系方式
	private String config_content;// 内容
	private String config_reply;// 答复

	public MinYiWang() {
		super();
	}

	public MinYiWang(String config_noticeNo, String config_noticeUnit,
			String config_noticeDate, String config_noticeHost,
			String config_noticeContent, String config_noticeLeaderComment,
			String config_noticeReply, String config_noticeReplyTime,
			String config_name, String config_contact, String config_content,
			String config_reply) {
		super();
		this.config_noticeNo = config_noticeNo;
		this.config_noticeUnit = config_noticeUnit;
		this.config_noticeDate = config_noticeDate;
		this.config_noticeHost = config_noticeHost;
		this.config_noticeContent = config_noticeContent;
		this.config_noticeLeaderComment = config_noticeLeaderComment;
		this.config_noticeReply = config_noticeReply;
		this.config_noticeReplyTime = config_noticeReplyTime;
		this.config_name = config_name;
		this.config_contact = config_contact;
		this.config_content = config_content;
		this.config_reply = config_reply;
	}

	public String getConfig_noticeNo() {
		return config_noticeNo;
	}

	public void setConfig_noticeNo(String config_noticeNo) {
		this.config_noticeNo = config_noticeNo;
	}

	public String getConfig_noticeUnit() {
		return config_noticeUnit;
	}

	public void setConfig_noticeUnit(String config_noticeUnit) {
		this.config_noticeUnit = config_noticeUnit;
	}

	public String getConfig_noticeDate() {
		return config_noticeDate;
	}

	public void setConfig_noticeDate(String config_noticeDate) {
		this.config_noticeDate = config_noticeDate;
	}

	public String getConfig_noticeHost() {
		return config_noticeHost;
	}

	public void setConfig_noticeHost(String config_noticeHost) {
		this.config_noticeHost = config_noticeHost;
	}

	public String getConfig_noticeContent() {
		return config_noticeContent;
	}

	public void setConfig_noticeContent(String config_noticeContent) {
		this.config_noticeContent = config_noticeContent;
	}

	public String getConfig_noticeLeaderComment() {
		return config_noticeLeaderComment;
	}

	public void setConfig_noticeLeaderComment(String config_noticeLeaderComment) {
		this.config_noticeLeaderComment = config_noticeLeaderComment;
	}

	public String getConfig_noticeReply() {
		return config_noticeReply;
	}

	public void setConfig_noticeReply(String config_noticeReply) {
		this.config_noticeReply = config_noticeReply;
	}

	public String getConfig_noticeReplyTime() {
		return config_noticeReplyTime;
	}

	public void setConfig_noticeReplyTime(String config_noticeReplyTime) {
		this.config_noticeReplyTime = config_noticeReplyTime;
	}

	public String getConfig_name() {
		return config_name;
	}

	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}

	public String getConfig_contact() {
		return config_contact;
	}

	public void setConfig_contact(String config_contact) {
		this.config_contact = config_contact;
	}

	public String getConfig_content() {
		return config_content;
	}

	public void setConfig_content(String config_content) {
		this.config_content = config_content;
	}

	public String getConfig_reply() {
		return config_reply;
	}

	public void setConfig_reply(String config_reply) {
		this.config_reply = config_reply;
	}

}
