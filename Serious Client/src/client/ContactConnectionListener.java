package client;

import java.io.*;
import java.net.*;
import javax.swing.*;
import shared.*;

public class ContactConnectionListener extends Thread {
	
	private Client m_client;
	private ServerSocket m_connection;
	
	public ContactConnectionListener() {
		
	}
	
	public void initialize(Client client) {
		m_client = client;
		if(client == null) { return; }
		try {
			m_connection = new ServerSocket(m_client.getPort());
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to initialize contact listener on port " + m_client.getPort() + ": " + e.getMessage(), "Error Initializing Server", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public void run() {
		Contact contact;
		while(true) {
			contact = null;
			try {
				contact = new Contact();
				contact.intitialize(m_client, m_connection.accept());
				m_client.addContact(contact);
			}
			catch(IOException e) { }
			
			try { sleep(Globals.CONNECTION_LISTEN_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
