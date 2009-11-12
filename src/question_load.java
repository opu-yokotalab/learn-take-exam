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
	
	question_load(int lev,String p_set){
	
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
	    String url = "jdbc:postgresql://127.0.0.1:5432/problem_DB";
	    String user = "postgres";
	    String pass = "postgres";
	    
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
	        
	        ArrayList idlist = new ArrayList();
	        while (rs.next()){
	        	//System.out.println("id:"+rs.getInt("program_ID"));	
	        	idlist.add(rs.getInt("program_ID"));
	        }
	        int listsize = idlist.size();
	        //System.out.println("listsize:"+listsize);
	        //int p_id = (int)Math.ceil(listsize * Math.random());
	        int p_id = (int)(listsize * Math.random());
	        //System.out.println("p_id:"+p_id);
	        int id = Integer.parseInt(idlist.get(p_id).toString());
	        //System.out.println("id:"+id);
	        qID.add(id);
	        
	        //sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND level="+lev+";";
	        sql = "SELECT * FROM question where program_set=\'"+p_set+"\' AND level="+lev+" AND program_ID="+id+";";
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
		        Document doc = parser.parse(new ByteArrayInputStream(so.getBytes("UTF-8")));
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
