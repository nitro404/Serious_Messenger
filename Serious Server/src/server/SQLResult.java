package server;

import java.util.Vector;
import java.sql.*;

public class SQLResult {
	
	private SQLResultRow m_headers;
	private Vector<SQLResultRow> m_data;
	
	public SQLResult() {
		m_headers = new SQLResultRow();
		m_data = new Vector<SQLResultRow>();
	}
	
	public SQLResult(ResultSet rs) {
		m_headers = new SQLResultRow();
		m_data = new Vector<SQLResultRow>();
		
		if(rs == null) { return; }
		
		try {
			ResultSetMetaData meta = rs.getMetaData();
			
			if(meta == null) { return; }
			
			for(int i=1;i<=meta.getColumnCount();i++) {
				m_headers.add(meta.getColumnName(i));
			}
			
			while(rs.next()) {
				SQLResultRow rowData = new SQLResultRow();
				for(int i=1;i<=meta.getColumnCount();i++) {
					rowData.add(rs.getString(i));
				}
				m_data.add(rowData);
			}
		}
		catch(SQLException e) { }
	}
	
	public SQLResultRow getHeaders() {
		return m_headers;
	}
	
	public String getHeader(int index) {
		if(index < 0 || index >= m_headers.size()) { return null; }
		return m_headers.elementAt(index);
	}
	
	public Vector<SQLResultRow> getData() {
		return m_data;
	}
	
	public SQLResultRow getRow(int row) {
		if(row < 0 || row >= m_data.size()) { return null; }
		return m_data.elementAt(row);
	}
	
	public String getElement(int row, int index) {
		if(row < 0 || row >= m_data.size()) { return null; }
		if(index < 0 || index >= m_data.elementAt(row).size()) { return null; }
		return m_data.elementAt(row).elementAt(index);
	}
	
	public String toString() {
		String s = m_headers.toString();
		for(int i=0;i<m_data.size();i++) {
			s += "\n" + m_data.elementAt(i).toString();
		}
		return s;
	}
	
}
