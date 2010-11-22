package client;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import signal.*;

public class Client extends Thread {

	private Socket m_connection;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private InputSignalQueue m_inSignalQueue;
	private OutputSignalQueue m_outSignalQueue;
	
	public Client() {
		m_inSignalQueue = new InputSignalQueue();
		m_outSignalQueue = new OutputSignalQueue();
	}
	
	public void initialize(String host, int port) {
		try {
			m_connection = new Socket(host, port);
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(this, m_in, m_outSignalQueue);
			m_outSignalQueue.initialize(this, m_out);
			start();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to server at " + host + ":" + port + ": " + e.getMessage(), "Error Connecting to Server", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Socket getConnection() { return m_connection; }
	
	public boolean isConnected() { return m_connection.isConnected(); }
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public void run() {
		while(isConnected()) {
			m_inSignalQueue.readSignal();
		}
	}

}
