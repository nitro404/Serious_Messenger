package client;

public class StatusType {
	
	final public static int Offline = 0;
	final public static int Online = 1;
	final public static int Idle = 2;
	final public static int Away = 3;
	final public static int Busy = 4;
	final public static int BeRightBack = 5;
	
	public static boolean isValid(int status) {
		return status >= 0 && status <= 5;
	}
	
}
