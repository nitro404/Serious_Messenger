package signal;

import shared.*;

public class ContactBlocked extends Signal {
	
	private String m_userName;
	private boolean m_blocked;
	
	private ContactBlocked() {
		super(SignalType.ContactBlocked);
	}
	
	public ContactBlocked(String userName, boolean blocked) {
		super(SignalType.ContactBlocked);
		m_userName = userName;
		m_blocked = blocked;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public boolean getBlocked() {
		return m_blocked;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_blocked);
		return checksum;
	}
	
	public static ContactBlocked readFrom(ByteStream byteStream) {
		ContactBlocked s2 = new ContactBlocked();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_blocked = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_blocked);
		byteStream.addLong(checksum());
	}
	
}
