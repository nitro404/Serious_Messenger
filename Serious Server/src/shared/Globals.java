package shared;

import java.awt.*;

public class Globals {

	// signal constants
	final public static int MAX_USERNAME_LENGTH = 32;
	final public static int MAX_PASSWORD_LENGTH = 32;
	final public static int MAX_NICKNAME_LENGTH = 128;
	final public static int MAX_MESSAGE_LENGTH = 1024;
	final public static int MAX_PERSONAL_MESSAGE_LENGTH = 256;
	final public static int MAX_FONTFACE_LENGTH = 32;
	
	// gui constants
	final public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
	final public static Color DEFAULT_TEXT_COLOUR = new Color(0, 0, 0);
	
	// networking constants
	final public static int DEFAULT_PORT = 25500;
	final public static String DEFAULT_HOST = "localhost";
	final public static long QUEUE_INTERVAL = 100;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 2500;
	final public static long CONNECTION_TIMEOUT = 10000;
	
}
