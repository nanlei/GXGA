package cn.gov.dl.ga.gxga.model;

/**
 * 局长信箱
 * 
 * @author Nan Lei
 * 
 */
public class OfficalMailBox {
	private String config_no; // 单号：【2014】第8号
	private String config_unit;// 单位：
	private String config_date;// 日期：2014年11月19日
	private String config_content;// 办理事项
	private String config_comment;// 办公室意见
	private String config_leaderComment;// 领导批示
	private String config_result;// 办理结果

	public OfficalMailBox() {
		super();
	}

	public OfficalMailBox(String config_no, String config_unit,
			String config_date, String config_content, String config_comment,
			String config_leaderComment, String config_result) {
		super();
		this.config_no = config_no;
		this.config_unit = config_unit;
		this.config_date = config_date;
		this.config_content = config_content;
		this.config_comment = config_comment;
		this.config_leaderComment = config_leaderComment;
		this.config_result = config_result;
	}

	public String getConfig_no() {
		return config_no;
	}

	public void setConfig_no(String config_no) {
		this.config_no = config_no;
	}

	public String getConfig_unit() {
		return config_unit;
	}

	public void setConfig_unit(String config_unit) {
		this.config_unit = config_unit;
	}

	public String getConfig_date() {
		return config_date;
	}

	public void setConfig_date(String config_date) {
		this.config_date = config_date;
	}

	public String getConfig_content() {
		return config_content;
	}

	public void setConfig_content(String config_content) {
		this.config_content = config_content;
	}

	public String getConfig_comment() {
		return config_comment;
	}

	public void setConfig_comment(String config_comment) {
		this.config_comment = config_comment;
	}

	public String getConfig_leaderComment() {
		return config_leaderComment;
	}

	public void setConfig_leaderComment(String config_leaderComment) {
		this.config_leaderComment = config_leaderComment;
	}

	public String getConfig_result() {
		return config_result;
	}

	public void setConfig_result(String config_result) {
		this.config_result = config_result;
	}

}
