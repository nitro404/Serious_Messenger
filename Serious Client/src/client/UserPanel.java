package client;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shared.*;

public class UserPanel extends JPanel implements ActionListener {
	
	private JTextField personalMessageTextField;
	private JTextField nickNameTextField;
	private JLabel displayPicIconLabel;
	private JComboBox statusComboBox;
	
	private boolean m_editable = true;
	
	private static final long serialVersionUID = 1L;
	
	public UserPanel(boolean editable) {
		m_editable = editable;
		
		initComponents();
		initLayout();
		
		setEditable(m_editable);
	}
	
	public void setEditable(boolean editable) {
        nickNameTextField.setFocusable(m_editable);
        personalMessageTextField.setFocusable(m_editable);
        statusComboBox.setEnabled(m_editable);
	}
	
	private void initComponents() {
		displayPicIconLabel = new JLabel();
        nickNameTextField = new JTextField();
        personalMessageTextField = new JTextField();
        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));
        displayPicIconLabel.setIcon(new ImageIcon("img/serious_logo.png"));
        
        statusComboBox = new JComboBox();
        statusComboBox.setModel(new DefaultComboBoxModel(StatusType.statusTypes));
        statusComboBox.setToolTipText("Use this drop down box to select your status");
        statusComboBox.addActionListener(this);
	}
	
	private void initLayout() {
	    GroupLayout contactPanelLayout = new GroupLayout(this);
	    setLayout(contactPanelLayout);
	    contactPanelLayout.setHorizontalGroup(
	        contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(contactPanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addComponent(displayPicIconLabel)
	            .addGap(18, 18, 18)
	            .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addGroup(contactPanelLayout.createSequentialGroup()
	                    .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
	                    .addGap(18, 18, 18)
	                    .addComponent(statusComboBox, GroupLayout.DEFAULT_SIZE, 70, GroupLayout.PREFERRED_SIZE))
	                .addComponent(personalMessageTextField, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
	            .addContainerGap())
	    );
	    
	    contactPanelLayout.setVerticalGroup(
	        contactPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        .addGroup(contactPanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
	                .addComponent(displayPicIconLabel)
	                .addGroup(contactPanelLayout.createSequentialGroup()
	                    .addGroup(contactPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                        .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	                        .addComponent(statusComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
	            .addContainerGap())
	    );
	}

	public static String getNickName(String userName) {
		if(userName == null) { return ""; }
		
		if(userName.equalsIgnoreCase("nitro404")) { return "Teh E|ite |K|scrogg"; }
		else if(userName.equalsIgnoreCase("corey")) { return "Corey"; }
		else if(userName.equalsIgnoreCase("tristan")) { return "Tristan"; }
		else if(userName.equalsIgnoreCase("dan")) { return "FURIOUS D"; }
		else if(userName.equalsIgnoreCase("engineer")) { return "Engineer"; }
		else if(userName.equalsIgnoreCase("brad")) { return "Yeldarbish"; }
		return "";
	}
	
	public static String getPersonalMessage(String userName) {
		if(userName == null) { return ""; }
		
		if(userName.equalsIgnoreCase("nitro404")) { return "silly dust making my server overheat, how dare you!"; }
		else if(userName.equalsIgnoreCase("corey")) { return "doing ui presentation"; }
		else if(userName.equalsIgnoreCase("tristan")) { return "If per chance I am dreaming, please let me sleep."; }
		else if(userName.equalsIgnoreCase("dan")) { return "skateboard trashed need ride asap"; }
		else if(userName.equalsIgnoreCase("engineer")) { return "Sentry goin' up!"; }
		else if(userName.equalsIgnoreCase("brad")) { return "Talkin' bout minecraft!"; }
		return "";
	}
	
	public static ImageIcon getDisplayPicture(String userName) {
		
		if(userName == null) { return new ImageIcon(Globals.DEFAULT_DISPLAY_PICTURE); }
		
		String filePath = "img/" + userName + ".png";
		File file = new File(filePath);
		
		if(!file.exists() || !file.isFile()) { return new ImageIcon(Globals.DEFAULT_DISPLAY_PICTURE); }
		
		return new ImageIcon(filePath);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == statusComboBox) {
			
		}
	}
}
