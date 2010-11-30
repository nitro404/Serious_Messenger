package client;

import java.util.Vector;
import java.awt.*;
import shared.*;

public class User extends UserData {
	 
	protected String m_password;
	
	protected Vector<Contact> m_contacts;
	
	private ContactDisconnectHandler m_disconnectHandler;
	
	public User() {
		super(null, null, null, StatusType.Offline, null, null);
		m_password = null;
		m_contacts = new Vector<Contact>();
		m_disconnectHandler = new ContactDisconnectHandler(); 
	}
	
	public User(String userName, String password, String nickName, String personalMessage, byte status, Font font, Color textColour) {
		super(userName, nickName, personalMessage, status, font, textColour);
		m_password = password;
		m_contacts = new Vector<Contact>();
		m_disconnectHandler = new ContactDisconnectHandler();
	}
	
	public void initialize() {
		m_disconnectHandler.initialize(m_contacts);
	}
	
	public String getPassword() { return m_password; }
	
	public void setPassword(String password) { m_password = password; } 
	
	public int getContactIndex(String userName) {
		if(userName == null || userName.length() == 0) { return -1; }
		for(int i=0;i<m_contacts.size();i++) {
			if(userName.equalsIgnoreCase(m_contacts.elementAt(i).getUserName())) {
				return i;
			}
		}
		return -1;
	}
	
	public int getContactIndex(Contact c) {
		if(c == null) { return -1; }
		for(int i=0;i<m_contacts.size();i++) {
			if(c.equals(m_contacts.elementAt(i))) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean hasContact(String userName) {
		if(userName == null || userName.length() == 0) { return false; }
		for(int i=0;i<m_contacts.size();i++) {
			if(userName.equalsIgnoreCase(m_contacts.elementAt(i).getUserName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasContact(Contact c) {
		if(c == null) { return false; }
		return m_contacts.contains(c);
	}
	
	public Contact getContact(String userName) {
		if(userName == null || userName.length() == 0) { return null; }
		for(int i=0;i<m_contacts.size();i++) {
			if(userName.equalsIgnoreCase(m_contacts.elementAt(i).getUserName())) {
				return m_contacts.elementAt(i);
			}
		}
		return null;
	}
	
	public Contact getContact(int index) {
		if(index < 0 || index >= m_contacts.size()) { return null; }
		return m_contacts.elementAt(index);
	}
	
	public boolean addContact(Contact c) {
		if(c == null || m_contacts.contains(c)) { return false; }
		m_contacts.add(c);
		return true;
	}
	
	public boolean removeContact(Contact c) {
		if(c == null) { return false; }
		return m_contacts.remove(c);
	}
	
	public boolean removeContact(int index) {
		if(index < 0 || index >= m_contacts.size()) { return false; }
		m_contacts.remove(index);
		return true;
	}
	
	public boolean removeContact(String userName) {
		if(userName == null || userName.length() == 0) { return false; }
		for(int i=0;i<m_contacts.size();i++) {
			if(userName.equalsIgnoreCase(m_contacts.elementAt(i).getUserName())) {
				m_contacts.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public String getContactNickName(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? null : c.getNickName();
	}
	
	public String getContactNickName(int index) {
		return (index < 0 || index >= m_contacts.size()) ? null : m_contacts.elementAt(index).getNickName();
	}
	
	public String getContactPersonalMessage(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? null : c.getPersonalMessage();
	}
	
	public String getContactPersonalMessage(int index) {
		return (index < 0 || index >= m_contacts.size()) ? null : m_contacts.elementAt(index).getPersonalMessage();
	}
	
	public int getContactStatus(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? StatusType.Offline : c.getStatus();
	}
	
	public int getContactStatus(int index) {
		return (index < 0 || index >= m_contacts.size()) ? StatusType.Offline : m_contacts.elementAt(index).getStatus();
	}
	
	public FontStyle getContactFont(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? Globals.DEFAULT_FONTSTYLE : c.getFont();
	}
	
	public FontStyle getContactFont(int index) {
		return (index < 0 || index >= m_contacts.size()) ? Globals.DEFAULT_FONTSTYLE : m_contacts.elementAt(index).getFont();
	}
	
	public Color getContactTextColour(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? Globals.DEFAULT_TEXT_COLOUR : c.getFont().getColour();
	}
	
	public Color getContactTextColour(int index) {
		return (index < 0 || index >= m_contacts.size()) ? Globals.DEFAULT_TEXT_COLOUR : m_contacts.elementAt(index).getFont().getColour();
	}
	
	public void setContactNickName(String userName, String nickName) {
		Contact c = getContact(userName);
		if(c != null) { c.setNickName(nickName); }
	}
	
	public void setContactNickName(int index, String nickName) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setNickName(nickName);
		}
	}
	
	public void setContactPersonalMessage(String userName, String personalMessage) {
		Contact c = getContact(userName);
		if(c != null) { c.setPersonalMessage(personalMessage); }
	}
	
	public void setContactPersonalMessage(int index, String personalMessage) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setPersonalMessage(personalMessage);
		}
	}
	
	public void setContactStatus(String userName, byte status) {
		Contact c = getContact(userName);
		if(c != null) { c.setStatus(status); }
	}
	
	public void setContactStatus(int index, byte status) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setStatus(status);
		}
	}
	
	public void setContactFont(String userName, FontStyle font) {
		Contact c = getContact(userName);
		if(c != null) { c.setFont(font); }
	}
	
	public void setContactFont(int index, FontStyle font) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setFont(font);
		}
	}
	
	public void setContactTextColour(String userName, Color textColour) {
		Contact c = getContact(userName);
		if(c != null) { c.getFont().setColour(textColour); }
	}
	
	public void setContactTextColour(int index, Color textColour) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).getFont().setColour(textColour);
		}
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User)) { return false; }
		return ((User)o).m_userName.equalsIgnoreCase(m_userName);
	}
	
	public String toString() {
		return m_userName;
	}
	
}
