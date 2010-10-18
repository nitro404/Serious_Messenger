package signal;

import shared.ByteStream;

public class Message extends Signal {
	
	private long m_messageID;
	private boolean m_acknowledge;
	private String m_message; 
	
	private static long m_currentMessageID = 0;
	
	private Message() {
		super(SignalType.Message);
	}
	
	public Message(String message) {
		super(SignalType.Message);
		m_messageID = m_currentMessageID++;
		m_acknowledge = true;
		m_message = message;
	}
	
	public long getMessageID() {
		return m_messageID;
	}
	
	public String getMessage() {
		return m_message;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_messageID);
		checksum += ByteStream.getChecksum(m_acknowledge);
		checksum += ByteStream.getChecksum(m_message.length());
		checksum += ByteStream.getChecksum(m_message);
		return checksum;
	}
	
	public static Message readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.Message) { return null; }		
		
		Message s2 = new Message();
		
		s2.m_messageID = byteStream.nextLong();
		s2.m_acknowledge = byteStream.nextBoolean();
		int messageLength = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		s2.m_message = byteStream.nextString(messageLength);
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addLong(m_messageID);
		byteStream.addBoolean(m_acknowledge);
		byteStream.addInteger(m_message.length());
		byteStream.addLong(checksum());
		byteStream.addString(m_message);
	}
	
}
