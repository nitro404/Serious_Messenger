package signal;

import java.awt.*;
import shared.*;

public class ChangeFontSignal extends Signal {
	
	private FontStyle m_font;
	
	final public static int LENGTH = FontStyle.LENGTH +
									 ((Long.SIZE) / 8);
	
	private ChangeFontSignal() {
		super(SignalType.ChangeFont);
	}
	
	public ChangeFontSignal(FontStyle font) {
		super(SignalType.ChangeFont);
		m_font = (font == null) ? Globals.DEFAULT_FONTSTYLE : font;
	}
	
	public ChangeFontSignal(Font font, Color colour) {
		super(SignalType.ChangeFont);
		m_font = new FontStyle(font, colour);
	}
	
	public ChangeFontSignal(String face, int size, boolean bold, boolean italic, Color colour) {
		super(SignalType.ChangeFont);
		m_font = new FontStyle(face, size, bold, italic, colour);
	}
	
	public FontStyle getFont() {
		return m_font;
	}

	public long checksum() {
		return m_font.checksum();
	}
	
	public static ChangeFontSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ChangeFontSignal s2 = new ChangeFontSignal();
		
		FontStyle.readFrom(byteStream);
		
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		m_font.writeTo(byteStream);
		byteStream.addLong(checksum());
	}
	
}
