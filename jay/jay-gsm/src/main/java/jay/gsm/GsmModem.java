package jay.gsm;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

public class GsmModem {
	private static final Logger logger = Logger.getLogger(GsmModem.class.getName());
	
	private Driver driver;
	
	private Connection connection;
	
	private static final MessageFormat CMTI = new MessageFormat("+CMTI: \"{0}\",{1}");
	private static final MessageFormat CMGW = new MessageFormat("+CMGW: {0}");
	
	private abstract static class At {
		abstract public static class Sms {
			public static String CMGR(int index) {
				return String.format("AT+CMGR=%s",index);
			}
	    	/**
	    	 * AT+CMGW="+919845525316"  <CR>
	         * Hello SMS<CTRL-Z> 
	         *
	         * ASCII equivalent of ctrl-z is #026
	    	 */		
			public static String CMGW(String receipient, String message) {
				return String.format("AT+CMGW=\"%s\"\n%s\u001A",receipient, message);
			}
			
			public static String CMSS(int index) {
				return String.format("AT+CMSS=%s",index);
			}
		}
	}	
	
	public GsmModem() {
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
    
    public float getSignalStrenth() {
    	return 0.0f; // [0.0f ... 1.0f]
    }
    
    public void addMessageListener(SmsMessageListener l) {
    	
    }
    
    public void removeMessageListener(SmsMessageListener l) {
    	
    }
    
    protected void fireMessage(SmsMessage message) {
    	
    }
    
    public List<SmsMessage> getMessages() {
    	return null;
    }
    
    public int sendMessage(String text, String receipient) throws ConnectionException {
		String response = this.connection.executeCommand(At.Sms.CMGW(receipient, text));
		
		Object[] args = parse(CMGW, response);
		Integer index = (Integer)args[0];
		
		response = this.connection.executeCommand(At.Sms.CMSS(index));
		
    	return index;
    }
    
    public void deleteAllMessages() {
    	
    }
    
    public void deleteMessage(int messageId) {
    	
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

    private Object [] parse(MessageFormat messageFormat, String string) {
    	Object[] result = null;
		try {
			result = messageFormat.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    private class EventManager implements ConnectionListener 
    {
        @Override
		public void onCommandExecuted(CommandResult commandResult) {
			System.out.printf("RESPONSE: %s\n",commandResult.getResponseText());
		}

		@Override
		public void onUnsolicitedResult(UnsolicitedResult result) {
			
			if (result.getResultCode().equals("+CMTI")) {
            	try {
            		String response = result.getText();
            		logger.info("Parsing " + result.getText());
					Object[] args = CMTI.parse(response);
					if (args.length==2) {
						sendCommand("AT+CMGR="+args[1]+"\r");	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
		}
    }        
}
