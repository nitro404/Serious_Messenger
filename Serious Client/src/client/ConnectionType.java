package client;

public class ConnectionType {
	
	final public static int LANHost = 0;
	final public static int LANClient = 1;
	final public static int WANHost = 2;
	final public static int WANClient = 3;
	final public static int RouteThroughServer = 4;
	
	public static boolean isValid(int type) {
		return type >= 0 && type <= 4;
	}
	
}
