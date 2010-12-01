package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shared.*;

public class ProfileWindow extends JFrame implements ActionListener {
	
	private Client m_client;
	
	private JMenuBar MenuBar;
    private JLabel addressHeaderLabel;
    private JPanel addressPanel;
    private JLabel birthdateLabel;
    private JTextField birthdateTextField;
    private JLabel cityLabel;
    private JTextField cityTextField;
    private JLabel countryLabel;
    private JTextField countryTextField;
    private JLabel displayPicIconLabel;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JMenu fileMenu;
    private JLabel firstNameLabel;
    private JTextField firstNameTextField;
    private JLabel genderLabel;
    private JTextField genderTextField;
    private JMenu helpMenu;
    private JLabel homeLabel;
    private JTextField homeTextField;
    private JLabel informationHeaderLabel;
    private JPanel informationPanel;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JLabel lastNameLabel;
    private JTextField lastNameTextField;
    private JLabel middleNameLabel;
    private JTextField middleNameTextField;
    private JLabel mobileLabel;
    private JTextField mobileTextField;
    private JTextField nickNameTextField;
    private JTextField personalMessageTextField;
    private JLabel phoneHeaderLabel;
    private JPanel phonePanel;
    private JLabel stateProvLabel;
    private JTextField stateProvTextField;
    private JComboBox statusComboBox;
    private JPanel userInfoPanel;
    private JLabel userNameLabel;
    private JTextField userNameTextField;
    private JLabel workLabel;
    private JTextField workTextField;
    private JLabel zipPostalLabel;
    private JTextField zipPostalTextField;
    
    private static final long serialVersionUID = 1L;
    
    public ProfileWindow() {
    	this(null);
    }
    
    public ProfileWindow(Client client) {
    	m_client = client;
    	
        initComponents();
        initContent();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Profile");
        setSize(432, 700);
        setResizable(false);
    }
    
    public void initContent() {
    	if(m_client == null) { return; }
    	
    	displayPicIconLabel.setIcon(UserPanel.getDisplayPicture(m_client.getUserName()));
    	nickNameTextField.setText(UserPanel.getNickName(m_client.getUserName()));
    	personalMessageTextField.setText(UserPanel.getPersonalMessage(m_client.getUserName()));
    	statusComboBox.setSelectedItem(StatusType.getStatus(m_client.getStatus()));
    	
    	if(m_client.getUserName().equalsIgnoreCase("nitro404")) {
    		userNameTextField.setText(m_client.getUserName());
    		firstNameTextField.setText("Kevin");
    		middleNameTextField.setText("Andrew");
    		lastNameTextField.setText("Scroggins");
    		genderTextField.setText("Male");
    		birthdateTextField.setText("June 18, 1987");
    		emailTextField.setText("nitro404@hotmail.com");
    		
    		homeTextField.setText("(613) 823-4547");
    		mobileTextField.setText("(613) 324-2284");
    		workTextField.setText("");
    		
    		countryTextField.setText("Canada");
    		stateProvTextField.setText("Ontario");
    		cityTextField.setText("Nepean");
    		zipPostalTextField.setText("K2J 4K9");
    	}
    	else if(m_client.getUserName().equalsIgnoreCase("dan")) {
        	userNameTextField.setText(m_client.getUserName());
    		firstNameTextField.setText("Daniel");
    		middleNameTextField.setText("Lavoie");
    		lastNameTextField.setText("McNerney");
    		genderTextField.setText("Male");
    		birthdateTextField.setText("August 17, 1988");
    		emailTextField.setText("danlm@rogers.com");
    		
    		homeTextField.setText("(613) 555-5555");
    		mobileTextField.setText("");
    		workTextField.setText("");
    		
    		countryTextField.setText("Canada");
    		stateProvTextField.setText("Ontario");
    		cityTextField.setText("Nepean");
    		zipPostalTextField.setText("K1S 3J1");
    	}
    }
    
