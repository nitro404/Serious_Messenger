package server;

import java.io.*;
import java.net.*;

import signal.Signal;
import logger.*;

public class Client extends Thread {
	
	private int m_clientNumber;
	private String m_userName;
	private String m_password;
	private boolean m_online;
	
	private Socket m_connection;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private InputSignalQueue m_inSignalQueue;
	private OutputSignalQueue m_outSignalQueue;
	
	private Logger m_logger;
	
	public Client(Socket connection, int clientNumber) {
		m_clientNumber = clientNumber;
		m_connection = connection;
		m_inSignalQueue = new InputSignalQueue();
		m_outSignalQueue = new OutputSignalQueue();
	}
	
	public void initialize(Server server, Logger logger) {
		try {
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(server, this, m_in, m_out);
			m_outSignalQueue.initialize(server, this, m_in, m_out);
			start();
		}
		catch(IOException e) {
			m_logger.addError("Unable to initalize connection to client #" + m_clientNumber + ".");
		}
	}
	
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
	
	public boolean isOnline() { return m_online; }
	
	public void run() {
		
	}
	
}
