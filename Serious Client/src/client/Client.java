package client;

import java.io.*;
import java.net.*;
import javax.swing.*;
import shared.*;
import signal.*;

public class Client extends User {
	
	private int m_state = ClientState.Disconnected;
	
	private Socket m_connection;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ServerInputSignalQueue m_inSignalQueue = null;
	private ServerOutputSignalQueue m_outSignalQueue = null;
	private ClientThread m_clientThread = null;
	private MessageBoxSystem m_messageBoxSystem = null;
	
	private String m_hostName;
	private int m_port;
	private ServerDisconnectHandler m_disconnectHandler = null;
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	private ClientWindow m_clientWindow;
	
	public Client(ClientWindow clientWindow) {
		super();
		m_clientWindow = clientWindow;
		m_messageBoxSystem = new MessageBoxSystem();
	}
	
	public void initialize() {
		initialize(Globals.DEFAULT_HOST, Globals.DEFAULT_PORT);
	}
	
	public void initialize(String hostName, int port) {
		if(hostName == null) { m_hostName = Globals.DEFAULT_HOST; }
		else { m_hostName = hostName; }
		if(port < 0 || port > 65355) { m_port = Globals.DEFAULT_PORT; }
		else { m_port = port; }
	}
	
	public Socket getConnection() { return m_connection; }
	
	public boolean isConnected() {
		return m_state > ClientState.Disconnected && !timeout();
	}
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public int getClientState() { return m_state; }
	
