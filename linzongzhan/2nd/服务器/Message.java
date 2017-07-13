package ServerSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class Message extends Thread {
	
	public String ip;
	
	private String goal;
	
	private Socket socket = null;
	
	public Message (Socket socket) {
		this.socket = socket;
	}

	public void run () {
		while (true) {
			try {
				dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void dispose () throws IOException {
	/*	InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader (isr);
		String line = br.readLine();
		System.out.println(line);  */
		//System.out.println(socket.getInetAddress().getHostAddress());
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader (is);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		
		//is.close();
		//isr.close();
		//br.close();
		
		//System.out.println(socket.getInetAddress().getHostAddress());
		//System.out.println("hahahaha");
		
		ip = socket.getInetAddress().getHostAddress();
		
		Set setKey = Connect.socketMap.keySet();
		int i = 0;
		Iterator<String> it = setKey.iterator();
		while(it.hasNext()) {
			if(line.indexOf(goal = it.next()) != -1) {
				i = -1;
				break;
			}
		}
		
		if(i == 0) {
			Collection<Socket> socketValus = Connect.socketMap.values();
			Iterator<Socket> its = socketValus.iterator();
			while (its.hasNext()) {
			//	OutputStream oss = its.next().getOutputStream();
			//	PrintWriter pw = new PrintWriter(oss);
			//	pw.println(line);
			//	pw.flush();
			//“‘œ¬“—–ﬁ∏ƒ£¨Œ¥≤‚ ‘	
				String ll;
				
				Set<String> setKeys = Connect.socketMap.keySet();
				Iterator<String> itt = setKeys.iterator();
				while (itt.hasNext()) {
					    ll = itt.next();
					//if(ip.equals(ll) == true) {
					//	OutputStream oss1 = its.next().getOutputStream();
					//	PrintWriter pw1 = new PrintWriter(oss1);
					//	pw1.println("Œ“ÀΩ¡ƒ" + ip + ":" + line);
					//} else {
						OutputStream oss = its.next().getOutputStream();
						PrintWriter pw = new PrintWriter(oss);
						pw.println(ip + ":" + line);
						pw.flush();
						
						//oss.close();
						//pw.close();
						
					//}
				}
			}
		}
		else {
			Socket goalSocket = Connect.socketMap.get(goal);
			
			OutputStream os = goalSocket.getOutputStream();
			PrintWriter pw1 = new PrintWriter(os);
			
			String newline = line.replaceFirst(goal + ":","");
			
			pw1.println(ip +"ÀΩ¡ƒƒ„" + ":" + newline);
			pw1.flush();
			
			//os.close();
			//pw1.close();
			
		}
		
		
	}
}
