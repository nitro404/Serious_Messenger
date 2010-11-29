package client;

import java.io.DataInputStream;
import java.util.ArrayDeque;
import shared.*;
import signal.*;

public class ContactInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ContactOutputSignalQueue m_outSignalQueue;
	private Client m_client;
	
	public ContactInputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataInputStream in, ContactOutputSignalQueue out) {
		m_client = client;
		m_in = in;
		m_outSignalQueue = out;
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void addSignal(Signal s) {
		if (s == null){ return; }
		
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
		else if(s.getSignalType() == SignalType.Message) {
			s2 = MessageSignal.readFrom(ByteStream.readFrom(m_in, MessageSignal.LENGTH), m_in);
		}
		else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
			s2 = AcknowledgeMessageSignal.readFrom(ByteStream.readFrom(m_in, AcknowledgeMessageSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.UserTyping) {
			s2 = UserTypingSignal.readFrom(ByteStream.readFrom(m_in, UserTypingSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeFont) {
			s2 = ChangeFontSignal.readFrom(ByteStream.readFrom(m_in, ChangeFontSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeNickname) {
			s2 = ChangeNicknameSignal.readFrom(ByteStream.readFrom(m_in, ChangeNicknameSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
			s2 = ChangePersonalMessageSignal.readFrom(ByteStream.readFrom(m_in, ChangePersonalMessageSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeStatus) {
			s2 = ChangeStatusSignal.readFrom(ByteStream.readFrom(m_in, ChangeStatusSignal.LENGTH));
		}
		else {
			return;
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()){
				Signal s = m_inSignalQueue.remove();
				
				if(s == null) { continue; }
				
				if(s.getSignalType() == SignalType.Ping) {
					sendSignal(new Signal(SignalType.Pong));
				}
				else if(s.getSignalType() == SignalType.Pong) {
					m_client.pong();
				}
				else if(s.getSignalType() == SignalType.Message) {
					MessageSignal s2 = (MessageSignal) s;
					// display message
				}
				else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
					AcknowledgeMessageSignal s2 = (AcknowledgeMessageSignal) s;
					if(s2.getReceived()) {
						//re-send message
					}
				}
				else if(s.getSignalType() == SignalType.UserTyping) {
					UserTypingSignal s2 = (UserTypingSignal) s;
					if(s2.getTyping()) {
						//update GUI + do stuff
					}
				}
				else if(s.getSignalType() == SignalType.ChangeFont) {
					ChangeFontSignal s2 = (ChangeFontSignal) s;
					//create font style
				}
				else if(s.getSignalType() == SignalType.ChangeNickname) {
					ChangeNicknameSignal s2 = (ChangeNicknameSignal) s;
					//update client
				}
				else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
					ChangePersonalMessageSignal s2 = (ChangePersonalMessageSignal) s;
					//update client
				}
				else if(s.getSignalType() == SignalType.ChangeStatus) {
					ChangeStatusSignal s2 = (ChangeStatusSignal) s;
					//update client
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
