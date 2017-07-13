package ClientSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.Socket;

public class ClientReceive extends Thread {

	private Socket socket;
	
	public ClientReceive (Socket socket) throws IOException {
		this.socket = socket;
	}
	
	public void run() {
		while (true) {
			try {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader (is);
				BufferedReader br = new BufferedReader(isr);
				String line = br.readLine();
				
				System.out.println(line);
				
				File file = new File ("Ë½ÁÄ¼ÇÂ¼.txt");
			    if(!file.exists()) {
			    	file.createNewFile();
			    }
			    if(line.indexOf(":") != -1) {
			    	Writer w = new FileWriter(file,true);
			    //	String lines = line + "\r\n";
			    //	CharSequence cs = lines;
			    //	w.append(cs);
			    	w.write("\r\n" + line);
			    	w.close();
			    }
			    
			    //is.close();
			    //isr.close();
			    //br.close();
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
