package signal;

import shared.*;

public class ContactDeletedSignal extends Signal {
	
	private String m_userName;
	private boolean m_deleted;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ContactDeletedSignal() {
		super(SignalType.ContactDeleted);
	}
	
	public ContactDeletedSignal(String userName, boolean deleted) {
		super(SignalType.ContactDeleted);
		m_userName = userName;
		m_deleted = deleted;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public boolean getDeleted() {
		return m_deleted;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_deleted);
		return checksum;
	}
	
	public static ContactDeletedSignal readFrom(ByteStream byteStream) {
		ContactDeletedSignal s2 = new ContactDeletedSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_deleted = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_deleted);
		byteStream.addLong(checksum());
	}
	
}
