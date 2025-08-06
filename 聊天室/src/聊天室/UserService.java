package 聊天室;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

//提供相关的用户服务

public class UserService {
    //创建用户的类
	private static User user =new User();
    
	//创建socket
	private static Socket socket;
    
	//设置是否结束的标志
	private static boolean flag=false;
    
	//创建输入输出流
	private static ObjectInputStream ois;
    private static ObjectOutputStream oos;
    
    //判断是否为合法用户的方法
    public static boolean checkUser(String name,String key){
        
    	user.setName(name);
        user.setKey(key);
        
        try {
            
        	socket =new Socket(InetAddress.getLocalHost(),9999);//这里的IP地址需要改为服务器的IP地址
            oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            ois=new ObjectInputStream(socket.getInputStream());
            Message message=(Message) ois.readObject();
            
            if (message.messageType.equals("success")) {
                UserThread thread=new UserThread(socket, name);
                thread.start();
                flag=true;
            }else{
                socket.close();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return flag;
    }
    
    //获得在线用户的方法
    public static void getOnlineUser(){
        
    	Message message=new Message(user.getName(),"getOnline");
        
    	try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
   
    //用户退出的方法
    public static void closed(){
        
    	Message message=new Message(user.getName(),"exit");
        
    	try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //获得当前时间的方法并输出为字符串
    public static String time(){
        
    	Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        StringBuilder stringBuilder= new StringBuilder("\n");
        
        if (hour<10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hour+":");
        
        if (minute<10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(minute);        
        
        return stringBuilder.toString();
    }
    
    //发送消息的方法
    public static void send(String getter,String contents){
        //设置消息的信息
    	Message message1=new Message(user.getName(), "common");
        message1.setGetter(getter);
        message1.setContent(contents);
        message1.setSentime(time());
        
        try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
