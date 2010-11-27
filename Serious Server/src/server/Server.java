package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import shared.*;
import logger.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private DisconnectHandler m_disconnectHandler;
	private static int m_clientCounter = 0;
	
	private UserDBMS m_dbms;
	private Logger m_logger;
    
	public Server() {
		m_clients = new Vector<Client>();
		m_dbms = new UserDBMS();
		m_logger = new Logger();
		m_disconnectHandler = new DisconnectHandler();
	}
	
	public void initialize(int port, ServerWindow serverWindow) {
		if(port < 0 || port > 65355) { port = Globals.DEFAULT_PORT; }
		
		try {
			m_connection = new ServerSocket(port);
		}
		catch(Exception e) {
			m_logger.addError("Unable to initialize server on port " + port + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Unable to initialize server on port " + port + ": " + e.getMessage(), "Error Initializing Server", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		m_logger.initialize(serverWindow);
		m_dbms.initialize(m_logger);
		m_dbms.connect();
		m_disconnectHandler.initialize(m_clients, m_logger);
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public void run() {
		Client newClient;
		while(true) {
			newClient = null;
			m_clientCounter++;
			try {
				newClient = new Client(m_connection.accept(), m_clientCounter);
			}
			catch(IOException e) {
				m_logger.addError("Unable to connect to client #" + m_clientCounter);
			}
			
			// if a connection was establish to the client, store the client object
			if(newClient != null) {
				newClient.initialize(this, m_logger);
				m_clients.add(newClient);
				m_logger.addInfo("Established connection to client #" + newClient.getClientNumber() + " at " + newClient.getIPAddressString());
			}
		}
	}
	
	public int numberOfClients() { return m_clients.size(); }
	
	public Client getClient(int index) {
		if(index < 0 || index >= m_clients.size()) { return null; }
		return m_clients.elementAt(index);
	}
	
	public UserDBMS getDBMS() {
		return m_dbms;
	}
	
	public void databaseConnect() {
		m_dbms.connect();
	}
	
	public void databaseDisconnect() {
		m_dbms.disconnect();
	}
	
	public boolean databaseConnnected() {
		return m_dbms.isConnected();
	}
	
	public void createTables() {
		m_dbms.createTables();
	}
	
	public void dropTables() {
		m_dbms.dropTables();
	}
	
	public void resetTables() {
		m_dbms.resetTables();
	}
	
	public boolean userLogin(Client client, String userName, String password) {
		
		// make sure the user isn't already logged in
		boolean alreadyLoggedIn = false;
		for(int i=0;i<m_clients.size();i++) {
			if(userName.equalsIgnoreCase(m_clients.elementAt(i).getUserName())) {
				alreadyLoggedIn = true;
			}
		}
		
		// if the user is not already logged in, authenticate the user and update the database/client info
		boolean authenticated = false;
		if(!alreadyLoggedIn) {
			authenticated = m_dbms.userLogin(userName, password);
			
			if(authenticated) {
				client.setUserName(userName);
				client.setPassword(password);
			}
		}
		
		return authenticated;
	}
	
	public boolean changeUserPassword(Client client, String userName, String oldPassword, String newPassword) {
		if(client.getUserName() != null &&
		   client.getUserName().equalsIgnoreCase(userName) &&
		   m_dbms.changeUserPassword(userName, oldPassword, newPassword)) {
			client.setPassword(newPassword);
			return true;
		}
		return false;
	}
	
	public boolean addUserContact(Client client, String contactUserName) {
		if(client == null || contactUserName == null) { return false; }
		
		if(client.getUserName() != null) {
			return m_dbms.addUserContact(client.getUserName(), contactUserName);
		}
		return false;
	}
	
	public int executeUpdate(String query) {
		return m_dbms.executeUpdate(query);
	}
	
	public SQLResult executeQuery(String query) {
		return m_dbms.executeQuery(query);
	}
	
	public Object[] getLastSystemLogEntryAsArray() {
		return m_logger.getLastSystemLogEntryAsArray();
	}
	
	public Object[] getLastCommandLogEntryAsArray() {
		return m_logger.getLastCommandLogEntryAsArray();
	}
	
}
