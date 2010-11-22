package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayDeque;
import shared.*;
import signal.*;

public class InputSignalQueue {
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	
	public InputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client c, DataInputStream in, DataOutputStream out){
		m_in = in;
		m_out = out;
	}
	
	public void addSignal(Signal s){
		if (s == null){ return; }
		
		m_inSignalQueue.add(s);
	}
	
	public void run(){
		while (true){
			if (!m_inSignalQueue.isEmpty()){
				Signal s = m_inSignalQueue.remove();
				
				if (s.getSignalType() == SignalType.LoginAuthenticated) {
					LoginAuthenticated s2 = (LoginAuthenticated) s;
					s2.getAuthenticated();
					// update client
				}
				else if (s.getSignalType() == SignalType.BroadcastLogin) {
					BroadcastLogin s2 = (BroadcastLogin) s;
					Contact c = new Contact(s2.getUserName(), s2.getNickName(), s2.getPersonalMessage(), s2.getStatus(), Globals.DEFAULT_FONT, Globals.DEFAULT_TEXT_COLOUR);
					// add to collection
				}
				else if (s.getSignalType() == SignalType.Message) {
					//Message s2 = (Message)s;
					//random error ^
				}
				else if (s.getSignalType() == SignalType.AcknowledgeMessage) {
					AcknowledgeMessage s2 = (AcknowledgeMessage) s;
					if (s2.getReceived()) {
						//resend message
					}
				}
				else if (s.getSignalType() == SignalType.UserTyping) {
					UserTyping s2 = (UserTyping) s;
					if (s2.getTyping()) {
						//update gui + do shit
					}
				}
				else if (s.getSignalType() == SignalType.ChangeFont) {
					ChangeFont s2 = (ChangeFont) s;
					//create font style
				}
				else if (s.getSignalType() == SignalType.PasswordChanged) {
					PasswordChanged s2 = (PasswordChanged) s;
					if (s2.getPasswordChanged()) {
						//do something
					}
				}
				else if (s.getSignalType() == SignalType.ContactAdded) {
					ContactAdded s2 = (ContactAdded) s;
					if (s2.getAdded()) {
						//update client list locaclly
					}
				}
				else if (s.getSignalType() == SignalType.ContactDeleted) {
					ContactDeleted s2 = (ContactDeleted) s;
					if (s2.getDeleted()){
						//update client gui
					}
				}
				else if (s.getSignalType() == SignalType.ContactBlocked) {
					ContactBlocked s2 = (ContactBlocked) s;
					if (s2.getBlocked()){
						//update client gui
					}
				}
				else if (s.getSignalType() == SignalType.ChangeNickname) {
					ChangeNickname s2 = (ChangeNickname) s;
					//update client
				}
				else if (s.getSignalType() == SignalType.ChangePersonalMessage) {
					ChangePersonalMessage s2 = (ChangePersonalMessage) s;
					//update client
				}
				else if (s.getSignalType() == SignalType.ChangeStatus) {
					ChangeStatus s2 = (ChangeStatus) s;
					//update client
				}
				else {
					//unexpected signal
				}
			}
		}
	}
}
