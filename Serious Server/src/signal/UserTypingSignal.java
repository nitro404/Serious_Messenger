package signal;

import shared.*;

public class UserTypingSignal extends Signal {
	
	private boolean m_typing;
	private String m_userName;
	private String m_contactUserName;
	
	final public static int LENGTH = (Byte.SIZE +
									  (Character.SIZE * Globals.MAX_USERNAME_LENGTH * 2) +
									  Long.SIZE) / 8;
	
	private UserTypingSignal() {
		super(SignalType.UserTyping);
	}
	
	public UserTypingSignal(boolean typing, String userName, String contactUserName) {
		super(SignalType.UserTyping);
		m_typing = typing;
		m_userName = userName;
		m_contactUserName = contactUserName;
	}
	
	public boolean getTyping() {
		return m_typing;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getContactUserName() {
		return m_contactUserName;
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_typing);
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static UserTypingSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UserTypingSignal s2 = new UserTypingSignal();
		
		s2.m_typing = byteStream.nextBoolean();
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_contactUserName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addBoolean(m_typing);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
