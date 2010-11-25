package client;

import java.util.*;
import java.awt.*;

import javax.swing.*;

public class MessageBoxSystem extends Thread {
	
	private ArrayDeque<MessageBox> m_messages;
	
	public MessageBoxSystem() {
		
	}
	
	public void show(Component parentComponent, Object message) {
		m_messages.add(new MessageBox(parentComponent, message));
	}
	
	public void show(Component parentComponent, Object message, String title, int messageType) {
		m_messages.add(new MessageBox(parentComponent, message, title, messageType));
	}
	
	public void show(Component parentComponent, Object message, String title, int messageType, Icon icon) {
		m_messages.add(new MessageBox(parentComponent, message, title, messageType, icon));
	}
	
	public void show(MessageBox messageBox) {
		if(messageBox != null) {
			m_messages.add(messageBox);
		}
	}
	
	public void initialize() {
		m_messages = new ArrayDeque<MessageBox>(); 
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public void run() {
		MessageBox box = null;
		
		while(true) {
			if(!m_messages.isEmpty()) {
				box = m_messages.remove();
				
				if(box.constructor == 1) {
					JOptionPane.showMessageDialog(box.parentComponent, box.message);
				}
				else if(box.constructor == 2) {
					JOptionPane.showMessageDialog(box.parentComponent, box.message, box.title, box.messageType);
				}
				else if(box.constructor == 3) {
					JOptionPane.showMessageDialog(box.parentComponent, box.message, box.title, box.messageType, box.icon);
				}
			}
			
			try { Thread.sleep(100); }
			catch (InterruptedException e) { }
		}
	}
	
}
