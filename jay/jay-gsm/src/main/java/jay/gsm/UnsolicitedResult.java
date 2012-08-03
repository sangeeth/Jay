package jay.gsm;

import java.io.Serializable;

public class UnsolicitedResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String resultCode;
	
	private String text;

	public UnsolicitedResult() {
		super();
	}

	public UnsolicitedResult(String resultCode, String text) {
		super();
		this.resultCode = resultCode;
		this.text = text;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
