package 聊天室;

import java.io.Serializable;

//聊天信息的基本属性

public class Message implements Serializable{
    //版本代码
	private static final long serialVersionUID=1L;
    
	//信息的发送者
	public String sender;
    
	//信息的接收者
	public String getter;
    
	//信息的内容
	public String content;
    
	//信息的发送时间
	public String sentime;
    
	//信息的类型
	public String messageType;
    
	//构造方法（通过发送者和信息类型）
	public Message(String sender,String messageType){
        this.sender=sender;
        this.messageType=messageType;
    }
	
	//构造方法
    public Message() {
    }
    
    //设置发送者的方法
    public void setSender(String sender){
        this.sender=sender;
    }
    
    //设置接收者的方法
    public void setGetter(String getter){
        this.getter=getter;
    }
    
    //设置内容的方法
    public void setContent(String content){
        this.content=content;
    }
    
    //设置发送时间的方法
    public void setSentime(String sentime){
        this.sentime=sentime;
    }
    
    //设置信息类型的方法
    public void setMessageType(String messagetype){
        this.messageType=messagetype;
    }
}
