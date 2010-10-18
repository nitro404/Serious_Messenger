package signal;

import shared.ByteStream;

public class DeleteContact extends Signal {
	
	private String m_userName;
	
	private DeleteContact() {
		super(SignalType.DeleteContact);
	}
	
	public DeleteContact(String userName) {
		super(SignalType.DeleteContact);
		m_userName = userName;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static DeleteContact readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.DeleteContact) { return null; }		
		
		DeleteContact s2 = new DeleteContact();
		
		s2.m_userName = byteStream.nextString(SignalConstants.MAX_USERNAME_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
