package shared;

import java.net.*;

public class UserNetworkData extends UserData {
	
	protected boolean m_blocked;
	protected InetAddress m_ipAddress;
	protected int m_port;
	
	final public static int LENGTH = UserData.LENGTH +
									 ((Byte.SIZE +
									   (Byte.SIZE * 4) +
									   Integer.SIZE) / 8);
	
	public UserNetworkData(String userName, String nickName, String personalMessage, byte status, FontStyle font, boolean blocked, InetAddress ipAddress, int port) {
		super(userName, nickName, personalMessage, status, font);
		m_blocked = blocked;
		m_ipAddress = ipAddress;
		m_port = (port < 0 || port > 65535) ? 0 : port;
	}
	
	public UserNetworkData(String userName, String nickName, String personalMessage, byte status, FontStyle font) {
		this(userName, nickName, personalMessage, status, font, false, null, 0);
	}
	
	public UserNetworkData(UserData userData, boolean blocked, InetAddress ipAddress, int port) {
		super(userData.m_userName, userData.m_nickName, userData.m_personalMessage, userData.m_status, userData.m_font);
		m_blocked = blocked;
		m_ipAddress = ipAddress;
		m_port = (port < 0 || port > 65535) ? 0 : port;
	}
	
	public UserNetworkData(UserData userData) {
		this(userData, false, null, 0);
	}
	
	public boolean isBlocked() { return m_blocked; }
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public String getIPAddressString() { return (m_ipAddress == null) ? "" : m_ipAddress.getHostAddress(); }
	
	public int getPort() { return m_port; }
	
	public void setBlocked(boolean blocked) { m_blocked = blocked; }
	
	public void setIPAddress(InetAddress ipAddress) { if(ipAddress != null) { m_ipAddress = ipAddress; } }
	
	public void setIPAddress(String ipAddress) { setIPAddress(parseIPAddress(ipAddress)); }
	
	public void setPort(int port) { if(port >= 0 && port <= 65535) { m_port = port; } }
	
	public static InetAddress parseIPAddress(String ipAddress) {
		InetAddress ip = null;
		try { ip = InetAddress.getByName(ipAddress); }
		catch (UnknownHostException e) { }
		return ip;
	}

	public long checksum() {
		long checksum = 0;
		checksum += super.checksum();
		checksum += ByteStream.getChecksum(m_blocked);
		if(m_ipAddress != null) {
			byte[] ipData = m_ipAddress.getAddress();
			for(int i=0;i<4;i++) {
				checksum += ByteStream.getChecksum(ipData[i]);
			}
		}
		checksum += ByteStream.getChecksum(m_port);
		return checksum;
	}
	
	public static UserNetworkData readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UserData userData = UserData.readFrom(byteStream);
		boolean blocked = byteStream.nextBoolean();
		InetAddress ipAddress = null;
		byte[] ipData = new byte[4];
		for(int i=0;i<4;i++) {
			ipData[i] = byteStream.nextByte();
		}
		try { ipAddress = InetAddress.getByAddress(ipData); }
		catch(UnknownHostException e) { }
		int port = byteStream.nextInteger();
		
		return new UserNetworkData(userData, blocked, ipAddress, port);
	}
	
	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addBoolean(m_blocked);
		byte[] ipData = null;
		if(m_ipAddress != null) { ipData = m_ipAddress.getAddress(); }
		for(int i=0;i<4;i++) {
			byteStream.addByte((ipData == null) ? 0 : ipData[i]);
		}
		byteStream.addInteger(m_port);
	}
	
}
