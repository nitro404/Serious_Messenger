package server;

import java.net.*;

public class Client extends Thread {
	
	private int m_clientNumber;
	private String m_userName;
	private String m_password;
	private boolean m_online;
	
	private Socket m_connection;
	
	public Client(Socket connection, int clientNumber) {
		m_clientNumber = clientNumber;
		m_connection = connection;
	}
	
	public void initialize() {
		start();
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
