package signal;

import shared.ByteStream;

public class Logout extends Signal {
	
	private String m_userName;
	
	private Logout() {
		super(SignalType.Logout);
	}
	
	public Logout(String userName) {
		super(SignalType.Logout);
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
	
	public static Logout readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.Logout) { return null; }		
		
		Logout s2 = new Logout();
		
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
