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
	final public static int DEFAULT_FONT_SIZE = 12;
	final public static String DEFAULT_FONT_FACE = "Arial";
	final public static Color DEFAULT_TEXT_COLOUR = new Color(0, 0, 0);
	final public static Font DEFAULT_FONT = new Font(DEFAULT_FONT_FACE, Font.PLAIN, DEFAULT_FONT_SIZE);
	final public static FontStyle DEFAULT_FONTSTYLE = new FontStyle(DEFAULT_FONT_FACE, DEFAULT_FONT_SIZE, false, false, DEFAULT_TEXT_COLOUR);
	
	// networking constants
	final public static int DEFAULT_PORT = 25500;
	final public static String DEFAULT_HOST = "localhost";
	final public static long QUEUE_INTERVAL = 50;
	final public static long CONNECTION_LISTEN_INTERVAL = 75;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 5000;
	final public static long CONNECTION_TIMEOUT = 5000;
	
}
