package server;

import java.io.*;
import java.net.*;
import shared.*;
import signal.*;
import logger.*;

public class Client extends Thread {
	
	private int m_clientNumber;
	private String m_userName;
	private String m_password;
	
	private InetAddress m_ipAddress;
	private int m_port;
	
	private Socket m_connection;
	private boolean m_connected = false;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ClientInputSignalQueue m_inSignalQueue;
	private ClientOutputSignalQueue m_outSignalQueue;
	
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	private Logger m_logger;
	
	public static int currentPort = 25502;
	
	public Client(Socket connection, int clientNumber) {
		m_clientNumber = clientNumber;
		m_connection = connection;
		m_ipAddress = connection.getInetAddress();
		m_port = currentPort++;
		m_inSignalQueue = new ClientInputSignalQueue();
		m_outSignalQueue = new ClientOutputSignalQueue();
	}
	
	public void initialize(Server server, Logger logger) {
		m_connected = true;
		
		try {
			m_logger = logger;
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(server, this, m_in, m_outSignalQueue, m_logger);
			m_outSignalQueue.initialize(this, m_out, m_logger);
			if(getState() == Thread.State.NEW) { start(); }
		}
		catch(IOException e) {
			m_connected = false;
			m_logger.addError("Unable to initalize connection to client #" + m_clientNumber);
		}
	}
	
	public Socket getConnection() { return m_connection; }
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public String getIPAddressString() { return m_ipAddress.getHostAddress(); }
	
	public int getPort() { return m_port; }
	
	public boolean isConnected() {
		return m_connected && !timeout();
	}
	
	public boolean ping() {
		if(!m_awaitingResponse && m_timeElapsed >= Globals.PING_INTERVAL) {
			m_timeElapsed = 0;
			m_awaitingResponse = true;
			m_outSignalQueue.addSignal(new Signal(SignalType.Ping));
			return true;
		}
		return false;
	}
	
	public void pong() {
		m_timeElapsed = 0;
		m_awaitingResponse = false;
	}
	
	public boolean awaitingResponse() { return m_awaitingResponse; }
	
	public int timeElapsed() { return m_timeElapsed; }
	
	public void addTime(long time) {
		if(time <= 0) { return; }
		m_timeElapsed += time;
	}
	
	public boolean timeout() {
		return m_awaitingResponse && m_timeElapsed >= Globals.CONNECTION_TIMEOUT;
	}
	
	public void disconnect() {
		m_connected = false;
		
		try { if(m_out != null) { m_out.close(); } } catch(IOException e) { }
		try { if(m_in != null) { m_in.close(); } } catch(IOException e) { }
		try { if(m_connection != null) { m_connection.close(); } } catch(IOException e) { }

		m_out = null;
		m_in = null;
		m_connection = null;
	}
	
	public void sendSignal(Signal s) {
		m_outSignalQueue.addSignal(s);
	}
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public int getClientNumber() { return m_clientNumber; }
	
	public String getUserName() { return m_userName; }
	
	public boolean checkUserName(String userName) {
		return m_userName != null &&
			   userName != null &&
			   m_userName.equalsIgnoreCase(userName);
	}
	
	public boolean checkPassword(String password) {
		return m_password != null &&
			   password != null &&
			   m_password.equals(password);
	}
	
	public void setUserName(String userName) { m_userName = userName; }
	
	public void setPassword(String password) { m_password = password; }
	
	public void run() {
		while(isConnected()) {
			m_inSignalQueue.readSignal();
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
