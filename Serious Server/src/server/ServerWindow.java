package server;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import shared.*;
import logger.*;

public class ServerWindow extends JFrame {
	
	private Server m_server;
	
	private TableUpdateThread m_tableUpdateThread;
	private MessageBoxSystem m_messageBoxSystem;
	
    private JMenuBar serverMenuBar;
    
    private JMenu serverFileMenu;
    private JMenuItem serverFileConnectMenuItem;
    private JMenuItem serverFileDisconnectMenuItem;
    private JMenuItem serverFileExitMenuItem;
    
    private JMenu serverDatabaseMenu;
    private JMenuItem serverDatabaseDeleteUserMenuItem;
    private JMenuItem serverDatabaseExecuteQueryMenuItem;
    private JMenuItem serverDatabaseExecuteUpdateMenuItem;
    
    private JMenu serverTablesMenu;
    private JMenuItem serverTablesCreateMenuItem;
    private JMenuItem serverTablesDeleteMenuItem;
    private JMenuItem serverTablesResetMenuItem;
    
    private JTabbedPane serverTabbedPane;
    
    private JPanel systemLogPanel;
    private JScrollPane systemLogScrollPane;
    private JTable systemLogTable;
    private DefaultTableModel systemLogTableModel;
    
    private JPanel commandLogPanel;
    private JScrollPane commandLogScrollPane;
    private JTable commandLogTable;
    private DefaultTableModel commandLogTableModel;
    
    private JPanel userDataPanel;
    private JScrollPane userDataScrollPane;
    private JTable userDataTable;
    
    private JPanel userProfilePanel;
    private JScrollPane userProfileScrollPane;
    private JTable userProfileTable;
    
    private JPanel userContactPanel;
    private JScrollPane userContactScrollPane;
    private JTable userContactTable;
    
    private JPanel userGroupPanel;
    private JScrollPane userGroupScrollPane;
    private JTable userGroupTable;
    
	private static final long serialVersionUID = 1L;
	
	public ServerWindow() {
		m_server = new Server();
		m_messageBoxSystem = new MessageBoxSystem();
		m_tableUpdateThread = new TableUpdateThread();
		initComponents();
	}
	
	public void initialize() {
		initialize(Globals.DEFAULT_PORT);
	}
	
