package cn.gov.dl.ga.gxga.model;

public class ReadHeadBranchFile {
	private String branchFileNo;
	private String articleTitle;
	private String bottomEnding;
	private String bottomDate;

	public ReadHeadBranchFile() {
		super();
	}

	public ReadHeadBranchFile(String branchFileNo, String articleTitle,
			String bottomEnding, String bottomDate) {
		super();
		this.branchFileNo = branchFileNo;
		this.articleTitle = articleTitle;
		this.bottomEnding = bottomEnding;
		this.bottomDate = bottomDate;
	}

	public String getBranchFileNo() {
		return branchFileNo;
	}

	public void setBranchFileNo(String branchFileNo) {
		this.branchFileNo = branchFileNo;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getBottomEnding() {
		return bottomEnding;
	}

	public void setBottomEnding(String bottomEnding) {
		this.bottomEnding = bottomEnding;
	}

	public String getBottomDate() {
		return bottomDate;
	}

	public void setBottomDate(String bottomDate) {
		this.bottomDate = bottomDate;
	}

	@Override
	public String toString() {
		return "ReadHeadBranchFile [branchFileNo=" + branchFileNo
				+ ", articleTitle=" + articleTitle + ", bottomEnding="
				+ bottomEnding + ", bottomDate=" + bottomDate + "]";
	}

}
