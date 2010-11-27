package shared;

import java.awt.*;
import javax.swing.*;

public class MessageBox {
	
	public Component parentComponent = null;
	public Object message = null;
	public String title = null;
	public int messageType = 0;
	public Icon icon;
	public int constructor = 0;
	
	public MessageBox(Component parentComponent, Object message) {
		this.parentComponent = parentComponent;
		this.message = message;
		this.constructor = 1;
	}
	
	public MessageBox(Component parentComponent, Object message, String title, int messageType) {
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.messageType = messageType;
		this.constructor = 2;
	}
	
	public MessageBox(Component parentComponent, Object message, String title, int messageType, Icon icon) {
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.messageType = messageType;
		this.icon = icon;
		this.constructor = 3;
	}
	
}
