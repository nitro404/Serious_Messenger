package signal;

import java.awt.*;
import shared.*;

public class ChangeFontSignal extends Signal {
	
	private FontStyle m_font;
	private String m_userName;
	private String m_contactUserName;
	
	final public static int LENGTH = FontStyle.LENGTH +
									 ((Character.SIZE * Globals.MAX_USERNAME_LENGTH * 2) +
									  (Long.SIZE) / 8);
	
	private ChangeFontSignal() {
		super(SignalType.ChangeFont);
	}
	
	public ChangeFontSignal(FontStyle font, String userName, String contactUserName) {
		super(SignalType.ChangeFont);
		m_font = (font == null) ? Globals.DEFAULT_FONTSTYLE : font;
		m_userName = userName;
		m_contactUserName = contactUserName;
	}
	
	public ChangeFontSignal(Font font, Color colour, String userName, String contactUserName) {
		super(SignalType.ChangeFont);
		m_font = new FontStyle(font, colour);
		m_userName = userName;
		m_contactUserName = contactUserName;
	}
	
	public ChangeFontSignal(String face, int size, boolean bold, boolean italic, Color colour, String userName, String contactUserName) {
		super(SignalType.ChangeFont);
		m_font = new FontStyle(face, size, bold, italic, colour);
		m_userName = userName;
		m_contactUserName = contactUserName;
	}
	
	public FontStyle getFont() {
		return m_font;
	}
	
	public String getUserName() {
		return m_userName;
	}
	
	public String getContactUserName() {
		return m_contactUserName;
	}

	public long checksum() {
		long checksum = m_font.checksum();
		checksum += ByteStream.getChecksum(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		checksum += ByteStream.getChecksum(m_userName, Globals.MAX_USERNAME_LENGTH);
		return checksum;
	}
	
	public static ChangeFontSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ChangeFontSignal s2 = new ChangeFontSignal();
		
		FontStyle.readFrom(byteStream);
		s2.m_userName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		s2.m_contactUserName = byteStream.nextString(Globals.MAX_USERNAME_LENGTH);
		
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		m_font.writeTo(byteStream);
		byteStream.addStringFixedLength(m_userName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addStringFixedLength(m_contactUserName, Globals.MAX_USERNAME_LENGTH);
		byteStream.addLong(checksum());
	}
	
}
