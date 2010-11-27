package server;

import javax.swing.*;

public class TableUpdateThread extends Thread {
	
	private UserDBMS m_dbms;
	
	private JTable userDataTable;
	private JTable userProfileTable;
	private JTable userContactTable;
	private JTable userGroupTable;
	
	public void initialize(UserDBMS dbms, JTable userDataTable, JTable userProfileTable, JTable userContactTable, JTable userGroupTable) {
		m_dbms = dbms;
		
		this.userDataTable = userDataTable;
		this.userProfileTable = userProfileTable;
		this.userContactTable = userContactTable;
		this.userGroupTable = userGroupTable;
		
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public void setDBMS(UserDBMS dbms) {
		m_dbms = dbms;
	}
	
	public void run() {
		while(true) {
			if(m_dbms != null) {
				m_dbms.updateTable(UserDBMS.userDataTableName, userDataTable);
				m_dbms.updateTable(UserDBMS.userProfileTableName, userProfileTable);
				m_dbms.updateTable(UserDBMS.userContactTableName, userContactTable);
				m_dbms.updateTable(UserDBMS.userGroupTableName, userGroupTable);
			}
			
			try { sleep(5000); }
			catch (InterruptedException e) { }
		}
	}
	
}
