package 聊天室;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

//服务器的主类

public class Server {
	//创建服务器的Socket
    ServerSocket serverSocket;
    
    //创建用户的Hash Map
    private static ConcurrentHashMap <String ,User>userMap=new ConcurrentHashMap<>();
    
    //添加一些初始用户
    static{
        userMap.put("张三", new User("张三", "3"));
        userMap.put("李四", new User("李四","4"));
        userMap.put("123", new User("123", "123"));
    }
    
    //主方法
    public static void main(String[] args) {
        new Server();//启动服务器
    }
    
    //添加用户的方法
    public static void addUser(String name,String key){
        userMap.put(name, new User(name, key));
    }
    
    //得到Hash Map
    public static ConcurrentHashMap <String ,User> getUserMap(){
        return userMap;
    }
    
    //判断尝试进行登陆的用户是否在用户库中（通过名字和密码）
    public boolean isUser(String name,String key){
        User user =userMap.get(name);
        if (user==null) {
            return false;
        }
        if (!user.getKey().equals(key)) {
            return false;
        }
        return true;
    }
    
    //判断是否在用户库中（通过名字）
    public boolean isUser(String name){
        User user =userMap.get(name);
        if (user==null) {
            return false;
        }
        return true;
    }
    
    //构造方法
    public Server(){
        try {
        	//服务器绑定9999接口
            serverSocket=new ServerSocket(9999);
            //创建服务界面
            new ServerFrame();
            //进入死循环
            while (true) {
            	//服务器在9999端口监听
            	//服务器阻塞在这
                Socket socket=serverSocket.accept();
                //创建输入输出流
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                
                User user=(User) ois.readObject();
                Message message=new Message();
                
                //判断用户是否存在
                if (isUser(user.getName(),user.getKey())) {
                    message.messageType="success";
                    oos.writeObject(message);
                    ServerThread thread=new ServerThread(socket, user.getName());
                    thread.start();
                    ManageServerThread.addThread(user.getName(),thread);
                }else{
                    message.messageType="fail";
                    oos.writeObject(message);
                    addUser(user.getName(),user.getKey());
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