	public void connect() {
		if(m_state != ClientState.Disconnected) { return; }
		
		setState(ClientState.Connected);
		
		m_timeElapsed = 0;
		m_awaitingResponse = false;
		
		m_messageBoxSystem.initialize();
		
		try {
			m_connection = new Socket(m_hostName, m_port);
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			
			if(m_outSignalQueue == null || m_outSignalQueue.isTerminated()) {
				m_outSignalQueue = new ServerOutputSignalQueue();
				m_outSignalQueue.initialize(this, m_clientWindow, m_out);
			}
			
			if(m_inSignalQueue == null || m_inSignalQueue.isTerminated()) {
				m_inSignalQueue = new ServerInputSignalQueue();
				m_inSignalQueue.initialize(this, m_clientWindow, m_in, m_outSignalQueue, m_messageBoxSystem);
			}
			
			if(m_clientThread == null || m_clientThread.isTerminated()) {
				m_clientThread = new ClientThread();
				m_clientThread.initialize(this);
			}
			
			if(m_disconnectHandler == null || m_disconnectHandler.isTerminated()) {
				m_disconnectHandler = new ServerDisconnectHandler();
				m_disconnectHandler.initialize(this, m_messageBoxSystem);
			}
		}
		catch(IOException e) {
			disconnect();
			m_messageBoxSystem.show(null, "Unable to connect to Serious Server at " + m_hostName + ":" + m_port + ": " + e.getMessage(), "Error Connecting to Server", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void disconnect() {
		setState(ClientState.Disconnected);
		
		try { if(m_out != null) { m_out.close(); } } catch(IOException e) { }
		try { if(m_in != null) { m_in.close(); } } catch(IOException e) { }
		try { if(m_connection != null) { m_connection.close(); } } catch(IOException e) { }
		
		m_out = null;
		m_in = null;
		m_connection = null;
	}
	
	public void createAccount(String userName, String password) {
		if(m_state != ClientState.Connected) { return; }
		
		m_outSignalQueue.addSignal(new CreateUserSignal(userName, password));
	}
	
	public void login(String userName, String password) {
		if(m_state != ClientState.Connected) { return; }
		
		m_userName = userName;
		m_password = password;
		
		m_outSignalQueue.addSignal(new LoginRequestSignal(userName, password));
		
		setState(ClientState.AwaitingAuthentication);
	}
	
	public void logout() {
		if(m_state == ClientState.Disconnected) { return; }
		
		m_outSignalQueue.addSignal(new LogoutSignal(m_userName));
	}
	
	public void authenticated() {
		setState(ClientState.Online);
		
		setStatus(StatusType.Online);
		
		m_outSignalQueue.addSignal(new BroadcastLoginSignal(this));
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if(oldPassword == null || newPassword == null) { return; }
		
		m_outSignalQueue.addSignal(new ChangePasswordSignal(m_userName, oldPassword, newPassword));
	}
	
	public void addContact(String contactUserName) {
		if(contactUserName == null) { return; }
		
		m_outSignalQueue.addSignal(new AddContactSignal(contactUserName));
	}
	
	public void deleteContact(String contactUserName) {
		if(contactUserName == null) { return; }
		
		m_outSignalQueue.addSignal(new DeleteContactSignal(contactUserName));
	}
	
	public void blockContact(String contactUserName) {
		if(contactUserName == null) { return; }
		
		m_outSignalQueue.addSignal(new BlockContactSignal(contactUserName, true));
	}
	
	public void unblockContact(String contactUserName) {
		if(contactUserName == null) { return; }
		
		m_outSignalQueue.addSignal(new BlockContactSignal(contactUserName, false));
	}
	
	public void updateNickName(String nickName) {
		if(nickName == null) { return; }
		
		setNickName(nickName);
		
		m_outSignalQueue.addSignal(new ChangeNicknameSignal(m_userName, m_nickName));
	}
	
	public void updatePersonalMessage(String personalMessage) {
		if(personalMessage == null) { return; }
		
		setPersonalMessage(personalMessage);
		
		m_outSignalQueue.addSignal(new ChangePersonalMessageSignal(m_userName, m_personalMessage));
	}
	
	public void updateStatus(byte status) {
		if(!StatusType.isValid(status)) { return; }
		
		setStatus(status);
		
		m_outSignalQueue.addSignal(new ChangeStatusSignal(m_userName, m_status));
	}
	
	public void updateUserNickName(String userName, String nickName) {
		if(nickName == null) { return; }
		
		UserNetworkData contact = getContact(userName);
		
		if(contact != null) {
			contact.setNickName(nickName);
		}
	}
	
	public void updateUserPersonalMessage(String userName, String personalMessage) {
		if(personalMessage == null) { return; }
		
		UserNetworkData contact = getContact(userName);
		
		if(contact != null) {
			contact.setPersonalMessage(personalMessage);
		}
	}
	
	public void updateUserStatus(String userName, byte status) {
		if(!StatusType.isValid(status)) { return; }
		
		UserNetworkData contact = getContact(userName);
		
		if(contact != null) {
			contact.setStatus(status);
		}
	}
	
	public void sendMessage(String message, long messageID, String userName, String contactUserName) {
		m_outSignalQueue.addSignal(new MessageSignal(message, messageID, userName, contactUserName));
	}
	
	public void receiveMessage(String message, long messageID, String contactUserName) {
		ConversationWindow conversationWindow = m_clientWindow.startConversation(contactUserName);
		
		if(conversationWindow != null) {
			conversationWindow.receiveMessage(message, messageID, contactUserName);
		}
	}
	
	public void announce(String message) {
		if(message == null) { return; }
		
		for(int i=0;i<m_contacts.size();i++) {
			if(m_contacts.elementAt(i).getStatus() != StatusType.Offline) {
				m_outSignalQueue.addSignal(new MessageSignal(message, Message.nextID(), m_userName, m_contacts.elementAt(i).getUserName()));
			}
		}
	}
	
	public boolean ping() {
		if(!m_awaitingResponse && m_timeElapsed >= Globals.PING_INTERVAL) {
			m_timeElapsed = 0;
			m_awaitingResponse = true;
			m_outSignalQueue.addSignal(new Signal(SignalType.Ping));
			return true;
		}
		return false;
	}
	
	public void pong() {
		m_timeElapsed = 0;
		m_awaitingResponse = false;
	}
	
	public boolean awaitingResponse() { return m_awaitingResponse; }
	
	public int timeElapsed() { return m_timeElapsed; }
	
	public void addTime(long time) {
		if(time <= 0) { return; }
		m_timeElapsed += time;
	}
	
	public boolean timeout() {
		return m_awaitingResponse && m_timeElapsed >= Globals.CONNECTION_TIMEOUT;
	}

	public boolean setState(byte newState) {
		if(!ClientState.isValid(newState)) { return false; }
		if(m_state == newState) { return true; }
		
		if(m_state == ClientState.Disconnected) {
			if(newState == ClientState.Connected) {
				m_state = newState;
				m_status = StatusType.Offline;
				return true;
			}
			else {
				return false;
			}
		}
		else if(m_state == ClientState.Connected) {
			if(newState == ClientState.AwaitingAuthentication) {
				m_state = newState;
				m_status = StatusType.Offline;
				return true;
			}
			else if(newState == ClientState.Disconnected) {
				m_state = newState;
				m_status = StatusType.Offline;
				return true;
			}
			else {
				return false;
			}
		}
		else if(m_state == ClientState.AwaitingAuthentication) {
			if(newState == ClientState.Online) {
				m_state = newState;
				m_status = StatusType.Online;
				return true;
			}
			else if(newState == ClientState.Disconnected) {
				m_state = newState;
				m_status = StatusType.Offline;
			}
		}
		else if(m_state == ClientState.Online) {
			if(newState == ClientState.Disconnected) {
				m_state = newState;
				m_status = StatusType.Offline;
			}
		}
		return true;
	}
	
	public void readSignal() {
		m_inSignalQueue.readSignal();
	}
	
}
