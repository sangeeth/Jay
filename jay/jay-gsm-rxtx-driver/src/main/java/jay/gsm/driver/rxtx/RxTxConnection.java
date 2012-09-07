package jay.gsm.driver.rxtx;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.TooManyListenersException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import jay.gsm.CommandResult;
import jay.gsm.Connection;
import jay.gsm.ConnectionException;
import jay.gsm.ConnectionListener;
import jay.gsm.ConnectionState;
import jay.gsm.UnsolicitedResult;

public class RxTxConnection implements Connection, SerialPortEventListener {
	private static final Logger logger = Logger.getLogger(RxTxConnection.class.getName());
	
	private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;
    
    private byte[] buffer = new byte[1024];
    
    private boolean open;
    
    private List<ConnectionListener> listeners;
    
    private Queue<String> commandQueue;
    
    private ConnectionState connectionState;
	
	public RxTxConnection(SerialPort serialPort) throws IOException, TooManyListenersException {
		super();

		this.connectionState = ConnectionState.ACTIVE;
		this.serialPort = serialPort;
		
        this.in = serialPort.getInputStream();
        this.out = serialPort.getOutputStream();
        
        this.serialPort.addEventListener(this);
        this.serialPort.notifyOnDataAvailable(true);	
        
        this.listeners = new ArrayList<ConnectionListener>();
        
        this.commandQueue = new ArrayBlockingQueue<String>(50);
	}

	// Implementing Connection methods
	@Override
	public long sendCommand(String command) throws ConnectionException {
		
		this.commandQueue.add(command);
		
		if (this.connectionState!=ConnectionState.ACTIVE) {
		    return -1;
		}
		
		System.out.println("Sending "+command);
		command = command + "\r";
		try {
			out.write(command.getBytes());
			out.flush();
		} catch (IOException e) {
		    // FIXME This mainly means the Mobile Equipment is not reachable. 
		    // Hence it better to clear the command queue.
		    logger.log(Level.WARNING, String.format("Failed to submit command %s. Probably the Stub is turned-off. Clearing the command-queue: %s", command, this.commandQueue));
    		this.commandQueue.clear();    
			// TODO Auto-generated catch block
			throw new ConnectionException(e);
		}
		
		return 0;
	}

	@Override
	public String executeCommand(String command) throws ConnectionException {
		return null;
	}

	@Override
	public void addConnectionListener(ConnectionListener l)
			throws ConnectionException {
		this.listeners.add(l);
	}

	@Override
	public void removeConnectionListener(ConnectionListener l)
			throws ConnectionException {
		this.listeners.remove(l);
	}
	
	@Override
	public boolean isClosed() throws ConnectionException {
		return !open;
	}

	@Override
	public void close() throws ConnectionException {
        logger.info("Closing I/O");
        
        try {
        	this.in.close();
        	this.out.close();
        } catch(IOException e) {
        	// FIXME improve exception handling
        	throw new ConnectionException(e);
        }
        
        if (this.serialPort!=null) {
            this.serialPort.removeEventListener();
            logger.info("Closing comm port");
            this.serialPort.close();
            logger.info("Closed");
        }	
        
        this.open = false;
	}

	// Implementing SerialPortEventListener
	@Override
	public void serialEvent(SerialPortEvent event) {
		int data;
        
        try {
            int len = 0;
            while ( ( data = in.read()) > -1 ) {
                if (data=='\0') {
//                    if (this.connectionState==ConnectionState.SUSPENDED) {
//                        onStateChanged(ConnectionState.RESET);
//                    } else {
//                        onStateChanged(ConnectionState.SUSPENDED);
//                    }
                    String message = String.format("type: %s,  newValue: %s; oldValue: %s",event.getEventType(),
                    event.getNewValue(),
                    event.getOldValue());
                    System.err.println("-----NULL-----" + message);
//                    return;
                }
                if ( data == '\n' ) {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            
            String response = new String(buffer,0,len).trim(); 
            
            System.out.printf("RESPONSE: %s\n",response);
            
            handleResponseLine(response);
        } catch ( IOException e ) {
            e.printStackTrace();
            
            onStateChanged(ConnectionState.DISCONNECTED);
        }    		
	}
	
	private void onStateChanged(ConnectionState connectionState) {
	    this.connectionState = connectionState;
	    
	    for(ConnectionListener l:this.listeners) {
            l.onStateChanged(connectionState);
        }
	}
	
	private void onCommandExecuted(CommandResult commandResult) {
		for(ConnectionListener l:this.listeners) {
			l.onCommandExecuted(commandResult);
		}
	}
	
	private void onUnsolicitedResult(UnsolicitedResult unsolicitedResult) {
		for(ConnectionListener l:this.listeners) {
			l.onUnsolicitedResult(unsolicitedResult);
		}
	}
		
	
	private StringBuffer responseTextBuffer = new StringBuffer();
	private void handleResponseLine(String message) {
		if (message.startsWith("+CMTI:")) {
			UnsolicitedResult unsolicitedResult = new UnsolicitedResult();
			unsolicitedResult.setResultCode("+CMTI");
			unsolicitedResult.setText(message);
			
			onUnsolicitedResult(unsolicitedResult);
		} else {
			responseTextBuffer.append(message);
			responseTextBuffer.append('\n');
			
			if ("OK".equals(message)) {
				CommandResult commandResult = new CommandResult();
				String command = commandQueue.remove();
				commandResult.setCommandString(command);
				commandResult.setResponseText(responseTextBuffer.toString());
				
				responseTextBuffer.setLength(0);
			
				onCommandExecuted(commandResult);
			}
		}
	}
}