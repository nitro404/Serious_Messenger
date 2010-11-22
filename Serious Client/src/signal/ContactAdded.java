package signal;

import shared.*;

public class ContactAdded extends Signal {
	
	private String m_userName;
	private boolean m_added;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ContactAdded() {
		super(SignalType.ContactAdded);
	}
	
	public ContactAdded(String userName, boolean added) {
		super(SignalType.ContactAdded);
		m_userName = userName;
		m_added = added;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public boolean getAdded() {
		return m_added;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_added);
		return checksum;
	}
	
	public static ContactAdded readFrom(ByteStream byteStream) {
		ContactAdded s2 = new ContactAdded();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_added = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_added);
		byteStream.addLong(checksum());
	}
	
}
