package signal;

import shared.*;

public class LoginAuthenticatedSignal extends Signal {
	
	private UserData m_data;
	private boolean m_authenticated;
	private int m_port;
	
	final public static int LENGTH = UserData.LENGTH +
									 ((Byte.SIZE +
									   Integer.SIZE + 
									   Long.SIZE) / 8);
	
	private LoginAuthenticatedSignal() {
		super(SignalType.LoginAuthenticated);
	}
	
	public LoginAuthenticatedSignal(String userName, String nickName, String personalMessage, boolean authenticated, int port) {
		super(SignalType.LoginAuthenticated);
		m_data = new UserData(userName, nickName, personalMessage, StatusType.Online, Globals.DEFAULT_FONTSTYLE);
		m_authenticated = authenticated;
		m_port = port;
	}
	
	public LoginAuthenticatedSignal(UserData data, boolean authenticated, int port) {
		super(SignalType.LoginAuthenticated);
		m_data = data;
		m_authenticated = authenticated;
		m_port = port;
	}
	
	public UserData getData() {
		return m_data;
	}
	
	public boolean getAuthenticated() {
		return m_authenticated;
	}
	
	public int getPort() {
		return m_port;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += m_data.checksum();
		checksum += ByteStream.getChecksum(m_authenticated);
		checksum += ByteStream.getChecksum(m_port);
		return checksum;
	}
	
	public static LoginAuthenticatedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		LoginAuthenticatedSignal s2 = new LoginAuthenticatedSignal();
		
		s2.m_data = UserData.readFrom(byteStream);
		s2.m_authenticated = byteStream.nextBoolean();
		s2.m_port = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		
		//if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		m_data.writeTo(byteStream);
		byteStream.addBoolean(m_authenticated);
		byteStream.addInteger(m_port);
		byteStream.addLong(checksum());
	}
	
}
