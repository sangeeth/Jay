package jay.gsm.driver.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TooManyListenersException;
import java.util.TreeMap;

import jay.gsm.Connection;
import jay.gsm.ConnectionException;
import jay.gsm.Driver;
import jay.gsm.DriverException;

public class RxTxDriver implements Driver {

	private Map<String, Connection> connectionMap;

	public RxTxDriver() {
		super();

		connectionMap = new TreeMap<String, Connection>();
	}

	@Override
	public Connection connect(String port, Properties properties) throws DriverException {

		Connection connection = connectionMap.get(port);

		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
		} catch (ConnectionException e) {
			//FIXME Handle this exception appropriately.
			throw new DriverException(e.getMessage());
		}

		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(port);
		} catch(NoSuchPortException e) {
			//FIXME Handle this exception appropriately.
			throw new DriverException(e.getMessage());
		}
		
		if (portIdentifier.isCurrentlyOwned()) {
			throw new DriverException(String.format("Port %s is currently in use",port));
		}
		
		CommPort commPort = null;
		
		try {
			commPort = portIdentifier.open(this.getClass().getName(), 2000);
		} catch(PortInUseException e) {
			throw new DriverException(e.getMessage()); //FIXME Handle this exception appropriately. 
		}

		if (!(commPort instanceof SerialPort)) {
			// FIXME Need to improve error handling
			throw new DriverException("Unsupported port type");
		}
		
		SerialPort serialPort = (SerialPort) commPort;
		
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			//FIXME Handle this exception appropriately. 
			throw new DriverException(e.getMessage());
		}
		
		try {
			connection = new RxTxConnection(serialPort);
		} catch (IOException e) {
			//FIXME Handle this exception appropriately. 
			throw new DriverException(e.getMessage());
		} catch (TooManyListenersException e) {
			//FIXME Handle this exception appropriately. 
			throw new DriverException(e.getMessage());
		}

		return connection;
	}

}
