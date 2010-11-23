package shared;

import java.io.*;

public class ByteStream {
	
	private byte[] m_data;
	private int m_length = 0;
	
	private int m_position = 0;
	
	private int m_initialCapacity;
	final public static int DEFAULT_CAPACITY = 64;
	final public static int CAPACITY_INCREMENT = 32;
	
	public ByteStream() {
		m_data = new byte[DEFAULT_CAPACITY];
		m_initialCapacity = DEFAULT_CAPACITY;
	}
	
	public ByteStream(int initialCapacity) {
		m_initialCapacity = (initialCapacity > 0) ? initialCapacity : DEFAULT_CAPACITY;
		m_data = new byte[m_initialCapacity];
	}
	
	public int size() {
		return m_length;
	}
	
	public byte[] getContents() {
		return m_data;
	}
	
	public void clear() {
		m_data = new byte[m_initialCapacity];
		m_length = 0;
	}
	
	public void resetPosition() {
		m_position = 0;
	}
	
	public int getPosition() {
		return m_position;
	}
	
	public void setPosition(int offset) {
		if(offset < 0 || offset >= m_length) { return; }
		m_position = offset;
	}
	
	public void addBoolean(boolean b) {
		addByte((byte) ((b) ? 1 : 0));
	}
	
	public void addByte(byte b) {
		m_data[m_length] = b;
		m_length++;
		checkCapacity();
	}
	
	public void addShort(short s) {
		addByte((byte) (s >> 8));
		addByte((byte) (s));
	}
	
	public void addInteger(int i) {
		addByte((byte) (i >> 24));
		addByte((byte) (i >> 16));
		addByte((byte) (i >> 8));
		addByte((byte) (i));
	}
	
	public void addLong(long l) {
		addByte((byte) (l >> 56));
		addByte((byte) (l >> 48));
		addByte((byte) (l >> 40));
		addByte((byte) (l >> 32));
		addByte((byte) (l >> 24));
		addByte((byte) (l >> 16));
		addByte((byte) (l >> 8));
		addByte((byte) (l));
	}
	
	public void addFloat(float f) {
		addInteger(Float.floatToIntBits(f));
	}
	
	public void addDouble(double d) {
		addLong(Double.doubleToLongBits(d));
	}
	
	public void addChar(char c) {
		addByte((byte) (c >> 8));
		addByte((byte) (c));
	}
	
	public void addString(String s) {
		if(s == null) { return; }
		for(int i=0;i<s.length();i++) {
			addChar(s.charAt(i));
		}
	}
	
	public void addStringFixedLength(String s, int length) {
		if(s == null) { return; }
		int tempLength = (s.length() > length) ? length : s.length();
		for(int i=0;i<tempLength;i++) {
			addChar(s.charAt(i));
		}
		for(int i=s.length();i<length;i++) {
			addChar('\0');
		}
	}
	
	public boolean getBoolean(int offset) {
		return (getByte(offset) != 0) ? true : false;
	}
	
	public byte getByte(int offset) {
		return (offset < 0 || offset >= m_data.length) ? -1 : m_data[offset];
	}
	
	public short getShort(int offset) {
		if(offset < 0 || offset + 1 >= m_data.length) { return -1; }
		return (short) (m_data[offset] << 8
					 | (m_data[offset + 1] & 0xff));
	}
	
	public int getInteger(int offset) {
		if(offset < 0 || offset + 3 >= m_data.length) { return -1; }
		return (int) (m_data[offset] << 24
				   | (m_data[offset + 1] & 0xff) << 16
				   | (m_data[offset + 2] & 0xff) << 8
				   | (m_data[offset + 3] & 0xff));
	}
	
	public long getLong(int offset) {
		if(offset < 0 || offset + 7 >= m_data.length) { return -1; }
		long l = 0;
		for(int i=0;i<8;++i) {
			l |= ((long) m_data[i + offset] & 0xff) << ((8-i-1) << 3);
		}
		return l;
	}
	
	public float getFloat(int offset) {
		if(offset < 0 || offset + 3 >= m_data.length) { return -1; }
		return Float.intBitsToFloat(getInteger(offset));
	}
	
	public double getDouble(int offset) {
		if(offset < 0 || offset + 7 >= m_data.length) { return -1; }
		return Double.longBitsToDouble(getLong(offset));
	}
	
	public char getChar(int offset) {
		if(offset < 0 || offset + 1 >= m_data.length) { return '\0'; }
		return (char) (m_data[offset] << 8
					| (m_data[offset + 1] & 0xff));
	}
	
