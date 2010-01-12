import org.w3c.dom.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import java.sql.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;

//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class question_load {
//��蕶�ǂݍ��݃N���X
	
	ArrayList program_set;
	ArrayList qID;
	ArrayList level;
	ArrayList question;
	ArrayList blank;
	ArrayList answer;
	ArrayList hint;
	ArrayList supplementation;
	ArrayList max_time;
	ArrayList length;
	
	question_load(int lev,String p_set,int order,int full_order,ArrayList ready){
	
		int i;
		String questiontext = new String();
		
		program_set = new ArrayList();
	    qID = new ArrayList();
	    level = new ArrayList();
	    question = new ArrayList();
	    blank = new ArrayList();
	    answer = new ArrayList();
	    length = new ArrayList();
	    //hint = new ArrayList();
	    supplementation = new ArrayList();
	    max_time = new ArrayList();
	    program_set.add(p_set);
	    
	    level.add(lev);
	    
	    
	    //���[�J����xml�t�@�C����������Ăяo��
		/*String filename = "C:\\Users\\ariyasu\\workspace\\mkexam2\\"+p_set+"_"+ID+".xml";
		 
	    try {
	      // �h�L�������g�r���_�[�t�@�N�g���𐶐�
	      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	      // �h�L�������g�r���_�[�𐶐�
	      DocumentBuilder builder = dbfactory.newDocumentBuilder();
	      // �p�[�X�����s����Document�I�u�W�F�N�g���擾
	      Document doc = builder.parse(new File(filename));
	      // ���[�g�v�f���擾
	      Element root = doc.getDocumentElement();
	      
	      //----------��蕶--------------
	      //��蕶���L�q���Ă���question�v�f��T���o��
	      NodeList list = root.getElementsByTagName("question");
	      for(i=0;i<list.getLength();i++){
	    	  Element element = (Element)list.item(i);
	    	  NodeList clist = element.getChildNodes();
	    	  
	    	  //��蕶���擾
	    	  for(int j=0;j<clist.getLength();j++){
	    		  if(clist.item(j).getNodeValue()!=null){
	    			  questiontext = questiontext+clist.item(j).getNodeValue();
	    			  //if(questiontext.length()>15){
	    			  //  String div = questiontext;
	    			  //}
	    		  }
	    	  }
	    	  //pre�ȉ��̖��̃\�[�X�R�[�h���擾
	    	  NodeList pre = element.getElementsByTagName("pre");
	    	  for(int k=0;k<pre.getLength();k++){
	    		  //System.out.println("pre.getLength():"+pre.getLength());
	    		  questiontext = questiontext + pre.item(k).getFirstChild().getNodeValue();
	    	  }
	      }
	      
		  question.add(questiontext); 
	      
		  //-----�����܂Ŗ�蕶-----------
		  
	      //------�񓚗�-------------
	      list = root.getElementsByTagName("response");
	      length.add(list.getLength());
	      for(i=0;i<list.getLength();i++){
	    	  //JLabel ansLabel=new JLabel(list.item(i).getFirstChild().getNodeValue());
	    	  //ansLabel[i].setText(list.item(i).getFirstChild().getNodeValue());
	    	  //System.out.println("response:"+list.item(i).getFirstChild().getNodeValue());
	    	  //ansPanel.add(ansLabel);
	    	  //ansPanel.add(resp[i]=new JTextField(10));
	    	  blank.add(list.item(i).getFirstChild().getNodeValue());
	      }
	      //-----�����܂ŉ񓚗�----------------
	      //-------�Ƃ肠������---------------
	      list = root.getElementsByTagName("correct");
	      for(i=0;i<list.getLength();i++){
	    	  answer.add(list.item(i).getFirstChild().getNodeValue());
	      }
	      //-------�����܂ŉ�---------------
	      //-------�⑫����----------------------
	      list = root.getElementsByTagName("explanation");
	      for(i=0;i<list.getLength();i++){
	    	  supplementation.add(list.item(i).getFirstChild().getNodeValue());
	      }	      
	      //-------�����܂ŕ⑫����-----------------
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }*/
	    
	    
	    //DB����̌Ăяo��
	    String driver = "org.postgresql.Driver";
	    //�ǂ���DB�ɐڑ����邩�̔���(0:���[�J���A1:emerald)
	    int DB_C = 1;

	    //�f�t�H���g�̓��[�J���z�X�g�ւ̃A�N�Z�X
	    String url = "jdbc:postgresql://127.0.0.1:5432/problem_DB";
	    String user = "postgres";
	    String pass = "postgres";
	    
	    
	    if(DB_C == 1){
	    	//�T�[�o(emerald.c.oka-pu.ac.jp)�ւ̃A�N�Z�X
	    	url = "jdbc:postgresql://163.225.223.42:5432/problem_DB2";
	    	user = "ariyasu";
	    	pass = "flyinggarnet";
	    }
	    
	    try {
	        // �h���C�o�N���X�����[�h
	        Class.forName(driver); // PostgreSQL�̏ꍇ
	        // �f�[�^�x�[�X�֐ڑ�

	        Connection con = DriverManager.getConnection(url,user,pass);
	    	//System.out.println("DB conect OK3");//DB�ڑ��`�F�b�N
	        // �X�e�[�g�����g�I�u�W�F�N�g�𐶐�
	        Statement stmt = con.createStatement();
	        
	        
	        //String sql = "SELECT source FROM question WHERE program_set = \"sample\" AND id= \"1\";";
	        //���x���ƃv���O�����Z�b�g���w�肵�Ė��̃��X�g�𓾂�
	        String sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND level="+lev+";";
	        ResultSet rs = stmt.executeQuery(sql);
	        int id=1;
	        
	        //���ʖ��̖��w��
	        if(p_set.equals("point12")){
	        	if(full_order==1){
	        		id = 74;
	        	}else if(full_order==2){
	        		id = 76;
	        	}else if(full_order==3){
	        		id = 105;
	        	}else if(full_order==4){
	        		id = 92;
	        	}else if(full_order==5){
	        		id = 104;
	        	}else if(full_order==6){
	        		id = 80;
	        	}else {
	        		id = 73;
	        	}
	        }
	        else if(p_set.equals("point22")){
	        	if(full_order==1){
	        		id = 90;
	        	}else if(full_order==2){
	        		id = 110;
	        	}else if(full_order==3){
	        		id = 96;
	        	}else if(full_order==4){
	        		id = 109;
	        	}else if(full_order==5){
	        		id = 85;
	        	}else if(full_order==6){
	        		id = 84;
	        	}else {
	        		id = 91;
	        	}
	        
	        /*}else if(p_set.equals("point11")){
	        	if(full_order==1){
	        		id = 79;
	        	}else if(full_order==2){
	        		id = 101;
	        	}else if(full_order==3){
	        		id = 78;
	        	}else if(full_order==4){
	        		id = 88;
	        	}else if(full_order==5){
	        		id = 98;
	        	}else if(full_order==6){
	        		id = 99;
	        	}else {
	        		id = 86;
	        	}
	        }else if(p_set.equals("point21")){
	        	if(full_order==1){
	        		id = 108;
	        	}else if(full_order==2){
	        		id = 93;
	        	}else if(full_order==3){
	        		id = 94;
	        	}else if(full_order==4){
	        		id = 81;
	        	}else if(full_order==5){
	        		id = 108;
	        	}else if(full_order==6){
	        		id = 97;
	        	}else {
	        		id = 87;
	        	}*/
	        	
	        
	        }else{
	        	//��Ď�@�ŏo��
	        	ArrayList idlist = new ArrayList();
	        	while (rs.next()){
	        		//System.out.println("id:"+rs.getInt("program_ID"));	
	        		idlist.add(rs.getInt("program_ID"));
	        	}
	        	int listsize = idlist.size();
	        	int k=0;
	        	int mae =0;
	        	//System.out.println("listsize:"+listsize);
	        	//int p_id = (int)Math.ceil(listsize * Math.random());
	        	int p_id = (int)(listsize * Math.random());//������^���ĉ��ڂ̖����o��������
	        	id = Integer.parseInt(idlist.get(p_id).toString());//���ڂ������ۂ�p_id�ɕϊ�
	        	//System.out.println("first_ID:"+id);
	        	int flag =0;
	        	//for(int k=0;k<ready.size();k++){
	        	if(ready.size()!=0){
	        		do{
	        			//System.out.println("ready("+k+"):"+ready.get(k).toString());
	        			
	        			//�O�ɓ�����肪�o�����ǂ����̃`�F�b�N
	        			if(id == Integer.parseInt(ready.get(k).toString())){
	        				p_id = (int)(listsize * Math.random());//������^���ĉ��ڂ̖����o��������
	        				if(Integer.parseInt(idlist.get(p_id).toString())!=mae){//1�O�ɏo�����Ɠ�����������ς���
	        					id = Integer.parseInt(idlist.get(p_id).toString());//���ۂ�p_id�ɕϊ�
	        					//System.out.println("MOVE!!");
	        				}
	        				//System.out.println("change_ID:"+id);
	        				//System.out.println("!!CHANGE!!");
	        				k=0;
	        				flag++;
	        			}
	        			else{
	        				k++;
	        			}
        			}while(k<ready.size()&&flag<10);
        		}
        		mae = id;//�ЂƂO�ɏo�肵�����Ƃ��ċL��
        		//System.out.println("mae:"+mae);
        		
	        }//���w�肷��Ƃ��p�̃J�b�R
        	
	        qID.add(id);
	        ready.add(id);
	        
	        //for(int j=0;j<qID.size();j++){
	        //	System.out.println("qID:"+qID.get(j).toString());
	        //}
	        System.out.println(order+"��ڏo�莞");
	        for(int j=0;j<ready.size();j++){
	        	System.out.println("list"+j+":"+ready.get(j).toString());
	        }
	        System.out.println("-------------------");
	        
	        //��蕶���擾
	        //sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND level="+lev+";";
	        //sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND level="+lev+" AND program_ID="+id+";";
	        sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND program_ID="+id+";";
	        rs = stmt.executeQuery(sql);
	        //String sql = "SELECT source FROM question where program_set=\'sample\' AND id=1;";
	        // �N�G���[�����s���Č��ʃZ�b�g���擾
	        
	        // �������ꂽ�s�������[�v
	        while (rs.next()){
	        	//System.out.println("DB conect OK3");//DB�ڑ��`�F�b�N
	        	//System.out.println("p_set:"+rs.getString("program_set"));
	        	//rs.next();
	        	//System.out.println(rs.getString("source"));
	        	
	        	String so = rs.getString("source");

	        	// �h�L�������g�r���_�[�t�@�N�g���𐶐�
		        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		        // �h�L�������g�r���_�[�𐶐�
		        DocumentBuilder parser = dbfactory.newDocumentBuilder();	           
		        // �p�[�X�����s����Document�I�u�W�F�N�g���擾
		        //���[�J��DB�̂Ƃ�
		        Document doc = parser.parse(new ByteArrayInputStream(so.getBytes("Shift_JIS")));
		        //Document doc = parser.parse(new ByteArrayInputStream(so.getBytes("UTF-8")));
		        if(DB_C == 0){
		        	doc = parser.parse(new ByteArrayInputStream(so.getBytes("UTF-8")));
		        }
		        if(DB_C == 1){
		        	//emerald���DB�ɃA�N�Z�X����Ƃ�
		        	doc = parser.parse(new ByteArrayInputStream(so.getBytes("Shift_JIS")));
		        }
		        //Document doc = parser.parse(stream);
		        //System.out.println("DB conect OK8");//DB�ڑ��`�F�b�N
		        // ���[�g�v�f���擾
		        Element root = doc.getDocumentElement();
		        // Do something...
		        //System.out.println("root:"+root.getTagName());
			      
			      //----------��蕶--------------
			      //��蕶���L�q���Ă���question�v�f��T���o��
			      NodeList list = root.getElementsByTagName("question");
			      for(i=0;i<list.getLength();i++){
			    	  Element element = (Element)list.item(i);
			    	  NodeList clist = element.getChildNodes();
			    	  
			    	  //��蕶���擾
			    	  for(int j=0;j<clist.getLength();j++){
			    		  if(clist.item(j).getNodeValue()!=null){
			    			  questiontext = questiontext+clist.item(j).getNodeValue();
			    		  }
			    	  }
			    	  //pre�ȉ��̖��̃\�[�X�R�[�h���擾
			    	  NodeList pre = element.getElementsByTagName("pre");
			    	  for(int l=0;l<pre.getLength();l++){
			    		  //System.out.println("pre.getLength():"+pre.getLength());
			    		  questiontext = questiontext + pre.item(l).getFirstChild().getNodeValue();
			    	  }
			      }
			      
				  question.add(questiontext); 
			      
				  //-----�����܂Ŗ�蕶-----------
				  
			      //------�񓚗�-------------
			      list = root.getElementsByTagName("response");
			      length.add(list.getLength());
			      for(i=0;i<list.getLength();i++){
			    	  //JLabel ansLabel=new JLabel(list.item(i).getFirstChild().getNodeValue());
			    	  //ansLabel[i].setText(list.item(i).getFirstChild().getNodeValue());
			    	  //System.out.println("response:"+list.item(i).getFirstChild().getNodeValue());
			    	  //ansPanel.add(ansLabel);
			    	  //ansPanel.add(resp[i]=new JTextField(10));
			    	  blank.add(list.item(i).getFirstChild().getNodeValue());
			      }
			      //-----�����܂ŉ񓚗�----------------
			      //-------�Ƃ肠������---------------
			      list = root.getElementsByTagName("correct");
			      for(i=0;i<list.getLength();i++){
			    	  answer.add(list.item(i).getFirstChild().getNodeValue());
			      }
			      //-------�����܂ŉ�---------------
			      //-------�⑫����----------------------
			      list = root.getElementsByTagName("explanation");
			      for(i=0;i<list.getLength();i++){
			    	  supplementation.add(list.item(i).getFirstChild().getNodeValue());
			      }	      
			      //-------�����܂ŕ⑫����-----------------
			      //-----���x���̎擾------------------
			      //list = root.getElementsByTagName("score");
			      //for(i=0;i<list.getLength();i++){
			      //  level.add(list.item(i).getFirstChild().getNodeValue());
			      //}
			      //-------�����܂Ń��x��-----------------
			      //-----����------------------
			      list = root.getElementsByTagName("function");
			      for(i=0;i<list.getLength();i++){
			    	  max_time.add(list.item(i).getFirstChild().getNodeValue());
			      }
	        }	        

	        // �f�[�^�x�[�X����ؒf
	        stmt.close();
	        con.close();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    
	    
	}
	
	//public void add_question(String aa){
	//}
	
}
