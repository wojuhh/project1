package 聊天室;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//创建服务器的图形界面

public class ServerFrame extends JFrame implements ActionListener{
	//版本编号
    private static final long serialVersionUID = 1L;
    
    //创建服务器界面
	public void serverView(){
        JFrame jFrame=new JFrame();
        jFrame.setSize(300, 500);
        jFrame.setTitle("Server");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        
        JButton jb2=new JButton("刷新列表");
        JButton jb3=new JButton("退出");
        JButton jb=new JButton("所有人退出");
        JPanel jp=new JPanel();
        JPanel topPanel=new JPanel();
        JLabel jLabel=new JLabel("Online List");
        
        String[] string=ManageServerThread.getOnline().split(" ");
        JList<String> jList=new JList<>(string);
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
                serverView();
            }
            
        });
        
        jb3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String selectedUser=jList.getSelectedValue();                
                if (selectedUser!=null) {                    
                    ManageServerThread.exitOne(selectedUser);                    
                    jList.clearSelection();                
                }
            }
            
        });
        
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ManageServerThread.exitAll();
            }
            
        });
        
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.getContentPane().add(topPanel,BorderLayout.NORTH);
        jFrame.getContentPane().add(new JScrollPane(jList),BorderLayout.CENTER);
        jFrame.getContentPane().add(jp,BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }
	
	//构造方法
    public ServerFrame(){
        serverView();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
