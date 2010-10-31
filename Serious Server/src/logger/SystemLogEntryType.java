package logger;

public class SystemLogEntryType {
	
	final public static int Information = 0;
	final public static int Warning = 1;
	final public static int Error = 2;
	
	final private static String[] typeString = new String[] {
		"Information", "Warning", "Error"
	};
	
	public static boolean isValid(int systemLogEntryType) {
		return systemLogEntryType >= 0 && systemLogEntryType <= 2;
	}
	
	public static String getString(int type) {
		return isValid(type) ? typeString[type] : typeString[0];
	}
	
}