	public String getString(int offset, int length) {
		if(length < 0 || offset < 0 || offset + ((length - 1) * 2) >= m_data.length) { return null; }
		String s = "";
		for(int i=0;i<length;i++) {
			char c = getChar(offset + (i * 2));
			if(c == '\0') { break; }
			s += c;
		}
		return s;
	}
	
	public boolean nextBoolean() {
		boolean b = getBoolean(m_position);
		m_position++;
		return b;
	}
	
	public byte nextByte() {
		byte b = getByte(m_position);
		m_position++;
		return b;
	}
	
	public short nextShort() {
		short s = getShort(m_position);
		m_position += 2;
		return s;
	}
	
	public int nextInteger() {
		int i = getInteger(m_position);
		m_position += 4;
		return i;
	}
	
	public long nextLong() {
		long l = getLong(m_position);
		m_position += 8;
		return l;
	}
	
	public double nextFloat() {
		float f = getFloat(m_position);
		m_position += 4;
		return f;
	}
	
	public double nextDouble() {
		double d = getDouble(m_position);
		m_position += 8;
		return d;
	}
	
	public char nextChar() {
		char c = getChar(m_position);
		m_position += 2;
		return c;
	}
	
	public String nextString(int length) {
		String s = getString(m_position, length);
		if(length > 0) { m_position += 2 * length; }
		return s;
	}
	
	public static long getChecksum(boolean b) {
		return (b) ? 1 : 0;
	}
	
	public static long getChecksum(byte b) {
		long checksum = 0;
		
		for(int i=0;i<8;i++) {
			if((b & (int) Math.pow(2, i)) > 0) {
				checksum++;
			}
		}
		
		return checksum;
	}
	
	public static long getChecksum(short s) {
		long checksum = 0;
		checksum += getChecksum((byte) (s >> 8));
		checksum += getChecksum((byte) (s));
		return checksum;
	}
	
	public static long getChecksum(int i) {
		long checksum = 0;
		checksum += getChecksum((byte) (i >> 24));
		checksum += getChecksum((byte) (i >> 16));
		checksum += getChecksum((byte) (i >> 8));
		checksum += getChecksum((byte) (i));
		return checksum;
	}
	
	public static long getChecksum(long l) {
		long checksum = 0;
		checksum += getChecksum((byte) (l >> 56));
		checksum += getChecksum((byte) (l >> 48));
		checksum += getChecksum((byte) (l >> 40));
		checksum += getChecksum((byte) (l >> 32));
		checksum += getChecksum((byte) (l >> 24));
		checksum += getChecksum((byte) (l >> 16));
		checksum += getChecksum((byte) (l >> 8));
		checksum += getChecksum((byte) (l));
		return checksum;
	}
	
	public static long getChecksum(float f) {
		return getChecksum(Float.floatToIntBits(f));
	}
	
	public static long getChecksum(double d) {
		return getChecksum(Double.doubleToLongBits(d));
	}
	
	public static long getChecksum(char c) {
		long checksum = 0;
		checksum += getChecksum((byte) (c >> 8));
		checksum += getChecksum((byte) (c));
		return checksum;
	}
	
	public static long getChecksum(String s) {
		if(s == null || s.length() == 0) { return 0; }
		long checksum = 0;
		for(int i=0;i<s.length();i++) {
			checksum += getChecksum(s.charAt(i));
		}
		return checksum;
	}
	
	public static long getChecksum(String s, int length) {
		if(s == null || s.length() == 0) { return 0; }
		long checksum = 0;
		int tempLength = (s.length() > length) ? length : s.length(); 
		for(int i=0;i<tempLength;i++) {
			checksum += getChecksum(s.charAt(i));
		}
		return checksum;
	}
	
	public long checksum() {
		long checksum = 0;
		for(int i=0;i<m_length;i++) {
			checksum += getChecksum(m_data[i]);
		}
		return checksum;
	}
	
	private void checkCapacity() {
		if(m_length < m_data.length) { return; }
		byte[] temp = new byte[m_data.length + CAPACITY_INCREMENT];
		for(int i=0;i<m_data.length;i++) {
			temp[i] = m_data[i];
		}
		m_data = temp;
	}
	
	public static ByteStream readFrom(DataInputStream in, int length) {
		if(in == null || length < 1) { return null; }
		ByteStream bs = new ByteStream(length);
		try {
			if(in.available() <= 0) { return null; }
			in.read(bs.getContents(), 0, length);
		}
		catch(IOException e) {
			return null;
		}
		return bs;
	}
	
	public boolean writeTo(DataOutputStream out) {
		if(out == null) { return false; }
		try {
			out.write(m_data, 0, m_length);
			return true;
		}
		catch(IOException e) { }
		return false;
	}
	
}
