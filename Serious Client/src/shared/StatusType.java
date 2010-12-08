package shared;

public class StatusType {
	
	final public static String[] statusTypes = { "Online", "Busy", "Away", "Idle" };
	
	final public static byte Offline = 0;
	final public static byte Online = 1;
	final public static byte Idle = 2;
	final public static byte Away = 3;
	final public static byte Busy = 4;
	
	public static byte getStatus(String status) {
		if(status.equalsIgnoreCase("Offline")) {
			return Offline;
		}
		else if(status.equalsIgnoreCase("Online")) {
			return Online;
		}
		else if(status.equalsIgnoreCase("Busy")) {
			return Busy;
		}
		else if(status.equalsIgnoreCase("Away")) {
			return Away;
		}
		else if(status.equalsIgnoreCase("Idle")) {
			return Idle;
		}
		return Offline;
	}
	
	public static String getStatus(byte status) {
		if(status == Offline) { return "Offline"; }
		else if(status == Online) { return "Online"; }
		else if(status == Busy) { return "Busy"; }
		else if(status == Away) { return "Away"; }
		else if(status == Idle) { return "Idle"; }
		else { return "Offline"; }
	}
	
	public static boolean isValid(int status) {
		return status >= 0 && status <= 4;
	}
	
}
