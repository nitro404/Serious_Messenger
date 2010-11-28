package client;

import shared.*;

public class ContactThread extends Thread {
	
	private Contact m_contact;
	
	public ContactThread() {
		
	}
	
	public void initialize(Contact contact) {
		m_contact = contact;
		if(m_contact == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		while(m_contact.isConnected()) {
			m_contact.readSignal();
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
