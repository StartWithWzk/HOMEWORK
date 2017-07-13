package ClientSocket;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClass {

	public static void main (String[] args) throws UnknownHostException, IOException {
		Client c = new Client ();
		c.connect();
	}
}
