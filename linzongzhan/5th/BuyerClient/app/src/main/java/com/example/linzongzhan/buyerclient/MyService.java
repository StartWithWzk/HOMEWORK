package com.example.linzongzhan.buyerclient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MyService extends Service {

    private CustomApplication app;

    private static final String TAG = "MyService";

    private Socket socket;

    private ServiceWay mServiceWay = new ServiceWay();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mServiceWay;
    }

    @Override
    public void onCreate() {


       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   socket = new Socket("192.168.199.56",8899);
                   Log.d(TAG, "onCreate: sockrt连接成功");
               } catch (Exception e) {
                   e.printStackTrace();
                   Log.d(TAG, "onCreate: socket连接失败");
               }
           }
       }).start();

        app = (CustomApplication) getApplication();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onDestroy: Socket关闭失败");
        }

        super.onDestroy();
    }

    class ServiceWay extends Binder {

        public void listen () {

            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                     //        if (socket.isClosed()) {
                     //            break;
                     //        }

                            InputStream is = socket.getInputStream();

                            DataInputStream dis = new DataInputStream(is);
                            String line;
                            byte[] b = new byte[100000];
                            int len = dis.read(b);
                            line = new String(b,0,len,"UTF-8");

                            Log.d(TAG, line);
                            //获得服务器传来的消息
                            Gson gson = new Gson();
                            Guide guide = gson.fromJson(line,Guide.class);
                            app.setGuide(guide);

                            //发送一条已接收到服务器消息的广播
                            Intent intent = new Intent("com.example.broadcasttext.get");
                            sendBroadcast(intent);


                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "listen: 获得服务器传来的消息失败");
                        }
                    }
                }
            }).start();
        }

        public void sendMessage (Command command) {

            command.setIdentity(2);
            Gson gson = new Gson();
            String line = gson.toJson(command);

            try {
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.println(line);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "sendMessage: 发送消息给服务器失败");
            }
        }
    }
}
