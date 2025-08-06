package 聊天室;

import java.io.Serializable;


public class User implements Serializable{
	//版本代码
    private static final long serialVersionUID=1L;
    
    //用户名字
    private String name;
    
    //用户密码
    private String key;
    
    //构造方法（通过名字和密码）
    public User(String name,String key){
        this.name=name;
        this.key=key;
    }
    
    //构造方法
    public User() {
    }
    
    //设置用户名字
    public void setName(String name){
        this.name=name;
    }
    
    //设置用户密码
    public void setKey(String key) {
        this.key=key;
    }
    
    //得到用户名字
    public String getName() {
        return this.name;
    }
    
    //得到用户密码
    public String getKey(){
        return this.key;
    }
}
