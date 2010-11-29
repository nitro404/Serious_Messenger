package signal;

import shared.*;

public class PasswordChangedSignal extends Signal {
	
	private boolean m_passwordChanged;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private PasswordChangedSignal() {
		super(SignalType.PasswordChanged);
	}
	
	public PasswordChangedSignal(boolean passwordChanged) {
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
	
	public static PasswordChangedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		PasswordChangedSignal s2 = new PasswordChangedSignal();
		
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
