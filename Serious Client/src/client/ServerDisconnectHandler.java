package client;

import javax.swing.*;
import shared.*;

public class ServerDisconnectHandler extends Thread {
	
	private Client m_client;
	private MessageBoxSystem m_messageBoxSystem;
	
	public ServerDisconnectHandler() {
		
	}
	
	public void initialize(Client client, MessageBoxSystem messageBoxSystem) {
		m_client = client;
		m_messageBoxSystem = messageBoxSystem;
		if(m_client == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		while(m_client.isConnected()) {
			m_client.addTime(Globals.TIMEOUT_INTERVAL);
			
			m_client.ping();
			
			if(!m_client.isConnected()) {
				m_client.disconnect();
				
				if(m_client.timeout()) {
					m_messageBoxSystem.show(null, "Connection to server timed out.", "Lost Connection", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			try { sleep(Globals.TIMEOUT_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
