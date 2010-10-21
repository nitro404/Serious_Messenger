package client;

import java.awt.*;
import java.util.Calendar;

import shared.Globals;

public class Message {
	
	private int m_id;
	private String m_userName;
	private String m_nickName;
	private String m_text;
	private Font m_font;
	private Color m_textColour;
	private Calendar m_timeStamp;
	
	private static int m_idCounter = 0;
	
	public Message(Contact contact, int id, String text) {
		this(id, contact.getUserName(), contact.getNickName(), text, contact.getFont(), contact.getTextColour());
	}
	
	public Message(User user, String text) {
		this(m_idCounter++, user.getUserName(), user.getNickName(), text, user.getFont(), user.getTextColour());
	}
	
	private Message(int id, String userName, String nickName, String text, Font font, Color textColour) {
		m_id = id;
		m_userName = (userName == null) ? "" : userName;
		m_nickName = (nickName == null) ? "" : nickName;
		m_text = (text == null) ? "" : text;
		m_font = (font == null) ? Globals.DEFAULT_FONT : font;
		m_textColour = (font == null) ? Globals.DEFAULT_TEXT_COLOUR : textColour;
		m_timeStamp = Calendar.getInstance();
	}
	
	public int getID() { return m_id; }
	
	public String getUserName() { return m_userName; }
	
	public String getNickName() { return m_nickName; }
	
	public String getText() { return m_text; }
	
	public Font getFont() { return m_font; }
	
	public Color getTextColour() { return m_textColour; }
	
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
