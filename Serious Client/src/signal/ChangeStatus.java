package signal;

import shared.ByteStream;

public class ChangeStatus extends Signal {
	
	private byte m_status;
	
	private ChangeStatus() {
		super(SignalType.ChangeStatus);
	}
	
	public ChangeStatus(byte status) {
		super(SignalType.ChangeStatus);
		m_status = status;
	}
	
	public byte getStatus() {
		return m_status;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_status);
		return checksum;
	}
	
	public static ChangeStatus readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.ChangeStatus) { return null; }		
		
		ChangeStatus s2 = new ChangeStatus();
		
		s2.m_status = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addByte(m_status);
		byteStream.addLong(checksum());
	}
	
}
