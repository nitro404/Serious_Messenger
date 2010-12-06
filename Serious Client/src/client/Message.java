package client;

import java.util.Calendar;
import shared.*;

import shared.Globals;

public class Message {
	
	private long m_id;
	private String m_userName;
	private String m_nickName;
	private String m_text;
	private FontStyle m_font;
	private Calendar m_timeStamp;
	
	private static long m_idCounter = 0;
	
	public Message(String text, long messageID, UserNetworkData contact) {
		this(text, messageID, contact.getUserName(), contact.getNickName(), contact.getFont());
	}
	
	public Message(String text, User user) {
		this(text, m_idCounter++, user.getUserName(), user.getNickName(), user.getFont());
	}
	
	private Message(String text, long id, String userName, String nickName, FontStyle font) {
		m_id = id;
		m_userName = (userName == null) ? "" : userName;
		m_nickName = (nickName == null) ? "" : nickName;
		m_text = (text == null) ? "" : text;
		m_font = (font == null) ? Globals.DEFAULT_FONTSTYLE : font;
		m_timeStamp = Calendar.getInstance();
	}
	
	public static long nextID() {
		return m_idCounter++;
	}
	
	public long getID() { return m_id; }
	
	public String getUserName() { return m_userName; }
	
	public String getNickName() { return m_nickName; }
	
	public String getText() { return m_text; }
	
	public FontStyle getFont() { return m_font; }
	
	public Calendar getTimeStamp() { return m_timeStamp; }
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Message)) { return false; }
		Message m = (Message) o;
		return m.m_userName.equalsIgnoreCase(m_userName) &&
			   m.m_text.equals(m_text) &&
			   m.m_timeStamp.equals(m_timeStamp);
	}
	
	public String toString() {
		return m_userName + ": " + m_text;
	}
	
}
