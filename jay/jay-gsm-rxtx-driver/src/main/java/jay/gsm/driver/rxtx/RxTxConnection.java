package jay.gsm.driver.rxtx;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import java.util.logging.Logger;

import jay.gsm.Connection;
import jay.gsm.ConnectionException;
import jay.gsm.ConnectionListener;

public class RxTxConnection implements Connection, SerialPortEventListener {
	private static final Logger logger = Logger.getLogger(RxTxConnection.class.getName());
	
	private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;
    
    private byte[] buffer = new byte[1024];
    
    private boolean open;
	
	public RxTxConnection(SerialPort serialPort) throws IOException, TooManyListenersException {
		super();

		this.serialPort = serialPort;
		
        this.in = serialPort.getInputStream();
        this.out = serialPort.getOutputStream();
        
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);		
	}

	// Implementing Connection methods
	@Override
	public long sendCommand(String command) throws ConnectionException {
		return 0;
	}

	@Override
	public String executeCommand(String command) throws ConnectionException {
		return null;
	}

	@Override
	public void addConnectionListener(ConnectionListener l) throws ConnectionException {
	}

	@Override
	public void removeConnectionListener(ConnectionListener l) throws ConnectionException {
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
        } catch(IOException e) {
        	// FIXME improve exception handling
        	throw new ConnectionException(e);
        }
        
        try {
			this.out.close();
		} catch (IOException e) {
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
                if ( data == '\n' ) {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            
            String response = new String(buffer,0,len).trim(); 
            
            System.out.printf("RESPONSE: %s\n",response);
        } catch ( IOException e ) {
            e.printStackTrace();
        }    		
	}
}
