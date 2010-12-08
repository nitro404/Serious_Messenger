package signal;

import shared.*;

public class ChangeStatusSignal extends Signal {
	
	private String m_userName;
	private byte m_status;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ChangeStatusSignal() {
		super(SignalType.ChangeStatus);
	}
	
	public ChangeStatusSignal(String userName, byte status) {
		super(SignalType.ChangeStatus);
		m_userName = userName;
		m_status = status;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public byte getStatus() {
		return m_status;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_status);
		return checksum;
	}
	
	public static ChangeStatusSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ChangeStatusSignal s2 = new ChangeStatusSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_status = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addByte(m_status);
		byteStream.addLong(checksum());
	}
	
}
