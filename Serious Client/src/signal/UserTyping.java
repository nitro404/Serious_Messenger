package signal;

import shared.*;

public class UserTyping extends Signal {
	
	private boolean m_typing;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private UserTyping() {
		super(SignalType.UserTyping);
	}
	
	public UserTyping(boolean typing) {
		super(SignalType.UserTyping);
		m_typing = typing;
	}
	
	public boolean getTyping() {
		return m_typing;
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_typing);
		return checksum;
	}
	
	public static UserTyping readFrom(ByteStream byteStream) {
		UserTyping s2 = new UserTyping();
		
		s2.m_typing = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addBoolean(m_typing);
		byteStream.addLong(checksum());
	}
	
}
