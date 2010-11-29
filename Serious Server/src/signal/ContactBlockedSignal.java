package signal;

import shared.*;

public class ContactBlockedSignal extends Signal {
	
	private String m_userName;
	private boolean m_blocked;
	private boolean m_succeeded;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ContactBlockedSignal() {
		super(SignalType.ContactBlocked);
	}
	
	public ContactBlockedSignal(String userName, boolean blocked, boolean succeeded) {
		super(SignalType.ContactBlocked);
		m_userName = userName;
		m_blocked = blocked;
		m_succeeded = succeeded;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public boolean getBlocked() {
		return m_blocked;
	}
	
	public boolean getSucceeded() {
		return m_succeeded;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_blocked);
		checksum += ByteStream.getChecksum(m_succeeded);
		return checksum;
	}
	
	public static ContactBlockedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ContactBlockedSignal s2 = new ContactBlockedSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_blocked = byteStream.nextBoolean();
		s2.m_succeeded = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_blocked);
		byteStream.addBoolean(m_succeeded);
		byteStream.addLong(checksum());
	}
	
}
