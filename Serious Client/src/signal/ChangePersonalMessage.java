package signal;

import shared.*;

public class ChangePersonalMessage extends Signal {
	
	private String m_personalMessage;
	
	private ChangePersonalMessage() {
		super(SignalType.ChangePersonalMessage);
	}
	
	public ChangePersonalMessage(String personalMessage) {
		super(SignalType.ChangeNickname);
		m_personalMessage = personalMessage;
	}
	
	public String getPersonalMessage() {
		return m_personalMessage;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		return checksum;
	}
	
	public static ChangePersonalMessage readFrom(ByteStream byteStream) {
		Signal s = Signal.readFrom(byteStream);
		if(s == null || s.getSignalType() != SignalType.ChangePersonalMessage) { return null; }		
		
		ChangePersonalMessage s2 = new ChangePersonalMessage();
		
		s2.m_personalMessage = byteStream.nextString(Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_personalMessage, Globals.MAX_PERSONAL_MESSAGE_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
