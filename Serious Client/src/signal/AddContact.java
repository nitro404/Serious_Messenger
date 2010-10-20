package signal;

import shared.*;

public class AddContact extends Signal {
	
	private String m_userName;
	
	private AddContact() {
		super(SignalType.AddContact);
	}
	
	public AddContact(String userName) {
		super(SignalType.AddContact);
		m_userName = userName;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static AddContact readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.AddContact) { return null; }		
		
		AddContact s2 = new AddContact();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
