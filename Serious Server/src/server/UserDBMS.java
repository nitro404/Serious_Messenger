package server;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
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
					"UserName		VARCHAR(32)		NOT NULL," +
					"Password		VARCHAR(32)		NOT NULL," +
					"LastLogin		DATETIME		NOT NULL," +
					"JoinDate		DATETIME		NOT NULL," +
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
	
	/*
	// GET USER INFO
	"SELECT * FROM User WHERE UserName = '" + userName + "';"

	// GET USER PROFILE
	"SELECT * FROM UserProfile WHERE UserName = '" + userName + "';"

	// GET CONTACTS
	"SELECT * FROM UserContact WHERE UserName = '" + userName + "';"
	*/
	
	public void createUser(String userName, String password) {
		try {
			stmt.executeUpdate(
				"INSERT INTO " + userDataTableName + " VALUES(" +
					"'" + userName + "', " +
					"'" + password + "', " +
					"CURRENT_TIMESTAMP, " +
					"CURRENT_TIMESTAMP" +
				")"
			);
			
			m_logger.addInfo("Added user " + userName + " to database");
		}
		catch(SQLException e) {
			m_logger.addError("Error adding user " + userName + " to database: " + e.getMessage());
		}
	}
	
	public void removeUser(String userName) {
		if(userName == null) { return; }
		String temp = userName.trim();
		if(temp.length() == 0) { return; }
		
		try {
			stmt.executeUpdate("DELETE FROM " + userProfileTableName + " WHERE UserName = '" + userName + "'");
			stmt.executeUpdate("DELETE FROM " + userContactTableName + " WHERE UserName = '" + userName + "' OR Contact = '" + userName + "'");
			stmt.executeUpdate("DELETE FROM " + userGroupTableName + " WHERE UserName = '" + userName + "'");
			stmt.executeUpdate("DELETE FROM " + userDataTableName + " WHERE UserName = '" + userName + "'");
			
			m_logger.addInfo("Removed user " + userName + " from database");
		}
		catch(SQLException e) {
			m_logger.addError("Error removing " + userName + " from database: " + e.getMessage());
		}
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
	
	public boolean addUserContact(String userName, String contactUserName) {
		try {
			// verify that the contact exists
			SQLResult contactResult = new SQLResult(stmt.executeQuery(
				"SELECT * FROM " + userDataTableName + " " +
				"WHERE UserName = '" + contactUserName + "'"
			));
			if(!(contactResult.getRowCount() == 1)) { return false; }
			
			boolean contactAdded = stmt.executeUpdate(
				"INSERT INTO " + userContactTableName + " VALUES(" +	
					"'" + userName + "', " +
					"'" + contactUserName + "', " +
					"'', " +
					"0" +
				")"
			) != 0;
			
			m_logger.addInfo("User " + userName + " added contact " + contactUserName);
			
			return contactAdded;
		}
		catch(SQLException e) {
			m_logger.addError("Error adding contact " + contactUserName + " for user " + userName + ": " + e.getMessage());
		}
		return false;
	}
	
	public void removeUserContact(String userName, String contact) {
		try {
			stmt.executeUpdate(
				"DELETE FROM " + userContactTableName + " " +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contact + "'"
			);
			
			m_logger.addInfo("User " + userName + " removed contact " + contact);
		}
		catch(SQLException e) {
			m_logger.addError("Error removing contact " + contact + " for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void blockUserContact(String userName, String contact) {
		setBlockUserContact(userName, contact, 1);
	}
	
	public void unblockUserContact(String userName, String contact) {
		setBlockUserContact(userName, contact, 0);
	}
	
	public void setBlockUserContact(String userName, String contact, int blocked) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userContactTableName + " " +
					"SET Blocked = " + blocked +
				"WHERE UserName = '" + userName + "' " +
					"AND Contact = '" + contact + "'"
			);
			
			m_logger.addInfo("User " + userName + ((blocked == 0) ? " un" : " ") + "blocked contact " + contact);
		}
		catch(SQLException e) {
			m_logger.addError("Error " + ((blocked == 0) ? " un" : " ") + "blocking contact " + contact + " for user " + userName);
		}
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
			}
			tableModel.setColumnIdentifiers(columnNames);
			
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
	}
	
}
