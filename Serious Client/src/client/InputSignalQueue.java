package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayDeque;

import javax.swing.JOptionPane;

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
		if(getState() == Thread.State.NEW || getState() == Thread.State.TERMINATED) { start(); }
	}
	
	public void addSignal(Signal s){
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
			s2 = LoginAuthenticated.readFrom(ByteStream.readFrom(m_in, LoginAuthenticated.LENGTH));
		}
		else if(s.getSignalType() == SignalType.BroadcastLogin) {
			s2 = BroadcastLogin.readFrom(ByteStream.readFrom(m_in, LoginAuthenticated.LENGTH));
		}
		else if(s.getSignalType() == SignalType.Message) {
//			ByteStream bs = ByteStream.readFrom(m_in, LoginAuthenticated.LENGTH);
//			bs.readFrom(m_in, )
//			s2 = LoginAuthenticated.readFrom();
			// need to have read more stuff and append or w/e on bytestream for this to work
		}
		else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
			s2 = AcknowledgeMessage.readFrom(ByteStream.readFrom(m_in, AcknowledgeMessage.LENGTH));
		}
		else if(s.getSignalType() == SignalType.UserTyping) {
			s2 = UserTyping.readFrom(ByteStream.readFrom(m_in, UserTyping.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeFont) {
			s2 = ChangeFont.readFrom(ByteStream.readFrom(m_in, ChangeFont.LENGTH));
		}
		else if(s.getSignalType() == SignalType.PasswordChanged) {
			s2 = PasswordChanged.readFrom(ByteStream.readFrom(m_in, PasswordChanged.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactAdded) {
			s2 = ContactAdded.readFrom(ByteStream.readFrom(m_in, ContactAdded.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactDeleted) {
			s2 = ContactDeleted.readFrom(ByteStream.readFrom(m_in, ContactDeleted.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactBlocked) {
			s2 = ContactBlocked.readFrom(ByteStream.readFrom(m_in, ContactBlocked.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeNickname) {
			s2 = ChangeNickname.readFrom(ByteStream.readFrom(m_in, ChangeNickname.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
			s2 = ChangePersonalMessage.readFrom(ByteStream.readFrom(m_in, ChangePersonalMessage.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ChangeStatus) {
			s2 = ChangeStatus.readFrom(ByteStream.readFrom(m_in, ChangeStatus.LENGTH));
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
					LoginAuthenticated s2 = (LoginAuthenticated) s;
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
					BroadcastLogin s2 = (BroadcastLogin) s;
					Contact c = new Contact(s2.getUserName(), s2.getNickName(), s2.getPersonalMessage(), s2.getStatus(), Globals.DEFAULT_FONT, Globals.DEFAULT_TEXT_COLOUR);
					// add to collection
				}
				else if(s.getSignalType() == SignalType.Message) {
					//Message s2 = (Message) s;
					// conflicts with local message object, change signal to have signal at end (for all signals)
				}
				else if(s.getSignalType() == SignalType.AcknowledgeMessage) {
					AcknowledgeMessage s2 = (AcknowledgeMessage) s;
					if (s2.getReceived()) {
						//resend message
					}
				}
				else if(s.getSignalType() == SignalType.UserTyping) {
					UserTyping s2 = (UserTyping) s;
					if (s2.getTyping()) {
						//update gui + do stuff
					}
				}
				else if(s.getSignalType() == SignalType.ChangeFont) {
					ChangeFont s2 = (ChangeFont) s;
					//create font style
				}
				else if(s.getSignalType() == SignalType.PasswordChanged) {
					PasswordChanged s2 = (PasswordChanged) s;
					if (s2.getPasswordChanged()) {
						//do something
					}
				}
				else if(s.getSignalType() == SignalType.ContactAdded) {
					ContactAdded s2 = (ContactAdded) s;
					if (s2.getAdded()) {
						//update client list locaclly
					}
				}
				else if(s.getSignalType() == SignalType.ContactDeleted) {
					ContactDeleted s2 = (ContactDeleted) s;
					if (s2.getDeleted()){
						//update client gui
					}
				}
				else if(s.getSignalType() == SignalType.ContactBlocked) {
					ContactBlocked s2 = (ContactBlocked) s;
					if (s2.getBlocked()){
						//update client gui
					}
				}
				else if(s.getSignalType() == SignalType.ChangeNickname) {
					ChangeNickname s2 = (ChangeNickname) s;
					//update client
				}
				else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
					ChangePersonalMessage s2 = (ChangePersonalMessage) s;
					//update client
				}
				else if(s.getSignalType() == SignalType.ChangeStatus) {
					ChangeStatus s2 = (ChangeStatus) s;
					//update client
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
