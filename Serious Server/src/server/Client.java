package server;

import java.io.*;
import java.net.*;
import shared.*;
import signal.*;
import logger.*;

public class Client extends UserNetworkData {
	
	private int m_clientNumber;
	private String m_password;
	
	private Socket m_connection;
	private boolean m_connected = false;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ClientInputSignalQueue m_inSignalQueue;
	private ClientOutputSignalQueue m_outSignalQueue;
	private ClientThread m_clientThread = null;
	
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	private Logger m_logger;
	private Server m_server;
	
	public static int currentPort = 25502;
	
	public Client(Socket connection, int clientNumber) {
		super(null, null, null, StatusType.Offline, Globals.DEFAULT_FONTSTYLE, false, connection.getInetAddress(), currentPort++);
		m_clientNumber = clientNumber;
		m_connection = connection;
		m_inSignalQueue = new ClientInputSignalQueue();
		m_outSignalQueue = new ClientOutputSignalQueue();
	}
	
	public void initialize(Server server, Logger logger) {
		m_server = server;
		m_connected = true;
		
		try {
			m_logger = logger;
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(server, this, m_in, m_outSignalQueue, m_logger);
			m_outSignalQueue.initialize(this, m_out, m_logger);
			
			if(m_clientThread == null || m_clientThread.isTerminated()) {
				m_clientThread = new ClientThread();
				m_clientThread.initialize(this);
			}
		}
		catch(IOException e) {
			m_connected = false;
			m_logger.addError("Unable to initalize connection to client #" + m_clientNumber);
		}
	}
	
	public Socket getConnection() { return m_connection; }
	
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
		if(m_server != null) { m_server.updateUserStatus(this, StatusType.Offline); }
		
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
	
	public void setPassword(String password) { m_password = password; }
	
	public void readSignal() {
		m_inSignalQueue.readSignal();
	}
	
}
