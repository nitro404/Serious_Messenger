package signal;

import shared.*;

public class ContactAddedSignal extends Signal {
	
	private UserNetworkData m_data;
	private boolean m_added;
	
	final public static int LENGTH = UserNetworkData.LENGTH +
									 ((Byte.SIZE +
									  Long.SIZE) / 8);
	
	private ContactAddedSignal() {
		super(SignalType.ContactAdded);
	}
	
	public ContactAddedSignal(UserNetworkData data, boolean added) {
		super(SignalType.ContactAdded);
		m_data = data;
		m_added = added;
	}
	
	public UserNetworkData getData() {
		return m_data;
	}
	
	public boolean getAdded() {
		return m_added;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += m_data.checksum();
		checksum += ByteStream.getChecksum(m_added);
		return checksum;
	}
	
	public static ContactAddedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ContactAddedSignal s2 = new ContactAddedSignal();
		
		s2.m_data = UserNetworkData.readFrom(byteStream);
		s2.m_added = byteStream.nextBoolean();
		long checksum = byteStream.nextLong();
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		m_data.writeTo(byteStream);
		byteStream.addBoolean(m_added);
		byteStream.addLong(checksum());
	}
	
}
