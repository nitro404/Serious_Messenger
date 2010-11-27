package server;

import java.util.Vector;

public class SQLResultRow {
	
	private Vector<String> m_data;
	
	public SQLResultRow() {
		m_data = new Vector<String>();
	}
	
	public SQLResultRow(String[] data) {
		m_data = new Vector<String>();
		add(data);
	}
	
	public SQLResultRow(Vector<String> data) {
		m_data = new Vector<String>();
		add(data);
	}
	
	public int size() { return m_data.size(); }
	
	public String elementAt(int index) {
		if(index < 0 || index >= m_data.size()) { return null; }
		return m_data.elementAt(index);
	}
	
	public void add(String data) {
		if(data == null) { return; }
		m_data.add(data);
	}
	
	public void add(String[] data) {
		if(data != null) {
			for(int i=0;i<data.length;i++) {
				m_data.add(data[i]);
			}
		}
	}
	
	public void add(Vector<String> data) {
		if(data != null) {
			for(int i=0;i<data.size();i++) {
				m_data.add(data.elementAt(i));
			}
		}
	}
	
	public String toString() {
		String s = "";
		for(int i=0;i<m_data.size();i++) {
			s += m_data.elementAt(i);
			if(i < m_data.size() - 1) {
				s += " | ";
			}
		}
		return s;
	}
	
}
