package client;

import javax.swing.*;

import shared.StatusType;

import java.awt.*;
import java.awt.event.*;

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
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == statusComboBox) {
			
		}
	}
}
