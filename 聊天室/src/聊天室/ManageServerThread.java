package 聊天室;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//提供服务器管理线程的方法服务

public class ManageServerThread {
    //利用Hash Map储存多线程
	public static ConcurrentHashMap<String,ServerThread>map=new ConcurrentHashMap<>();
    
	//得到Hash Map方法
    public ConcurrentHashMap<String,ServerThread>getMap(){
        return map;
    }
    
    //通过名字从Hash Map删除相应的线程
    public static void deletSocket(String name){
        map.remove(name);
    }
    
    //往Hash Map中添加线程
    public static void addThread(String name,ServerThread thread){
        map.put(name, thread);
    }
    
    //获得当前在线成员的方法
    public static String getOnline(){
        StringBuilder builder=new StringBuilder();
        for(Map.Entry<String, ServerThread>entry:map.entrySet()){
            builder.append(entry.getKey()+" ");
        }
        return builder.toString();
    }
    
    //通过名字找寻相应的线程的方法
    public static Socket getSocketByName(String Name){
        ServerThread thread=map.get(Name);
        if (thread==null) {
            return null;
        }else{
            return thread.socket;
        }
    }
    
    //在服务器端通过名字强制下线某一位用户
    public static void exitOne(String name){
        Message message=new Message();
        message.setMessageType("exit");
        Socket socket=getSocketByName(name);
        try {
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    //在服务器端强制下线所有在线用户
    public static void exitAll(){
        Message message=new Message();
        message.setMessageType("exit");
        for(Map.Entry<String,ServerThread>entry:map.entrySet()){
            Socket socket1=getSocketByName(entry.getKey());
            try {
                ObjectOutputStream oos=new ObjectOutputStream(socket1.getOutputStream());
                oos.writeObject(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
