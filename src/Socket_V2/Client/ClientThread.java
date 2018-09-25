package Socket_V2.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @program: TCP-UDPServer
 * @description:
 * @author: xw
 * @create: 2018-09-25 14:37
 */
//客户端与服务器端通信的线程类
class ClientThread extends Thread  {
    private Socket socket ;
    private JTextArea contentArea ;

    public ClientThread(Socket socket, JTextArea  contentArea)  {
        this.socket = socket ;
        this.contentArea = contentArea ;
    }

    public void run()  {
        BufferedReader br = null ;
        try  {
            br = new BufferedReader(new InputStreamReader( socket.getInputStream())) ;
            String str = null ;
            while( (str = br.readLine()) != null)  {
                System.out.println(str) ;
                contentArea.append(str);
                contentArea.append("\n");
            }
        } catch(IOException e)  {
            e.printStackTrace();
        } finally  {
            if(br != null)  {
                try  {
                    br.close () ;
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }}
