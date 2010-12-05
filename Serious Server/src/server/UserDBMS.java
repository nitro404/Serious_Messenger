package server;

import java.util.Vector;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import shared.*;
import logger.*;

public class UserDBMS {
	
	private Connection sqlConnection = null;
	private boolean m_connected = false;
	private Statement stmt = null;
	final public static String databaseFileName = "serious.db";
	final public static String userDataTableName = "UserData";
	final public static String userGroupTableName = "UserGroup";
	final public static String userProfileTableName = "UserProfile";
	final public static String userContactTableName = "UserContact";
	private Logger m_logger;
	
	public UserDBMS() {
		
	}
	
	public void initialize(Logger logger) {
		m_logger = logger;
	}
	
	public void connect() {
		m_logger.addInfo("Connecting to SQL Database: " + databaseFileName);
		
		try {
			m_connected = true;
			
			DriverManager.registerDriver(new org.sqlite.JDBC());
			sqlConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);

			m_logger.addInfo("Connected to SQL Database");
			
			stmt = sqlConnection.createStatement();
		}
		catch(SQLException e) {
			m_connected = false;
			m_logger.addError("Error connecting to SQL database: " + e.getMessage());
		}
	}
	
	public void disconnect() {
		try {
			m_connected = false;
			stmt = null;
			sqlConnection.close();
			
			m_logger.addInfo("Disconnected from SQL Database");
		}
		catch(SQLException e) {
			m_logger.addError("Error disconnecting from SQL database: " + e.getMessage());
		}
	}
	
	public boolean isConnected() {
		return m_connected;
	}
	
	public void resetTables() {
		dropTables();
		createTables();
	}
	
	public void dropTables() {
		dropUserProfileTable();
		dropUserContactTable();
		dropUserGroupTable();
		dropUserDataTable();
	}
	
	public void createTables() {
		createUserDataTable();
		createUserProfileTable();
		createUserGroupTable();
		createUserContactTable();
	}
	
	public void dropUserProfileTable() {
		try {
			stmt.executeUpdate(
				"DROP TABLE IF EXISTS " + userProfileTableName
			);
			
			m_logger.addInfo("Dropped table " + userProfileTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error dropping table " + userProfileTableName + " table: " + e.getMessage());
		}
	}
	
	public void dropUserContactTable() {
		try {
			stmt.executeUpdate(
				"DROP TABLE IF EXISTS " + userContactTableName
			);
			
			m_logger.addInfo("Dropped table " + userContactTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error dropping table " + userContactTableName + " table: " + e.getMessage());
		}
	}
	
	public void dropUserGroupTable() {
		try {
			stmt.executeUpdate(
				"DROP TABLE IF EXISTS " + userGroupTableName
			);
			
			m_logger.addInfo("Dropped table " + userGroupTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error dropping table " + userGroupTableName + " table: " + e.getMessage());
		}
	}
	
	public void dropUserDataTable() {
		try {
			stmt.executeUpdate(
				"DROP TABLE IF EXISTS " + userDataTableName 
			);
			
			m_logger.addInfo("Dropped table " + userDataTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error dropping table " + userDataTableName + " table: " + e.getMessage());
		}
	}
	
	public void createUserDataTable() {
		try {
			stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS " + userDataTableName + " (" +
					"UserName			VARCHAR(32)		NOT NULL," +
					"Password			VARCHAR(32)		NOT NULL," +
					"NickName			VARCHAR(128)	NOT NULL," +
					"PersonalMessage	VARCHAR(256)	NOT NULL," +
					"Status				TINYINT			NOT NULL," +
					"LastLogin			DATETIME		NOT NULL," +
					"JoinDate			DATETIME		NOT NULL," +
					"PRIMARY KEY(UserName)" +
				")"
			);
			
			m_logger.addInfo("Created table " + userDataTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error creating table " + userDataTableName + " table: " + e.getMessage());
		}
	}
	
	public void createUserGroupTable() {
		try {
			stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS " + userGroupTableName + " (" +
					"UserName		VARCHAR(32)		NOT NULL," +
					"GroupName		VARCHAR(32)		NOT NULL," +
					"PRIMARY KEY(UserName, GroupName)," +
					"FOREIGN KEY(UserName) REFERENCES " + userDataTableName + "(UserName)" +
				")"
			);
			
			m_logger.addInfo("Created table " + userGroupTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error creating table " + userGroupTableName + " table: " + e.getMessage());
		}
	}
	
	public void createUserProfileTable() {
		try {
			stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS " + userProfileTableName + " (" +
					"UserName		VARCHAR(32)		NOT NULL," +
					"FirstName		VARCHAR(32)		NOT NULL," +
					"MiddleName		VARCHAR(32)," +
					"LastName		VARCHAR(32) 	NOT NULL," +
					"Gender			CHAR			NOT NULL," +
					"BirthDate		DATETIME		NOT NULL," +
					"Email			VARCHAR(64)," +
					"HomePhone		VARCHAR(10)," +
					"MobilePhone	VARCHAR(10)," +
					"WorkPhone		VARCHAR(10)," +
					"Country		VARCHAR(32)		NOT NULL," +
					"StateProv		VARCHAR(32)		NOT NULL," +
					"City			VARCHAR(32)		NOT NULL," +
					"ZipPostal		VARCHAR(6)		NOT NULL," +
					"PRIMARY KEY(UserName)," +
					"FOREIGN KEY(UserName) REFERENCES " + userDataTableName + "(UserName)" +
				")"
			);
			
			m_logger.addInfo("Created table " + userProfileTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error creating table " + userProfileTableName + " table: " + e.getMessage());
		}
	}
	
	public void createUserContactTable() {
		try {
			stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS " + userContactTableName + " (" +
					"UserName		VARCHAR(32)		NOT NULL," +
					"Contact		VARCHAR(32)		NOT NULL," +
					"GroupName		VARCHAR(32)		NOT NULL," +
					"Blocked		INT				NOT NULL DEFAULT 0," +
					"PRIMARY KEY(UserName, Contact)," +
					"FOREIGN KEY(UserName) REFERENCES " + userDataTableName + "(UserName)," +
					"FOREIGN KEY(Contact) REFERENCES " + userDataTableName + "(UserName)," +
					"FOREIGN KEY(GroupName) REFERENCES " + userGroupTableName + "(GroupName)" +
				")"
			);
			
			m_logger.addInfo("Created table " + userContactTableName);
		}
		catch(SQLException e) {
			m_logger.addError("Error creating table " + userContactTableName + " table: " + e.getMessage());
		}
	}
	
	public Vector<UserNetworkData> getUserContacts(String userName, Vector<Client> clients) {
		try {
			// get user's contacts
			SQLResult result = new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userContactTableName + " " +
				"WHERE UserName = '" + userName + "'"
			));
			
			Vector<UserNetworkData> contacts = new Vector<UserNetworkData>();
			for(int i=0;i<result.getRowCount();i++) {
				
				String contactUserName = result.getElement(i, 1);
				
				// check to see if the current contact has the user blocked
				boolean contactHasUserBlocked = false;
				SQLResult result2 = new SQLResult(stmt.executeQuery(
					"SELECT * FROM " + userContactTableName + " " +
					"WHERE UserName = '" + contactUserName + "' " +
						"AND Contact = '" + userName + "'"
				));
				
				// get the blocked value from the query
				if(result2.getRowCount() != 0) {
					try { contactHasUserBlocked = Integer.parseInt(result2.getElement(0, 3)) != 0; }
					catch(NumberFormatException e) { }
				}
				
				if(!contactHasUserBlocked) {
					// check to see if the user has the current contact blocked
					boolean contactBlocked = false;
					try { contactBlocked = Integer.parseInt(result.getElement(i, 3)) != 0; }
					catch(NumberFormatException e) { }
					
					//String contactGroup = result.getElement(i, 2);
					
					// check to see if the current contact is online
					byte contactStatus = StatusType.Offline;
					Client client = null;
					for(int j=0;j<clients.size();j++) {
						String clientUserName = clients.elementAt(j).getUserName();
						if(clientUserName != null && clientUserName.equalsIgnoreCase(contactUserName)) {
							client = clients.elementAt(j);
							contactStatus = StatusType.Online;
							break;
						}
					}
					
					// store the current contact's information
					contacts.add(new UserNetworkData(contactUserName, "", "", contactStatus, Globals.DEFAULT_FONTSTYLE, contactBlocked, (client == null) ? null : client.getIPAddress(), (client == null) ? 0 : client.getPort()));
				}
				
			}
			
			m_logger.addInfo("Collected contact list for user " + userName);
			
			return contacts;
		}
		catch(SQLException e) {
			m_logger.addError("Error collecting contacts for user " + userName + ": " + e.getMessage());
		}
		return null;
	}
	
	public boolean createUser(String userName, String password) {
		try {
			// verify that the user does not already exist
			if(new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userDataTableName + " " +
				"WHERE UserName = '" + userName + "' " 
			)).getRowCount() != 0) { return false; }
			
			boolean userCreated = stmt.executeUpdate(
				"INSERT INTO " + userDataTableName + " VALUES(" +
					"'" + userName + "', " +
					"'" + password + "', " +
					"'" + userName + "', " +
					"'', " +
					"'" + StatusType.Offline + "', " +
					"CURRENT_TIMESTAMP, " +
					"CURRENT_TIMESTAMP" +
				")"
			) != 0;
			
			if(userCreated) {
				m_logger.addInfo("Added user " + userName + " to database");
			}
			
			return userCreated;
		}
		catch(SQLException e) {
			m_logger.addError("Error adding user " + userName + " to database: " + e.getMessage());
		}
		return false;
	}
	
	public boolean deleteUser(String userName) {
		if(userName == null) { return false; }
		
		boolean userDeleted = false;
		
		try {
			userDeleted = stmt.executeUpdate("DELETE FROM " + userProfileTableName + " WHERE UserName = '" + userName + "'") != 0 || userDeleted;
			userDeleted = stmt.executeUpdate("DELETE FROM " + userContactTableName + " WHERE UserName = '" + userName + "' OR Contact = '" + userName + "'") != 0  || userDeleted;
			userDeleted = stmt.executeUpdate("DELETE FROM " + userGroupTableName + " WHERE UserName = '" + userName + "'") != 0 || userDeleted;
			userDeleted = stmt.executeUpdate("DELETE FROM " + userDataTableName + " WHERE UserName = '" + userName + "'") != 0 || userDeleted;
			
			if(userDeleted) {
				m_logger.addInfo("Deleted user " + userName + " from database");
			}
		}
		catch(SQLException e) {
			m_logger.addError("Error deleting " + userName + " from database: " + e.getMessage());
		}
		
		return userDeleted;
	}

	public void createUserProfile(String userName, String firstName, String middleName, String lastName, char gender, String birthDate, String email, String homePhone, String mobilePhone, String workPhone, String country, String stateProvince, String zipPostalCode) {
		try {
			stmt.executeUpdate(
				"INSERT INTO " + userProfileTableName + " VALUES(" +
					"'" + userName + "', " +
					"'" + firstName + "', " +
					"'" + middleName + "', " +
					"'" + lastName + "', " +
					"'" + gender + "', " + 
					"'" + birthDate + "', " +
					"'" + email + "', " +
					"'" + homePhone + "', " +
					"'" + mobilePhone + "', " +
					"'" + workPhone + "', " +
					"'" + country + "', " +
					"'" + stateProvince + "', " +
					"'" + zipPostalCode + "'" +
				")"
			);
			
			m_logger.addInfo("Created profile for user " + userName);
		}
		catch(SQLException e) {
			m_logger.addError("Error creating profile for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void updateUserProfile(String userName, String firstName, String middleName, String lastName, char gender, String birthDate, String email, String homePhone, String mobilePhone, String workPhone, String country, String stateProvince, String zipPostalCode) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userProfileTableName + " " +
					"SET FirstName = '" + firstName + "', " + 
					"MiddleName = '" + middleName + "', " +
					"LastName = '" + lastName + "', " +
					"Gender = '" + gender + "', " +
					"BirthDate = '" + birthDate + "', " +
					"Email = '" + email + "', " +
					"HomePhone = '" + homePhone + "', " +
					"MobilePhone = '" + mobilePhone + "', " +
					"WorkPhone = '" + workPhone + "', " +
					"Country = '" + country + "', " +
					"StateProv = '" + stateProvince + "', " +
					"ZipPostal = '" + zipPostalCode + "' " +
				"WHERE UserName = '" + userName + "'"
			);
			
			m_logger.addInfo("Updated profile for user " + userName);
		}
		catch(SQLException e) {
			m_logger.addError("Error updating profile for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void removeUserProfile(String userName) {
		try {
			stmt.executeUpdate(
				"DELETE FROM " + userProfileTableName + " " +
				"WHERE UserName = '" + userName + "'"
			);
			
			m_logger.addInfo("Removed profile for user " + userName);
		}
		catch(SQLException e) {
			m_logger.addError("Error removing profile for user " + userName + ": " + e.getMessage());
		}
	}
	
	public boolean userLogin(String userName, String password) {
		try {
			boolean authenticated = stmt.executeUpdate(
				"UPDATE " + userDataTableName + " " +
					"SET LastLogin = CURRENT_TIMESTAMP " +
				"WHERE UserName = '" + userName + "' " +
					"AND Password = '" + password + "'"
			) != 0;
			
			if(authenticated) {
				m_logger.addInfo("User " + userName + " logged in");
			}
			
			return authenticated;
		}
		catch(SQLException e) {
			m_logger.addError("Error processing login request for user " + userName + ": " + e.getMessage());
		}
		return false;
	}
	
	public void userLogout(String userName) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userDataTableName + " " +
					"SET LastLogin = CURRENT_TIMESTAMP " +
				"WHERE UserName = '" + userName + "'"
			);
			
			m_logger.addInfo("User " + userName + " logged out");
		}
		catch(SQLException e) {
			m_logger.addError("Error processing logout request for user " + userName + ": " + e.getMessage());
		}
	}

	public boolean changeUserPassword(String userName, String oldPassword, String newPassword) {
		try {
			boolean passwordChanged = stmt.executeUpdate(
				"UPDATE " + userDataTableName + " " +
					"SET Password = '" + newPassword + "' " +
				"WHERE UserName = '" + userName + "' " +
					"AND Password = '" + oldPassword + "'"
			) != 0;
			
			if(passwordChanged) {
				m_logger.addInfo("Changed password for user " + userName);
			}
			
			return passwordChanged;
		}
		catch(SQLException e) {
			m_logger.addError("Error changing password for user " + userName + ": " + e.getMessage());
		}
		return false;
	}
	
	public UserNetworkData addUserContact(String userName, String contactUserName) {
		try {
			// verify that the contact exists
			SQLResult contactResult = new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userDataTableName + " " +
				"WHERE UserName = '" + contactUserName + "'"
			));
			if(!(contactResult.getRowCount() == 1)) { return null; }
			
			// add the contact to the database
			boolean contactAdded = stmt.executeUpdate(
				"INSERT INTO " + userContactTableName + " VALUES(" +	
					"'" + userName + "', " +
					"'" + contactUserName + "', " +
					"'', " +
					"0" +
				")"
			) != 0;
			
			if(contactAdded) {
				m_logger.addInfo("User " + userName + " added contact " + contactUserName);
			}
			
			return new UserNetworkData(contactUserName, null, null, StatusType.Offline, Globals.DEFAULT_FONTSTYLE);
		}
		catch(SQLException e) {
			m_logger.addError("Error adding contact " + contactUserName + " for user " + userName + ": " + e.getMessage());
		}
		return null;
	}
	
	public boolean deleteUserContact(String userName, String contactUserName) {
		try {
			// delete the contact from the database
			boolean contactDeleted = stmt.executeUpdate(
				"DELETE FROM " + userContactTableName + " " +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contactUserName + "'"
			) != 0;
			
			if(contactDeleted) {
				m_logger.addInfo("User " + userName + " deleted contact " + contactUserName);
			}
			
			return contactDeleted;
		}
		catch(SQLException e) {
			m_logger.addError("Error deleting contact " + contactUserName + " for user " + userName + ": " + e.getMessage());
		}
		return false;
	}
	
	public boolean userHasContactBlocked(String userName, String contactUserName) {
		try {
			SQLResult result = new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userContactTableName + " " +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contactUserName + "'"
			));
			
			int columnIndex = -1;
			for(int i=0;i<result.getColumnCount();i++) {
				if(result.getHeader(i).equalsIgnoreCase("Blocked")) {
					columnIndex = i;
					break;
				}
			}
			
			if(columnIndex == -1) { return false; }
			
			if(result.getRowCount() == 0) { return false; }
			
			boolean blocked = false;
			try { blocked = Integer.parseInt(result.getElement(0, columnIndex)) == 1; }
			catch(NumberFormatException e) { return false; }
			
			return blocked;
		}
		catch(SQLException e) { }
		return false;
	}
	
	public int setBlockUserContact(String userName, String contactUserName, boolean blocked) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userContactTableName + " " +
					"SET Blocked = '" + (blocked ? 1 : 0) + "' " +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contactUserName + "'"
			);
			
			SQLResult result = new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userContactTableName + " " +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contactUserName + "'"
			));
			
			if(result.getRowCount() != 1) { return 2; }
			
			int columnIndex = -1;
			for(int i=0;i<result.getColumnCount();i++) {
				if(result.getHeader(i).equalsIgnoreCase("Blocked")) {
					columnIndex = i;
					break;
				}
			}
			
			if(columnIndex == -1) { return 2; }
			
			int blockedValue = 0;
			try {
				blockedValue = Integer.parseInt(result.getElement(0, columnIndex));
			}
			catch(NumberFormatException e) {
				return 2;
			}
			
			m_logger.addInfo("User " + userName + " " + (blocked ? "" : "un") + "blocked contact " + contactUserName);
			
			return blockedValue;
		}
		catch(SQLException e) {
			m_logger.addError("Error " + (blocked ? "" : "un") + "blocking contact " + contactUserName + " for user " + userName + ": " + e.getMessage());
		}
		return 2;
	}
	
	public int executeUpdate(String query) {
		int updated = -1;
		
		try {
			updated = stmt.executeUpdate(query);
			
			m_logger.addInfo("Executed custom update");
		}
		catch(SQLException e) {
			m_logger.addError("Error executing custom update: " + e.getMessage());
		}
		
		return updated;
	}
	
	public SQLResult executeQuery(String query) {
		try {
			ResultSet rs = stmt.executeQuery(query);
			
			m_logger.addInfo("Executed custom query");
			
			return new SQLResult(rs);
		}
		catch(SQLException e) {
			m_logger.addError("Error executing custom query: " + e.getMessage());
		}
		
		return null;
	}
	
	public void updateTable(String tableName, JTable table) {
		if(tableName == null || table == null) { return; }
		
		try {
			DefaultTableModel tableModel = new DefaultTableModel();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
			ResultSetMetaData meta = rs.getMetaData();
			
			int numberOfColumns = meta.getColumnCount();
			String[] columnNames = new String[numberOfColumns];
			for(int i=1;i<=numberOfColumns;i++) {
				columnNames[i-1] = meta.getColumnName(i);
				tableModel.addColumn(meta.getColumnName(i));
			}
			tableModel.setColumnIdentifiers(columnNames);
			
			table.setModel(tableModel);
			
			String[] rowData;
			while(rs.next()) {
				rowData = new String[numberOfColumns];
				for(int i=1;i<=numberOfColumns;i++) {
					rowData[i-1] = rs.getString(i);
				}
				tableModel.addRow(rowData);
			}
			
			table.setModel(tableModel);
		}
		catch(SQLException e) {
			m_logger.addError("Error updating table " + tableName + ": " + e.getMessage());
		}
		catch(Exception e) { }
	}
	
}
