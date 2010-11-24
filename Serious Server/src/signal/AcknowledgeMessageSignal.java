package signal;

import shared.*;

public class AcknowledgeMessageSignal extends Signal {
	
	private long m_messageID;
	private boolean m_received;
	
	final public static int LENGTH = (Long.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private AcknowledgeMessageSignal() {
		super(SignalType.AcknowledgeMessage);
	}
	
	public AcknowledgeMessageSignal(int messageID, boolean received) {
		super(SignalType.AcknowledgeMessage);
		m_messageID = messageID;
		m_received = received;
	}
	
	public long getMessageID() {
		return m_messageID;
	}
	
	public boolean getReceived() {
		return m_received;
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_messageID);
		checksum += ByteStream.getChecksum(m_received);
		return checksum;
	}
	
	public static AcknowledgeMessageSignal readFrom(ByteStream byteStream) {
		AcknowledgeMessageSignal s2 = new AcknowledgeMessageSignal();
		
		s2.m_messageID = byteStream.nextLong();
		s2.m_received = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addLong(m_messageID);
		byteStream.addBoolean(m_received);
		byteStream.addLong(checksum());
	}
	
	
}