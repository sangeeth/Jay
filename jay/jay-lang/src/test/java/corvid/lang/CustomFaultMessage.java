package corvid.lang;

import jay.lang.IFaultMessage;

public class CustomFaultMessage implements IFaultMessage {
	private String description;
	private String cause;
	private String action;
	private String formattedString;
	
	public CustomFaultMessage() {
		super();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String getSummary() {
		return this.description;
	}
	public String getFormattedString() {
		return formattedString;
	}
	public void setFormattedString(String formattedString) {
		this.formattedString = formattedString;
	}
	public String toString() {
		return this.formattedString;
	}
}