package signal;

import java.io.DataInputStream;

import shared.*;

public class MessageSignal extends Signal {
	
	private long m_messageID;
	private String m_userName;
	private String m_contactUserName;
	private int m_messageLength = -1;
	private String m_message; 
	
	private static long m_currentMessageID = 0;
	
	final public static int LENGTH = (Long.SIZE +
									  (Character.SIZE * Globals.MAX_USERNAME_LENGTH * 2) + 
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private MessageSignal() {
		super(SignalType.Message);
	}
	
	public MessageSignal(String message, String userName, String contactUserName) {
		super(SignalType.Message);
		m_messageID = m_currentMessageID++;
		m_userName = userName;
		m_contactUserName = contactUserName;
		m_message = message;
		m_messageLength = message.length();
	}
	
	public long getMessageID() {
		return m_messageID;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getContactUserName() {
		return m_contactUserName;
	}
	
	public int getMessageLength() {
		return m_messageLength;
	}
	
	public String getMessage() {
		return m_message;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_messageID);
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_message.length());
		checksum += ByteStream.getChecksum(m_message);
		return checksum;
	}
	
	public static MessageSignal readFrom(ByteStream byteStream, DataInputStream in) {
		if(byteStream == null || in == null) { return null; }
		
		MessageSignal s2 = new MessageSignal();
		
		s2.m_messageID = byteStream.nextLong();
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_contactUserName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_messageLength = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		s2.m_message = ByteStream.readFrom(in, (s2.m_messageLength * Character.SIZE)).nextString(s2.m_messageLength);
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addLong(m_messageID);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addInteger(m_message.length());
		byteStream.addLong(checksum());
		byteStream.addString(m_message);
	}
	
}
