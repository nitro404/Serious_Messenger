package signal;

import shared.*;

public class ChangePersonalMessageSignal extends Signal {
	
	private String m_userName;
	private String m_personalMessage;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  (Character.SIZE * Globals.MAX_PERSONAL_MESSAGE_LENGTH) +
									  Long.SIZE) / 8;
	
	private ChangePersonalMessageSignal() {
		super(SignalType.ChangePersonalMessage);
	}
	
	public ChangePersonalMessageSignal(String userName, String personalMessage) {
		super(SignalType.ChangePersonalMessage);
		m_userName = userName;
		m_personalMessage = personalMessage;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getPersonalMessage() {
		return m_personalMessage;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		return checksum;
	}
	
	public static ChangePersonalMessageSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ChangePersonalMessageSignal s2 = new ChangePersonalMessageSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_personalMessage = byteStream.nextString(Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
