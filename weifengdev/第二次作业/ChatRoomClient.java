/**
 * Created by 黄伟烽 on 2017/7/13.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class ChatRoomClient {

    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null;
    private String name = "";
    private static String USER_EXIST = "system message: user exist or your name contains \" \", please change a name";
    private static String USER_CONTENT_SPILIT = "###";
    private static String PRIVATE_CHAT = "private chat: ";

    public void init() throws IOException
    {
        selector = Selector.open();
        //连接远程主机的IP和端口
        sc = SocketChannel.open(new InetSocketAddress("10.230.179.168",port));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        //开辟一个新线程来读取从服务器端的数据
        new Thread(new ClientThread()).start();
        //在主线程中 从键盘读取数据输入到服务器端
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine())
        {
            String line = scan.nextLine();
            if("".equals(line)) continue; //不允许发空消息
            if("".equals(name)) {
                name = line;
                line = name+USER_CONTENT_SPILIT;
            } else {
                line = name+USER_CONTENT_SPILIT+line;
            }
            sc.write(charset.encode(line));//sc既能写也能读，这边是写
        }

    }
    private class ClientThread implements Runnable
    {
        public void run()
        {
            try
            {
                while(true) {
                    int readyChannels = selector.select();
                    if(readyChannels == 0) continue;
                    Set selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
                    Iterator keyIterator = selectedKeys.iterator();
                    while(keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            }
            catch (IOException io)
            {}
        }

        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if(sk.isReadable())
            {
                //使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
                //sc既能写也能读，这边是读
                SocketChannel sc = (SocketChannel)sk.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content += charset.decode(buff);
                }
                //若系统发送通知名字已经存在，则需要换个昵称
                if(USER_EXIST.equals(content)) {
                    name = "";
                }
                System.out.println(content);
                sk.interestOps(SelectionKey.OP_READ);

                String[] contentArray = content.split(" ");
                String senderName = contentArray[2];

                final String finalContent = content;

                //开启线程保存聊天记录
                if(content.startsWith(PRIVATE_CHAT)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                saveFile(senderName, finalContent);
                        }
                    }).start();
                }

            }
        }
    }

    private void saveFile(String senderName, String finalContent) {
        File file = new File(senderName + "txt");
        FileOutputStream fos = null;
        try{

            fos = new FileOutputStream(file);
            FileChannel channel = fos.getChannel();
            ByteBuffer buffer = Charset.forName("utf8").encode(finalContent);

            int length = 0;
            while((length = channel.write(buffer)) != 0){
                System.out.println("写入文件长度：" + length);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try{
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws IOException
    {
        new ChatRoomClient().init();
    }
}
