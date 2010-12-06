package client;

import java.util.Vector;
import shared.*;

public class Conversation {
	
	private Vector<Message> m_messages;
	private User m_user;
	private Vector<UserNetworkData> m_participants;
	
	private Client m_client;
	private ConversationWindow m_conversationWindow;
	
	public Conversation(User user, UserNetworkData contact, Client client, ConversationWindow conversationWindow) {
		m_client = client;
		m_conversationWindow = conversationWindow;
		m_messages = new Vector<Message>();
		m_participants = new Vector<UserNetworkData>();
		
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
	
	public UserNetworkData getParticipant(String userName) {
		if(userName == null) { return null; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				return m_participants.elementAt(i);
			}
		}
		return null;
	}
	
	public UserNetworkData getParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return null; }
		return m_participants.elementAt(index);
	}
	
	public boolean addParticipant(UserNetworkData c) {
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
	
	public boolean removeParticipant(UserNetworkData c) {
		if(c == null || m_participants.contains(c)) { return false; }
		return m_participants.remove(c);
	}
	
	public boolean removeParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return false; }
		m_participants.remove(index);
		return true;
	}
	
	public boolean sendMessage(String message) {
		Message m = new Message(message, m_user);
		m_messages.add(m);
		
		for(int i=0;i<m_participants.size();i++) {
			m_client.sendMessage(message, m.getID(), m_client.getUserName(), m_participants.elementAt(i).getUserName());
		}
		
		return true;
	}
	
	public boolean receiveMessage(String message, long messageID, String contactUserName) {
		if(contactUserName == null || message == null) { return false; }
		
		return receiveMessage(message, messageID, getParticipant(contactUserName));
	}
	
	public boolean receiveMessage(String message, long messageID, UserNetworkData contact) {
		if(contact == null || message == null) { return false; }
		
		Message m = new Message(message, messageID, contact);
		m_messages.add(m);
		
		m_conversationWindow.addMessage(m.getText(), m.getUserName());
		
		return true;
	}
    
}
