package signal;

import shared.*;

public class Logout extends Signal {
	
	private String m_userName;
	
	final public static int LENGTH = (Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									 Long.SIZE;
	
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
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static Logout readFrom(ByteStream byteStream) {
		Logout s2 = new Logout();
		
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
