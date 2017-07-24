package com.example.supplier.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

import com.example.supplier.MyApplication;
import com.example.supplier.R;
import com.example.supplier.View.activity.MainActivity;
import com.example.supplier.uitl.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Charset charset = Charset.forName("UTF-8");
                    Gson gson = new Gson();
                    SocketChannel sc = SocketChannel.open();
                    Selector selector = Selector.open();
                    sc.configureBlocking(false);
                    sc.connect(new InetSocketAddress("192.168.43.47", 8076));
                    sc.register(selector, SelectionKey.OP_READ);
                    Tool.toast("服务已启动,链接中");
                    while (!sc.finishConnect()) {

                    }

                        Tool.toast("订单提醒已开启");
                        String command = "supplier!@#map!@#"+ Tool.getUser().getObjectId();
                        sc.write(charset.encode(command));
                        while (true){
                            if (selector.select(2000) == 0) {
                                continue;
                            }
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            while (iterator.hasNext()){
                                SelectionKey key = iterator.next();
                                if (key.isReadable()) {
                                    Tool.log("订单提醒key.isReadable()");
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    channel.read(buffer);
                                    buffer.flip();
                                    String info = charset.decode(buffer).toString();
                                    Tool.log(info);
                                    try{
                                        Order order = gson.fromJson(info,Order.class);
                                        showNotification();
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                iterator.remove();
                            }
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
    private void showNotification(){
        Intent intent = new Intent(MyApplication.context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MyApplication.context,0,intent,0);
        NotificationManager manager = (NotificationManager) MyApplication.context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MyApplication.context)
                .setContentTitle("你有新的订单接入")
                .setContentText("点击查看")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_rice)
                .build();
        manager.notify(1,notification);
    }
}
