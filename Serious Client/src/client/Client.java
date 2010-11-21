package client;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Client {

	private Socket m_connection;
	
	public Client() {
		
	}
	
	public void initialize(String host, int port) {
		try {
			m_connection = new Socket(host, port);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to server at " + host + ":" + port + ": " + e.getMessage(), "Error Connecting to Server", JOptionPane.ERROR_MESSAGE);
		}
	}

}
