package signal;

import shared.ByteStream;

public class BroadcastLogin extends Signal {
	
	private String m_userName;
	private String m_nickName;
	private String m_personalMessage;
	private byte m_status;
	
	private BroadcastLogin() {
		super(SignalType.LoginRequest);
	}
	
	public BroadcastLogin(String userName, String nickName, byte status, String personalMessage) {
		super(SignalType.LoginRequest);
		m_userName = userName;
		m_nickName = nickName;
		m_personalMessage = personalMessage;
		m_status = status;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getNickName() {
		return m_nickName;
	}
	
	public String getPersonalMessage() {
		return m_personalMessage;
	}
	
	public byte getStatus() {
		return m_status;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_nickName, SignalConstants.MAX_NICKNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_personalMessage, SignalConstants.MAX_PERSONAL_MESSAGE_LENGTH);
		checksum += ByteStream.getChecksum(m_status);
		return checksum;
	}
	
	public static BroadcastLogin readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.BroadcastLogin) { return null; }		
		
		BroadcastLogin s2 = new BroadcastLogin();
		
		s2.m_userName = byteStream.nextString(SignalConstants.MAX_USERNAME_LENGTH);
		s2.m_nickName = byteStream.nextString(SignalConstants.MAX_NICKNAME_LENGTH);
		s2.m_personalMessage = byteStream.nextString(SignalConstants.MAX_PERSONAL_MESSAGE_LENGTH);
		s2.m_status = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_nickName, SignalConstants.MAX_NICKNAME_LENGTH);
		byteStream.addStringFixedLength(m_personalMessage, SignalConstants.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addByte(m_status);
		byteStream.addLong(checksum());
	}
	
}
