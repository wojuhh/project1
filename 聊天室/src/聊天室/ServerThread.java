package 聊天室;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//服务器线程

public class ServerThread extends Thread{
    //创建socket
	public Socket socket;
	
	//名字变量
    public String name;
    
    //输入输出流
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    
    //线程是否结束标志
    public boolean flag=true;
    
    //构造方法
    public ServerThread(Socket socket,String name){
        this.socket=socket;
        this.name=name;
    }
    
    //对消息进行反应的方法
    public void action(Message message) throws IOException{
        
    	switch (message.messageType) {
            case "getOnline":
                Message message1=new Message();
                message1.setMessageType("retOnline");
                message1.setGetter(message.sender);
                message1.setContent(ManageServerThread.getOnline());
                oos=new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message1);
                break;
            case "exit":
                ManageServerThread.deletSocket(name);
                flag=false;
                socket.close();
                break;
            case "common":
                Socket socket=ManageServerThread.getSocketByName(message.getter);
                Message message2=new Message();
                message2=message;
                oos=new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message2);
            default:
                break;
        }
    }
    
    //线程一直运行的程序
    public void run(){
        while(true){
            try {
                ois=new ObjectInputStream(socket.getInputStream());
                Message message=(Message) ois.readObject();
                action(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
