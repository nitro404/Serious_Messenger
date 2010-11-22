package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayDeque;
import java.util.Vector;

import signal.*;
import shared.*;

public class InputSignalQueue {
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private Server m_server;
	private Client m_client;

	public InputSignalQueue() {
		m_inSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Server server, Client client, DataInputStream in, DataOutputStream out) {
		m_server = server;
		m_client = client;
		m_in = in;
		m_out = out;
	}

	public void addSignal(Signal s) {
		if (s == null) {
			return;
		}

		m_inSignalQueue.add(s);
	}
	
	public void run() {
		while (true) {
			if (!m_inSignalQueue.isEmpty()){
				Signal s = m_inSignalQueue.remove();
				
				if (s.getSignalType() == SignalType.LoginRequest) {
					LoginRequest s2 = (LoginRequest) s;
					//do stuff
				}	
				else if (s.getSignalType() == SignalType.Logout) {
					Logout s2 = (Logout) s;
					//do stuff
				}
				else if (s.getSignalType() == SignalType.ChangePassword) {
					ChangePassword s2 = (ChangePassword) s;
					//do stuff
				}
				else if (s.getSignalType() == SignalType.AddContact) {
					AddContact s2 = (AddContact) s;
					//do stuff
				}
				else if (s.getSignalType() == SignalType.DeleteContact) {
					DeleteContact s2 = (DeleteContact) s;
					//do stuff
				}
				else if (s.getSignalType() == SignalType.BlockContact) {
					BlockContact s2 = (BlockContact) s;
					//do stuff
				}
			}
		}
	}
}

