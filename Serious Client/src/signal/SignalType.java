package signal;

public class SignalType {
	
	final public static int Invalid = -1;
	final public static int LoginRequest = 0;
	final public static int LoginAuthenticated = 1;
	final public static int Logout = 2;
	final public static int BroadcastLogin = 3;
	final public static int Message = 4;
	final public static int AcknowledgeMessage = 5;
	final public static int UserTyping = 6;
	final public static int ChangeFont = 7;
	final public static int ChangePassword = 8;
	final public static int PasswordChanged = 9;
	final public static int AddContact = 10;
	final public static int ContactAdded = 11;
	final public static int DeleteContact = 12;
	final public static int ContactDeleted = 13;
	final public static int BlockContact = 14;
	final public static int ContactBlocked = 15;
	final public static int ChangeNickname = 16;
	final public static int ChangePersonalMessage = 17;
	final public static int ChangeStatus = 18;
	
	public static boolean isValid(int signalType) {
		return signalType >= -1 && signalType <= 18;
	}
	
}
