package shared;

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
	
	final public static int LENGTH = ((Globals.MAX_USERNAME_LENGTH * Character.SIZE) +
									  (Globals.MAX_NICKNAME_LENGTH * Character.SIZE) +
									  (Globals.MAX_PERSONAL_MESSAGE_LENGTH * Character.SIZE) +
									  Byte.SIZE +
									  (Globals.MAX_FONTFACE_LENGTH * Character.SIZE) +
									  (Integer.SIZE * 3) +
									  Byte.SIZE +
									  (Byte.SIZE * 4) +
									  Integer.SIZE) / 8;
	
	private ClientData() {
		
	}
	
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
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		checksum += ByteStream.getChecksum(m_status);
		checksum += ByteStream.getChecksum(m_font.getFontName(), Globals.MAX_FONTFACE_LENGTH);
		checksum += ByteStream.getChecksum(m_textColour.getRed());
		checksum += ByteStream.getChecksum(m_textColour.getGreen());
		checksum += ByteStream.getChecksum(m_textColour.getBlue());
		checksum += ByteStream.getChecksum(m_blocked);
		if(m_ipAddress != null) {
			byte[] ipData = m_ipAddress.getAddress();
			for(int i=0;i<4;i++) {
				checksum += ByteStream.getChecksum(ipData[i]);
			}
		}
		checksum += ByteStream.getChecksum(m_port);
		return checksum;
	}
	
	public static ClientData readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ClientData c = new ClientData();
		
		c.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		c.m_nickName = byteStream.nextString(Globals.MAX_NICKNAME_LENGTH);
		c.m_personalMessage = byteStream.nextString(Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		c.m_status = byteStream.nextByte();
		int[] colourData = new int[3];
		for(int i=0;i<3;i++) {
			colourData[i] = byteStream.nextInteger();
		}
		c.m_font = new Font(byteStream.nextString(Globals.MAX_FONTFACE_LENGTH), Font.PLAIN, 12);
		c.m_textColour = new Color(colourData[0], colourData[1], colourData[2]);
		c.m_blocked = byteStream.nextBoolean();
		byte[] ipData = new byte[4];
		for(int i=0;i<4;i++) {
			ipData[i] = byteStream.nextByte();
		}
		try { c.m_ipAddress = InetAddress.getByAddress(ipData); }
		catch(UnknownHostException e) { }
		c.m_port = byteStream.nextInteger();
		
		return c;
	}
	
	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		byteStream.addStringFixedLength(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addByte(m_status);
		byteStream.addStringFixedLength(m_font.getFontName(), Globals.MAX_FONTFACE_LENGTH);
		byteStream.addInteger(m_textColour.getRed());
		byteStream.addInteger(m_textColour.getGreen());
		byteStream.addInteger(m_textColour.getBlue());
		byteStream.addBoolean(m_blocked);
		byte[] ipData = null;
		if(m_ipAddress != null) { ipData = m_ipAddress.getAddress(); }
		for(int i=0;i<4;i++) {
			byteStream.addByte((ipData == null) ? 0 : ipData[i]);
		}
		byteStream.addInteger(m_port);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof ClientData)) { return false; }
		return ((ClientData)o).m_userName.equalsIgnoreCase(m_userName);
	}
	
	public String toString() {
		return m_userName;
	}
	
}
