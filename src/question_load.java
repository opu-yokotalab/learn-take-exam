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
	
	question_load(int lev,String p_set,int order,int full_order){
	
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
	    	url = "jdbc:postgresql://163.225.223.42:5432/problem_DB";
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
	        
	        //���S�ɏo�����̎w��
	        /*if(full_order==1){
	        	if(p_set.equals("struct")){
	        		p_set = "struct2";
	        		id = 14;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct2";
	        		id = 23;
	        	}else {//struct3
	        		p_set = "struct2";
	        		id = 16;
	        	}
	        }else if(full_order==2){
	        	if(p_set.equals("struct")){
	        		p_set = "struct";
	        		id = 6;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct3";
	        		id = 26;
	        	}else {//struct3
	        		p_set = "struct2";
	        		id = 18;
	        	}
	        }else if(full_order==3){
	        	if(p_set.equals("struct")){
	        		p_set = "struct3";
	        		id = 33;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct";
	        		id = 4;
	        	}else {//struct3
	        		p_set = "struct3";
	        		id = 34;
	        	}
	        }else if(full_order==4){
	        	if(p_set.equals("struct")){
	        		p_set = "struct2";
	        		id = 20;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct2";
	        		id = 17;
	        	}else {//struct3
	        		p_set = "struct3";
	        		id = 27;
	        	}
	        }else if(full_order==5){
	        	if(p_set.equals("struct")){
	        		p_set = "struct";
	        		id = 13;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct";
	        		id = 12;
	        	}else {//struct3
	        		p_set = "struct";
	        		id = 5;
	        	}
	        }else if(full_order==6){
	        	if(p_set.equals("struct")){
	        		p_set = "struct3";
	        		id = 25;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct";
	        		id = 1;
	        	}else {//struct3
	        		p_set = "struct2";
	        		id = 24;
	        	}
	        }else if(full_order==7){
	        	if(p_set.equals("struct")){
	        		p_set = "struct";
	        		id = 7;
	        	}else if(p_set.equals("struct2")){
	        		p_set = "struct3";
	        		id = 28;
	        	}else {//struct3
	        		p_set = "struct2";
	        		id = 21;
	        	}
	        }*/
	        
	        //1��ڂ�2��ڂ����͎w�肵�������o���悤��
	        //1���
	        if(order==1){
	        	//Lv.1
	        	if(lev==1){
	        		if(p_set.equals("struct")){
	        			id=1;
	        		}else if(p_set.equals("struct2")){
	        			id=14;
	        		}else if(p_set.equals("struct3")){
	        			id=25;
	        		}else if (p_set.equals("pointer")){
	        			id=41;
	        		}else if (p_set.equals("pointer2")){
	        			id=46;
	        		}else if (p_set.equals("pointer3")){
	        			id=58;
	        		}
	        	}
	        	//Lv.2
	        	else if (lev == 2){
	        		if(p_set.equals("struct")){
	        			id=7;
	        		}else if(p_set.equals("struct2")){
	        			id=20;
	        		}else if(p_set.equals("struct3")){
	        			id=26;
	        		}else if (p_set.equals("pointer")){
	        			id=37;
	        		}else if (p_set.equals("pointer2")){
	        			//id=48;
	        			id=60;//���q�����p���������
	        			p_set = "pointer3";
	        		}else if (p_set.equals("pointer3")){
	        			//id=60;
	        			id=35;//���q�����p�i�ȒP��������j
	        			p_set = "pointer";
	        		}
	        	}
	        	//Lv.3
	        	else if (lev == 3){
	        		if(p_set.equals("struct")){
	        			id=12;
	        		}else if(p_set.equals("struct2")){
	        			id=18;
	        		}else if(p_set.equals("struct3")){
	        			id=33;
	        		}else if (p_set.equals("pointer")){
	        			id=36;
	        		}else if (p_set.equals("pointer2")){
	        			id=52;
	        		}else if (p_set.equals("pointer3")){
	        			//id=66;
	        			id=44;//���q�����p�i�ȒP��������j
	        			p_set = "pointer";
	        		}
	        	}
	        //2���
	        }else if(order==2){
	        	//Lv.1
	        	if(lev==1){
	        		if(p_set.equals("struct")){
	        			id=6;
	        		}else if(p_set.equals("struct2")){
	        			id=17;
	        			
	        		}else if(p_set.equals("struct3")){
	        			id=27;
	        		}else if (p_set.equals("pointer")){
	        			//id=35;
	        			id=48;//���q�����p�i������j
	        			p_set = "pointer2";
	        		}else if (p_set.equals("pointer2")){
	        			id=47;
	        		}else if (p_set.equals("pointer3")){
	        			id=64;
	        		}
	        	}
	        	//Lv.2
	        	else if (lev == 2){
	        		if(p_set.equals("struct")){
	        			id=4;
	        		}else if(p_set.equals("struct2")){
	        			id=16;
	        		}else if(p_set.equals("struct3")){
	        			id=28;
	        		}else if (p_set.equals("pointer")){
	        			//id=44;
	        			id=66;//���q�����p�i���������)
	        			p_set = "pointer3";
	        		}else if (p_set.equals("pointer2")){
	        			id=50;
	        		}else if (p_set.equals("pointer3")){
	        			id=67;
	        		}
	        	}
	        	//Lv.3
	        	else if (lev == 3){
	        		if(p_set.equals("struct")){
	        			id=13;
	        		}else if(p_set.equals("struct2")){
	        			id=23;
	        		}else if(p_set.equals("struct3")){
	        			id=34;
	        		}else if (p_set.equals("pointer")){
	        			id=37;
	        		}else if (p_set.equals("pointer2")){
	        			id=55;
	        		}else if (p_set.equals("pointer3")){
	        			id=65;
	        		}
	        	}
	        }else{
	        //�����x����2��ڈȍ~�̓����_���ŏo��
	        ArrayList idlist = new ArrayList();
	        while (rs.next()){
	        	//System.out.println("id:"+rs.getInt("program_ID"));	
	        	idlist.add(rs.getInt("program_ID"));
	        }
	        int listsize = idlist.size();
	        //System.out.println("listsize:"+listsize);
	        //int p_id = (int)Math.ceil(listsize * Math.random());
	        int p_id = (int)(listsize * Math.random());//������^���ĉ��ڂ̖����o��������
	        //System.out.println("p_id:"+p_id);
	        id = Integer.parseInt(idlist.get(p_id).toString());//���ڂ������ۂ�p_id�ɕϊ�
	        //System.out.println("id:"+id);
	        }
	        qID.add(id);
	        
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
