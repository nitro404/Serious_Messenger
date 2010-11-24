package server;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import signal.*;
import shared.*;
import logger.*;

public class InputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private OutputSignalQueue m_outSignalQueue;
	private Server m_server;
	private Client m_client;
	private Logger m_logger;

	public InputSignalQueue() {
		m_inSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Server server, Client client, DataInputStream in, OutputSignalQueue out, Logger logger) {
		m_server = server;
		m_client = client;
		m_in = in;
		m_outSignalQueue = out;
		m_logger = logger;
		if(getState() == Thread.State.NEW || getState() == Thread.State.TERMINATED) { start(); }
	}

	public void addSignal(Signal s) {
		if(s == null) { return; }

		m_inSignalQueue.add(s);
	}
	
	private void sendSignal(Signal s) {
		if(s == null) { return; }
		
		m_outSignalQueue.addSignal(s);
	}
	
	public void readSignal() {
		if(!m_client.isConnected()) { return; }
		
		Signal s = Signal.readFrom(ByteStream.readFrom(m_in, Signal.LENGTH));
		Signal s2 = null;
		
		if(s == null) { return; }
		
		
		if(s.getSignalType() == SignalType.Ping) {
			s2 = s;
		}
		else if(s.getSignalType() == SignalType.Pong) {
			s2 = s;
		}
		else if(s.getSignalType() == SignalType.LoginRequest) {
			s2 = LoginRequest.readFrom(ByteStream.readFrom(m_in, LoginRequest.LENGTH)); 
		}	
		else if(s.getSignalType() == SignalType.Logout) {
			s2 = Logout.readFrom(ByteStream.readFrom(m_in, Logout.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangePassword) {
			s2 = ChangePassword.readFrom(ByteStream.readFrom(m_in, ChangePassword.LENGTH));
		}
		else if(s.getSignalType() == SignalType.AddContact) {
			s2 = AddContact.readFrom(ByteStream.readFrom(m_in, AddContact.LENGTH));
		}
		else if(s.getSignalType() == SignalType.DeleteContact) {
			s2 = DeleteContact.readFrom(ByteStream.readFrom(m_in, DeleteContact.LENGTH));
		}
		else if(s.getSignalType() == SignalType.BlockContact) {
			s2 = BlockContact.readFrom(ByteStream.readFrom(m_in, BlockContact.LENGTH));
		}
		else {
// TODO: FIX THIS
			m_logger.addWarning("Unexpected input signal of type: " + s.getSignalType());
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()) {
				Signal s = m_inSignalQueue.remove();
				
				if(s.getSignalType() == SignalType.Ping) {
					sendSignal(new Signal(SignalType.Pong));
				}
				else if(s.getSignalType() == SignalType.Pong) {
					m_client.pong();
				}
				else if(s.getSignalType() == SignalType.LoginRequest) {
					LoginRequest s2 = (LoginRequest) s;
					
					boolean authenticated = m_server.authenticateUser(m_client, s2.getUserName(), s2.getPassword());
					sendSignal(new LoginAuthenticated(authenticated));
					
					m_logger.addCommand(s2.getUserName(), "Login Request: " + ((authenticated) ? "Accepted" : "Rejected"));
				}	
				else if(s.getSignalType() == SignalType.Logout) {
					Logout s2 = (Logout) s;
					
					m_client.terminate();
					
					m_logger.addCommand(s2.getUserName(), "Logged Out");
				}
				else if(s.getSignalType() == SignalType.ChangePassword) {
					ChangePassword s2 = (ChangePassword) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.AddContact) {
					AddContact s2 = (AddContact) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.DeleteContact) {
					DeleteContact s2 = (DeleteContact) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.BlockContact) {
					BlockContact s2 = (BlockContact) s;
					//do stuff
				}
				else {
					m_logger.addWarning("Unexpected input signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}

