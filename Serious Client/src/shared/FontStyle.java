package shared;

import java.awt.*;

public class FontStyle {
	
	protected Font m_font;
	
	protected String m_face;
	protected int m_size;
	protected boolean m_bold;
	protected boolean m_italic;
	protected Color m_colour;
	
	final public static int LENGTH = ((Character.SIZE * Globals.MAX_FONTFACE_LENGTH) +
									  Integer.SIZE +
									  Byte.SIZE +
									  Byte.SIZE +
									  (Integer.SIZE * 3)) / 8;
	
	public FontStyle(Font font) {
		m_colour = Globals.DEFAULT_TEXT_COLOUR;
		setFont(font);
	}
	
	public FontStyle(Font font, Color colour) {
		m_colour = (colour == null) ? Globals.DEFAULT_TEXT_COLOUR : colour;
		setFont(font);
	}
	
	public FontStyle(String face, int size, boolean bold, boolean italic, Color colour) {
		m_face = (face == null) ? Globals.DEFAULT_FONT_FACE : face;
		m_size = (size < 2) ? 2 : size;
		m_bold = bold;
		m_italic = italic;
		m_colour = (colour == null) ? Globals.DEFAULT_TEXT_COLOUR : colour;
		initializeFont();
	}
	
	private void initializeFont() {
		m_font = new Font(m_face, getStyle(), m_size);
	}
	
	public Font getFont() { return m_font; }
	
	public int getSize() { return m_size; }
	
	public Color getColour() { return m_colour; }
	
	public int[] getColourData() {
		int[] colourData = new int[3];
		colourData[0] = m_colour.getRed();
		colourData[1] = m_colour.getGreen();
		colourData[2] = m_colour.getBlue();
		return colourData;
	}
	
	public boolean isPlain() { return !m_bold && !m_italic; }
	
	public boolean isBold() { return m_bold; }
	
	public boolean isItalic() { return m_italic; }
	
	public boolean isBoldItalic() { return m_bold && m_italic; }
	
	public int getStyle() { return Font.PLAIN + (m_bold ? Font.BOLD : 0) + (m_italic ? Font.ITALIC : 0); }
	
	public void setFont(Font font) {
		if(font == null) {
			m_face = Globals.DEFAULT_FONT_FACE;
			m_size = Globals.DEFAULT_FONT_SIZE;
			m_bold = false;
			m_italic = false;
			initializeFont();
		}
		else {
			m_face = font.getFontName();
			m_size = font.getSize();
			m_bold = font.isBold();
			m_italic = font.isItalic();
			m_font = font;
		}
	}
	
	public void setSize(int size) {
		if(size >= 2) { m_size = size; } 
		initializeFont();
	}
	
	public void setColour(Color colour) {
		if(colour != null) { m_colour = colour; }
	}
	
	public void setColour(int[] colourData) {
		if(colourData == null || colourData.length != 3) { return; }
		for(int i=0;i<3;i++) {
			if(colourData[i] < 0 || colourData[i] > 255) {
				return;
			}
		}
		m_colour = new Color(colourData[0], colourData[1], colourData[2]);
	}
	
	public void setBold(boolean bold) {
		m_bold = bold;
		initializeFont();
	}
	
	public void setItalic(boolean italic) {
		m_italic = italic;
		initializeFont();
	}
	
	public void setBoldItalic(boolean boldItalic) {
		m_bold = boldItalic;
		m_italic = boldItalic;
		initializeFont();
	}
	
	public void setPlain(boolean plain) {
		if(plain) {
			m_bold = false;
			m_italic = false;
			initializeFont();
		}
	}
	
	public void setStyle(int style) {
		if(style < 0 || style > 2) { return; }
		if(style == Font.PLAIN) {
			setPlain(true);
		}
		else if(style == Font.BOLD) {
			setBold(true);
		}
		else if(style == Font.ITALIC) {
			setItalic(true);
		}
		else if(style == Font.BOLD + Font.ITALIC) {
			setBoldItalic(true);
		}
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_face, Globals.MAX_FONTFACE_LENGTH);
		checksum += ByteStream.getChecksum(m_size);
		checksum += ByteStream.getChecksum(m_bold);
		checksum += ByteStream.getChecksum(m_italic);
		checksum += ByteStream.getChecksum(m_colour.getRed());
		checksum += ByteStream.getChecksum(m_colour.getGreen());
		checksum += ByteStream.getChecksum(m_colour.getBlue());
		return checksum;
	}
	
	public static FontStyle readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		String face = byteStream.nextString(Globals.MAX_FONTFACE_LENGTH);
		int size = byteStream.nextInteger();
		boolean bold = byteStream.nextBoolean();
		boolean italic = byteStream.nextBoolean();
		int[] colourData = new int[3];
		colourData[0] = byteStream.nextInteger();
		colourData[1] = byteStream.nextInteger();
		colourData[2] = byteStream.nextInteger();
		Color colour = new Color(colourData[0], colourData[1], colourData[2]);
		
		return new FontStyle(face, size, bold, italic, colour);
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		byteStream.addStringFixedLength(m_face, Globals.MAX_FONTFACE_LENGTH);
		byteStream.addInteger(m_size);
		byteStream.addBoolean(m_bold);
		byteStream.addBoolean(m_italic);
		byteStream.addInteger(m_colour.getRed());
		byteStream.addInteger(m_colour.getGreen());
		byteStream.addInteger(m_colour.getBlue());
	}
	
}
