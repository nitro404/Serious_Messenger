package signal;

import shared.*;

public class LoginAuthenticatedSignal extends Signal {
	
	private boolean m_authenticated;
	private int m_port;
	
	final public static int LENGTH = (Byte.SIZE +
									  Integer.SIZE + 
									  Long.SIZE) / 8;
	
	private LoginAuthenticatedSignal() {
		super(SignalType.LoginAuthenticated);
	}
	
	public LoginAuthenticatedSignal(boolean authenticated, int port) {
		super(SignalType.LoginAuthenticated);
		m_authenticated = authenticated;
		m_port = port;
	}
	
	public boolean getAuthenticated() {
		return m_authenticated;
	}
	
	public int getPort() {
		return m_port;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_authenticated);
		checksum += ByteStream.getChecksum(m_port);
		return checksum;
	}
	
	public static LoginAuthenticatedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		LoginAuthenticatedSignal s2 = new LoginAuthenticatedSignal();
		
		s2.m_authenticated = byteStream.nextBoolean();
		s2.m_port = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addBoolean(m_authenticated);
		byteStream.addInteger(m_port);
		byteStream.addLong(checksum());
	}
	
}
