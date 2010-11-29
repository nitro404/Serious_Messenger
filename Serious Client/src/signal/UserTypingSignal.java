package signal;

import shared.*;

public class UserTypingSignal extends Signal {
	
	private boolean m_typing;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private UserTypingSignal() {
		super(SignalType.UserTyping);
	}
	
	public UserTypingSignal(boolean typing) {
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
	
	public static UserTypingSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UserTypingSignal s2 = new UserTypingSignal();
		
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
