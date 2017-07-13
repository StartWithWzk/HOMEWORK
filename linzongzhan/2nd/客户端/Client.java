package ClientSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	public static Socket socket = null;
	
	public void connect () throws UnknownHostException, IOException {
		socket = new Socket("192.168.1.123",9856);
		
		//System.out.println("hahahaha1");
		
		
		ClientReceive t = new ClientReceive(socket);
		
		//System.out.println("hahahaha2");
		t.start();
		//new ClientReceive(socket).start();
		
		
		
		while (true) {
			//Scanner scan = new Scanner(System.in);
		    //String read = scan.nextLine();
			//InputStream is = System.in;
			
			//System.out.println("hahahaha");
			
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String readLine = br.readLine();
		    
			//System.out.println(readLine);
			
		    OutputStream os = socket.getOutputStream();
		    PrintWriter pw = new PrintWriter(os);
		    pw.println(readLine);
		    pw.flush();
		    
		    File file = new File ("Ë½ÁÄ¼ÇÂ¼.txt");
		    if(!file.exists()) {
		    	file.createNewFile();
		    }
		    if(readLine.indexOf(":") != -1) {
		    	Writer w = new FileWriter(file,true);
		    	//String lines = readLine + "\r\n";
		    	//CharSequence cs = lines;
		    	//w.append(cs);
		    	w.write("\r\n" + "ÎÒË½ÁÄ" + readLine);
		    	w.close();
		    }
		    
		   // isr.close();
		   // br.close();
		   // os.close();
		   // pw.close();
		    
		}
		
		//new ClientReceive(socket).start();
		
		
	}
	
}
