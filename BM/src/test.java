import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Wesitu implements ActionListener{
	JFrame f;
	JTextField tf1,tf2;
	JTable oldTable,newTable;
	JScrollPane  spOldTable,spNewTable,spText;
	JPanel pOldPanel,pText,p3,p4,pNewPanel;
	JTextArea ta;
	JLabel l_allocNum,l_recovery;
	JButton b_alloc,b_recover,b_init,b_recoverAll;
	
	ButtonGroup bGroup;
	JRadioButton rb_Dispersed,rb_Continuous;
	
	String newData[][];      	//新数据
	String oldData[][];			//旧数据
	String newData1[][];		//新位示图表格数据
	String oldData1[][];		//旧位示图表格数据
	String newName[];			//列名
	String oldName[];
	int empty[];           		// 空闲盘块相对块号数组  
	int used[];           		// 已分配盘块相对块号数组
	int emptyNum;				// 空闲盘块数
	int usedNum;              	//已分配盘块数
	public Wesitu() {
    	f = new JFrame("位示图");
    	newData = new String[40][17];	//第0列用于存放行号
    	oldData = new String[40][17];
    	newData1 = new String[40][17];
    	oldData1 = new String[40][17];
    	newName = new String[17];
    	oldName = new String[17];
    	newName[0] = "新";				//标识位示图
    	oldName[0] = "旧";
    	for(int i=1;i<17;i++) {     //位标号
			newName[i]=""+(i-1);
    		oldName[i]=""+(i-1);
    	} 
    	emptyNum = 0;
    	usedNum = 0;
    	empty = new int[640];
    	used = new int[640];
    	oldTable = new JTable(oldData1,oldName);
    	newTable = new JTable(newData1,newName);
    	tf1 = new JTextField(5);
    	tf2 = new JTextField(5);
    	ta = new JTextArea(24,26);
    	spOldTable = new JScrollPane(oldTable);    //滚动面板，显示table
    	spNewTable = new JScrollPane(newTable);
    	spText = new JScrollPane(ta);
    	pOldPanel = new JPanel();
    	pText = new JPanel();
    	pNewPanel = new JPanel();
    	p3 = new JPanel();
    	p4 = new JPanel(new GridLayout(2,5,2,2));
    	
    	l_allocNum = new JLabel("请输入磁盘分配块数");
    	l_recovery = new JLabel("请输入要回收的盘块号");
    	
    	b_alloc = new JButton("分配");
    	b_alloc.addActionListener(this);
    	b_recover = new JButton("回收");
    	b_recover.addActionListener(this);
    	b_init = new JButton("初始化");
    	b_init.addActionListener(this);
    	b_recoverAll = new JButton("全部回收");
    	b_recoverAll.addActionListener(this);
    	
    	rb_Continuous = new JRadioButton("连续分配");
    	rb_Dispersed =  new JRadioButton("离散分配");
    	rb_Dispersed.setSelected(true);
    	bGroup = new ButtonGroup();
    	bGroup.add(rb_Dispersed);
    	bGroup.add(rb_Continuous);
    	f.setSize(1500,600);
    	f.setLocationRelativeTo(null);				
    	f.setLayout(new BorderLayout());            
    	
    	pOldPanel.add(spOldTable);
    	pNewPanel.add(spNewTable);
    	pText.add(spText);
    	p3.add(p4);
    	p4.add(l_allocNum);
    	p4.add(tf1);
    	p4.add(b_alloc);
    	p4.add(b_init);
    	p4.add(rb_Continuous);
    	p4.add(l_recovery);
    	p4.add(tf2);
    	p4.add(b_recover);
    	p4.add(b_recoverAll);
    	p4.add(rb_Dispersed);
    	f.add(BorderLayout.NORTH,p3);
    	f.add(BorderLayout.EAST,pText);
    	f.add(BorderLayout.WEST,pOldPanel);
    	f.add(BorderLayout.CENTER,pNewPanel);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setVisible(true);
    	newtu();	
    	
     }
	
    public void newtu() {       //初始化位示图
    	tf1.setText("");
		tf2.setText("");
		ta.setText("");
		int k;
		for(int i=0;i<40;i++)
			for(int j=0;j<17;j++) {
					if(j == 0) {	//行号
						newData[i][j] = ""+i;
						oldTable.setValueAt(newData[i][j],i,j);		
						newTable.setValueAt(newData[i][j],i,j);
					}
					else {
						k = (int)(Math.random()*2);      
						newData[i][j] = ""+k;
						oldTable.setValueAt(""+k,i,j);		
						newTable.setValueAt(""+k, i, j);
					}
				}
		oldData = newData.clone();
		find();
		JOptionPane.showMessageDialog(null, "初始化完成,当前空闲块数"+emptyNum);
	}
    
    public void upDataOld() {	//更新旧表格
    	for(int i = 0;i<40;i++)
			oldData1[i] = oldData[i].clone();
    	for(int i=0;i<40;i++)
    		for(int j=1;j<17;j++) {
				oldTable.setValueAt(oldData1[i][j], i, j);
    		}
    }
    
	public void find() {    	//更新空闲块和使用块数据
		int m;        		
		emptyNum = 0;         
		usedNum = 0;
		for(int i=0;i<40;i++)
			for(int j=1;j<17;j++) {
				m = i*16+j-1;                
				if(newData[i][j].equals("0")) 
					empty[emptyNum++] = m;              
				else 
					used[usedNum++] = m;
			}
	}
	public void change() {			//更新新表格
		for(int i = 0;i<40;i++)
			oldData1[i] = oldData[i].clone();
		for(int i=0;i<40;i++)
			for(int j=1;j<17;j++) {
				if(!oldData[i][j].equals(newData[i][j])) {
					String s1 = newData[i][j]+"*";
					newTable.setValueAt(s1, i, j);
				}
				else {
					newTable.setValueAt(oldData1[i][j], i, j);
				}
			}
	}
	public void dispersedAlloc() {      //离散分配
		for(int i = 0;i<40;i++)
			oldData[i] = newData[i].clone();
		upDataOld();
		int n=0;
		try {
			n = Integer.parseInt(tf1.getText());	//获取用户输入
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "请输入有效整数","输入错误",JOptionPane.ERROR_MESSAGE);
		}
		tf2.setText("");
		String result = "";
		ta.setText("");
		if(emptyNum-n<0) {
			JOptionPane.showMessageDialog(null, "空间不足,当前剩余空闲块数"+emptyNum);
			return;
		}
		int j,k;
	  	result = "分配结果：\n";
	  	int x = usedNum;
	  	for(int i=0;i<n;i++) {
	  		j = empty[i]/16;        //行号
	  		k = empty[i]-j*16;      //列号
	  	    used[x+i] = empty[i];   //更新used数组  
  		    newData[j][k+1] = "1";                
  		    result+="相对块号:"+empty[i]+"柱面号:"+empty[i]/16+"磁道号:"+(empty[i]%16)/4+"扇区号:"+(empty[i]%16)%4+"\n";
	  		emptyNum--;               
	  		usedNum++;
	  	}
	  	for(int t=0;t<emptyNum;t++) {          
	  		empty[t] = empty[t+n];	//更新empty数组
	  	}
	  	result += "空闲块个数为:"+emptyNum+"\n";
	  	ta.append(result);
	  	change();
	}
	public void continuousAlloc() {	//连续分配
		for(int i = 0;i<40;i++)
			oldData[i] = newData[i].clone();
		upDataOld();
		int n=0;
		try {
			n = Integer.parseInt(tf1.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "请输入有效整数","输入错误",JOptionPane.ERROR_MESSAGE);
		}
		
		tf2.setText("");
		String result = "";
		ta.setText("");
		if(emptyNum-n<0) {
			JOptionPane.showMessageDialog(null, "空间不足,当前剩余空闲块数"+emptyNum);
			return;
		}
		int j,k;
	  	result = "分配结果：\n";
	  	int x = usedNum;
	  	int index=0,m=0;
	  	for(m=0;m<emptyNum;)
	  	{
	  		if(empty[m+n-1] == empty[m]+n-1) {	//查找符合连续分配条件的第一个元素下标位置
	  			index=m;
	  			break;
	  		}
	  		m++;
	  	}
	  	if(m == emptyNum) {
	  		JOptionPane.showMessageDialog(null, "连续空间不足,无法分配，可尝试离散分配");
	  		return;
	  	}
	  	for(int i = 0;i<n;i++) {
	  		j=empty[i+index]/16;   			
	  		k = empty[i+index]-j*16;       
	  	    used[x+i] = empty[i+index];     //从index开始分配盘块
  		    newData[j][k+1] = "1"; 
  		    result+="相对块号:"+empty[i+index]+"柱面号:"+empty[i+index]/16+"磁道号:"+(empty[i+index]%16)/4+"扇区号:"+(empty[i+index]%16)%4+"\n";
	  		emptyNum--;               
	  		usedNum++;
	  	}
	  	for(int t=index;t<emptyNum;t++) {          
	  		empty[t] = empty[t+n];		//从查找下标开始更新empty
	  	}
	  	result += "空闲块个数为:"+emptyNum+"\n";
	  	ta.append(result);		//柱面号等信息添加到文本框
	  	change();
	}
	public void back() {  //  单个回收
		for(int i = 0;i<40;i++)
			oldData[i] = newData[i].clone();
		upDataOld();
		int n=0;
		try {
			n = Integer.parseInt(tf2.getText());	//获取回收的盘块号
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "请输入有效整数","输入错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(n>=640) {
			JOptionPane.showMessageDialog(null, "超出范围","输入错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tf1.setText("");
		ta.setText("");
		if(emptyNum==640) {
			JOptionPane.showMessageDialog(null, "盘块现在全空闲");
			return;
		}
		int a,b,c;
		a = n/16;     	//柱面号   		
		b = n%16/4;     //磁道号      
		c = n%16%4;     //扇区号        	
    	if(newData[a][4*b+c+1].equals("0")) {       
    		JOptionPane.showMessageDialog(null, "该块空闲未分配","数据错误",JOptionPane.ERROR_MESSAGE);
        	return;
        }
        else {
        	newData[a][4*b+c+1] = "0";
        	emptyNum++;
        	usedNum--;
        	ta.append("相对块号:"+n+"柱面:"+a+"磁道:"+b+"扇区:"+c+"\n");
        	for(int i=0;i<usedNum;i++) {
        		used[i] = used[i+1];    	//修改used   
        	}
	    }
	    find();
	    change();
	}
	
	public void allback() {      //全部回收
		tf1.setText("");
		tf2.setText("");
		ta.setText("");
		for(int i = 0;i<40;i++)
			oldData[i] = newData[i].clone();
		upDataOld();
		String result = "";
		ta.setText("");
		if(emptyNum==640) {
			JOptionPane.showMessageDialog(null, "盘块现在全空闲");
        	return;
		}
		result+="回收结果：\n";
		int a,b,c;
		for(int i=0;i<usedNum;i++) {
			a = used[i]/16;		//柱面
			b = used[i]%16/4;	//磁道
			c = used[i]%16%4;	//扇区
			newData[a][4*b+c+1]="0";
        	result+="柱面"+a+"磁道"+b+"扇区"+c+"\n";
        	used[i] = 0;
		}
		emptyNum = emptyNum+usedNum;  	//修改emptyNum
		usedNum=0;
		find();
		ta.append(result);
		change();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b_alloc) 
			if(rb_Dispersed.isSelected()) 
				dispersedAlloc();
			else if (rb_Continuous.isSelected()) 
				continuousAlloc();
		
    	if(e.getSource()==b_recover)        
			back();
    	
    	if(e.getSource()==b_init)      
    		newtu();
    	
    	if(e.getSource()==b_recoverAll)                
    		allback();
	}
}

public class test {
    public static void main(String[] args) {
   	new Wesitu();
	}
}