package Socket_V2.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @program: TCP-UDPServer
 * @description:
 * @author: xw
 * @create: 2018-09-25 14:39
 */
class ServerRunner implements Runnable  {
    private List<Socket> sockets ;
    private Socket currentSocket ;   //当前socket

    public ServerRunner (List<Socket> sockets, Socket currentSocket)  {
        this.sockets = sockets ;
        this.currentSocket = currentSocket ;
    }

    public void run()  {
        String ip = currentSocket.getInetAddress().getHostAddress() ;
        BufferedReader br = null ;
        try  {
            br = new BufferedReader(new InputStreamReader(currentSocket.getInputStream())) ;
            String str = null ;
            while((str = br.readLine()) != null)  {
                System.out.println(ip+"说"+str) ;
                //往所有的客户端写入信息

                for(Socket temp : sockets)  {
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(temp.getOutputStream())) ;
                    pw.println(str) ;
                    pw.flush();
                }
            }
        }catch(IOException e)  {
            e.printStackTrace();
        }
    }
}