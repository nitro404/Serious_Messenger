package client;

import shared.*;

public class SeriousClient {
	
	public static void main(String[] args) {
		ClientWindow client = new ClientWindow();
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("-connect")) {
				String[] data = args[1].split(":");
				
				if(data.length == 2) {
					String hostName = data[0];
						
					int port = -1;
					try { port = Integer.parseInt(data[1]); }
					catch(NumberFormatException e) { port = Globals.DEFAULT_PORT; }
					client.initialize(hostName, port);
				}
				else {
					client.initialize();
				}
			}
			else {
				client.initialize();
			}
		}
		else {
			client.initialize();
		}
	}
	
}
