package client;

import java.awt.*;
import shared.*;

public class ContactData {
	
	protected String m_userName;
	protected String m_nickName;
	protected String m_personalMessage;
	protected int m_status;
	protected Font m_font;
	protected Color m_textColour;
	
	public ContactData(String userName, String nickName, String personalMessage, int status, Font font, Color textColour) {
		m_userName = userName;
		m_nickName = (nickName == null) ? "" : nickName;
		m_personalMessage = (personalMessage == null) ? "" : personalMessage;
		m_status = StatusType.isValid(status) ? status : StatusType.Offline;
		m_font = (font == null) ? Globals.DEFAULT_FONT : font;
		m_textColour = (textColour == null) ? Globals.DEFAULT_TEXT_COLOUR : textColour;
	}
	
	public String getUserName() { return m_userName; }
	
	public String getNickName() { return m_nickName; }
	
	public String getPersonalMessage() { return m_personalMessage; }
	
	public int getStatus() { return m_status; }
	
	public Font getFont() { return m_font; }
	
	public Color getTextColour() { return m_textColour; }
	
	public void setUserName(String userName) { m_userName = userName; }
	
	public void setNickName(String nickName) { if(nickName != null) { m_nickName = nickName; } }
	
	public void setPersonalMessage(String personalMessage) { if(personalMessage != null) { m_personalMessage = personalMessage; } }
	
	public void setStatus(int status) { if(StatusType.isValid(status)) { m_status = status; } }
	
	public void setFont(Font font) { if(font != null) { m_font = font; } }
	
	public void setTextColour(Color textColour) { if(textColour != null) { m_textColour = textColour; } }
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Contact)) { return false; }
		return ((Contact)o).m_userName.equalsIgnoreCase(m_userName);
	}
	
	public String toString() {
		return m_userName;
	}
	
}
