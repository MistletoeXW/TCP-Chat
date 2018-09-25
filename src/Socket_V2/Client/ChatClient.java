package Socket_V2.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: TCP-UDPServer
 * @description:
 * @author: xw
 * @create: 2018-09-25 14:36
 */
public class ChatClient extends JFrame {
    private Socket socket ;                  //负责和服务器通信
    private JTextArea sendArea ;        //消息编辑区域
    private JTextArea contentArea ;   //群聊消息显示框
    private String name ;                   //当前用户名称

    public ChatClient(Socket socket,String name)  {
        this.socket = socket ;
        this.name  = name ;
        this.init()   ;       //初始化聊天客户端
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        /*接下来启动单独线程，专门从服务器中读取数据
         *
         */

        ClientThread thread  = new ClientThread(socket,contentArea) ;
        thread.start();
    }

    public void init( )  {
        this.setTitle(name + "的聊天室");
        this.setSize(500,450);
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() ;
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;

        this.setLocation( (x-this.getWidth() )/2, ( y-this.getHeight() )/2 );
        this.setResizable(false);      //不允许用户改变大小

        contentArea = new JTextArea() ;
        contentArea.setLineWrap(true);  //换行方法
        JScrollPane logPanel  = new JScrollPane(contentArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) ;

        sendArea = new JTextArea() ;
        sendArea.setLineWrap(true);    //控制每行显示长度最大不超过界面长度
        JScrollPane sendPanel  = new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) ;

        //创建一个分隔窗格
        JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,logPanel,sendPanel) ;
        splitpane.setDividerLocation(250);
        this.add(splitpane,BorderLayout.CENTER) ;

        //按钮面板

        JPanel bPanel  = new JPanel() ;
        bPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)) ;
        this.add(bPanel,BorderLayout.SOUTH) ;

        JLabel namelabel = new JLabel("昵称： "+this.name+"  ") ;
        bPanel.add(namelabel) ;

        //JButton closeButton = new JButton("关闭") ;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);
                if (a == 1) {
                    System.exit(0); // 关闭
                }
            }
        });
        //bPanel.add(closeButton) ;

        JButton sendButton = new JButton("发送") ;

        sendButton.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String str = sendArea.getText() ;
                SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss") ;
                String time  = formater.format(new Date() ) ;
                String sendStr = name+" "+time+" 说: "+str ;
                PrintWriter out = null ;
                try  {
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream() ) ) ;
                    out.println(sendStr) ;
                    out.flush();
                }catch(Exception e1)  {
                    e1.printStackTrace();
                }
                sendArea.setText("");
            }
        });

        bPanel.add(sendButton) ;
    }

}