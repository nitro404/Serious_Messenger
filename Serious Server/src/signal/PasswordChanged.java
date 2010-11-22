package signal;

import shared.*;

public class PasswordChanged extends Signal {
	
	private boolean m_passwordChanged;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private PasswordChanged() {
		super(SignalType.PasswordChanged);
	}
	
	public PasswordChanged(boolean passwordChanged) {
		super(SignalType.PasswordChanged);
		m_passwordChanged = passwordChanged;
	}
	
	public boolean getPasswordChanged() {
		return m_passwordChanged;
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_passwordChanged);
		return checksum;
	}
	
	public static PasswordChanged readFrom(ByteStream byteStream) {
		PasswordChanged s2 = new PasswordChanged();
		
		s2.m_passwordChanged = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addBoolean(m_passwordChanged);
		byteStream.addLong(checksum());
	}
	
}
