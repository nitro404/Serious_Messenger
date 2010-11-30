package client;

import java.io.*;
import java.net.*;
import shared.*;
import signal.*;

public class Contact extends UserNetworkData {
	
	private Socket m_connection;
	private boolean m_connected = false;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ContactInputSignalQueue m_inSignalQueue;
	private ContactOutputSignalQueue m_outSignalQueue;
	private ContactThread m_contactThread;
	
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	public Contact() {
		super(null, null, null, StatusType.Offline, null, false, null, 0);
	}
	
	public Contact(String userName, String nickName, String personalMessage, byte status, FontStyle font, boolean blocked, String ipAddress, int port) {
		super(userName, nickName, personalMessage, status, font, blocked, parseIPAddress(ipAddress), port);
	}
	
	public Contact(String userName, String nickName, String personalMessage, byte status, FontStyle font, boolean blocked, InetAddress ipAddress, int port) {
		super(userName, nickName, personalMessage, status, font, blocked, ipAddress, port);
	}
	
	public Contact(UserNetworkData data) {
		super(data.getUserName(), data.getNickName(), data.getPersonalMessage(), data.getStatus(), data.getFont(), data.isBlocked(), data.getIPAddress(), data.getPort());
	}
	
	public void intitialize(Client client, Socket connection) {
		if(client == null || connection == null) { return; }
		
		m_connected = true;
		
		try {
			m_connection = connection;
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			
			if(m_outSignalQueue == null || m_outSignalQueue.isTerminated()) {
				m_outSignalQueue = new ContactOutputSignalQueue();
				m_outSignalQueue.initialize(client, m_out);
			}
			
			if(m_inSignalQueue == null || m_inSignalQueue.isTerminated()) {
				m_inSignalQueue = new ContactInputSignalQueue();
				m_inSignalQueue.initialize(client, this, m_in, m_outSignalQueue);
			}
			
			if(m_contactThread == null || m_contactThread.isTerminated()) {
				m_contactThread = new ContactThread();
				m_contactThread.initialize(this);
			}
		}
		catch(IOException e) {
			m_connected = false;
		}
	}
	
	public Socket getConnection() {
		return m_connection;
	}
	
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
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public void readSignal() {
		m_inSignalQueue.readSignal();
	}
	
}
