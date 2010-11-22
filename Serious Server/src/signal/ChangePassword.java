package signal;

import shared.*;

public class ChangePassword extends Signal {
	
	private String m_userName;
	private String m_oldPassword;
	private String m_newPassword;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  ((Character.SIZE * Globals.MAX_PASSWORD_LENGTH) * 2) +
									  Long.SIZE) / 8;
	
	private ChangePassword() {
		super(SignalType.ChangePassword);
	}
	
	public ChangePassword(String userName, String oldPassword, String newPassword) {
		super(SignalType.ChangePassword);
		m_userName = userName;
		m_oldPassword = oldPassword;
		m_newPassword = newPassword;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getOldPassword() {
		return m_oldPassword;
	}
	
	public String getNewPassword() {
		return m_newPassword;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_oldPassword, Globals.MAX_PASSWORD_LENGTH);
		checksum += ByteStream.getChecksum(m_newPassword, Globals.MAX_PASSWORD_LENGTH);
		return checksum;
	}
	
	public static ChangePassword readFrom(ByteStream byteStream) {
		ChangePassword s2 = new ChangePassword();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_oldPassword = byteStream.nextString(Globals.MAX_PASSWORD_LENGTH);
		s2.m_newPassword = byteStream.nextString(Globals.MAX_PASSWORD_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_oldPassword, Globals.MAX_PASSWORD_LENGTH);
		byteStream.addStringFixedLength(m_newPassword, Globals.MAX_PASSWORD_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
