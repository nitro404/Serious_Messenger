package signal;

import shared.ByteStream;

public class ChangePassword extends Signal {
	
	private String m_userName;
	private String m_oldPassword;
	private String m_newPassword;
	
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
		checksum += ByteStream.getChecksum(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_oldPassword, SignalConstants.MAX_PASSWORD_LENGTH);
		checksum += ByteStream.getChecksum(m_newPassword, SignalConstants.MAX_PASSWORD_LENGTH);
		return checksum;
	}
	
	public static ChangePassword readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.ChangePassword) { return null; }		
		
		ChangePassword s2 = new ChangePassword();
		
		s2.m_userName = byteStream.nextString(SignalConstants.MAX_USERNAME_LENGTH);
		s2.m_oldPassword = byteStream.nextString(SignalConstants.MAX_PASSWORD_LENGTH);
		s2.m_newPassword = byteStream.nextString(SignalConstants.MAX_PASSWORD_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_oldPassword, SignalConstants.MAX_PASSWORD_LENGTH);
		byteStream.addStringFixedLength(m_newPassword, SignalConstants.MAX_PASSWORD_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
