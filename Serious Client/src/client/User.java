package client;

import java.util.Vector;
import java.awt.*;
import shared.*;

public class User {
	
	protected String m_userName;
	protected String m_password;
	protected String m_nickName;
	protected String m_personalMessage;
	protected int m_status;
	protected Font m_font;
	protected Color m_textColour;
	
	protected Vector<Contact> m_contacts;
	
	public User(String userName, String password, String nickName, String personalMessage, int status, Font font, Color textColour) {
		m_userName = (userName == null) ? "" : userName;
		m_password = (password == null) ? "" : password;
		m_nickName = (nickName == null) ? "" : nickName;
		m_personalMessage = (personalMessage == null) ? "" : personalMessage;
		m_status = StatusType.isValid(status) ? status : StatusType.Offline;
		m_font = (font == null) ? Globals.DEFAULT_FONT : font;
		m_textColour = (textColour == null) ? Globals.DEFAULT_TEXT_COLOUR : textColour;
		m_contacts = new Vector<Contact>();
	}
	
	public String getUserName() { return m_userName; }
	
	public String getPassword() { return m_password; }
	
	public String getNickName() { return m_nickName; }
	
	public String getPersonalMessage() { return m_personalMessage; }
	
	public int getStatus() { return m_status; }
	
	public Font getFont() { return m_font; }
	
	public Color getTextColour() { return m_textColour; }
	
	public void setPassword(String password) { if(password != null) { m_password = password; } } 
	
	public void setNickName(String nickName) { if(nickName != null) { m_nickName = nickName; } }
	
	public void setPersonalMessage(String personalMessage) { if(personalMessage != null) { m_personalMessage = personalMessage; } }
	
	public void setStatus(int status) { if(StatusType.isValid(status)) { m_status = status; } }
	
	public void setFont(Font font) { if(font != null) { m_font = font; } }
	
	public void setTextColour(Color textColour) { if(textColour != null) { m_textColour = textColour; } }
	
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
	
	public Font getContactFont(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? Globals.DEFAULT_FONT : c.getFont();
	}
	
	public Font getContactFont(int index) {
		return (index < 0 || index >= m_contacts.size()) ? Globals.DEFAULT_FONT : m_contacts.elementAt(index).getFont();
	}
	
	public Color getContactTextColour(String userName) {
		Contact c = getContact(userName);
		return (c == null) ? Globals.DEFAULT_TEXT_COLOUR : c.getTextColour();
	}
	
	public Color getContactTextColour(int index) {
		return (index < 0 || index >= m_contacts.size()) ? Globals.DEFAULT_TEXT_COLOUR : m_contacts.elementAt(index).getTextColour();
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
	
	public void setContactStatus(String userName, int status) {
		Contact c = getContact(userName);
		if(c != null) { c.setStatus(status); }
	}
	
	public void setContactStatus(int index, int status) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setStatus(status);
		}
	}
	
	public void setContactFont(String userName, Font font) {
		Contact c = getContact(userName);
		if(c != null) { c.setFont(font); }
	}
	
	public void setContactFont(int index, Font font) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setFont(font);
		}
	}
	
	public void setContactTextColour(String userName, Color textColour) {
		Contact c = getContact(userName);
		if(c != null) { c.setTextColour(textColour); }
	}
	
	public void setContactTextColour(int index, Color textColour) {
		if(index < 0 || index >= m_contacts.size()) {
			m_contacts.elementAt(index).setTextColour(textColour);
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