    private void initComponents() {
        userInfoPanel = new JPanel();
        nickNameTextField = new JTextField();
        statusComboBox = new JComboBox();
        informationPanel = new JPanel();
        lastNameTextField = new JTextField();
        userNameTextField = new JTextField();
        userNameTextField.setEditable(false);
        firstNameTextField = new JTextField();
        emailTextField = new JTextField();
        birthdateTextField = new JTextField();
        middleNameTextField = new JTextField();
        genderTextField = new JTextField();
        phonePanel = new JPanel();
        mobileTextField = new JTextField();
        workTextField = new JTextField();
        homeTextField = new JTextField();
        countryTextField = new JTextField();
        stateProvTextField = new JTextField();
        cityTextField = new JTextField();
        zipPostalTextField = new JTextField();
        jSeparator3 = new JSeparator();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        MenuBar = new JMenuBar();
        fileMenu = new JMenu();
        helpMenu = new JMenu();
        addressPanel = new JPanel();
        personalMessageTextField = new JTextField();
        userNameLabel = new JLabel("Username:");
        
        informationHeaderLabel = new JLabel("Information");
        informationHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        firstNameLabel = new JLabel("First Name:");
        middleNameLabel = new JLabel("Middle Name:");
        lastNameLabel = new JLabel("Last Name:");
        genderLabel = new JLabel("Gender:");
        birthdateLabel = new JLabel("Birthdate:");
        emailLabel = new JLabel("Email:");
        
        phoneHeaderLabel = new JLabel("Phone");
        phoneHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        homeLabel = new JLabel("Home:");
        mobileLabel = new JLabel("Mobile:");
        workLabel = new JLabel("Work:");
        
        addressHeaderLabel = new JLabel("Address");
        addressHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        countryLabel = new JLabel("Country:");
        stateProvLabel = new JLabel("State/Province:");
        cityLabel = new JLabel("City:");
        zipPostalLabel = new JLabel("Zip/Postal Code:");
        
        displayPicIconLabel = new JLabel();
        displayPicIconLabel.setIcon(new ImageIcon("img/serious_logo.png"));

        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));

        statusComboBox.setModel(new DefaultComboBoxModel(StatusType.statusTypes));
        statusComboBox.addActionListener(this);

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
                        .addComponent(nickNameTextField, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                    .addComponent(personalMessageTextField, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
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
                            .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        GroupLayout informationPanelLayout = new GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(userNameLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 317, GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(firstNameLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 314, GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(middleNameLabel)
                            .addComponent(lastNameLabel)
                            .addComponent(genderLabel)
                            .addComponent(birthdateLabel)
                            .addComponent(emailLabel))
                        .addGap(47, 47, 47)
                        .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(firstNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(middleNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(genderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(birthdateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailTextField, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(userNameTextField, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
                    .addComponent(informationHeaderLabel))
                .addContainerGap())
        );

        informationPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {birthdateTextField, emailTextField, firstNameTextField, genderTextField, lastNameTextField, middleNameTextField, userNameTextField});

        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationHeaderLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(middleNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleNameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(genderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(genderLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(birthdateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthdateLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        informationPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {birthdateTextField, emailTextField, firstNameTextField, genderTextField, lastNameTextField, middleNameTextField, userNameTextField});

        GroupLayout phonePanelLayout = new GroupLayout(phonePanel);
        phonePanel.setLayout(phonePanelLayout);
        phonePanelLayout.setHorizontalGroup(
            phonePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(phonePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(phoneHeaderLabel)
                    .addGroup(phonePanelLayout.createSequentialGroup()
                        .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(homeLabel)
                            .addComponent(mobileLabel)
                            .addComponent(workLabel))
                        .addGap(79, 79, 79)
                        .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(workTextField)
                            .addComponent(mobileTextField)
                            .addComponent(homeTextField, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        phonePanelLayout.setVerticalGroup(
            phonePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(phonePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(phoneHeaderLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(homeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(homeLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(mobileTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobileLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(phonePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(workTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(workLabel))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        GroupLayout addressPanelLayout = new GroupLayout(addressPanel);
        addressPanel.setLayout(addressPanelLayout);
        addressPanelLayout.setHorizontalGroup(
            addressPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(addressHeaderLabel)
                    .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addGroup(GroupLayout.Alignment.LEADING, addressPanelLayout.createSequentialGroup()
                            .addComponent(zipPostalLabel)
                            .addGap(35, 35, 35)
                            .addComponent(zipPostalTextField))
                        .addGroup(GroupLayout.Alignment.LEADING, addressPanelLayout.createSequentialGroup()
                            .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(stateProvLabel)
                                .addComponent(countryLabel)
                                .addComponent(cityLabel))
                            .addGap(16, 16, 16)
                            .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(addressPanelLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(stateProvTextField, GroupLayout.Alignment.LEADING)
                                        .addComponent(countryTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
                                .addGroup(GroupLayout.Alignment.TRAILING, addressPanelLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))))))
                .addGap(24, 24, 24))
        );
        addressPanelLayout.setVerticalGroup(
            addressPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(addressHeaderLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(countryTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(countryLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateProvLabel)
                    .addComponent(stateProvTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addressPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(zipPostalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipPostalLabel))
                .addContainerGap())
        );

        fileMenu.setText("File");
        MenuBar.add(fileMenu);

        helpMenu.setText("Help");
        MenuBar.add(helpMenu);

        setJMenuBar(MenuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userInfoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationPanel, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(phonePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addressPanel, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                    .addComponent(jSeparator3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(userInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(informationPanel, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phonePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == statusComboBox) {
    		
    	}
    }
    
}