package client;

import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Conversation extends JFrame {
	
	private Vector<Message> m_messages;
	private User m_user;
	private Vector<Contact> m_participants;
    
	private JMenuBar messageMenuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileSaveConversationMenuItem;
    private JMenuItem fileViewHistoryMenuItem;
	private JMenuItem fileCloseMenuItem;
    
    private JMenu editMenu;
	private JMenuItem editChangeFontMenuItem;
    
    private JMenu userMenu;
    private JMenuItem userTransferFileMenuItem;
    private JMenuItem userBlockMenuItem;
    private JMenuItem userUnblockMenuItem;
    private JMenuItem userInviteMenuItem;
    
    private JLabel nickNameLabel;
    private JLabel statusLabel;
    private JLabel personalMessageLabel;
    private JScrollPane inputScrollPane;
    private JTextArea inputTextArea;
    private JScrollPane messageScrollPane;
    private JTextArea messageTextArea;
    
    private JButton changeFontButton;
	private JButton transferFileButton;
	private JButton blockButton;
	private JButton viewHistoryButton;
	private JButton sendButton;
	
    private static final long serialVersionUID = 1L;
	
	public Conversation(User user, Contact contact) {
		m_messages = new Vector<Message>();
		m_participants = new Vector<Contact>();
		
		m_user = user;
		m_participants.add(contact);
		
		initComponents();
	}
	
	public int numberOfMessages() { return m_messages.size(); }

	public Message getMessage(int index) {
		if(index < 0 || index >= m_messages.size()) { return null; }
		return m_messages.elementAt(index);
	}
	
	public int numberOfParticipants() { return m_participants.size(); }
	
	public boolean hasParticipant(String userName) {
		if(userName == null) { return false; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				return true;
			}
		}
		return false;
	}
	
	public Contact getParticipant(String userName) {
		if(userName == null) { return null; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				return m_participants.elementAt(i);
			}
		}
		return null;
	}
	
	public Contact getParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return null; }
		return m_participants.elementAt(index);
	}
	
	public boolean addParticipant(Contact c) {
		if(c == null || m_participants.contains(c)) { return false; }
		return m_participants.add(c);
	}
	
	public boolean removeParticipant(String userName) {
		if(userName == null) { return false; }
		for(int i=0;i<m_participants.size();i++) {
			if(m_participants.elementAt(i).getUserName().equalsIgnoreCase(userName)) {
				m_participants.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeParticipant(Contact c) {
		if(c == null || m_participants.contains(c)) { return false; }
		return m_participants.remove(c);
	}
	
	public boolean removeParticipant(int index) {
		if(index < 0 || index >= m_participants.size()) { return false; }
		m_participants.remove(index);
		return true;
	}
	
	public boolean sendMessage(String message) {
		Message m = new Message(m_user, message);
		m_messages.add(m);
		
		// TODO: Finish me
		
		return true;
	}
	
	public boolean receiveMessage(String userName, long id, String message) {
		if(userName == null || message == null) { return false; }
		// TODO: Finish me
		return receiveMessage(getParticipant(userName), id, message);
	}
	
	public boolean receiveMessage(Contact c, long id, String message) {
		if(c == null || message == null) { return false; }
		
		Message m = new Message(c, id, message);
		m_messages.add(m);
		
		// TODO: Finish me
		
		return true;
	}
	
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Message");
        setMinimumSize(new Dimension(200, 150));
        
    	messageMenuBar = new JMenuBar();
    	
    	fileMenu = new JMenu();
        fileSaveConversationMenuItem = new JMenuItem();
        fileViewHistoryMenuItem = new JMenuItem();
        fileCloseMenuItem = new JMenuItem();
        
        editMenu = new JMenu();
        editChangeFontMenuItem = new JMenuItem();
        
        userMenu = new JMenu();
        userTransferFileMenuItem = new JMenuItem();
        userBlockMenuItem = new JMenuItem();
        userUnblockMenuItem = new JMenuItem();
        userInviteMenuItem = new JMenuItem();
    	
        nickNameLabel = new JLabel();
        statusLabel = new JLabel();
        personalMessageLabel = new JLabel();
        inputScrollPane = new JScrollPane();
        inputTextArea = new JTextArea();
        messageScrollPane = new JScrollPane();
        messageTextArea = new JTextArea();
        
        changeFontButton = new JButton();
        transferFileButton = new JButton();
        blockButton = new JButton();
        viewHistoryButton = new JButton();
        sendButton = new JButton();

        inputTextArea.setColumns(20);
        inputTextArea.setRows(5);
        inputTextArea.setPreferredSize(new Dimension(164, 94));
        inputTextArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                inputTextAreaKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                inputTextAreaKeyReleased(evt);
            }
        });
        inputScrollPane.setViewportView(inputTextArea);

        messageTextArea.setColumns(20);
        messageTextArea.setRows(5);
        messageScrollPane.setViewportView(messageTextArea);

        changeFontButton.setText("Change Font");
        changeFontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                changeFontButtonActionPerformed(evt);
            }
        });

        nickNameLabel.setText("Nickname");

        statusLabel.setText("Status");

        personalMessageLabel.setText("Personal Message");

        viewHistoryButton.setText("View History");
        viewHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                viewHistoryButtonActionPerformed(evt);
            }
        });

        blockButton.setText("Block");
        blockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                blockButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        transferFileButton.setText("Transfer File");
        transferFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                transferFileButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        fileSaveConversationMenuItem.setText("Save Conversation");
        fileSaveConversationMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSaveConversationMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSaveConversationMenuItem);

        fileViewHistoryMenuItem.setText("View History");
        fileViewHistoryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileViewHistoryMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileViewHistoryMenuItem);

        fileCloseMenuItem.setText("Close");
        fileCloseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileCloseMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileCloseMenuItem);

        messageMenuBar.add(fileMenu);

        editMenu.setText("Edit");

        editChangeFontMenuItem.setText("Change Font");
        editChangeFontMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editChangeFontMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(editChangeFontMenuItem);

        messageMenuBar.add(editMenu);

        userMenu.setText("User");

        userTransferFileMenuItem.setText("Transfer File");
        userTransferFileMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userTransferFileMenuItemActionPerformed(evt);
            }
        });
        userMenu.add(userTransferFileMenuItem);

        userBlockMenuItem.setText("Block");
        userBlockMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userBlockMenuItemActionPerformed(evt);
            }
        });
        userMenu.add(userBlockMenuItem);

        userUnblockMenuItem.setText("Unblock");
        userUnblockMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userUnblockMenuItemActionPerformed(evt);
            }
        });
        userMenu.add(userUnblockMenuItem);

        userInviteMenuItem.setText("Invite");
        userInviteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userInviteMenuItemActionPerformed(evt);
            }
        });
        userMenu.add(userInviteMenuItem);

        messageMenuBar.add(userMenu);

        setJMenuBar(messageMenuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(inputScrollPane, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(changeFontButton, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(transferFileButton, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(blockButton, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(viewHistoryButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(nickNameLabel, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(personalMessageLabel, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(messageScrollPane, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nickNameLabel)
                    .addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalMessageLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageScrollPane, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(changeFontButton)
                    .addComponent(sendButton)
                    .addComponent(transferFileButton)
                    .addComponent(blockButton)
                    .addComponent(viewHistoryButton))
                .addGap(1, 1, 1)
                .addComponent(inputScrollPane, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    private void inputTextAreaKeyReleased(KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void inputTextAreaKeyPressed(KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void fileSaveConversationMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fileViewHistoryMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fileCloseMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void editChangeFontMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void userTransferFileMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void userBlockMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void userUnblockMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void userInviteMenuItemActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void changeFontButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void transferFileButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void blockButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void viewHistoryButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void sendButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
    
}
