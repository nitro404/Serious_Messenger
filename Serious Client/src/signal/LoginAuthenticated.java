package signal;

import shared.*;

public class LoginAuthenticated extends Signal {
	
	private boolean m_authenticated;
	
	final public static int LENGTH = Byte.SIZE +
									 Long.SIZE;
	
	private LoginAuthenticated() {
		super(SignalType.LoginAuthenticated);
	}
	
	public LoginAuthenticated(boolean authenticated) {
		super(SignalType.LoginAuthenticated);
		m_authenticated = authenticated;
	}
	
	public boolean getAuthenticated() {
		return m_authenticated;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_authenticated);
		return checksum;
	}
	
	public static LoginAuthenticated readFrom(ByteStream byteStream) {
		LoginAuthenticated s2 = new LoginAuthenticated();
		
		s2.m_authenticated = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addBoolean(m_authenticated);
		byteStream.addLong(checksum());
	}
	
}
