package 聊天室;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//创建用户端的图形界面

public class UserFrame extends JFrame implements ActionListener{
    //版本代码
	private static final long serialVersionUID = 1L;
	
	//设置框架组件变量
	JButton jb1;
    JButton jb2;
    JPanel jp1;
    JPanel jp2;
    public static JTextField jtf;
    public static JTextArea jta;
    public static JFrame jf;
    JTextField jtf1;
    JTextField jtf2;
    public static String name;
    
    //登录界面的实现
    public void first(){
        
    	this.setSize(400, 150);
        this.setTitle("登录界面");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        jp1=new JPanel();
        jp2=new JPanel();
        JLabel jl1=new JLabel("Name:");
        jtf1=new JTextField(30);
        JLabel jl2=new JLabel("key:");
        jtf2=new JTextField(30);
        jb1=new JButton("登录");
        jb2 =new JButton("新建用户");
        
        jp1.setLayout(new GridLayout(2, 2));
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);
        jp2.add(jb1);
        jp2.add(jb2);
        jp1.setBackground(Color.GRAY);
        jp2.setBackground(Color.GRAY);
        
        this.add(jp1,BorderLayout.CENTER);
        this.add(jp2,BorderLayout.SOUTH);
        this.setVisible(true);
        
        jb1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                login();
            }
            
        });
        
        jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               newuser();
            }
            
        });
    }
    
    //构造方法
    public UserFrame(){
        first();
    }
    
    //新建用户功能的实现
    public void newuser(){
        name=jtf1.getText();
        String key=jtf2.getText();
        UserService.checkUser(name,key);
        JOptionPane.showMessageDialog(this,"创建成功,请点击登录","Success",JOptionPane.INFORMATION_MESSAGE);
    }
    
    //登录功能的实现
    public void login(){
        name=jtf1.getText();
        String key=jtf2.getText();
        if(UserService.checkUser(name,key)){
            this.dispose();
            secend();
        }else{
            JOptionPane.showMessageDialog(this, "用户不存在,请输入用户名和密码后点击新建用户", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //用户界面的实现
    public static void view(){        
        
    	JFrame jFrame=new JFrame();
        jFrame.setSize(300, 500);
        jFrame.setTitle("本人："+name);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        
        JButton jb2=new JButton("刷新列表");
        JButton jb3=new JButton("退出");
        JButton jb=new JButton("开始聊天");
        JPanel jp=new JPanel();
        JPanel topPanel=new JPanel();
        JLabel jLabel=new JLabel("Online List");
        
        jLabel.setFont(new Font("Arial", Font.BOLD, 24));
        jp.setLayout(new BorderLayout());
        jp.add(jb,BorderLayout.EAST);
        jp.add(jb2,BorderLayout.WEST);
        jp.add(jb3,BorderLayout.CENTER);
        topPanel.add(jLabel);
        
        jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                jFrame.dispose();
                UserService.getOnlineUser();
            }
            
        });
        
        jb3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                UserService.closed();
            }
            
        });
        
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String selectedUser=UserThread.userList.getSelectedValue();
                if (selectedUser!=null) {
                    new Thread(()->{
                        chatWindow(selectedUser);
                        UserThread.userList.clearSelection();
                    }).start();;
                }
            }
            
        });
        
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.getContentPane().add(topPanel,BorderLayout.NORTH);
        jFrame.getContentPane().add(new JScrollPane(UserThread.userList),BorderLayout.CENTER);
        jFrame.getContentPane().add(jp,BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }
    
    //聊天窗口界面的实现
    public static void chatWindow(String name) {
        
    	jf=new JFrame();
        jtf=new JTextField(30);
        jta=new JTextArea();
        JButton jb=new JButton("发送");
        JPanel jp=new JPanel();
        
        jf.setTitle("Chat with "+name);
        jf.setSize(400, 300);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jta.setEditable(false);
        JScrollPane scrollPane=new JScrollPane(jta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jf.getContentPane().add(scrollPane,BorderLayout.CENTER);

        jp.setLayout(new BorderLayout());
        jp.add(jtf,BorderLayout.CENTER);
        jp.add(jb, BorderLayout.EAST);
        jf.getContentPane().add(jp,BorderLayout.SOUTH);
        jf.isShowing();
        
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String string=jtf.getText();
                addmessage(UserService.time()+":");
                addmessage("You:"+string);
                UserService.send(name,string);
                jtf.setText("");
            }
            
        });
        
        jf.setVisible(true);
    }
    
    //获得在线用户的方法
    public void secend() {
        UserService.getOnlineUser();
    }
    
    //添加信息的方法
    public static void addmessage(String string){
        jta.append(string+"\n");
    }
    
    //
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