	public void initialize(int port) {
		m_server.initialize(port, this);
		m_messageBoxSystem.initialize();
		m_tableUpdateThread.initialize(m_server.getDBMS(), userDataTable, userProfileTable, userContactTable, userGroupTable);
		setVisible(true);
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serious Messenger Server");
        setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
        setMinimumSize(new Dimension(640, 480));
        setName("ServerWindow");
        
		serverMenuBar = new JMenuBar();
		
		serverFileMenu = new JMenu();
        serverFileConnectMenuItem = new JMenuItem();
        serverFileDisconnectMenuItem = new JMenuItem();
        serverFileExitMenuItem = new JMenuItem();
        
        serverDatabaseMenu = new JMenu();
        serverDatabaseDeleteUserMenuItem = new JMenuItem();
        serverDatabaseExecuteQueryMenuItem = new JMenuItem();
        serverDatabaseExecuteUpdateMenuItem = new JMenuItem();
        
        serverTablesMenu = new JMenu();
        serverTablesCreateMenuItem = new JMenuItem();
        serverTablesDeleteMenuItem = new JMenuItem();
        serverTablesResetMenuItem = new JMenuItem();
		
        serverTabbedPane = new JTabbedPane();
        
        systemLogPanel = new JPanel();
        systemLogScrollPane = new JScrollPane();
        systemLogTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        commandLogPanel = new JPanel();
        commandLogScrollPane = new JScrollPane();
        commandLogTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        userDataPanel = new JPanel();
        userDataScrollPane = new JScrollPane();
        userDataTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        userProfilePanel = new JPanel();
        userProfileScrollPane = new JScrollPane();
        userProfileTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        userContactPanel = new JPanel();
        userContactScrollPane = new JScrollPane();
        userContactTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        userGroupPanel = new JPanel();
        userGroupScrollPane = new JScrollPane();
        userGroupTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
        		return false;
        	}
        };
        
        // System Log Table =======================================================================
        systemLogTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"Time", "Type", "Message"
        	}
        );
        
        systemLogTable.setModel(systemLogTableModel);
        systemLogScrollPane.setViewportView(systemLogTable);

        GroupLayout commandLogPanelLayout = new GroupLayout(systemLogPanel);
        systemLogPanel.setLayout(commandLogPanelLayout);
        commandLogPanelLayout.setHorizontalGroup(
            commandLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(systemLogScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        commandLogPanelLayout.setVerticalGroup(
            commandLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(systemLogScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("System Log", systemLogPanel);

        // Command Log Table ======================================================================
        commandLogTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"Time", "UserName", "Command"
        	}
        );
        
        commandLogTable.setModel(commandLogTableModel);
        commandLogScrollPane.setViewportView(commandLogTable);

        GroupLayout errorLogPanelLayout = new GroupLayout(commandLogPanel);
        commandLogPanel.setLayout(errorLogPanelLayout);
        errorLogPanelLayout.setHorizontalGroup(
            errorLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(commandLogScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        errorLogPanelLayout.setVerticalGroup(
            errorLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(commandLogScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("Command Log", commandLogPanel);
        
        // User Data Table ========================================================================
        DefaultTableModel userDataTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"UserName", "Password", "IPAddress", "LastLogin", "JoinDate"
        	}
        );
        
        userDataTable.setModel(userDataTableModel);
        userDataScrollPane.setViewportView(userDataTable);

        GroupLayout userDataPanelLayout = new GroupLayout(userDataPanel);
        userDataPanel.setLayout(userDataPanelLayout);
        userDataPanelLayout.setHorizontalGroup(
            userDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userDataScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        userDataPanelLayout.setVerticalGroup(
            userDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userDataScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("User Data", userDataPanel);
        
        // User Profile Table =====================================================================
        DefaultTableModel userProfileTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"UserName", "FirstName", "MiddleName", "LastName", "Gender", "BirthDate", "Email", "HomePhone", "MobilePhone", "WorkPhone", "Country", "StateProv", "ZipPostal"
        	}
        );
        
        userProfileTable.setModel(userProfileTableModel);
        userProfileScrollPane.setViewportView(userProfileTable);

        GroupLayout userProfilePanelLayout = new GroupLayout(userProfilePanel);
        userProfilePanel.setLayout(userProfilePanelLayout);
        userProfilePanelLayout.setHorizontalGroup(
            userProfilePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userProfileScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        userProfilePanelLayout.setVerticalGroup(
            userProfilePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userProfileScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("User Profile", userProfilePanel);
        
        // User Contact Table =====================================================================
        DefaultTableModel userContactTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"UserName", "Contact", "GroupName", "Blocked"
        	}
        );
        
        userContactTable.setModel(userContactTableModel);
        userContactScrollPane.setViewportView(userContactTable);

        GroupLayout userContactPanelLayout = new GroupLayout(userContactPanel);
        userContactPanel.setLayout(userContactPanelLayout);
        userContactPanelLayout.setHorizontalGroup(
            userContactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userContactScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        userContactPanelLayout.setVerticalGroup(
            userContactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userContactScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("User Contact", userContactPanel);
        
        // User Group Table =======================================================================
        DefaultTableModel userGroupTableModel = new DefaultTableModel(
        	null,
        	new String[] {
        		"UserName", "UserGroup"
        	}
        );
        
        userGroupTable.setModel(userGroupTableModel);
        userGroupScrollPane.setViewportView(userGroupTable);

        GroupLayout userGroupPanelLayout = new GroupLayout(userGroupPanel);
        userGroupPanel.setLayout(userGroupPanelLayout);
        userGroupPanelLayout.setHorizontalGroup(
            userGroupPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userGroupScrollPane, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        userGroupPanelLayout.setVerticalGroup(
            userGroupPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(userGroupScrollPane, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        serverTabbedPane.addTab("User Group", userGroupPanel);

        serverFileMenu.setText("File");

        serverFileConnectMenuItem.setText("Connect to SQL Database");
        serverFileConnectMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverFileConnectMenuItemActionPerformed(evt);
            }
        });
        serverFileMenu.add(serverFileConnectMenuItem);

        serverFileDisconnectMenuItem.setText("Disconnect from SQL Database");
        serverFileDisconnectMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverFileDisconnectMenuItemActionPerformed(evt);
            }
        });
        serverFileMenu.add(serverFileDisconnectMenuItem);

        serverFileExitMenuItem.setText("Exit");
        serverFileExitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverFileExitMenuItemActionPerformed(evt);
            }
        });
        serverFileMenu.add(serverFileExitMenuItem);

        serverMenuBar.add(serverFileMenu);

        serverDatabaseMenu.setText("Database");
        
        serverDatabaseDeleteUserMenuItem.setText("Delete User");
        serverDatabaseDeleteUserMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseDeleteUserMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseDeleteUserMenuItem);
        
        serverDatabaseExecuteQueryMenuItem.setText("Execute Query");
        serverDatabaseExecuteQueryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseExecuteQueryMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseExecuteQueryMenuItem);
        
        serverDatabaseExecuteUpdateMenuItem.setText("Execute Update");
        serverDatabaseExecuteUpdateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseExecuteUpdateMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseExecuteUpdateMenuItem);
        
        serverMenuBar.add(serverDatabaseMenu);

        serverTablesMenu.setText("Tables");

        serverTablesCreateMenuItem.setText("Create");
        serverTablesCreateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverTablesCreateMenuItemActionPerformed(evt);
            }
        });
        serverTablesMenu.add(serverTablesCreateMenuItem);

        serverTablesDeleteMenuItem.setText("Delete");
        serverTablesDeleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverTablesDeleteMenuItemActionPerformed(evt);
            }
        });
        serverTablesMenu.add(serverTablesDeleteMenuItem);

        serverTablesResetMenuItem.setText("Reset");
        serverTablesResetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverTablesResetMenuItemActionPerformed(evt);
            }
        });
        serverTablesMenu.add(serverTablesResetMenuItem);

        serverMenuBar.add(serverTablesMenu);

        setJMenuBar(serverMenuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(serverTabbedPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(serverTabbedPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );

        pack();
    }
	
	public void addSystemLogEntry(SystemLogEntry e) {
		if(e == null) { return; }
		systemLogTableModel.addRow(m_server.getLastSystemLogEntryAsArray());
	}
	
	public void addCommandLogEntry(CommandLogEntry e) {
		if(e == null) { return; }
		commandLogTableModel.addRow(m_server.getLastCommandLogEntryAsArray());
	}
	
    private void serverFileConnectMenuItemActionPerformed(ActionEvent evt) {
    	if(!m_server.databaseConnnected()) {
    		m_server.databaseConnect();
    		m_tableUpdateThread.setDBMS(m_server.getDBMS());
    	}
    	else {
    		m_messageBoxSystem.show(null, "Already connected to database.", "Already Connected", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    private void serverFileDisconnectMenuItemActionPerformed(ActionEvent evt) {
    	if(m_server.databaseConnnected()) {
    		m_server.databaseDisconnect();
    		m_tableUpdateThread.setDBMS(null);
    	}
    	else {
    		m_messageBoxSystem.show(null, "Not connected to database.", "Not Connected", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    private void serverFileExitMenuItemActionPerformed(ActionEvent evt) {
    	m_server.databaseDisconnect();
        System.exit(0);
    }
    
    private void serverDatabaseDeleteUserMenuItemActionPerformed(ActionEvent evt) {
    	String userName = JOptionPane.showInputDialog(null, "Username:", "Delete User", JOptionPane.QUESTION_MESSAGE);
    	
    	if(userName == null) { return; }
    	
    	boolean userDeleted = m_server.deleteUser(userName);
    	
    	if(userDeleted) {
    		m_messageBoxSystem.show(null, "User deleted successfully!", "User Deleted", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		m_messageBoxSystem.show(null, "Unable to delete user.", "Unable to Delete User", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    private void serverDatabaseExecuteQueryMenuItemActionPerformed(ActionEvent evt) {
    	String query = JOptionPane.showInputDialog(null, "What do you wish to query?", "Query Database", JOptionPane.QUESTION_MESSAGE);
    	
    	if(query == null) { return; }
    	
    	SQLResult result = m_server.executeQuery(query);
    	
    	m_messageBoxSystem.show(null, result, "Query Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void serverDatabaseExecuteUpdateMenuItemActionPerformed(ActionEvent evt) {
    	String query = JOptionPane.showInputDialog(null, "Update Database", "What do you wish to update?", JOptionPane.QUESTION_MESSAGE);
    	
    	if(query == null) { return; }
    	
    	int updated = m_server.executeUpdate(query);
    	
    	m_messageBoxSystem.show(null, "Update result: " + updated, "Update Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private void serverTablesCreateMenuItemActionPerformed(ActionEvent evt) {
    	int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to create all tables?", "Create Tables", JOptionPane.YES_NO_CANCEL_OPTION);
    	
    	if(result == JOptionPane.YES_OPTION) {
    		m_server.createTables();
    	}
    }

    private void serverTablesDeleteMenuItemActionPerformed(ActionEvent evt) {
    	int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete all tables?", "Delete Tables", JOptionPane.YES_NO_CANCEL_OPTION);
    	
    	if(result == JOptionPane.YES_OPTION) {
    		m_server.dropTables();
    	}
    }

    private void serverTablesResetMenuItemActionPerformed(ActionEvent evt) {
    	int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to reset (clear) all tables?", "Reset Tables", JOptionPane.YES_NO_CANCEL_OPTION);
    	
    	if(result == JOptionPane.YES_OPTION) {
    		m_server.resetTables();
    	}
    }
	
}
