package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import logger.*;

public class UserDBMS {
	
	private Connection sqlConnection = null;
	private Statement stmt = null;
	final private static String databaseFileName = "serious.db";
	private String userDataTableName = "UserData";
	private String userGroupTableName = "UserGroup";
	private String userProfileTableName = "UserProfile";
	private String userContactTableName = "UserContact";
	private Logger m_logger;
	
	public UserDBMS() {
		
	}
	
	public void initialize(Logger logger) {
		m_logger = logger;
	}
	
	public void connect() {
		m_logger.addInfo("Connecting to SQL Database: " + databaseFileName);
		
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			sqlConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);

			m_logger.addInfo("Connected to SQL Database");
			
			stmt = sqlConnection.createStatement();
		}
		catch(SQLException e) {
			m_logger.addError("Error connecting to SQL database: " + e.getMessage());
		}
	}
	
	public void disconnect() {
		try {
			stmt = null;
			sqlConnection.close();
			
			m_logger.addInfo("Disconnected from SQL Database");
		}
		catch(SQLException e) {
			m_logger.addError("Error disconnecting from SQL database: " + e.getMessage());
		}
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
					"IPAddress		VARCHAR(15)," +
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
		createUser(userName, password, "");
	}
	
	public void createUser(String userName, String password, String ipAddress) {
		try {
			stmt.executeUpdate(
				"INSERT INTO " + userDataTableName + " VALUES(" +
					"'" + userName + "', " +
					"'" + password + "', " +
					"'" + ipAddress + "', " +
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

	public void changeUserPassword(String userName, String oldPassword, String newPassword) {
		try {
			stmt.executeUpdate(
				"UPADTE " + userDataTableName + " " +
					"SET Password = '" + newPassword + "' " +
				"WHERE UserName = '" + userName + "' " +
					"AND Password = '" + oldPassword + "'"
			);
			
			m_logger.addInfo("Changed password for user " + userName);
		}
		catch(SQLException e) {
			m_logger.addError("Error changing password for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void userLogin(String userName, String ipAddress) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userDataTableName + " " +
					"SET LastLogin = CURRENT_TIMESTAMP, " +
					"IPAddress = '" + ipAddress + "' " +
				"WHERE UserName = '" + userName + "'"
			);
			
			m_logger.addInfo("User " + userName + " logged in");
		}
		catch(SQLException e) {
			m_logger.addError("Error processing login request for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void userLogout(String userName) {
		try {
			stmt.executeUpdate(
				"UPDATE " + userDataTableName + " " +
					"SET LastLogin = CURRENT_TIMESTAMP, " +
					"IPAddress = ''" +
				"WHERE UserName = '" + userName + "'"
			);
			
			m_logger.addInfo("User " + userName + " logged out");
		}
		catch(SQLException e) {
			m_logger.addError("Error processing logout request for user " + userName + ": " + e.getMessage());
		}
	}

	public void addContact(String userName, String contact) {
		try {
			stmt.executeUpdate(
				"INSERT INTO " + userContactTableName + " VALUES(" +	
					"'" + userName + "', " +
					"'" + contact + "', " +
					"0" +
				")"
			);
			
			m_logger.addInfo("User " + userName + " added contact " + contact);
		}
		catch(SQLException e) {
			m_logger.addError("Error adding contact " + contact + " for user " + userName + ": " + e.getMessage());
		}
	}
	
	public void removeContact(String userName, String contact) {
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
	
	public void blockContact(String userName, String contact) {
		setBlockContact(userName, contact, 1);
	}
	
	public void unblockContact(String userName, String contact) {
		setBlockContact(userName, contact, 0);
	}
	
	public void setBlockContact(String userName, String contact, int blocked) {
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
	
	public String[][] executeQuery(String query) {
		try {
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//TODO: finish me
			
			/*
			rs.last();
			int numberOfElements = rs.getRow();
			rs.first();
			String[][] result = new String[numberOfElements][rsmd.getColumnCount()];
			
			for(int i=0;i<rsmd.getColumnCount();i++) {
				System.out.println(rsmd.getColumnName(i));
			}
			
			while(rs.next()) {
				for(int i=0;i<rsmd.getColumnCount();i++) {
					System.out.println(rs.getObject(rsmd.getColumnName(i)));
				}
			}
			*/
			
			m_logger.addInfo("Executed custom query");
		}
		catch(SQLException e) {
			m_logger.addError("Error executing custom query: " + e.getMessage());
		}
		
		return null;
	}
	
}
