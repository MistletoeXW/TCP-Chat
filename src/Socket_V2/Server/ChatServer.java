package Socket_V2.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: TCP-UDPServer
 * @description:
 * @author: xw
 * @create: 2018-09-25 14:39
 */
public class ChatServer {
    private List<Socket> sockets = new ArrayList<Socket>() ;    //类集的应用
    public ChatServer() throws IOException {
        ServerSocket ss = new ServerSocket(8888) ;
        System.out.println("服务器已在监听8888端口") ;

        while(true)  {
            Socket socket = ss.accept() ;
            sockets.add(socket) ;
            String ip = socket.getInetAddress().getHostAddress() ;
            System.out.println("新用户进入！ip是"+ip) ;
            Thread thread = new Thread(new ServerRunner(sockets,socket)) ;
            thread.start();
        }
    }

    public static void main(String args[])  {
        try {
            new ChatServer() ;
        } catch(Exception e)  {
            e.printStackTrace();
        }
    }

}
