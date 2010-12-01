package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import shared.*;
import signal.*;
import logger.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private ClientDisconnectHandler m_disconnectHandler;
	private static int m_clientCounter = 0;
	
	private UserDBMS m_dbms;
	private Logger m_logger;
    
	public Server() {
		m_clients = new Vector<Client>();
		m_dbms = new UserDBMS();
		m_logger = new Logger();
		m_disconnectHandler = new ClientDisconnectHandler();
	}
	
	public void initialize(int port, ServerWindow serverWindow) {
		if(port < 0 || port > 65355) { port = Globals.DEFAULT_PORT; }
		
		m_logger.initialize(serverWindow);
		
		try {
			m_connection = new ServerSocket(port);
		}
		catch(Exception e) {
			m_logger.addError("Unable to initialize server on port " + port + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Unable to initialize server on port " + port + ": " + e.getMessage(), "Error Initializing Server", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		m_logger.addInfo("Successfully started server on port: " + port);
		
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
			
			// if a connection was established to the client, store the client object
			if(newClient != null) {
				newClient.initialize(this, m_logger);
				m_clients.add(newClient);
				m_logger.addInfo("Established connection to client #" + newClient.getClientNumber() + " at " + newClient.getIPAddressString());
			}
			
			try { sleep(Globals.CONNECTION_LISTEN_INTERVAL); }
			catch (InterruptedException e) { }
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
	
	public boolean createUser(Client client, String userName, String password) {
		boolean userCreated = m_dbms.createUser(userName, password);
		
		if(userCreated && client.getUserName() == null) {
			client.setUserName(userName);
			client.setPassword(password);
		}
		
		return userCreated;
	}
	
	public boolean deleteUser(String userName) {
		boolean userDeleted = m_dbms.deleteUser(userName);
		
		if(userDeleted) {
			for(int i=0;i<m_clients.size();i++) {
				String clientUserName = m_clients.elementAt(i).getUserName();
				if(clientUserName != null && clientUserName.equalsIgnoreCase(userName)) {
					m_clients.elementAt(i).disconnect();
					m_logger.addInfo("Client #" + m_clients.elementAt(i).getClientNumber() + " (" + clientUserName + ") disconnecting: account deleted");
					break;
				}
			}
		}
		
		return userDeleted;
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
	
	public void broadcastUserLogin(BroadcastLoginSignal s, Vector<UserNetworkData> contacts) {
		if(s == null || contacts == null) { return; }
		
		for(int i=0;i<m_clients.size();i++) {
			String clientUserName = m_clients.elementAt(i).getUserName();
			for(int j=0;j<contacts.size();j++) {
				if(clientUserName != null && clientUserName.equalsIgnoreCase(contacts.elementAt(j).getUserName())) {
					s.getData().setBlocked(userHasContactBlocked(contacts.elementAt(j).getUserName(), s.getData().getUserName()));
					m_clients.elementAt(i).sendSignal(s);
					break;
				}
			}
		}
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
	
	public UserNetworkData addUserContact(Client client, String contactUserName) {
		if(client == null || contactUserName == null) { return null; }
		
		if(client.getUserName() != null) {
			UserNetworkData data = m_dbms.addUserContact(client.getUserName(), contactUserName);
			
			if(data != null) {
				for(int i=0;i<m_clients.size();i++) {
					Client c = m_clients.elementAt(i);
					if(c.getUserName() != null && c.getUserName().equalsIgnoreCase(data.getUserName())) {
						data.setStatus(StatusType.Online);
						data.setIPAddress(c.getIPAddress());
						data.setPort(c.getPort());
					}
				}
			}
			
			return data;
		}
		return null;
	}
	
	public boolean deleteUserContact(Client client, String contactUserName) {
		if(client == null || contactUserName == null) { return false; }
		
		if(client.getUserName() != null) {
			return m_dbms.deleteUserContact(client.getUserName(), contactUserName);
		}
		return false;
	}
	
	public boolean userHasContactBlocked(String userName, String contactUserName) {
		if(userName == null || contactUserName == null) { return false; }
		
		return m_dbms.userHasContactBlocked(userName, contactUserName);
	}
	
	public int blockUserContact(Client client, String contactUserName, boolean blocked) {
		if(client == null || contactUserName == null) { return 2; }
		
		if(client.getUserName() != null) {
			return m_dbms.setBlockUserContact(client.getUserName(), contactUserName, blocked);
		}
		return 2;
	}
	
	public Vector<UserNetworkData> getUserContacts(String userName) {
		return m_dbms.getUserContacts(userName, m_clients);
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
