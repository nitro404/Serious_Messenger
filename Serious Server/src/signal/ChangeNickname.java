package signal;

import shared.*;

public class ChangeNickname extends Signal {
	
	private String m_nickName;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_NICKNAME_LENGTH) +
									  Long.SIZE) / 8;
	
	private ChangeNickname() {
		super(SignalType.ChangeNickname);
	}
	
	public ChangeNickname(String nickName) {
		super(SignalType.ChangeNickname);
		m_nickName = nickName;
	}
	
	public String getNickName() {
		return m_nickName;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		return checksum;
	}
	
	public static ChangeNickname readFrom(ByteStream byteStream) {
		ChangeNickname s2 = new ChangeNickname();
		
		s2.m_nickName = byteStream.nextString(Globals.MAX_NICKNAME_LENGTH);
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_nickName, Globals.MAX_NICKNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
