package signal;

public class SignalType {
	
	final public static int Invalid = 0;
	final public static int Ping = 1;
	final public static int Pong = 2;
	final public static int LoginRequest = 3;
	final public static int LoginAuthenticated = 4;
	final public static int Logout = 5;
	final public static int BroadcastLogin = 6;
	final public static int Message = 7;
	final public static int UserTyping = 8;
	final public static int ChangeFont = 9;
	final public static int ChangePassword = 10;
	final public static int PasswordChanged = 11;
	final public static int AddContact = 12;
	final public static int ContactAdded = 13;
	final public static int DeleteContact = 14;
	final public static int ContactDeleted = 15;
	final public static int BlockContact = 16;
	final public static int ContactBlocked = 17;
	final public static int ChangeNickname = 18;
	final public static int ChangePersonalMessage = 19;
	final public static int ChangeStatus = 20;
	final public static int CreateUser = 21;
	final public static int UserCreated = 22;
	final public static int ContactList = 23;
	
	public static boolean isValid(int signalType) {
		return signalType >= 1 && signalType <= 24;
	}
	
}
