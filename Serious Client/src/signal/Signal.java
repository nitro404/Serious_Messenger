package signal;

import shared.ByteStream;

public class Signal {
	
	protected int m_signalType;
	
	public Signal(int signalType) {
		m_signalType = signalType;
	}
	
	public int getSignalType() {
		return m_signalType;
	}
	
	public long checksum() {
		return ByteStream.getChecksum(m_signalType);
	}
	
	public static Signal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		int id = byteStream.nextInteger();
		long idChecksum = byteStream.nextLong();
		if(ByteStream.getChecksum(id) != idChecksum) {
			return new Signal(SignalType.Invalid);
		}
		else {
			return new Signal(id);
		}
	}
	
	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		byteStream.addInteger(m_signalType);
		byteStream.addLong(ByteStream.getChecksum(m_signalType));
	}
	
}
