package client;

import java.io.*;
import java.net.*;
import java.awt.*;
import shared.*;
import signal.*;

public class Contact extends ContactData {
	
	protected InetAddress m_ipAddress;
	protected int m_port;
	
	private Socket m_connection;
	private boolean m_connected = false;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ContactInputSignalQueue m_inSignalQueue;
	private ContactOutputSignalQueue m_outSignalQueue;
	private ContactThread m_contactThread;
	
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	private Client m_client;
	
	public Contact(String userName, String nickName, String personalMessage, byte status, Font font, Color textColour, String ipAddress, int port) {
		this(userName, nickName, personalMessage, status, font, textColour, parseIPAddress(ipAddress), port);
	}
	
	public Contact(String userName, String nickName, String personalMessage, byte status, Font font, Color textColour, InetAddress ipAddress, int port) {
		super(userName, nickName, personalMessage, status, font, textColour);
		setIPAddress(ipAddress);
		setPort(port);
	}
	
	public void intitialize(Client client) {
		m_connected = true;
		m_client = client;
		
		try {
			m_connection = new Socket(m_ipAddress, m_port);
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			
			if(m_outSignalQueue == null || m_outSignalQueue.isTerminated()) {
				m_outSignalQueue = new ContactOutputSignalQueue();
				m_outSignalQueue.initialize(client, m_out);
			}
			
			if(m_inSignalQueue == null || m_inSignalQueue.isTerminated()) {
				m_inSignalQueue = new ContactInputSignalQueue();
				m_inSignalQueue.initialize(client, m_in, m_outSignalQueue);
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
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public int getPort() { return m_port; }
	
	public void setIPAddress(String ipAddress) { setIPAddress(parseIPAddress(ipAddress)); }
	
	public void setIPAddress(InetAddress ipAddress) { if(ipAddress != null) { m_ipAddress = ipAddress; } }
	
	public void setPort(int port) { if(port >= 0 && port <= 65535) { m_port = port; } }
	
	public static InetAddress parseIPAddress(String ipAddress) {
		InetAddress ip = null;
		try { ip = InetAddress.getByName(ipAddress); }
		catch (UnknownHostException e) { }
		return ip;
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
