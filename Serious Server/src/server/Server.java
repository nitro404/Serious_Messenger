package server;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import logger.*;

public class Server extends JFrame {
	
	private int m_defaultPortNumber = 25500;
	private UserDBMS m_dbms;
	private Logger m_logger;
    
    private JMenuBar serverMenuBar;
    
    private JMenu serverFileMenu;
    private JMenuItem serverFileConnectMenuItem;
    private JMenuItem serverFileDisconnectMenuItem;
    private JMenuItem serverFileExitMenuItem;
    
    private JMenu serverSettingsMenu;
    private JCheckBoxMenuItem serverSettingsAutoconnectMenuItem;
    private JCheckBoxMenuItem serverSettingsAutoreconnectMenuItem;
    private JMenuItem serverSettingsSQLAddressMenuItem;
    private JMenuItem serverSettingsSQLPortMenuItem;
    
    private JMenu serverDatabaseMenu;
    private JMenuItem serverDatabaseCreateMenuItem;
    private JMenuItem serverDatabaseDeleteMenuItem;
    private JMenuItem serverDatabaseResetMenuItem;
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
    private DefaultTableModel userDataTableModel;
    
    private JPanel userProfilePanel;
    private JScrollPane userProfileScrollPane;
    private JTable userProfileTable;
    private DefaultTableModel userProfileTableModel;
    
    private JPanel userContactPanel;
    private JScrollPane userContactScrollPane;
    private JTable userContactTable;
    private DefaultTableModel userContactTableModel;
    
    private JPanel userGroupPanel;
    private JScrollPane userGroupScrollPane;
    private JTable userGroupTable;
    private DefaultTableModel userGroupTableModel;
    
	private static final long serialVersionUID = 1L;
	
	public Server() {
		m_dbms = new UserDBMS();
		m_logger = new Logger();
		initComponents();
	}
	
	public void initialize() {
		initialize(m_defaultPortNumber);
	}
	
	public void initialize(int portNumber) {
		m_logger.initialize(this);
		m_dbms.initialize(m_logger);
		m_dbms.connect();
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
        
        serverSettingsMenu = new JMenu();
        serverSettingsAutoconnectMenuItem = new JCheckBoxMenuItem();
        serverSettingsAutoreconnectMenuItem = new JCheckBoxMenuItem();
        serverSettingsSQLAddressMenuItem = new JMenuItem();
        serverSettingsSQLPortMenuItem = new JMenuItem();
        
        serverDatabaseMenu = new JMenu();
        serverDatabaseCreateMenuItem = new JMenuItem();
        serverDatabaseDeleteMenuItem = new JMenuItem();
        serverDatabaseResetMenuItem = new JMenuItem();
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
        userDataTableModel = new DefaultTableModel(
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
        userProfileTableModel = new DefaultTableModel(
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
        userContactTableModel = new DefaultTableModel(
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
        userGroupTableModel = new DefaultTableModel(
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

        serverSettingsMenu.setText("Settings");

        serverSettingsAutoconnectMenuItem.setSelected(true);
        serverSettingsAutoconnectMenuItem.setText("Autoconnect to SQL Database on Startup");
        serverSettingsAutoconnectMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverSettingsAutoconnectMenuItemActionPerformed(evt);
            }
        });
        serverSettingsMenu.add(serverSettingsAutoconnectMenuItem);

        serverSettingsAutoreconnectMenuItem.setSelected(true);
        serverSettingsAutoreconnectMenuItem.setText("Auto-reconnect to SQL Database ");
        serverSettingsAutoreconnectMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverSettingsAutoreconnectMenuItemActionPerformed(evt);
            }
        });
        serverSettingsMenu.add(serverSettingsAutoreconnectMenuItem);

        serverSettingsSQLAddressMenuItem.setText("Change SQL Database Address");
        serverSettingsSQLAddressMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverSettingsSQLAddressMenuItemActionPerformed(evt);
            }
        });
        serverSettingsMenu.add(serverSettingsSQLAddressMenuItem);

        serverSettingsSQLPortMenuItem.setText("Change SQL Database Port");
        serverSettingsSQLPortMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverSettingsSQLPortMenuItemActionPerformed(evt);
            }
        });
        serverSettingsMenu.add(serverSettingsSQLPortMenuItem);

        serverMenuBar.add(serverSettingsMenu);

        serverDatabaseMenu.setText("Database");

        serverDatabaseCreateMenuItem.setText("Create");
        serverDatabaseCreateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseCreateMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseCreateMenuItem);

        serverDatabaseDeleteMenuItem.setText("Delete");
        serverDatabaseDeleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseDeleteMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseDeleteMenuItem);

        serverDatabaseResetMenuItem.setText("Reset");
        serverDatabaseResetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverDatabaseResetMenuItemActionPerformed(evt);
            }
        });
        serverDatabaseMenu.add(serverDatabaseResetMenuItem);

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
		systemLogTableModel.addRow(m_logger.getLastSystemLogEntryAsArray());
	}
	
	public void addCommandLogEntry(CommandLogEntry e) {
		if(e == null) { return; }
		commandLogTableModel.addRow(m_logger.getLastCommandLogEntryAsArray());
	}
	
    private void serverFileConnectMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.connect();
    }
    
    private void serverFileDisconnectMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.disconnect();
    }
    
    private void serverFileExitMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.disconnect();
        System.exit(0);
    }

    private void serverSettingsAutoconnectMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    }

    private void serverSettingsAutoreconnectMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    }

    private void serverSettingsSQLAddressMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    }

    private void serverSettingsSQLPortMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    }

    private void serverDatabaseCreateMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.createDatabase();
    }

    private void serverDatabaseDeleteMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.dropDatabase();
    }

    private void serverDatabaseResetMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.resetDatabase();
    }
    
    private void serverDatabaseExecuteQueryMenuItemActionPerformed(ActionEvent evt) {
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    	//String query = JOptionPane.showInputDialog(null, "Query Database", "What do you wish to query?", JOptionPane.QUESTION_MESSAGE);
    	//String[][] result = m_dbms.executeQuery(query);
    	//TODO: finish me
    }
    
    private void serverDatabaseExecuteUpdateMenuItemActionPerformed(ActionEvent evt) {
    	JOptionPane.showMessageDialog(null, "Not yet implemented!", "Under Construction", JOptionPane.WARNING_MESSAGE);
    	//TODO: finish me
    }

    private void serverTablesCreateMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.createTables();
    }

    private void serverTablesDeleteMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.dropTables();
    }

    private void serverTablesResetMenuItemActionPerformed(ActionEvent evt) {
    	m_dbms.resetTables();
    }
	
}
