package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;

public class OutputSignalQueue {
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private Server m_server;
	private Client m_client;

	public OutputSignalQueue() {
		m_outSignalQueue = new ArrayDeque<Signal>();
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

		m_outSignalQueue.add(s);
	}

	public void run() {
		while (true) {
			if (!m_outSignalQueue.isEmpty()) {
				Signal s = m_outSignalQueue.remove();

				if (s.getSignalType() == SignalType.LoginAuthenticated) {
					// s.writeTo(clientOut);
				} 
				else if (s.getSignalType() == SignalType.PasswordChanged) {
					// s.writeTo(clientOut);
				} 
				else if (s.getSignalType() == SignalType.ContactAdded) {
					// s.writeTo(clientOut);
				} 
				else if (s.getSignalType() == SignalType.ContactDeleted) {
					// s.writeTo(clientOut);
				} 
				else if (s.getSignalType() == SignalType.ContactBlocked) {
					// s.writeTo(clientOut);
				} 
				else {
					// unexpected signal
				}
			}
		}
	}
}
