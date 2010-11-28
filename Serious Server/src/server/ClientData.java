package server;

import java.net.*;
import java.awt.*;
import shared.*;

public class ClientData {
	
	protected String m_userName;
	protected String m_nickName;
	protected String m_personalMessage;
	protected byte m_status;
	protected Font m_font;
	protected Color m_textColour;
	protected boolean m_blocked;
	protected InetAddress m_ipAddress;
	protected int m_port;
	
	public ClientData(String userName, String nickName, String personalMessage, byte status, Font font, Color textColour, boolean blocked, InetAddress ipAddress, int port) {
		m_userName = userName;
		m_nickName = (nickName == null) ? "" : nickName;
		m_personalMessage = (personalMessage == null) ? "" : personalMessage;
		m_status = StatusType.isValid(status) ? status : StatusType.Offline;
		m_font = (font == null) ? Globals.DEFAULT_FONT : font;
		m_textColour = (textColour == null) ? Globals.DEFAULT_TEXT_COLOUR : textColour;
		m_blocked = blocked;
		m_ipAddress = ipAddress;
		m_port = (port < 0 || port > 65535) ? 0 : port;
	}
	
	public String getUserName() { return m_userName; }
	
	public String getNickName() { return m_nickName; }
	
	public String getPersonalMessage() { return m_personalMessage; }
	
	public byte getStatus() { return m_status; }
	
	public Font getFont() { return m_font; }
	
	public Color getTextColour() { return m_textColour; }
	
	public boolean getBlocked() { return m_blocked; }
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public int getPort() { return m_port; }
	
	public void setUserName(String userName) { m_userName = userName; }
	
	public void setNickName(String nickName) { if(nickName != null) { m_nickName = nickName; } }
	
	public void setPersonalMessage(String personalMessage) { if(personalMessage != null) { m_personalMessage = personalMessage; } }
	
	public void setStatus(byte status) { if(StatusType.isValid(status)) { m_status = status; } }
	
	public void setFont(Font font) { if(font != null) { m_font = font; } }
	
	public void setTextColour(Color textColour) { if(textColour != null) { m_textColour = textColour; } }
	
	public void setBlocked(boolean blocked) { m_blocked = blocked; }
	
	public void setIPAddress(InetAddress ipAddress) { if(ipAddress != null) { m_ipAddress = ipAddress; } }
	
	public void setPort(int port) { if(port >= 0 && port <= 65535) { m_port = port; } }
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof ClientData)) { return false; }
		return ((ClientData)o).m_userName.equalsIgnoreCase(m_userName);
	}
	
	public String toString() {
		return m_userName;
	}
	
}
