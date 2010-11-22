package client;

import java.io.*;
import java.util.*;
import shared.*;

import javax.swing.JOptionPane;

import signal.*;
import shared.*;

public class OutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	
	public OutputSignalQueue(){
		m_outSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataOutputStream out) {
		m_client = client;
		m_out = out;
		start();
	}
	
	public void addSignal(Signal s){
		if (s == null) { return; }
		
		m_outSignalQueue.add(s);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_outSignalQueue.isEmpty()) {
				Signal s = m_outSignalQueue.remove();
					
				if(s.getSignalType() == SignalType.LoginRequest) {
					s.writeTo(m_out); 
				}
				else if(s.getSignalType() == SignalType.Logout) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.BroadcastLogin) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.Message) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.UserTyping) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.ChangeFont) {
					//s.writeTo(m_out);
					//TODO: send to client
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
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
				//	s.writeTo(m_out);
					//TODO: send to client
				}
				else if(s.getSignalType() == SignalType.ChangeStatus) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else {
					//unexpected signal
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
			
		}
	}
	
}
