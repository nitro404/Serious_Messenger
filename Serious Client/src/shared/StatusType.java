package shared;

public class StatusType {
	
	final public static byte Offline = 0;
	final public static byte Online = 1;
	final public static byte Idle = 2;
	final public static byte Away = 3;
	final public static byte Busy = 4;
	final public static byte BeRightBack = 5;
	
	public static boolean isValid(int status) {
		return status >= 0 && status <= 5;
	}
	
}
