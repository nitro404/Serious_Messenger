package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConversationWindow extends JFrame {
	
	private Conversation m_conversation;
	
    private JMenuBar menuBar;
    private JButton announceButton;
    private JButton clearButton;
    private JMenu contactMenu;
    private JTextPane conversationOutputTextPane;
    private JLabel displayPicIconLabel;
    private JMenu editMenu;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JPanel inputPanel;
    private JScrollPane jScrollPane1;
    private JTextField nickNameTextField;
    private JTextField personalMessageTextField;
    private JButton sendButton;
    private JTextField statusTextField;
    private JPanel userInfoPanel;
    private JTextField userInputTextField;
    private JTextField userTypingTextField;
	
	private static final long serialVersionUID = 1L;
	
    public ConversationWindow() {
        initComponents();
    }
    
    private void initComponents() {
        userInfoPanel = new JPanel();
        statusTextField = new JTextField();
        nickNameTextField = new JTextField();
        displayPicIconLabel = new JLabel();
        personalMessageTextField = new JTextField();
        jScrollPane1 = new JScrollPane();
        conversationOutputTextPane = new JTextPane();
        inputPanel = new JPanel();
        clearButton = new JButton();
        announceButton = new JButton();
        sendButton = new JButton();
        userInputTextField = new JTextField();
        userTypingTextField = new JTextField();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        editMenu = new JMenu();
        contactMenu = new JMenu();
        helpMenu = new JMenu();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        statusTextField.setEditable(false);

        nickNameTextField.setEditable(false);

        personalMessageTextField.setEditable(false);
        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));

        GroupLayout userInfoPanelLayout = new GroupLayout(userInfoPanel);
        userInfoPanel.setLayout(userInfoPanelLayout);
        userInfoPanelLayout.setHorizontalGroup(
            userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayPicIconLabel)
                .addGap(18, 18, 18)
                .addGroup(userInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, userInfoPanelLayout.createSequentialGroup()
                        .addComponent(nickNameTextField, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextField, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                    .addComponent(personalMessageTextField, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addContainerGap())
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
                            .addComponent(statusTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(conversationOutputTextPane);

        clearButton.setText("Clear");

        announceButton.setText("Announce");

        sendButton.setText("Send");

        userInputTextField.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userInputTextField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(announceButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sendButton)
                    .addComponent(clearButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap())
        );

        inputPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {announceButton, clearButton, sendButton});

        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(sendButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(announceButton))
                    .addComponent(userInputTextField, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap())
        );

        inputPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {announceButton, clearButton, sendButton});

        userTypingTextField.setEditable(false);
        userTypingTextField.setFont(new Font("Tahoma", 3, 11));

        fileMenu.setText("File");
        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        contactMenu.setText("Contact");
        menuBar.add(contactMenu);

        helpMenu.setText("Help");
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(userTypingTextField, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE))
                            .addComponent(userInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(userInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userTypingTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }
    
}
