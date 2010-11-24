package signal;

import shared.*;

public class ChangeFontSignal extends Signal {
	
	private String m_fontFace;
	private int m_size;
	private boolean m_bold;
	private boolean m_italic;
	private boolean m_underline;
	private boolean m_strikeout;
	private int[] m_colour;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_FONTFACE_LENGTH) +
									  Integer.SIZE +
									  (Byte.SIZE * 4) +
									  (Integer.SIZE * 3) +
									  Long.SIZE) / 8;
	
	private ChangeFontSignal() {
		super(SignalType.ChangeFont);
	}
	
	public ChangeFontSignal(String fontFace, int size, boolean bold, boolean italic, boolean underline, boolean strikeout, int red, int green, int blue) {
		super(SignalType.ChangeFont);
		m_fontFace = fontFace;
		m_size = size;
		m_bold = bold;
		m_italic = italic;
		m_underline = underline;
		m_strikeout = strikeout;
		m_colour = new int[3];
		m_colour[0] = red;
		m_colour[1] = green;
		m_colour[2] = blue;
	}
	
	public String getFontFace() {
		return m_fontFace;
	}
	
	public int getSize() {
		return m_size;
	}
	
	public boolean getBold() {
		return m_bold;
	}
	
	public boolean getItalic() {
		return m_italic;
	}
	
	public boolean getUnderline() {
		return m_underline;
	}
	
	public boolean getStrikeout() {
		return m_strikeout;
	}
	
	public int[] getColour() {
		return m_colour;
	}
	
	public int getRed() {
		return m_colour[0];
	}
	
	public int getGreen() {
		return m_colour[1];
	}
	
	public int getBlue() {
		return m_colour[2];
	}

	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_fontFace, Globals.MAX_FONTFACE_LENGTH);
		checksum += ByteStream.getChecksum(m_size);
		checksum += ByteStream.getChecksum(m_bold);
		checksum += ByteStream.getChecksum(m_italic);
		checksum += ByteStream.getChecksum(m_underline);
		checksum += ByteStream.getChecksum(m_strikeout);
		checksum += ByteStream.getChecksum(m_colour[0]);
		checksum += ByteStream.getChecksum(m_colour[1]);
		checksum += ByteStream.getChecksum(m_colour[2]);
		return checksum;
	}
	
	public static ChangeFontSignal readFrom(ByteStream byteStream) {
		ChangeFontSignal s2 = new ChangeFontSignal();
		
		s2.m_fontFace = byteStream.nextString(Globals.MAX_FONTFACE_LENGTH);
		s2.m_size = byteStream.nextInteger();
		s2.m_bold = byteStream.nextBoolean();
		s2.m_italic = byteStream.nextBoolean();
		s2.m_underline = byteStream.nextBoolean();
		s2.m_strikeout = byteStream.nextBoolean();
		s2.m_colour = new int[3];
		s2.m_colour[0] = byteStream.nextInteger();
		s2.m_colour[1] = byteStream.nextInteger();
		s2.m_colour[2] = byteStream.nextInteger();
		
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		super.writeTo(byteStream);
		byteStream.addStringFixedLength(m_fontFace, Globals.MAX_FONTFACE_LENGTH);
		byteStream.addInteger(m_size);
		byteStream.addBoolean(m_bold);
		byteStream.addBoolean(m_italic);
		byteStream.addBoolean(m_underline);
		byteStream.addBoolean(m_strikeout);
		byteStream.addInteger(m_colour[0]);
		byteStream.addInteger(m_colour[1]);
		byteStream.addInteger(m_colour[2]);
		byteStream.addLong(checksum());
	}
	
}
