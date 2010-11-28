package client;

public class ClientState {
	
	final public static byte Disconnected = 0;
	final public static byte Connected = 1;
	final public static byte AwaitingAuthentication = 2;
	final public static byte Online = 3;
	
	public static boolean isValid(byte state) {
		return state >= 0 && state <= 3;
	}
	
}
