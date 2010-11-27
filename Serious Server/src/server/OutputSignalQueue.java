package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;
import logger.*;

public class OutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	private Logger m_logger;

	public OutputSignalQueue() {
		m_outSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Client client, DataOutputStream out, Logger logger) {
		m_client = client;
		m_out = out;
		m_logger = logger;
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void addSignal(Signal s) {
		if (s == null) { return; }

		m_outSignalQueue.add(s);
	}

	public void run() {
		while(m_client.isConnected()) {
			if(!m_outSignalQueue.isEmpty()) {
				Signal s = m_outSignalQueue.remove();

				if(s.getSignalType() == SignalType.Ping) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.Pong) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.LoginAuthenticated) {
					s.writeTo(m_out);
					LoginAuthenticatedSignal s2 = (LoginAuthenticatedSignal) s;
					if(!s2.getAuthenticated()) {
						m_client.disconnect();
						m_logger.addInfo("Client #" + m_client.getClientNumber() + " disconnected: login rejected");
					}
				} 
				else if(s.getSignalType() == SignalType.PasswordChanged) {
					s.writeTo(m_out);
				} 
				else if(s.getSignalType() == SignalType.ContactAdded) {
					s.writeTo(m_out);
				} 
				else if(s.getSignalType() == SignalType.ContactDeleted) {
					s.writeTo(m_out);
				} 
				else if(s.getSignalType() == SignalType.ContactBlocked) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.UserCreated) {
					s.writeTo(m_out);
					m_client.disconnect();
				}
				else {
					m_logger.addWarning("Unexpected output signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
