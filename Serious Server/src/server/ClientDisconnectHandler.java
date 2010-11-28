package server;

import java.util.Vector;
import shared.*;
import logger.*;

public class ClientDisconnectHandler extends Thread {
	
	private Vector<Client> m_clients;
	
	private Logger m_logger;
	
	public ClientDisconnectHandler() {
		
	}
	
	public void initialize(Vector<Client> clients, Logger logger) {
		m_clients = clients;
		m_logger = logger;
		if(m_clients == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		Client c;
		while(true) {
			for(int i=0;i<m_clients.size();i++) {
				c = m_clients.elementAt(i);
				
				c.addTime(Globals.TIMEOUT_INTERVAL);
				
				c.ping();
				
				if(!c.isConnected()) {
					c.disconnect();
					
					m_clients.remove(i);
					i--;
					
					if(c.timeout()) {
						m_logger.addInfo("Client #" + c.getClientNumber() + " (" + c.getUserName() + ") timed out");
					}
				}
			}
			
			try { sleep(Globals.TIMEOUT_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
