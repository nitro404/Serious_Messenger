package signal;

import shared.ByteStream;

public class ContactDeleted extends Signal {
	
	private String m_userName;
	private boolean m_deleted;
	
	private ContactDeleted() {
		super(SignalType.ContactDeleted);
	}
	
	public ContactDeleted(String userName, boolean deleted) {
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
		checksum += ByteStream.getChecksum(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_deleted);
		return checksum;
	}
	
	public static ContactDeleted readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.ContactDeleted) { return null; }		
		
		ContactDeleted s2 = new ContactDeleted();
		
		s2.m_userName = byteStream.nextString(SignalConstants.MAX_USERNAME_LENGTH);
		s2.m_deleted = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_deleted);
		byteStream.addLong(checksum());
	}
	
}
