package cn.gov.dl.ga.gxga.model;

public class RedHeadNotice {
	private String topUnit;
	private String noticeNo;
	private String topDate;
	private String noticeHost;
	private String noticeContact;
	private String noticePhone;
	private String bottomDate;

	public RedHeadNotice() {
		super();
	}

	public RedHeadNotice(String topUnit, String noticeNo, String topDate,
			String noticeHost, String noticeContact, String noticePhone,
			String bottomDate) {
		super();
		this.topUnit = topUnit;
		this.noticeNo = noticeNo;
		this.topDate = topDate;
		this.noticeHost = noticeHost;
		this.noticeContact = noticeContact;
		this.noticePhone = noticePhone;
		this.bottomDate = bottomDate;
	}

	public String getTopUnit() {
		return topUnit;
	}

	public void setTopUnit(String topUnit) {
		this.topUnit = topUnit;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getTopDate() {
		return topDate;
	}

	public void setTopDate(String topDate) {
		this.topDate = topDate;
	}

	public String getNoticeHost() {
		return noticeHost;
	}

	public void setNoticeHost(String noticeHost) {
		this.noticeHost = noticeHost;
	}

	public String getNoticeContact() {
		return noticeContact;
	}

	public void setNoticeContact(String noticeContact) {
		this.noticeContact = noticeContact;
	}

	public String getNoticePhone() {
		return noticePhone;
	}

	public void setNoticePhone(String noticePhone) {
		this.noticePhone = noticePhone;
	}

	public String getBottomDate() {
		return bottomDate;
	}

	public void setBottomDate(String bottomDate) {
		this.bottomDate = bottomDate;
	}

	@Override
	public String toString() {
		return "RedHeadNotice [topUnit=" + topUnit + ", noticeNo=" + noticeNo
				+ ", topDate=" + topDate + ", noticeHost=" + noticeHost
				+ ", noticeContact=" + noticeContact + ", noticePhone="
				+ noticePhone + ", bottomDate=" + bottomDate + "]";
	}

}
