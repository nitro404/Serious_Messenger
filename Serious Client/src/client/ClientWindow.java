package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame implements ActionListener {
	
	private Client m_client;
	
	private JButton announceButton;
	private JLabel contactDisplayPicIconLabel;
	private JPanel contactListPanel;
	private JScrollPane contactListScrollPane;
	private JTextField contactNickNameTextField;
	private JPanel contactPanel;
	private JTextField contactPersonalMessageTextField;
	private JTextField contactStatusTextField;
	private JLabel displayPicIconLabel;
	
	private JTabbedPane groupsTabbedPane;
	private JTextField nickNameTextField;
	private JTextField personalMessageTextField;
	private JLabel searchLabel;
	private JTextField searchTextField;
	private JComboBox statusComboBox;
	private JPanel userInfoPanel;
	private JPanel utilityPanel;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileSignInMenuItem;
    private JMenuItem fileSignOutMenuItem;
    private JMenuItem fileCreateAccountMenuItem;
    private JMenuItem fileChangePasswordMenuItem;
	private JMenuItem fileExitMenuItem;
	
	private JMenu contactsMenu;
	private JMenuItem contactsAddContactMenuItem;
    private JMenuItem contactsDeleteContactMenuItem;
    private JMenuItem contactsBlockContactMenuItem;
    private JMenuItem contactsUnblockContactMenuItem;
    
	private JMenu settingsMenu;
	
	private JMenu helpMenu;
	private JMenuItem helpAboutMenuItem;
    
    private static final long serialVersionUID = 1L;
    
    public ClientWindow() {
    	m_client = new Client();
    	
    	initMenu();
        initComponents();
        
        setTitle("Serious Messenger");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public void initialize() {
    	setVisible(true);
    }
    
    private void initMenu() {
    	menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
    	fileSignInMenuItem = new JMenuItem("Sign In");
        fileSignOutMenuItem = new JMenuItem("Sign Out");
        fileCreateAccountMenuItem = new JMenuItem("Create Account");
        fileChangePasswordMenuItem = new JMenuItem("Change Password");
        fileExitMenuItem = new JMenuItem("Exit");
        
        contactsMenu = new JMenu("Contacts");
        contactsAddContactMenuItem = new JMenuItem("Add Contact");
        contactsDeleteContactMenuItem = new JMenuItem("Delete Contact");
        contactsBlockContactMenuItem = new JMenuItem("Block Contact");
        contactsUnblockContactMenuItem = new JMenuItem("Unblock Contact");
        
        settingsMenu = new JMenu("Settings");
        
        helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        fileSignInMenuItem.addActionListener(this);
        fileSignOutMenuItem.addActionListener(this);
        fileCreateAccountMenuItem.addActionListener(this);
        fileChangePasswordMenuItem.addActionListener(this);
        fileExitMenuItem.addActionListener(this);
        contactsAddContactMenuItem.addActionListener(this);
        contactsDeleteContactMenuItem.addActionListener(this);
        contactsBlockContactMenuItem.addActionListener(this);
        contactsUnblockContactMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        fileMenu.add(fileSignInMenuItem);
        fileMenu.add(fileSignOutMenuItem);
        fileMenu.add(fileCreateAccountMenuItem);
        fileMenu.add(fileChangePasswordMenuItem);
        fileMenu.add(fileExitMenuItem);
        
        contactsMenu.add(contactsAddContactMenuItem);
        contactsMenu.add(contactsDeleteContactMenuItem);
        contactsMenu.add(contactsBlockContactMenuItem);
        contactsMenu.add(contactsUnblockContactMenuItem);
        
        helpMenu.add(helpAboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(contactsMenu);
        //menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }
    
    private void initComponents() {
        userInfoPanel = new JPanel();
        nickNameTextField = new JTextField();
        displayPicIconLabel = new JLabel();
        personalMessageTextField = new JTextField();
        statusComboBox = new JComboBox();
        utilityPanel = new JPanel();
        announceButton = new JButton();
        searchTextField = new JTextField();
        searchLabel = new JLabel();
        groupsTabbedPane = new JTabbedPane();
        contactListScrollPane = new JScrollPane();
        contactListPanel = new JPanel();
        contactPanel = new JPanel();
        contactDisplayPicIconLabel = new JLabel();
        contactNickNameTextField = new JTextField();
        contactPersonalMessageTextField = new JTextField();
        contactStatusTextField = new JTextField();

        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));

        statusComboBox.setModel(new DefaultComboBoxModel(new String[] { "Online", "Busy", "Away", "Idle", "Offline" }));
        statusComboBox.setToolTipText("Use this drop down box to select your status");

        GroupLayout userInfoPanelLayout = new GroupLayout(userInfoPanel);
        userInfoPanel.setLayout(userInfoPanelLayout);
        userInfoPanelLayout.setHorizontalGroup(
            userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayPicIconLabel)
                .addGap(18, 18, 18)
                .addGroup(userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                    .addComponent(personalMessageTextField))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        userInfoPanelLayout.setVerticalGroup(
            userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(displayPicIconLabel)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addGroup(userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        announceButton.setText("Announce");

        searchTextField.setFont(new Font("Tahoma", 2, 11));
        searchTextField.setText("Type here to search through the contact list...");
        searchTextField.setToolTipText("Type here to search through the contact list...");

        searchLabel.setText("Search:");

        GroupLayout utilityPanelLayout = new GroupLayout(utilityPanel);
        utilityPanel.setLayout(utilityPanelLayout);
        utilityPanelLayout.setHorizontalGroup(
            utilityPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(utilityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTextField, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(announceButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        utilityPanelLayout.setVerticalGroup(
            utilityPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(utilityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(utilityPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLabel)
                    .addComponent(searchTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(announceButton))
                .addContainerGap())
        );

        groupsTabbedPane.setToolTipText("Group: All Contacts");
        groupsTabbedPane.setFont(new Font("Tahoma", 1, 11));

        contactListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contactListScrollPane.setToolTipText("Contact List");
        contactListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contactListPanel.setToolTipText("Group: All Contacts");
        contactListPanel.setPreferredSize(new Dimension(450, 268));

        contactNickNameTextField.setEditable(false);

        contactPersonalMessageTextField.setEditable(false);
        contactPersonalMessageTextField.setFont(new Font("Tahoma", 2, 11));

        contactStatusTextField.setEditable(false);

        GroupLayout contactPanelLayout = new GroupLayout(contactPanel);
        contactPanel.setLayout(contactPanelLayout);
        contactPanelLayout.setHorizontalGroup(
            contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contactDisplayPicIconLabel)
                .addGap(18, 18, 18)
                .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(contactPanelLayout.createSequentialGroup()
                        .addComponent(contactNickNameTextField, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(contactStatusTextField, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                    .addComponent(contactPersonalMessageTextField, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
                .addContainerGap())
        );
        contactPanelLayout.setVerticalGroup(
            contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(contactDisplayPicIconLabel)
                    .addGroup(contactPanelLayout.createSequentialGroup()
                        .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(contactNickNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactStatusTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactPersonalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        GroupLayout contactListPanelLayout = new GroupLayout(contactListPanel);
        contactListPanel.setLayout(contactListPanelLayout);
        contactListPanelLayout.setHorizontalGroup(
            contactListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contactPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        contactListPanelLayout.setVerticalGroup(
            contactListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactListPanelLayout.createSequentialGroup()
                .addComponent(contactPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        contactListScrollPane.setViewportView(contactListPanel);

        groupsTabbedPane.addTab("All Contacts", contactListScrollPane);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(userInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupsTabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(utilityPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(userInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupsTabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(utilityPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileSignInMenuItem) {
			if(m_client.getClientState() != ClientState.Disconnected) {
	    		JOptionPane.showMessageDialog(null, "Please log out first.", "Already Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
	    	String userName = JOptionPane.showInputDialog(null, "Username:", "Username", JOptionPane.QUESTION_MESSAGE);
	    	String password = JOptionPane.showInputDialog(null, "Password:", "Password", JOptionPane.QUESTION_MESSAGE);
	    	
	    	m_client.initialize();
	    	m_client.login(userName, password);
		}
		else if(e.getSource() == fileSignOutMenuItem) {
			if(m_client.getClientState() == ClientState.Disconnected) {
	    		JOptionPane.showMessageDialog(null, "Unable to log out, you are not logged in.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
	        m_client.logout();
		}
		else if(e.getSource() == fileCreateAccountMenuItem) {
			if(m_client.getClientState() != ClientState.Disconnected) {
	    		JOptionPane.showMessageDialog(null, "Please log out before creating an account.", "Please Log Out", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
	    	String userName = JOptionPane.showInputDialog(null, "Username:", "Username", JOptionPane.QUESTION_MESSAGE);
	    	String password = JOptionPane.showInputDialog(null, "Password:", "Password", JOptionPane.QUESTION_MESSAGE);
	    	
	    	m_client.initialize();
	    	
	    	m_client.createAccount(userName, password);
		}
		else if(e.getSource() == fileChangePasswordMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String oldPassword = JOptionPane.showInputDialog(null, "Old Password:", "Old Password", JOptionPane.QUESTION_MESSAGE);
	    	String newPassword = JOptionPane.showInputDialog(null, "New Password:", "New Password", JOptionPane.QUESTION_MESSAGE);
	    	String confirmNewPassword = JOptionPane.showInputDialog(null, "Confirm New Password:", "Confirm New Password", JOptionPane.QUESTION_MESSAGE);
	    	
	    	if(oldPassword == null || newPassword == null || confirmNewPassword == null) {
	    		JOptionPane.showMessageDialog(null, "Please fill out all information fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
	    	if(!newPassword.equals(confirmNewPassword)) {
	    		JOptionPane.showMessageDialog(null, "New password does not match.", "Mismatched Passwords", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
	    	m_client.changePassword(oldPassword, newPassword);
		 }
		else if(e.getSource() == fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == contactsAddContactMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
			
			m_client.addContact(contactUserName);
		}
		else if(e.getSource() == contactsDeleteContactMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
			
			m_client.deleteContact(contactUserName);
		}
		else if(e.getSource() == contactsBlockContactMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
			
			m_client.blockContact(contactUserName);
		}
		else if(e.getSource() == contactsUnblockContactMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
			
			m_client.unblockContact(contactUserName);
		}
		else if(e.getSource() == helpAboutMenuItem) {
			JOptionPane.showMessageDialog(null, "Serious Messenger.\n\nSerious about delivering messages.", "About Serious Messenger", JOptionPane.INFORMATION_MESSAGE);
		}
    }
    
}