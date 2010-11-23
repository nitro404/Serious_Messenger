package client;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

import shared.Globals;
import signal.*;

public class Client extends Thread {
	
	private int m_state = ClientState.Disconnected;
	private String m_userName;
	private String m_password;
	
	private Socket m_connection;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private InputSignalQueue m_inSignalQueue;
	private OutputSignalQueue m_outSignalQueue;
	
	public Client() {
		m_inSignalQueue = new InputSignalQueue();
		m_outSignalQueue = new OutputSignalQueue();
	}
	
	public void initialize() {
		connect(Globals.DEFAULT_HOST, Globals.DEFAULT_PORT);
	}

	public Socket getConnection() { return m_connection; }
	
	public boolean isConnected() { return m_connection.isConnected(); }
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public int getClientState() { return m_state; }
	
	public void connect(String host, int port) {
		if(m_state != ClientState.Disconnected) { return; }
		
		try {
			m_connection = new Socket(host, port);
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(this, m_in, m_outSignalQueue);
			m_outSignalQueue.initialize(this, m_out);
			if(getState() == Thread.State.NEW) { start(); }
			setState(ClientState.Connected);
		}
		catch(IOException e) {
			setState(ClientState.Disconnected);
			JOptionPane.showMessageDialog(null, "Unable to connect to Serious Server at " + host + ":" + port + ": " + e.getMessage(), "Error Connecting to Server", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void disconnect() {
		try {
			m_out.close();
			m_in.close();
			m_connection.close();
		}
		catch(IOException e) { }
		
		setState(ClientState.Disconnected);
	}
	
	public void login(String userName, String password) {
		if(m_state != ClientState.Connected) { return; }
		
		m_userName = userName;
		m_password = password;
		m_outSignalQueue.addSignal(new LoginRequest(userName, password));
		
		setState(ClientState.AwaitingAuthentication);
	}
	
	public void logout() {
		if(m_state == ClientState.Disconnected) { return; }
		
		m_outSignalQueue.addSignal(new Logout(m_userName));
	}
	
	public void authenticated() {
		// get contact list, then set status to online
		
		m_state = ClientState.Online;
	}

	public boolean setState(int newState) {
		if(!ClientState.isValid(newState)) { return false; }
		if(m_state == newState) { return true; }
		
		if(m_state == ClientState.Disconnected) {
			if(newState == ClientState.Connected) {
				m_state = newState;
				return true;
			}
			else {
				return false;
			}
		}
		else if(m_state == ClientState.Connected) {
			if(newState == ClientState.AwaitingAuthentication) {
				m_state = newState;
				return true;
			}
			else if(newState == ClientState.Disconnected) {
				m_state = newState;
				return true;
			}
			else {
				return false;
			}
		}
		else if(m_state == ClientState.AwaitingAuthentication) {
			if(newState == ClientState.Online) {
				m_state = newState;
				return true;
			}
			else if(newState == ClientState.Disconnected) {
				m_state = newState;
			}
		}
		else if(m_state == ClientState.Online) {
			if(newState == ClientState.Disconnected) {
				m_state = newState;
			}
		}
		return true;
	}
		
	public void run() {
		while(isConnected()) {
			m_inSignalQueue.readSignal();
		}
	}

}
