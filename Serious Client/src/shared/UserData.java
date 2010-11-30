package shared;

import java.awt.*;

public class UserData {
	
	protected String m_userName;
	protected String m_nickName;
	protected String m_personalMessage;
	protected byte m_status;
	protected FontStyle m_font;
	
	final public static int LENGTH = FontStyle.LENGTH +
									(((Globals.MAX_USERNAME_LENGTH * Character.SIZE) +
								      (Globals.MAX_NICKNAME_LENGTH * Character.SIZE) +
								      (Globals.MAX_PERSONAL_MESSAGE_LENGTH * Character.SIZE) +
								      Byte.SIZE) / 8);
	
	public UserData(String userName, String nickName, String personalMessage, byte status, FontStyle font) {
		m_userName = userName;
		m_nickName = (nickName == null) ? "" : nickName;
		m_personalMessage = (personalMessage == null) ? "" : personalMessage;
		m_status = StatusType.isValid(status) ? status : StatusType.Offline;
		m_font = (font == null) ? Globals.DEFAULT_FONTSTYLE : font;
	}
	
	public UserData(String userName, String nickName, String personalMessage, byte status, Font font, Color colour) {
		this(userName, nickName, personalMessage, status, new FontStyle(font, colour));
	}
	
	public UserData(String userName, String nickName, String personalMessage, byte status, Font font) {
		this(userName, nickName, personalMessage, status, new FontStyle(font));
	}
	
	public String getUserName() { return m_userName; }
	
	public String getNickName() { return m_nickName; }
	
	public String getPersonalMessage() { return m_personalMessage; }
	
	public byte getStatus() { return m_status; }
	
	public FontStyle getFont() { return m_font; }
	
	public void setUserName(String userName) { m_userName = userName; }
	
	public void setNickName(String nickName) { if(nickName != null) { m_nickName = nickName; } }
	
	public void setPersonalMessage(String personalMessage) { if(personalMessage != null) { m_personalMessage = personalMessage; } }
	
	public void setStatus(byte status) { if(StatusType.isValid(status)) { m_status = status; } }
	
	public void setFont(FontStyle font) { if(font != null) { m_font = font; } }
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		checksum += ByteStream.getChecksum(m_status);
		checksum += m_font.checksum();
		return checksum;
	}
	
	public static UserData readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		String userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		String nickName = byteStream.nextString(Globals.MAX_NICKNAME_LENGTH);
		String personalMessage = byteStream.nextString(Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byte status = byteStream.nextByte();
		FontStyle font = FontStyle.readFrom(byteStream);
		
		return new UserData(userName, nickName, personalMessage, status, font);
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		byteStream.addStringFixedLength(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addByte(m_status);
		m_font.writeTo(byteStream);
	}
	
}
