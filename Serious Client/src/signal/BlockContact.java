package signal;

import shared.ByteStream;

public class BlockContact extends Signal {
	
	private String m_userName;
	private boolean m_blocked;
	
	private BlockContact() {
		super(SignalType.BlockContact);
	}
	
	public BlockContact(String userName, boolean blocked) {
		super(SignalType.BlockContact);
		m_userName = userName;
		m_blocked = blocked;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public boolean getBlocked() {
		return m_blocked;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_blocked);
		return checksum;
	}
	
	public static BlockContact readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.BlockContact) { return null; }		
		
		BlockContact s2 = new BlockContact();
		
		s2.m_userName = byteStream.nextString(SignalConstants.MAX_USERNAME_LENGTH);
		s2.m_blocked = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, SignalConstants.MAX_USERNAME_LENGTH);
		byteStream.addBoolean(m_blocked);
		byteStream.addLong(checksum());
	}
	
}
