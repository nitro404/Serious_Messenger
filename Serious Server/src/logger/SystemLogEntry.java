package logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SystemLogEntry {
	
	private Calendar m_time;
	private int m_type;
	private String m_text;
	
	final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d hh:mm:ss a");
	
	public SystemLogEntry(int type, String text) {
		m_time = Calendar.getInstance();
		m_type = SystemLogEntryType.isValid(type) ? type : SystemLogEntryType.Information;
		m_text = text;
	}
	
	public Calendar getTime() { return m_time; }
	
	public String getTimeString() { return dateFormat.format(m_time.getTime()); }
	
	public int getType() { return m_type; }
	
	public String getTypeString() { return SystemLogEntryType.getString(m_type); }
	
	public String getText() { return m_text; }
	
}
