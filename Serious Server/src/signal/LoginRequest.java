package signal;

import shared.*;

public class LoginRequest extends Signal {
	
	private String m_userName;
	private String m_password;
	
	final public static int LENGTH = (Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									 (Character.SIZE * Globals.MAX_PASSWORD_LENGTH) +
									 Long.SIZE;
	
	private LoginRequest() {
		super(SignalType.LoginRequest);
	}
	
	public LoginRequest(String userName, String password) {
		super(SignalType.LoginRequest);
		m_userName = userName;
		m_password = password;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getPassword() {
		return m_password;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_password, Globals.MAX_PASSWORD_LENGTH);
		return checksum;
	}
	
	public static LoginRequest readFrom(ByteStream byteStream) {
		LoginRequest s2 = new LoginRequest();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_password = byteStream.nextString(Globals.MAX_PASSWORD_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_password, Globals.MAX_PASSWORD_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
