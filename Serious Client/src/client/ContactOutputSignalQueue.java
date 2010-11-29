package client;

import java.io.*;
import java.util.*;
import shared.*;
import signal.*;

public class ContactOutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	
	public ContactOutputSignalQueue(){
		m_outSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataOutputStream out) {
		m_client = client;
		m_out = out;
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
				else if(s.getSignalType() == SignalType.Message) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.UserTyping) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.ChangeFont) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.ChangeNickname) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.ChangeStatus) {
					s.writeTo(m_out);
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
			
		}
	}
	
}