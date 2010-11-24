package server;

import java.io.*;
import java.util.*;
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
			s2 = LoginRequestSignal.readFrom(ByteStream.readFrom(m_in, LoginRequestSignal.LENGTH)); 
		}	
		else if(s.getSignalType() == SignalType.Logout) {
			s2 = LogoutSignal.readFrom(ByteStream.readFrom(m_in, LogoutSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangePassword) {
			s2 = ChangePasswordSignal.readFrom(ByteStream.readFrom(m_in, ChangePasswordSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.AddContact) {
			s2 = AddContactSignal.readFrom(ByteStream.readFrom(m_in, AddContactSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.DeleteContact) {
			s2 = DeleteContactSignal.readFrom(ByteStream.readFrom(m_in, DeleteContactSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.BlockContact) {
			s2 = BlockContactSignal.readFrom(ByteStream.readFrom(m_in, BlockContactSignal.LENGTH));
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
					LoginRequestSignal s2 = (LoginRequestSignal) s;
					
					boolean authenticated = m_server.authenticateUser(m_client, s2.getUserName(), s2.getPassword());
					sendSignal(new LoginAuthenticatedSignal(authenticated));
					
					m_logger.addCommand(s2.getUserName(), "Login Request: " + ((authenticated) ? "Accepted" : "Rejected"));
				}	
				else if(s.getSignalType() == SignalType.Logout) {
					LogoutSignal s2 = (LogoutSignal) s;
					
					m_client.disconnect();
					
					m_logger.addCommand(s2.getUserName(), "Logged Out");
					
					m_logger.addInfo("Client #" + m_client.getClientNumber() + " (" + s2.getUserName() + ") logged out");
				}
				else if(s.getSignalType() == SignalType.ChangePassword) {
					ChangePasswordSignal s2 = (ChangePasswordSignal) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.AddContact) {
					AddContactSignal s2 = (AddContactSignal) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.DeleteContact) {
					DeleteContactSignal s2 = (DeleteContactSignal) s;
					//do stuff
				}
				else if(s.getSignalType() == SignalType.BlockContact) {
					BlockContactSignal s2 = (BlockContactSignal) s;
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

