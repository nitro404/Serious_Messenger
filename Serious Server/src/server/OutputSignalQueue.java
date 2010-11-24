package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;
import logger.*;

public class OutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Server m_server;
	private Client m_client;
	private Logger m_logger;

	public OutputSignalQueue() {
		m_outSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Server server, Client client, DataOutputStream out, Logger logger) {
		m_server = server;
		m_client = client;
		m_out = out;
		m_logger = logger;
		if(getState() == Thread.State.NEW || getState() == Thread.State.TERMINATED) { start(); }
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
				else {
					m_logger.addWarning("Unexpected output signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
