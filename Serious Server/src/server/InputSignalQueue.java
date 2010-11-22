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
		start();
	}

	public void addSignal(Signal s) {
		if (s == null) { return; }

		m_inSignalQueue.add(s);
	}
	
	public void readSignal() {
		Signal s = Signal.readFrom(ByteStream.readFrom(m_in, Signal.LENGTH));
		Signal s2 = null;
		
		if(s == null) { return; }
		
		if(s.getSignalType() == SignalType.LoginRequest) {
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
			return;
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()) {
				Signal s = m_inSignalQueue.remove();
				
				if(s.getSignalType() == SignalType.LoginRequest) {
					LoginRequest s2 = (LoginRequest) s;
					
					/*
					boolean authenticated = false;
					if(s2.getUserName().equalsIgnoreCase("nitro404") &&
					   s2.getPassword().equalsIgnoreCase("password")) {
						authenticated = true;
					}
					m_outSignalQueue.addSignal(new LoginAuthenticated(authenticated));
					m_logger.addCommand(s2.getUserName(), "Login Request [" + s2.getUserName() + "," + s2.getPassword() + "]: " + ((authenticated) ? "Accepted" : "Rejected"));
					*/
					//do stuff
				}	
				else if(s.getSignalType() == SignalType.Logout) {
					Logout s2 = (Logout) s;
					//do stuff
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
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}

