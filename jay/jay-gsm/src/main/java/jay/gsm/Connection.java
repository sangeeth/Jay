package jay.gsm;

public interface Connection {
	/**
	 * Queues the given AT command for execution.
	 * @param command
	 * @return command identifier
	 */
	public long sendCommand(String command) throws ConnectionException;
	
	public String executeCommand(String command) throws ConnectionException;
	
	public void addConnectionListener(ConnectionListener l) throws ConnectionException;
	
	public void removeConnectionListener(ConnectionListener l) throws ConnectionException;
	
	public boolean isClosed() throws ConnectionException;
	
	public void close() throws ConnectionException;
}
