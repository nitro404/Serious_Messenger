package client;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shared.*;

public class ClientWindow extends JFrame implements ActionListener {
	
	private Client m_client;
	
	private Vector<ConversationWindow> m_conversationWindows;
	
	private JButton announceButton;
	private JPanel contactListPanel;
	private JScrollPane contactListScrollPane;
	private JLabel displayPicIconLabel;
	
	private Vector<ContactPanel> contactPanels;
	
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
	private JMenuItem contactsStartConversationMenuItem;
	private JMenuItem contactsAddContactMenuItem;
    private JMenuItem contactsDeleteContactMenuItem;
    private JMenuItem contactsBlockContactMenuItem;
    private JMenuItem contactsUnblockContactMenuItem;
    
	private JMenu settingsMenu;
	private JMenuItem settingsProfileMenuItem;
	
	private JMenu helpMenu;
	private JMenuItem helpAboutMenuItem;
    
    private static final long serialVersionUID = 1L;
    
    public ClientWindow() {
    	m_client = new Client(this);
    	m_conversationWindows = new Vector<ConversationWindow>();
    	
    	initMenu();
        initComponents();
        initLayouts();
        
        setTitle("Serious Messenger");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(508, 492);
        setResizable(false);
    }
    
    public void initialize() {
    	initialize(Globals.DEFAULT_HOST, Globals.DEFAULT_PORT);
    }
    
    public void initialize(String hostName, int port) {
    	m_client.initialize(hostName, port);
    	setVisible(true);
    }
    
    public int numberOfConversations() {
    	return m_conversationWindows.size();
    }
    
    public Conversation getConversation(int index) {
    	if(index < 0 || index >= m_conversationWindows.size()) { return null; }
    	return m_conversationWindows.elementAt(index).getConversation();
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
        contactsStartConversationMenuItem = new JMenuItem("Start Conversation");
        contactsAddContactMenuItem = new JMenuItem("Add Contact");
        contactsDeleteContactMenuItem = new JMenuItem("Delete Contact");
        contactsBlockContactMenuItem = new JMenuItem("Block Contact");
        contactsUnblockContactMenuItem = new JMenuItem("Unblock Contact");
        
        settingsMenu = new JMenu("Settings");
        settingsProfileMenuItem = new JMenuItem("View/Edit Profile");
        
        helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        fileSignInMenuItem.addActionListener(this);
        fileSignOutMenuItem.addActionListener(this);
        fileCreateAccountMenuItem.addActionListener(this);
        fileChangePasswordMenuItem.addActionListener(this);
        fileExitMenuItem.addActionListener(this);
        contactsStartConversationMenuItem.addActionListener(this);
        contactsAddContactMenuItem.addActionListener(this);
        contactsDeleteContactMenuItem.addActionListener(this);
        contactsBlockContactMenuItem.addActionListener(this);
        contactsUnblockContactMenuItem.addActionListener(this);
        settingsProfileMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        fileMenu.add(fileSignInMenuItem);
        fileMenu.add(fileSignOutMenuItem);
        fileMenu.add(fileCreateAccountMenuItem);
        fileMenu.add(fileChangePasswordMenuItem);
        fileMenu.add(fileExitMenuItem);
        
        contactsMenu.add(contactsStartConversationMenuItem);
        contactsMenu.add(contactsAddContactMenuItem);
        contactsMenu.add(contactsDeleteContactMenuItem);
        contactsMenu.add(contactsBlockContactMenuItem);
        contactsMenu.add(contactsUnblockContactMenuItem);
        
        settingsMenu.add(settingsProfileMenuItem);
        
        helpMenu.add(helpAboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(contactsMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }
    
    private void initComponents() {
    	contactPanels = new Vector<ContactPanel>();
    	
        userInfoPanel = new JPanel();
        nickNameTextField = new JTextField();
        displayPicIconLabel = new JLabel();
        personalMessageTextField = new JTextField();
        statusComboBox = new JComboBox();
        utilityPanel = new JPanel();
        announceButton = new JButton("Announce");
        announceButton.addActionListener(this);
        searchTextField = new JTextField("Type here to search through the contact list...");
        searchLabel = new JLabel("Search:");
        groupsTabbedPane = new JTabbedPane();
        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));

        displayPicIconLabel.setIcon(new ImageIcon("img/serious_logo.png")); 
        
        statusComboBox.setModel(new DefaultComboBoxModel(StatusType.statusTypes));
        statusComboBox.setToolTipText("Use this drop down box to select your status");
        statusComboBox.addActionListener(this);

        searchTextField.setFont(new Font("Tahoma", 2, 11));
        searchTextField.setToolTipText("Type here to search through the contact list...");

    	contactListPanel = new JPanel();
    	contactListScrollPane = new JScrollPane();
        contactListPanel.setToolTipText("Group: All Contacts");
        contactListPanel.setPreferredSize(new Dimension(450, 268));
        contactListScrollPane.setToolTipText("Contact List");
        contactListScrollPane.setViewportView(contactListPanel);
        contactListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contactListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        groupsTabbedPane.setToolTipText("Group: All Contacts");
        groupsTabbedPane.setFont(new Font("Tahoma", 1, 11));
        groupsTabbedPane.addTab("All Contacts", contactListScrollPane);
    }
    
