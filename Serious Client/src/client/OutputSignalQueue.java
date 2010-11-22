package client;

import java.io.*;
import java.util.ArrayDeque;
import signal.*;
import shared.*;

public class OutputSignalQueue {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	
	public OutputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client c, DataInputStream in, DataOutputStream out){
		m_in = in;
		m_out = out;
	}
	
	public void addSignal(Signal s){
		if (s == null) { return; }
		
		m_inSignalQueue.add(s);
	}
	
	public void run(){
		while (true){
			if (!m_inSignalQueue.isEmpty()){
				Signal s = m_inSignalQueue.poll();
					
				if (s.getSignalType() == SignalType.LoginRequest) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.Logout) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.BroadcastLogin) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.Message) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.AcknowledgeMessage) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.UserTyping) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.ChangeFont) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.ChangePassword) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.AddContact) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.DeleteContact) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.BlockContact) {
					s.writeTo(m_out);
				}
				else if (s.getSignalType() == SignalType.ChangeNickname) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.ChangePersonalMessage) {
				//	s.writeTo(m_out);
					//TODO: send to client
				}
				else if (s.getSignalType() == SignalType.ChangeStatus) {
					//s.writeTo(m_out);
					//TODO: send to client
				}
			}
		}
	}
}
