package signal;

import java.net.*;
import shared.*;

public class BroadcastLoginSignal extends Signal {
	
	private UserNetworkData m_data;
	
	final public static int LENGTH = UserNetworkData.LENGTH + ((Long.SIZE) / 8);
	
	private BroadcastLoginSignal() {
		super(SignalType.BroadcastLogin);
	}
	
	public BroadcastLoginSignal(String userName, String nickName, String personalMessage, byte status, FontStyle font) {
		this(userName, nickName, personalMessage, status, font, false, null, 0);
	}
	
	public BroadcastLoginSignal(UserNetworkData data) {
		this(data.getUserName(), data.getNickName(), data.getPersonalMessage(), data.getStatus(), data.getFont(), data.isBlocked(), data.getIPAddress(), data.getPort());
	}
	
	public BroadcastLoginSignal(UserData data) {
		this(data.getUserName(), data.getNickName(), data.getPersonalMessage(), data.getStatus(), data.getFont(), false, null, 0);
	}
	
	public BroadcastLoginSignal(String userName, String nickName, String personalMessage, byte status, FontStyle font, boolean blocked, InetAddress ipAddress, int port) {
		super(SignalType.BroadcastLogin);
		m_data = new UserNetworkData(userName, nickName, personalMessage, status, font, blocked, ipAddress, port);
	}
	
	public UserNetworkData getData() {
		return m_data;
	}
	
	public long checksum() {
		return m_data.checksum();
	}
	
	public static BroadcastLoginSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		BroadcastLoginSignal s2 = new BroadcastLoginSignal();
		
		s2.m_data = UserNetworkData.readFrom(byteStream);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		m_data.writeTo(byteStream);
		byteStream.addLong(checksum());
	}
	
}
