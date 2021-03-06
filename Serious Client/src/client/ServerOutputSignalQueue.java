package client;

import java.io.*;
import java.util.*;
import shared.*;
import signal.*;

public class ServerOutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	private ClientWindow m_clientWindow;
	
	public ServerOutputSignalQueue(){
		m_outSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, ClientWindow clientWindow, DataOutputStream out) {
		m_client = client;
		m_clientWindow = clientWindow;
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
				else if(s.getSignalType() == SignalType.LoginRequest) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.Logout) {
					s.writeTo(m_out);
					m_client.disconnect();
					m_client.setUserName(null);
					m_client.setNickName("");
					m_client.setPersonalMessage("");
					m_client.clearContacts();
					m_clientWindow.resetContactPanels();
					m_clientWindow.update();
				}
				else if(s.getSignalType() == SignalType.BroadcastLogin) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.Message) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.ChangePassword) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.AddContact) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.DeleteContact) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.BlockContact) {
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
				else if(s.getSignalType() == SignalType.CreateUser) {
					s.writeTo(m_out);
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
			
		}
	}
	
}
