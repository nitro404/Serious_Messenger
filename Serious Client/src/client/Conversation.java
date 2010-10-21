package client;

import java.util.Vector;

public class Conversation {
	
	private Vector<Message> m_messages;
	private User m_user;
	private Vector<Contact> m_participants;
	
	public Conversation(User user, Contact contact) {
		m_messages = new Vector<Message>();
		m_participants = new Vector<Contact>();
		
		m_user = user;
		m_participants.add(contact);
	}
	
	public int numberOfMessages() { return m_messages.size(); }

	public Message getMessage(int index) {
		if(index < 0 || index >= m_messages.size()) { return null; }
		return m_messages.elementAt(index);
	}
	
	public int numberOfParticipants() { return m_participants.size(); }
	
	public boolean hasParticipant(String userName) {
		if(userName == null) { return false; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				return true;
			}
		}
		return false;
	}
	
	public Contact getParticipant(String userName) {
		if(userName == null) { return null; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				return m_participants.elementAt(i);
			}
		}
		return null;
	}
	
	public Contact getParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return null; }
		return m_participants.elementAt(index);
	}
	
	public boolean addParticipant(Contact c) {
		if(c == null || m_participants.contains(c)) { return false; }
		return m_participants.add(c);
	}
	
	public boolean removeParticipant(String userName) {
		if(userName == null) { return false; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				m_participants.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeParticipant(Contact c) {
		if(c == null || m_participants.contains(c)) { return false; }
		return m_participants.remove(c);
	}
	
	public boolean removeParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return false; }
		m_participants.remove(index);
		return true;
	}
	
	// TODO: Finish me
	public boolean sendMessage(String message) {
		Message m = new Message(m_user, message);
		m_messages.add(m);
		
		return true;
	}
	
	// TODO: Finish me
	public boolean receiveMessage(String userName, int id, String message) {
		if(userName == null || message == null) { return false; }
		return receiveMessage(getParticipant(userName), id, message);
	}
	
	// TODO: Finish me
	public boolean receiveMessage(Contact c, int id, String message) {
		if(c == null || message == null) { return false; }
		
		Message m = new Message(c, id, message);
		m_messages.add(m);
		
		return true;
	}
	
}