    public void initLayouts() {
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
                        .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
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

        GroupLayout utilityPanelLayout = new GroupLayout(utilityPanel);
        utilityPanel.setLayout(utilityPanelLayout);
        utilityPanelLayout.setHorizontalGroup(
            utilityPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(utilityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTextField, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
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

        GroupLayout contactListPanelLayout = new GroupLayout(contactListPanel);
        contactListPanel.setLayout(contactListPanelLayout);
        contactListPanelLayout.setHorizontalGroup(
            contactListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactListPanelLayout.createSequentialGroup()
                .addContainerGap())
        );
        contactListPanelLayout.setVerticalGroup(
            contactListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contactListPanelLayout.createSequentialGroup())
        );
        
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
	    	if(userName == null) { return; }
	    	String password = JOptionPane.showInputDialog(null, "Password:", "Password", JOptionPane.QUESTION_MESSAGE);
	    	if(password == null) { return; }
	    	
	    	m_client.connect();
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
	    	
	    	m_client.connect();
	    	
	    	m_client.createAccount(userName, password);
		}
		else if(e.getSource() == fileChangePasswordMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			String oldPassword = JOptionPane.showInputDialog(null, "Old Password:", "Old Password", JOptionPane.QUESTION_MESSAGE);
			if(oldPassword == null) { return; }
	    	String newPassword = JOptionPane.showInputDialog(null, "New Password:", "New Password", JOptionPane.QUESTION_MESSAGE);
	    	if(newPassword == null) { return; }
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
		else if(e.getSource() == contactsStartConversationMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
	    	
			UserNetworkData contact = null;
			String contactUserName = JOptionPane.showInputDialog(null, "Who would you like to start a conversation with?", "Start Conversation with User", JOptionPane.QUESTION_MESSAGE);
			
			if(contactUserName == null) { return; }
			for(int i=0;i<m_client.numberOfContacts();i++) {
				if(contactUserName.equalsIgnoreCase(m_client.getContact(i).getUserName())) {
					contact = m_client.getContact(i);
					break;
				}
			}
			
			if(contact == null) {
				JOptionPane.showMessageDialog(null, "Unable to find contact on your contact list.", "Invalid Contact", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(contact.getStatus() == StatusType.Offline) {
				JOptionPane.showMessageDialog(null, "Contact is not online.", "Contact Offline", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			boolean conversationExists = false;
			for(int i=0;i<numberOfConversations();i++) {
				if(getConversation(i).getParticipant(0).getUserName().equalsIgnoreCase(contact.getUserName())) {
					m_conversationWindows.elementAt(i).setVisible(true);
					conversationExists = true;
					break;
				}
			}
			
			if(!conversationExists) {
				ConversationWindow conversationWindow = new ConversationWindow(m_client, contact);
				conversationWindow.setVisible(true);
				m_conversationWindows.add(conversationWindow);
			}
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
		else if(e.getSource() == settingsProfileMenuItem) {
			if(m_client.getClientState() < ClientState.Online) {
	    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}
			
			ProfileWindow profileWindow = new ProfileWindow(m_client);
			profileWindow.setVisible(true);
		}
		else if(e.getSource() == helpAboutMenuItem) {
			JOptionPane.showMessageDialog(null, "Serious Messenger.\n\nSerious about delivering messages.", "About Serious Messenger", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource() == statusComboBox) {
			Object selectedItem = statusComboBox.getSelectedItem();
			if(selectedItem != null) {
				m_client.setStatus(StatusType.getStatus(selectedItem.toString()));
			}
		}
		else if(e.getSource() == announceButton) {
			m_client.announce(JOptionPane.showInputDialog(null, "Announcement Message:", "Announce", JOptionPane.QUESTION_MESSAGE));
		}
    }
    
    public void resetContactPanels() {
    	contactListPanel = new JPanel();
        contactListPanel.setToolTipText("Group: All Contacts");
        contactListPanel.setPreferredSize(new Dimension(450, 268));
        
    	contactPanels = new Vector<ContactPanel>();
    	ContactPanel panel = null;
    	for(int i=0;i<m_client.numberOfContacts();i++) {
    		panel = new ContactPanel(m_client.getContact(i));
    		contactPanels.add(panel);
    		contactListPanel.add(panel);
    	}
    	
    	contactListScrollPane.setViewportView(contactListPanel);
    	contactListScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    	
    	contactListPanel.setLayout(new FlowLayout());
    	
    	if(contactPanels.size() > 0) {
    		contactListPanel.setPreferredSize(new Dimension(440, 80 * contactPanels.size()));
    	}
        
        update();
    }
    
    public void update() {
    	for(int i=0;i<contactPanels.size();i++) {
    		contactPanels.elementAt(i).update();
    	}
//    	nickNameTextField.setText();
//    	personalMessageTextField.setText();
    	displayPicIconLabel.setIcon(UserPanel.getDisplayPicture(m_client.getUserName()));
    }
    
}