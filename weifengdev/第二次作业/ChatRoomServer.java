import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 黄伟烽 on 2017/7/13.
 */
public class ChatRoomServer {

    private Selector selector = null;
    static final int port = 9999;
    private Charset charset = Charset.forName("UTF-8");
    //用来记录在线人数，以及昵称
    private static HashSet<String> users = new HashSet<String>();
    private static HashMap<String, SelectionKey> usersMap = new HashMap<>();

    private static String USER_EXIST = "system message: user exist or your name contains \" \", please change a name";
    //自定义协议格式，与客户端协商好
    private static String USER_CONTENT_SPILIT = "###";
    private static String PRIVATE_CHAT = "private chat: ";

    private static boolean flag = false;

    public void init() throws IOException
    {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        //非阻塞的方式
        server.configureBlocking(false);
        //注册到选择器上，设置为监听状态
        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening now...");

        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey sk = (SelectionKey) keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(server,sk);
            }
        }
    }

    public void dealWithSelectionKey(ServerSocketChannel server,SelectionKey sk) throws IOException {
        if(sk.isAcceptable())
        {
            SocketChannel sc = server.accept();
            //非阻塞模式
            sc.configureBlocking(false);
            //注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
            sc.register(selector, SelectionKey.OP_READ);

            //将此对应的channel设置为准备接受其他客户端请求
            sk.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client :" + sc.getRemoteAddress());
            sc.write(charset.encode("Please input your name."));
        }
        //处理来自客户端的数据读取请求
        if(sk.isReadable())
        {
            //返回该SelectionKey对应的 Channel，其中有数据需要读取
            SocketChannel sc = (SocketChannel)sk.channel();
            ByteBuffer buff = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try
            {
                while(sc.read(buff) > 0)
                {
                    buff.flip();
                    content.append(charset.decode(buff));

                }
                System.out.println("Server is listening from client " + sc.getRemoteAddress() + " data rev is: " + content);
                //将此对应的channel设置为准备下一次接受数据
                sk.interestOps(SelectionKey.OP_READ);
            }
            catch (IOException io)
            {
                sk.cancel();
                if(sk.channel() != null)
                {
                    sk.channel().close();
                }
            }
            if(content.length() > 0)
            {
                String[] arrayContent = content.toString().split(USER_CONTENT_SPILIT);
                //注册用户
                if(arrayContent != null && arrayContent.length ==1) {
                    String name = arrayContent[0];
                    if(users.contains(name) || name.contains(" ")) {
                        sc.write(charset.encode(USER_EXIST));

                    } else {
                        users.add(name);
                        usersMap.put(name, sk);
                        int num = OnlineNum(selector);
                        String message = "welcome "+name+" to chat room! Online numbers:"+num;
                        BroadCast(selector, null, message);
                    }
                }
                //注册完了，发送消息
                else if(arrayContent != null && arrayContent.length >1){
                    //私聊处理
                    if(arrayContent[1].startsWith("@")){
                        int index = arrayContent[1].indexOf(" ");
                        String privateName = arrayContent[1].substring(1, index);
                        String privateMessage = arrayContent[1].substring(index + 1);
                        String senderName = getSenderName(sk);
                        privateMessage = PRIVATE_CHAT + senderName + " say " + privateMessage;
                        if(users.contains(privateName)){
                            privateChat(privateName, privateMessage);
                        }
                        return;
                    }
                    String name = arrayContent[0];
                    String message = content.substring(name.length()+USER_CONTENT_SPILIT.length());
                    message = name + " say " + message;
                    if(users.contains(name)) {
                        //不回发给发送此内容的客户端
                        BroadCast(selector, sc, message);
                    }
                }
            }

        }
    }

    //获取发送人的昵称
    private String getSenderName(SelectionKey sk) {
        Set<String> keySet = usersMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        String sendName = null;
        while(iterator.hasNext()){
            sendName = iterator.next();
            if(sk == usersMap.get(sendName)){
                return sendName;
            }
        }
        return null;
    }

    //发送私聊信息
    private void privateChat(String privateName, String privateMessage) throws IOException{
        SelectionKey key = usersMap.get(privateName);
        Channel targetChannel = key.channel();
        if(targetChannel instanceof SocketChannel){
            SocketChannel dest = (SocketChannel)targetChannel;
            dest.write(charset.encode(privateMessage));
        }
    }

    //要是能检测下线，就不用这么统计了
    public static int OnlineNum(Selector selector) throws IOException{
        int res = 0;
        for(SelectionKey key : selector.keys())
        {
            Channel targetchannel = key.channel();

            if(targetchannel instanceof SocketChannel)
            {
                res++;
            }
        }
        return res;
    }

    //公共聊天室
    public void BroadCast(Selector selector, SocketChannel except, String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys())
        {
            Channel targetchannel = key.channel();
            //如果except不为空，不回发给发送此内容的客户端
            if(targetchannel instanceof SocketChannel && targetchannel != except)
            {
                SocketChannel dest = (SocketChannel)targetchannel;
                dest.write(charset.encode(content));
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        new ChatRoomServer().init();
    }
}
