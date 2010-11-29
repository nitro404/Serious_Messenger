package signal;

import java.util.Vector;
import java.io.*;
import shared.*;

public class ContactListSignal extends Signal {
	
	private Vector<ClientData> m_contacts;
	
	final public static int LENGTH = (Integer.SIZE +
									  Long.SIZE) / 8;
	
	private ContactListSignal() {
		super(SignalType.ContactList);
		m_contacts = new Vector<ClientData>();
	}
	
	public ContactListSignal(Vector<ClientData> contacts) {
		super(SignalType.ContactList);
		m_contacts = (contacts == null) ? new Vector<ClientData>() : contacts;
	}
	
	public int numberOfContacts() {
		return m_contacts.size();
	}
	
	public ClientData getContact(int index) {
		if(index < 0 || index >= m_contacts.size()) { return null; }
		return m_contacts.elementAt(index);
	}
	
	public Vector<ClientData> getContacts() {
		return m_contacts;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_contacts.size());
		for(int i=0;i<m_contacts.size();i++) {
			checksum += m_contacts.elementAt(i).checksum();
		}
		return checksum;
	}
	
	public static ContactListSignal readFrom(ByteStream byteStream, DataInputStream in) {
		if(byteStream == null || in == null) { return null; }
		
		ContactListSignal s2 = new ContactListSignal();
		
		int numberOfContacts = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		
		for(int i=0;i<numberOfContacts;i++) {
			ClientData c = ClientData.readFrom(ByteStream.readFrom(in, ClientData.LENGTH));
			if(c != null) {
				s2.m_contacts.add(c);
			}
		}
		
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		
		byteStream.addInteger(m_contacts.size());
		byteStream.addLong(checksum());
		
		for(int i=0;i<m_contacts.size();i++) {
			m_contacts.elementAt(i).writeTo(byteStream);
		}
	}
	
}
