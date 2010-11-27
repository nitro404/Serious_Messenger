package signal;

import shared.*;

public class UserCreatedSignal extends Signal {
	
	private boolean m_created;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private UserCreatedSignal() {
		super(SignalType.UserCreated);
	}
	
	public UserCreatedSignal(boolean created) {
		super(SignalType.UserCreated);
		m_created = created;
	}
	
	public boolean getCreated() {
		return m_created;
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_created);
		return checksum;
	}
	
	public static UserCreatedSignal readFrom(ByteStream byteStream) {
		UserCreatedSignal s2 = new UserCreatedSignal();
		
		s2.m_created = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addBoolean(m_created);
		byteStream.addLong(checksum());
	}
	
}
