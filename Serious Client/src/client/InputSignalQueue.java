package client;

import java.io.DataInputStream;
import java.util.ArrayDeque;
import javax.swing.*;
import shared.*;
import signal.*;

public class InputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private OutputSignalQueue m_outSignalQueue;
	private Client m_client;
	
	public InputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataInputStream in, OutputSignalQueue out) {
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
		else if(s.getSignalType() == SignalType.LoginAuthenticated) {
			s2 = LoginAuthenticatedSignal.readFrom(ByteStream.readFrom(m_in, LoginAuthenticatedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.BroadcastLogin) {
			s2 = BroadcastLoginSignal.readFrom(ByteStream.readFrom(m_in, LoginAuthenticatedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.Message) {
//			ByteStream bs = ByteStream.readFrom(m_in, LoginAuthenticated.LENGTH);
//			bs.readFrom(m_in, )
//			s2 = LoginAuthenticated.readFrom();
			// need to have read more stuff and append or w/e on bytestream for this to work
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
		else if(s.getSignalType() == SignalType.PasswordChanged) {
			s2 = PasswordChangedSignal.readFrom(ByteStream.readFrom(m_in, PasswordChangedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactAdded) {
			s2 = ContactAddedSignal.readFrom(ByteStream.readFrom(m_in, ContactAddedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactDeleted) {
			s2 = ContactDeletedSignal.readFrom(ByteStream.readFrom(m_in, ContactDeletedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactBlocked) {
			s2 = ContactBlockedSignal.readFrom(ByteStream.readFrom(m_in, ContactBlockedSignal.LENGTH));
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
				else if(s.getSignalType() == SignalType.LoginAuthenticated) {
					LoginAuthenticatedSignal s2 = (LoginAuthenticatedSignal) s;
					if(s2.getAuthenticated()) {
						m_client.authenticated();
						JOptionPane.showMessageDialog(null, "Successfully Logged In");
					}
					else {
						m_client.disconnect();
						JOptionPane.showMessageDialog(null, "Login Failed");
					}
				}
				else if(s.getSignalType() == SignalType.BroadcastLogin) {
					BroadcastLoginSignal s2 = (BroadcastLoginSignal) s;
					Contact c = new Contact(s2.getUserName(), s2.getNickName(), s2.getPersonalMessage(), s2.getStatus(), Globals.DEFAULT_FONT, Globals.DEFAULT_TEXT_COLOUR);
					// add to collection
				}
				else if(s.getSignalType() == SignalType.Message) {
					MessageSignal s2 = (MessageSignal) s;
					// conflicts with local message object, change signal to have signal at end (for all signals)
				}
				else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
					AcknowledgeMessageSignal s2 = (AcknowledgeMessageSignal) s;
					if (s2.getReceived()) {
						//resend message
					}
				}
				else if(s.getSignalType() == SignalType.UserTyping) {
					UserTypingSignal s2 = (UserTypingSignal) s;
					if (s2.getTyping()) {
						//update gui + do stuff
					}
				}
				else if(s.getSignalType() == SignalType.ChangeFont) {
					ChangeFontSignal s2 = (ChangeFontSignal) s;
					//create font style
				}
				else if(s.getSignalType() == SignalType.PasswordChanged) {
					PasswordChangedSignal s2 = (PasswordChangedSignal) s;
					if (s2.getPasswordChanged()) {
						//do something
					}
				}
				else if(s.getSignalType() == SignalType.ContactAdded) {
					ContactAddedSignal s2 = (ContactAddedSignal) s;
					if (s2.getAdded()) {
						//update client list locaclly
					}
				}
				else if(s.getSignalType() == SignalType.ContactDeleted) {
					ContactDeletedSignal s2 = (ContactDeletedSignal) s;
					if (s2.getDeleted()){
						//update client gui
					}
				}
				else if(s.getSignalType() == SignalType.ContactBlocked) {
					ContactBlockedSignal s2 = (ContactBlockedSignal) s;
					if (s2.getBlocked()){
						//update client gui
					}
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
