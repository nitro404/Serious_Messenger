package client;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import shared.*;

public class ContactPanel extends JPanel {
	
	private Contact m_contact;
	
	private JTextField personalMessageTextField;
	private JTextField nickNameTextField;
	private JLabel displayPicIconLabel;
	private JTextField statusTextField;
	
	private boolean m_editable = false;
	
	private static final long serialVersionUID = 1L;
	
	public ContactPanel(Contact contact) {
		m_contact = contact;
		
		initComponents();
		initLayout();
		
		setEditable(m_editable);
		update();
	}
	
	public void setEditable(boolean editable) {
        nickNameTextField.setFocusable(m_editable);
        personalMessageTextField.setFocusable(m_editable);
        statusTextField.setFocusable(m_editable);
	}
	
	private void initComponents() {
		displayPicIconLabel = new JLabel();
        nickNameTextField = new JTextField();
        personalMessageTextField = new JTextField();
        personalMessageTextField.setFont(new Font("Tahoma", 2, 11));
        displayPicIconLabel.setIcon(new ImageIcon("img/serious_logo.png"));
        statusTextField = new JTextField();
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
	                    .addComponent(nickNameTextField, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
	                    .addGap(18, 18, 18)
	                    .addComponent(statusTextField, GroupLayout.DEFAULT_SIZE, 70, GroupLayout.PREFERRED_SIZE))
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
	                        .addComponent(statusTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(personalMessageTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
	            .addContainerGap())
	    );
	}
	
	public static ImageIcon getDisplayPicture(String userName) {
		if(userName == null) { return new ImageIcon(Globals.DEFAULT_DISPLAY_PICTURE); }
		
		String filePath = "img/" + userName + ".png";
		File file = new File(filePath);
		
		if(!file.exists() || !file.isFile()) { return new ImageIcon(Globals.DEFAULT_DISPLAY_PICTURE); }
		
		return new ImageIcon(filePath);
	}
	
	public void update() {
		if(m_contact == null) { return; }
		
		nickNameTextField.setText(m_contact.getNickName());
		personalMessageTextField.setText(m_contact.getPersonalMessage());
		statusTextField.setText(StatusType.getStatus(m_contact.getStatus()));
		displayPicIconLabel.setIcon(getDisplayPicture(m_contact.getUserName()));
	}
}
