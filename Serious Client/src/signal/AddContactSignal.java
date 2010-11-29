package signal;

import shared.*;

public class AddContactSignal extends Signal {
	
	private String m_userName;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_USERNAME_LENGTH) +
									  Long.SIZE) / 8;
	
	private AddContactSignal() {
		super(SignalType.AddContact);
	}
	
	public AddContactSignal(String userName) {
		super(SignalType.AddContact);
		m_userName = userName;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static AddContactSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		AddContactSignal s2 = new AddContactSignal();
		
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
