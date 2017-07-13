import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 小吉哥哥 on 2017/7/12.
 */
public class Sever {
    //存放在线客户
    public static Map<String, SocketChannel> onlineClientMap = new HashMap<>();

    public static void main(String[] args) {
        selector();
    }
    //处理客户端链接
    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(1024));
    }
    //处理读事件
    public static void handleRead(SelectionKey key) {
        SocketChannel sc = null;
        try {
            sc = (SocketChannel) key.channel();
            ByteBuffer buf = (ByteBuffer) key.attachment();
            System.out.println("信息长度： " + sc.read(buf));
            buf.flip();
            //转为字符串
            String info = byteBufferToString(buf);
            buf.clear();
            //处理客户命令
            handleCommand(info, sc);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                //把断开的客户端删除
                sc.close();
                for (String mKey : onlineClientMap.keySet()) {
                    if (!onlineClientMap.get(mKey).isOpen()) {
                        onlineClientMap.remove(mKey);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static void selector() {
        Selector selector;
        ServerSocketChannel ssc;
        try {
            //准备
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(8076));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            //监听接收和读事件
            while (true) {
                if (selector.select(3000) == 0) continue;
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable() && key.isValid()) {
                        System.out.println("isReadable");
                        handleRead(key);
                    }
                    iter.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //buffer转字符串
    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //处理命令
    public static boolean handleCommand(String info, SocketChannel socketChannel) {
        Charset charset = Charset.forName("UTF-8");
        System.out.println("info is-" + info);
        //上线命令
        if (isOrderContain(info, "login as name : ")) {
            System.out.println(info.split(": ")[1] + " login");
            onlineClientMap.put(info.split(": ")[1], socketChannel);
            try {
                //反馈客户端
                socketChannel.write(charset.encode("=====online====="));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        //发信息命令
        if (isOrderContain(info, " to : ")) {
            String[] infoArray = info.split(" ");
            //发给所有人
            if (infoArray[2].equals("all")) {
                for (String key : onlineClientMap.keySet()) {
                    try {
                        onlineClientMap.get(key).write(ByteBuffer.wrap(info.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //私聊
            } else {
                try {
                    boolean isUserExist = false;
                    if (infoArray[0].equals(infoArray[2])) {
                        socketChannel.write(ByteBuffer.wrap("can not talk to yourself".getBytes()));
                        return true;
                    }
                    for (String key : onlineClientMap.keySet()) {
                        if (key.equals(infoArray[2])) {
                            isUserExist = true;
                            byte[] infoBytes = info.getBytes();
                            onlineClientMap.get(key).write(ByteBuffer.wrap(infoBytes));
                            socketChannel.write(ByteBuffer.wrap(infoBytes));
                        }
                        System.out.println("key= " + key + " and value= " + onlineClientMap.get(key));
                    }
                    if (!isUserExist) socketChannel.write(ByteBuffer.wrap("user not found".getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        //查看在线名单
        if (info.equals("online list")) {
            for (String key : onlineClientMap.keySet()) {
                try {
                    socketChannel.write(ByteBuffer.wrap((key + "\n").getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //判断是否顺序包含
    public static boolean isOrderContain(String text, String target) {
        if (text == null || target == null) {
            return false;
        }
        char[] targetArray = target.toCharArray();
        String textCopy = new String(text.toCharArray());
        for (int i = 0; i < targetArray.length; i++) {
            if (textCopy.indexOf(targetArray[i]) == -1) {
                return false;
            }
            if ((textCopy = textCopy.substring(textCopy.indexOf(targetArray[i]) + 1)).equals("") && i != targetArray.length - 1) {
                return false;
            }

        }

        return true;
    }
}