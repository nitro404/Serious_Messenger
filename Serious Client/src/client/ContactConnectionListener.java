package client;

import java.io.*;
import java.net.*;
import shared.*;

public class ContactConnectionListener extends Thread {
	
	private Client m_client;
	private ServerSocket m_connection;
	
	public ContactConnectionListener() {
		
	}
	
	public void initialize(Client client) {
		m_client = client;
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
