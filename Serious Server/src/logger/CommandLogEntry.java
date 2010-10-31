package logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommandLogEntry {
	
	private Calendar m_time;
	private String m_userName;
	private String m_text;
	
	final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d hh:mm:ss a");
	
	public CommandLogEntry(String userName, String text) {
		m_time = Calendar.getInstance();
		m_userName = userName;
		m_text = text;
	}
	
	public Calendar getTime() { return m_time; }
	
	public String getTimeString() { return dateFormat.format(m_time.getTime()); }
	
	public String getUserName() { return m_userName; }
	
	public String getText() { return m_text; }
	
}
