package cn.gov.dl.ga.gxga.model;

public class Audit {
	private String fieldName;
	private String oldValue;
	private String newValue;

	public Audit() {
		super();
	}

	public Audit(String fieldName, String oldValue, String newValue) {
		super();
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

}
