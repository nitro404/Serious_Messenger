package signal;

import shared.*;

public class ChangeNicknameSignal extends Signal {
	
	private String m_nickName;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_NICKNAME_LENGTH) +
									  Long.SIZE) / 8;
	
	private ChangeNicknameSignal() {
		super(SignalType.ChangeNickname);
	}
	
	public ChangeNicknameSignal(String nickName) {
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
	
	public static ChangeNicknameSignal readFrom(ByteStream byteStream) {
		ChangeNicknameSignal s2 = new ChangeNicknameSignal();
		
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
