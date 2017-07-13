package ServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	Thread connect = null;
	public static ServerSocket server = null;
	
	public void launch () {
		try {
			server = new ServerSocket (9856);
			connect = new Connect (server);
			connect.start();
			System.out.println("·þÎñÆ÷Æô¶¯");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
