package client;

import java.io.*;
import java.util.*;
import javax.swing.*;
import shared.*;
import signal.*;

public class ServerInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ServerOutputSignalQueue m_outSignalQueue;
	private Client m_client;
	private MessageBoxSystem m_messageBoxSystem;
	private ClientWindow m_clientWindow;
	
	public ServerInputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, ClientWindow clientWindow, DataInputStream in, ServerOutputSignalQueue out, MessageBoxSystem messageBoxSystem) {
		m_client = client;
		m_clientWindow = clientWindow;
		m_in = in;
		m_outSignalQueue = out;
		m_messageBoxSystem = messageBoxSystem;
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
			s2 = BroadcastLoginSignal.readFrom(ByteStream.readFrom(m_in, BroadcastLoginSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.Message) {
			s2 = MessageSignal.readFrom(ByteStream.readFrom(m_in, MessageSignal.LENGTH), m_in);
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
		else if(s.getSignalType() == SignalType.UserCreated) {
			s2 = UserCreatedSignal.readFrom(ByteStream.readFrom(m_in, UserCreatedSignal.LENGTH));
		}
		else if(s.getSignalType() == SignalType.ContactList) {
			s2 = ContactListSignal.readFrom(ByteStream.readFrom(m_in, ContactListSignal.LENGTH), m_in);
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
						m_client.setNickName(s2.getData().getNickName());
						m_client.setPersonalMessage(s2.getData().getPersonalMessage());
						m_client.authenticated();
						m_messageBoxSystem.show(null, "Successfully logged in!", "Logged In", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to log in.", "Login Failed", JOptionPane.WARNING_MESSAGE);
						m_client.disconnect();
					}
				}
				else if(s.getSignalType() == SignalType.BroadcastLogin) {
					BroadcastLoginSignal s2 = (BroadcastLoginSignal) s;
					m_client.updateContact(s2.getData());
					m_clientWindow.resetContactPanels();
				}
				else if(s.getSignalType() == SignalType.Message) {
					MessageSignal s2 = (MessageSignal) s;
					if(s2.getUserName().equalsIgnoreCase(m_client.getUserName())) {
						m_client.receiveMessage(s2.getMessage(), s2.getMessageID(), s2.getContactUserName());
					}
				}
				else if(s.getSignalType() == SignalType.PasswordChanged) {
					PasswordChangedSignal s2 = (PasswordChangedSignal) s;
					if(s2.getPasswordChanged()) {
						m_messageBoxSystem.show(null, "Password changed successfully!", "Password Change Succeeded", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to change password.", "Password Change Failed", JOptionPane.WARNING_MESSAGE);
					}
				}
				else if(s.getSignalType() == SignalType.ContactAdded) {
					ContactAddedSignal s2 = (ContactAddedSignal) s;
					if(s2.getAdded()) {
						m_client.addContact(new UserNetworkData(s2.getData()));
						m_messageBoxSystem.show(null, "Contact " + s2.getData().getUserName() + " added successfully!", "Contact Added", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to add contact " + s2.getData().getUserName(), "Unable to Add Contact", JOptionPane.WARNING_MESSAGE);
					}
					m_clientWindow.resetContactPanels();
				}
				else if(s.getSignalType() == SignalType.ContactDeleted) {
					ContactDeletedSignal s2 = (ContactDeletedSignal) s;
					if(s2.getDeleted()) {
						m_client.removeContact(s2.getUserName());
						m_messageBoxSystem.show(null, "Contact " + s2.getUserName() + " deleted successfully!", "Contact Deleted", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to delete contact " + s2.getUserName(), "Unable to Delete Contact", JOptionPane.WARNING_MESSAGE);
					}
					m_clientWindow.resetContactPanels();
				}
				else if(s.getSignalType() == SignalType.ContactBlocked) {
					ContactBlockedSignal s2 = (ContactBlockedSignal) s;
					
					if(s2.getSucceeded()) {
						m_messageBoxSystem.show(null, "Contact " + s2.getUserName() + " is now " + (s2.getBlocked() ? "" : "un") + "blocked.", "Contact " + (s2.getBlocked() ? "Blocked" : "Unblocked"), JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to block/unblock contact " + s2.getUserName(), "Unable to Block/Unblock", JOptionPane.WARNING_MESSAGE);
					}
				}
				else if(s.getSignalType() == SignalType.ChangeNickname) {
					ChangeNicknameSignal s2 = (ChangeNicknameSignal) s;
					m_client.updateUserNickName(s2.getUserName(), s2.getNickName());
					m_clientWindow.update();
				}
				else if(s.getSignalType() == SignalType.ChangePersonalMessage) {
					ChangePersonalMessageSignal s2 = (ChangePersonalMessageSignal) s;
					m_client.updateUserPersonalMessage(s2.getUserName(), s2.getPersonalMessage());
					m_clientWindow.update();
				}
				else if(s.getSignalType() == SignalType.ChangeStatus) {
					ChangeStatusSignal s2 = (ChangeStatusSignal) s;
					m_client.updateUserStatus(s2.getUserName(), s2.getStatus());
					m_clientWindow.update();
				}
				else if(s.getSignalType() == SignalType.UserCreated) {
					UserCreatedSignal s2 = (UserCreatedSignal) s;
					
					if(s2.getCreated()) {
						m_messageBoxSystem.show(null, "Successfully created user account!\n\nYou can now sign in with your username and password.", "Created Account", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						m_messageBoxSystem.show(null, "Unable to create user account.", "Create User Account Failed", JOptionPane.WARNING_MESSAGE);
					}
					
					m_client.disconnect();
				}
				else if(s.getSignalType() == SignalType.ContactList) {
					ContactListSignal s2 = (ContactListSignal) s;
					m_client.updateContacts(s2.getContacts());
					m_clientWindow.resetContactPanels();
				}
			}
			
			try { sleep(Globals.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
