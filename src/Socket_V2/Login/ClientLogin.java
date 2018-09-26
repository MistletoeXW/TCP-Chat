package Socket_V2.Login;

import Socket_V2.Client.ChatClient;
import Socket_V2.OV.User;
import Socket_V2.Tool.AuthTool;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @program: TCP-UDPServer
 * @description:
 * @author: xw
 * @create: 2018-09-25 14:30
 */
public class ClientLogin extends JFrame {
    private JTextField nametext  ;
    private JPasswordField passwordtext ;
    //private Object bPanel;

    public ClientLogin()  {
        this.init() ;       //init方法初始化
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void init() {
        this.setTitle("聊天室登陆");
        this.setSize(380, 230);     //借用成熟美观尺寸
        int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        this.setLocation((x - this.getWidth()) / 2, (y - this.getHeight()) / 2);
        this.setResizable(false);     //不允许用户自行更改大小
        Icon icon = new ImageIcon("login.jpg");
        JLabel label = new JLabel(icon);   //设置登陆界面上边框
        this.add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入登陆信息", TitledBorder.CENTER, TitledBorder.TOP));
        this.add(mainPanel, BorderLayout.CENTER);     //将主面板加入frame
        mainPanel.setLayout(null);
        JLabel namelabel = new JLabel("请输入学号");
        namelabel.setBounds(30, 30, 80, 22);
        mainPanel.add(namelabel);
        nametext = new JTextField();
        nametext.setBounds(115, 30, 120, 22);
        mainPanel.add(nametext);
        JLabel passwordlabel = new JLabel("请输入密码");
        passwordlabel.setBounds(30, 60, 80, 22);
        mainPanel.add(passwordlabel);
        passwordtext = new JPasswordField();
        passwordtext.setBounds(115, 60, 120, 22);
        mainPanel.add(passwordtext);

        //接下来按钮位置排放
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(bPanel, BorderLayout.SOUTH);
        JButton reset = new JButton("重置");
        reset.addActionListener(new ActionListener() {    //为“重置”按钮添加事件监听
            public void actionPerformed(ActionEvent e) {
                nametext.setText("");
                passwordtext.setText("");
            }
        });
        bPanel.add(reset);

        /**下面开始实现提交按钮
         *
         */

        JButton submit = new JButton("登陆");
        submit.addActionListener(new LoginAction(this));  //因为登陆相对复杂，重新为登陆写一个类
        bPanel.add(submit);
    }



    public class LoginAction implements ActionListener {
        private JFrame self ;
        public LoginAction(JFrame self)  {
            this.self = self ;
        }

        public void actionPerformed(ActionEvent e)  {
            AuthTool authTool = new AuthTool();
            //System.out.println("用户名是："+ nametext.getText()+" 密码是："+new String(passwordtext.getPassword())) ;
            if(authTool.getAuth(nametext.getText(),new String(passwordtext.getPassword()))) {

                User user = new User();
                user = authTool.getInfo(nametext.getText());
                try {
                    //开始连接到服务器
                    Socket socket = new Socket("192.168.50.223", 8888);
                    new ChatClient(socket, user.getUserName());
                    //调用dispose方法关闭登陆框
                    self.dispose();
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                    JOptionPane.showConfirmDialog(self, "找不到指定服务器!~", "连接失败", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showConfirmDialog(self, "连接服务器出错，请重试！", "连接失败", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showConfirmDialog(self, "学号或密码错误！", "登录失败", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }

}
    public static void main(String args[])  {
        new ClientLogin() ;
    }
}

