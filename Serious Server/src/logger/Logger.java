package logger;

import java.util.Vector;
import server.*;

public class Logger {
	
	private Vector<SystemLogEntry> m_systemLog;
	private Vector<CommandLogEntry> m_commandLog;
	
	private ServerWindow m_serverWindow;
	
	public Logger() {
		m_systemLog = new Vector<SystemLogEntry>();
		m_commandLog = new Vector<CommandLogEntry>();
	}
	
	public void initialize(ServerWindow serverWindow) {
		m_serverWindow = serverWindow;
	}
	
	public int numberOfSystemLogEntries() { return m_systemLog.size(); }
	
	public int numberOfCommandLogEntries() { return m_commandLog.size(); }
	
	public SystemLogEntry getSystemLogEntry(int index) {
		if(index < 0 || index >= m_systemLog.size()) { return null; }
		return m_systemLog.elementAt(index);
	}
	
	public CommandLogEntry getCommandLogEntry(int index) {
		if(index < 0 || index >= m_commandLog.size()) { return null; }
		return m_commandLog.elementAt(index);
	}
	
	public Object[] getLastSystemLogEntryAsArray() {
		SystemLogEntry e = m_systemLog.elementAt(m_systemLog.size() - 1);
		return new Object[] { e.getTimeString(), e.getTypeString(), e.getText() };
	}
	
	public Object[] getLastCommandLogEntryAsArray() {
		CommandLogEntry e = m_commandLog.elementAt(m_commandLog.size() - 1);
		return new Object[] { e.getTimeString(), e.getUserName(), e.getText() };
	}
	
	public void addInfo(String text) {
		SystemLogEntry e = new SystemLogEntry(SystemLogEntryType.Information, text);
		m_systemLog.add(e);
		if(m_serverWindow != null) { m_serverWindow.addSystemLogEntry(e); }
	}
	
	public void addWarning(String text) {
		SystemLogEntry e = new SystemLogEntry(SystemLogEntryType.Warning, text);
		m_systemLog.add(e);
		if(m_serverWindow != null) { m_serverWindow.addSystemLogEntry(e); }
	}
	
	public void addError(String text) {
		SystemLogEntry e = new SystemLogEntry(SystemLogEntryType.Error, text);
		m_systemLog.add(e);
		if(m_serverWindow != null) { m_serverWindow.addSystemLogEntry(e); }
	}
	
	public void addCommand(String userName, String text) {
		CommandLogEntry e = new CommandLogEntry(userName, text);
		m_commandLog.add(e);
		m_serverWindow.addCommandLogEntry(e);
	}
	
}
