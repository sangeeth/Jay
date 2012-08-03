package jay.gsm;

import java.io.Serializable;

public class CommandResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private long commandId;
	
	private String command;
	
	private String commandString;
	
	private String responseText;

	public CommandResult() {
		super();
	}

	public long getCommandId() {
		return commandId;
	}

	public void setCommandId(long commandId) {
		this.commandId = commandId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCommandString() {
		return commandString;
	}

	public void setCommandString(String commandString) {
		this.commandString = commandString;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
}
