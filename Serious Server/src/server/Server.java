package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;
import shared.*;
import logger.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private static int m_clientCounter = 0;
	
	private UserDBMS m_dbms;
	private Logger m_logger;
    
	public Server() {
		m_clients = new Vector<Client>();
		m_dbms = new UserDBMS();
		m_logger = new Logger();
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
	}
	
	public void run() {
		Client newClient;
		while(true) {
			newClient = null;
			try {
				newClient = new Client(m_connection.accept(), m_clientCounter++);
			}
			catch(IOException e) {
				m_logger.addError("Unable to connect to client #" + m_clientCounter + ".");
			}
			
			if(newClient != null) {
				newClient.initialize(this, m_logger);
				m_clients.add(newClient);
				m_logger.addInfo("Established connection to client #" + m_clientCounter + ".");
			}
		}
	}
	
	public int numberOfClients() { return m_clients.size(); }
	
	public Client getClient(int index) {
		if(index < 0 || index >= m_clients.size()) { return null; }
		return m_clients.elementAt(index);
	}
	
	public Object[] getLastSystemLogEntryAsArray() {
		return m_logger.getLastSystemLogEntryAsArray();
	}
	
	public Object[] getLastCommandLogEntryAsArray() {
		return m_logger.getLastCommandLogEntryAsArray();
	}
	
	public void databaseConnect() {
		m_dbms.connect();
	}
	
	public void databaseDisconnect() {
		m_dbms.disconnect();
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
	
}
