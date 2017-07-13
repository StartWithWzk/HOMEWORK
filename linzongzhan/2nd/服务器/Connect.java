package ServerSocket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Connect extends Thread {
	
	public static Map<String,Socket> socketMap = new HashMap<>();
	
	private ServerSocket server = null;
	
	public Connect (ServerSocket server) {
		this.server = server;
	}
	
	public void run () {
		while (true) {
			try {
				Socket socket = server.accept();
				
				InetAddress ia = socket.getInetAddress();
				String ip = ia.getHostAddress();
				socketMap.put(ip, socket);
				
				Thread message = new Message (socket);
				message.start();
				System.out.println("客户端" + ip + "连接成功");
				
				String ll;
				
				Set<String> setKey = socketMap.keySet();
				Iterator<String> it = setKey.iterator();
				while (it.hasNext()) {
					 //   ll = it.next();
					if(ip.equals(ll = it.next()) == true) {
					} else {
						OutputStream os = socketMap.get(ll).getOutputStream();
						PrintWriter pw = new PrintWriter(os);
						pw.println("新上线的用户：" + ip);
						pw.flush();
						
						//os.close();
						//pw.close();
						
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
