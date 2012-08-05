package jay.gsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GsmModem {
	private static final Logger logger = Logger.getLogger(GsmModem.class.getName());

	private static final MessageFormat CMTI = new MessageFormat("+CMTI: \"{0}\",{1}");
	private static final MessageFormat CMGR = new MessageFormat("AT+CMGR={0}");
	private static final MessageFormat CMGD = new MessageFormat("AT+CMGD={0}");
	private static final MessageFormat _CMGR = new MessageFormat("+CMGR: {0},{1},{2},{3}"); // +CMGR: "REC UNREAD","+919845525316",,"12/07/30,19:41:34+22"

	private Driver driver;
	
	private Connection connection;
	
	private List<SmsMessageListener> listeners;

//	private abstract static class At {
//		abstract public static class Sms {
//			public static String CMGR(int index) {
//				return String.format("AT+CMGR=%s",index);
//			}
//	    	/**
//	    	 * AT+CMGW="+919845525316"  <CR>
//	         * Hello SMS<CTRL-Z> 
//	         *
//	         * ASCII equivalent of ctrl-z is #026
//	    	 */		
//			public static String CMGW(String receipient, String message) {
//				return String.format("AT+CMGW=\"%s\"\n%s\u001A",receipient, message);
//			}
//			
//			public static String CMSS(int index) {
//				return String.format("AT+CMSS=%s",index);
//			}
//		}
//	}	
	
	public GsmModem() {
		listeners = new ArrayList<SmsMessageListener>();
	}
	
    public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Connection connect(String portName) throws Exception {
		connection = driver.connect(portName, null);
		
		connection.addConnectionListener(new EventManager());
		
		return connection;
    }
    
//    public float getSignalStrenth() {
//    	return 0.0f; // [0.0f ... 1.0f]
//    }
    
    public void addMessageListener(SmsMessageListener l) {
    	listeners.add(l);
    }
    
    public void removeMessageListener(SmsMessageListener l) {
    	listeners.remove(l);
    }
    
    protected void notifySmsMessage(SmsMessage message) {
    	for(SmsMessageListener l:listeners) {
    		l.onSmsMessage(message);
    	}
    }
    
    public void fetchAllMessages() {
    	try {
			connection.sendCommand("AT+CMGL=\"ALL\"");
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void fetchMessage(int messageId) {
    	try {
			connection.sendCommand( CMGR.format(new Object[]{messageId}));
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
//    public int sendMessage(String text, String receipient) throws ConnectionException {
//		String response = this.connection.executeCommand(At.Sms.CMGW(receipient, text));
//		
//		Object[] args = parse(CMGW, response);
//		Integer index = (Integer)args[0];
//		
//		response = this.connection.executeCommand(At.Sms.CMSS(index));
//		
//    	return index;
//    }
    
//    public void deleteAllMessages() {
//    	
//    }
    
    public void deleteMessage(int messageId) {
    	try {
			connection.sendCommand(CMGD.format(new Object[]{messageId}));
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    public MessageStorageInfo getMessageStorageInfo() {
    	return null;
    }
       
    public boolean isConnected() {
    	return false;
    }
    
    public void sendCommand(String command) throws Exception {
    	this.connection.sendCommand(command);
    }
    
    public void close() throws Exception {
        this.connection.close();
    }
    
    String clipQuotes(String text) {
    	text = text.trim();
		if (text.startsWith("\"") && text.endsWith("\"")) {
			text = text.substring(1,text.length()-1);
		}
		return text;
    }
    
    String [] lines(String text) {
    	List<String> lines = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new StringReader(text.trim()));
		try {
			String line = null;
			while((line=r.readLine())!=null) {
				lines.add(line);
			}
			r.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines.toArray(new String[0]);
    }
    
    private class EventManager implements ConnectionListener 
    {
    	@Override
		public void onUnsolicitedResult(UnsolicitedResult e) {
			String text = e.getText();
			
			if (text.startsWith("+CMTI:")) {
				Object[] args;
				try {
					args = CMTI.parse(text);
					int index = Integer.valueOf(args[1].toString());
					
					fetchMessage(index);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			System.out.println("$ " + e.getText());
		}
		
		@Override
		public void onCommandExecuted(CommandResult commandResult) {
			String responseText = commandResult.getResponseText().trim();
			System.out.println("> " + commandResult.getCommandString());
			System.out.printf("{\n%s\n}\n",responseText);
			
			String commandString = commandResult.getCommandString();
			
			if (commandString.startsWith("AT+CMGR=")){
				int index = -1;
				try {
					Object [] args = CMGR.parse(commandString);
					index = Integer.valueOf(args[0].toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String sender = null;
				String senderName = null;
				boolean read = false;
				StringBuffer textBuffer = new StringBuffer();
				
				String [] lines = lines(responseText);
				try {
					Object [] args = _CMGR.parse(lines[0]);
					read = "\"REC READ\"".equals(args[0]);
					sender = clipQuotes(args[1].toString());
					senderName = clipQuotes(args[2].toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(int i=1;i<lines.length-2;i++) {
					textBuffer.append(lines[i]).append('\n');
				}

				SmsMessage message = new SmsMessage();
				message.setId(index);
				message.setRead(read);
				message.setSender(sender);
				message.setSenderName(senderName);
				message.setText(textBuffer.toString());
				
				notifySmsMessage(message);
			}
		}
    }        
}
