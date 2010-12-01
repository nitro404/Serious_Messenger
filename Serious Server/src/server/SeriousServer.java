package server;

import shared.*;

public class SeriousServer {
	
	public static void main(String[] args) {
		ServerWindow server = new ServerWindow();
		
		if(args.length == 0) {
			server.initialize();
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("-port")) {
				int port = -1;
				try { port = Integer.parseInt(args[1]); }
				catch(NumberFormatException e) { port = Globals.DEFAULT_PORT; }
				server.initialize(port);
			}
			else {
				server.initialize();
			}
		}
	}
	
}
