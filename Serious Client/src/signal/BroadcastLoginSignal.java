package signal;

import java.net.*;
import shared.*;

public class BroadcastLoginSignal extends Signal {
	
	private String m_userName;
	private String m_nickName;
	private String m_personalMessage;
	private byte m_status;
	private InetAddress m_ipAddress;
	private byte[] m_ipAddressData;
	private int m_port;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  (Character.SIZE * Globals.MAX_NICKNAME_LENGTH) +
									  (Character.SIZE * Globals.MAX_PERSONAL_MESSAGE_LENGTH) +
									  Byte.SIZE +
									  (Byte.SIZE * 4) +
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private BroadcastLoginSignal() {
		super(SignalType.LoginRequest);
	}
	
	public BroadcastLoginSignal(String userName, String nickName, String personalMessage, byte status) {
		this(userName, nickName, personalMessage, status, null, 0);
	}
	
	public BroadcastLoginSignal(String userName, String nickName, String personalMessage, byte status, InetAddress ipAddress, int port) {
		super(SignalType.LoginRequest);
		m_userName = userName;
		m_nickName = nickName;
		m_personalMessage = personalMessage;
		m_status = status;
		m_ipAddress = ipAddress;
		m_port = (port < 0 || port > 65535) ? 0 : port;
		
		m_ipAddressData = (m_ipAddress == null) ? new byte[4] : m_ipAddress.getAddress();
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
	
	public InetAddress getIPAddress() {
		return m_ipAddress;
	}
	
	public byte[] getIPAddressData() {
		return m_ipAddressData;
	}
	
	public int getPort() {
		return m_port;
	}
	
	public void setUserName(String userName) {
		m_userName = userName;
	}
	
	public void setIPAddress(InetAddress ipAddress) {
		m_ipAddress = ipAddress;
		m_ipAddressData = (m_ipAddress == null) ? new byte[4] : m_ipAddress.getAddress();
	}
	
	public void setPort(int port) {
		if(port >= 0 && port <= 65535) { m_port = port; }
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		checksum += ByteStream.getChecksum(m_status);
		for(int i=0;i<4;i++) {
			checksum += ByteStream.getChecksum(m_ipAddressData[i]);
		}
		checksum += ByteStream.getChecksum(m_port);
		return checksum;
	}
	
	public static BroadcastLoginSignal readFrom(ByteStream byteStream) {
		BroadcastLoginSignal s2 = new BroadcastLoginSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_nickName = byteStream.nextString(Globals.MAX_NICKNAME_LENGTH);
		s2.m_personalMessage = byteStream.nextString(Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		s2.m_status = byteStream.nextByte();
		s2.m_ipAddressData = new byte[4];
		for(int i=0;i<4;i++) {
			s2.m_ipAddressData[i] = byteStream.nextByte();
		}
		s2.m_port = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		try { s2.m_ipAddress = InetAddress.getByAddress(s2.m_ipAddressData); }
		catch(UnknownHostException e) { s2.m_ipAddress = null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		byteStream.addStringFixedLength(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addByte(m_status);
		for(int i=0;i<4;i++) {
			byteStream.addByte(m_ipAddressData[i]);
		}
		byteStream.addInteger(m_port);
		byteStream.addLong(checksum());
	}
	
}
