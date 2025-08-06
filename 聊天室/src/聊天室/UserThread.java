package 聊天室;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JList;

//用户运行的线程

public class UserThread extends Thread{
    //创建socket
	private Socket socket;
    
	//初始名字变量
	public String name;
	
	//创建输入流
    public ObjectInputStream ois;
    
    //创建可选择列表
    public static JList<String> userList;
    
    //创建字符串
    public static String string;
    
    //得到socket方法
    public Socket getSocket(){
        return socket;
    }
    
    //构造方法
    public UserThread(Socket socket,String name){
        this.socket=socket;
        this.name=name;
    }
    
    //一直运行方法
    public void run(){
        //一直监听消息
    	while (true) {
            try {
                ois=new ObjectInputStream(socket.getInputStream());
                Message message=(Message) ois.readObject();
                
                actionUser(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    //对不同消息类型的反应
    public void actionUser(Message message) {
        switch (message.messageType) {
            case "retOnline":
                String[] strings=message.content.split(" ");
                userList=new JList<>(strings);
                userList.setPreferredSize(new Dimension(200, 200));
                
                UserFrame.view();
                break;
            case "common":
                
                UserFrame.addmessage(message.sentime+":");
                UserFrame.addmessage(message.content);
                break;
            case "exit":
            	UserService.closed();
                break;
            default:
                break;
        }
    }
}
