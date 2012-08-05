package jay.gsm;

import java.io.Serializable;
import java.util.Date;

public class SmsMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String sender;
	
	private String senderName;
	
	private String text;
	
	private Date time;
	
	private boolean read;

	public SmsMessage() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}
