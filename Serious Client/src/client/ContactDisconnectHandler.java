package client;

import java.util.Vector;
import shared.*;

public class ContactDisconnectHandler extends Thread {
	
	private Vector<Contact> m_contacts;
	
	public ContactDisconnectHandler() {
		
	}
	
	public void initialize(Vector<Contact> contacts) {
		m_contacts = contacts;
		if(m_contacts == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		Contact c;
		while(true) {
			for(int i=0;i<m_contacts.size();i++) {
				c = m_contacts.elementAt(i);
				
				c.addTime(Globals.TIMEOUT_INTERVAL);
				
				c.ping();
				
				if(!c.isConnected()) {
					c.disconnect();
					
					m_contacts.remove(i);
					i--;
				}
			}
			
			try { sleep(Globals.TIMEOUT_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
