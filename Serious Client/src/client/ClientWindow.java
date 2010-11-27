package client;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame {
	
	private Client m_client;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
    private JMenuItem fileSignInMenuItem;
    private JMenuItem fileSignOutMenuItem;
    private JMenuItem fileChangePasswordMenuItem;
    private JMenuItem fileExitMenuItem;
	
    private JMenu contactsMenu;
    private JMenuItem contactsAddContactMenuItem;
    private JMenuItem contactsDeleteContactMenuItem;
    private JMenuItem contactsGreateGroupMenuItem;
    
	private JScrollPane contactListScrollPane;
    private JTree contactListTree;
    
    private JTextField nickNameTextField;
    private JTextField personalMessageTextField;
    private JComboBox statusComboBox;
	
    private static final long serialVersionUID = 1L;
    
    public ClientWindow() {
    	m_client = new Client();
    	
        initComponents();
        
        //fileSignOutMenuItem.setEnabled(false);
        //contactsAddContactMenuItem.setEnabled(false);
        //contactsDeleteContactMenuItem.setEnabled(false);
        //contactsGreateGroupMenuItem.setEnabled(false);
        //nickNameTextField.setEnabled(false);
        //statusComboBox.setEnabled(false);
        //personalMessageTextField.setEnabled(false);
    }
    
    public void initialize() {
    	setVisible(true);
    }
    
    private void initComponents() {
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serious Messenger");
        setMinimumSize(new Dimension(100, 200));
        
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu();
        fileSignInMenuItem = new JMenuItem();
        fileSignOutMenuItem = new JMenuItem();
        fileChangePasswordMenuItem = new JMenuItem();
        fileExitMenuItem = new JMenuItem();
        
        contactsMenu = new JMenu();
        contactsAddContactMenuItem = new JMenuItem();
        contactsDeleteContactMenuItem = new JMenuItem();
        contactsGreateGroupMenuItem = new JMenuItem();
        
        contactListScrollPane = new JScrollPane();
        contactListTree = new JTree();
        
        nickNameTextField = new JTextField();
        personalMessageTextField = new JTextField();
        statusComboBox = new JComboBox();

        nickNameTextField.setText("Nickname");
        nickNameTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                nickNameTextFieldFocusLost(evt);
            }
        });
        nickNameTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                nickNameTextFieldKeyReleased(evt);
            }
        });

        personalMessageTextField.setText("Personal Message");
        personalMessageTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                personalMessageTextFieldFocusLost(evt);
            }
        });
        personalMessageTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                personalMessageTextFieldKeyReleased(evt);
            }
        });

        statusComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                statusComboBoxItemStateChanged(evt);
            }
        });

        //DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode();
        //contactListTree.setModel(new DefaultTreeModel(treeNode1));
        contactListTree.setModel(null);
        contactListTree.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                contactListTreeMouseReleased(evt);
            }
        });
        contactListScrollPane.setViewportView(contactListTree);

        fileMenu.setText("File");

        fileSignInMenuItem.setText("Sign In");
        fileSignInMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSignInMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSignInMenuItem);

        fileSignOutMenuItem.setText("Sign Out");
        fileSignOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSignOutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSignOutMenuItem);

        fileChangePasswordMenuItem.setText("Change Password");
        fileChangePasswordMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	fileChangePasswordMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileChangePasswordMenuItem);
        
        fileExitMenuItem.setText("Exit");
        fileExitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileExitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileExitMenuItem);

        menuBar.add(fileMenu);

        contactsMenu.setText("Contacts");

        contactsAddContactMenuItem.setText("Add Contact");
        contactsAddContactMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                contactsAddContactMenuItemActionPerformed(evt);
            }
        });
        contactsMenu.add(contactsAddContactMenuItem);
        
        contactsDeleteContactMenuItem.setText("Delete Contact");
        contactsDeleteContactMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                contactsDeleteContactMenuItemActionPerformed(evt);
            }
        });
        contactsMenu.add(contactsDeleteContactMenuItem);

        contactsGreateGroupMenuItem.setText("Create Group");
        contactsGreateGroupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                contactsGreateGroupMenuItemActionPerformed(evt);
            }
        });
        contactsMenu.add(contactsGreateGroupMenuItem);

        menuBar.add(contactsMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(nickNameTextField, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
            .addComponent(personalMessageTextField, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
            .addComponent(contactListScrollPane, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(contactListScrollPane, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    
    public void update() {
    	if(m_client.getClientState() == ClientState.Disconnected) {
    		fileSignInMenuItem.setEnabled(true);
    		fileSignOutMenuItem.setEnabled(true);
    	}
    }
    
    private void fileSignInMenuItemActionPerformed(ActionEvent evt) {
    	if(m_client.getClientState() != ClientState.Disconnected) {
    		JOptionPane.showMessageDialog(null, "Please log out first.", "Already Logged In", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
    	String userName = JOptionPane.showInputDialog(null, "Username:", "Username", JOptionPane.QUESTION_MESSAGE);
    	String password = JOptionPane.showInputDialog(null, "Password:", "Password", JOptionPane.QUESTION_MESSAGE);
    	
    	m_client.initialize();
    	m_client.login(userName, password);
    }

    private void fileSignOutMenuItemActionPerformed(ActionEvent evt) {
    	if(m_client.getClientState() == ClientState.Disconnected) {
    		JOptionPane.showMessageDialog(null, "Unable to log out, you are not logged in.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
        m_client.logout();
    }
    
    private void fileChangePasswordMenuItemActionPerformed(ActionEvent evt) {
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

    private void fileExitMenuItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }
    
    private void contactsAddContactMenuItemActionPerformed(ActionEvent evt) {
    	if(m_client.getClientState() < ClientState.Online) {
    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
		String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
		
		m_client.addContact(contactUserName);
    }
    
    private void contactsDeleteContactMenuItemActionPerformed(ActionEvent evt) {
    	if(m_client.getClientState() < ClientState.Online) {
    		JOptionPane.showMessageDialog(null, "Please log in first.", "Not Logged In", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
		String contactUserName = JOptionPane.showInputDialog(null, "Contact's User Name:", "Contact User Name", JOptionPane.QUESTION_MESSAGE);
		
		m_client.deleteContact(contactUserName);
    }

    private void contactsGreateGroupMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void statusComboBoxItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void personalMessageTextFieldFocusLost(FocusEvent evt) {
        // TODO add your handling code here:
    }

    private void nickNameTextFieldFocusLost(FocusEvent evt) {
        // TODO add your handling code here:
    }

    private void personalMessageTextFieldKeyReleased(KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void nickNameTextFieldKeyReleased(KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void contactListTreeMouseReleased(MouseEvent evt) {
        // TODO add your handling code here:
    }
    
}
