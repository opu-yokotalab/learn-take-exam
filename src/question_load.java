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
//問題文読み込みクラス
	
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
	    
	    
	    //ローカルのxmlファイルから問題を呼び出す
		/*String filename = "C:\\Users\\ariyasu\\workspace\\mkexam2\\"+p_set+"_"+ID+".xml";
		 
	    try {
	      // ドキュメントビルダーファクトリを生成
	      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	      // ドキュメントビルダーを生成
	      DocumentBuilder builder = dbfactory.newDocumentBuilder();
	      // パースを実行してDocumentオブジェクトを取得
	      Document doc = builder.parse(new File(filename));
	      // ルート要素を取得
	      Element root = doc.getDocumentElement();
	      
	      //----------問題文--------------
	      //問題文を記述しているquestion要素を探し出す
	      NodeList list = root.getElementsByTagName("question");
	      for(i=0;i<list.getLength();i++){
	    	  Element element = (Element)list.item(i);
	    	  NodeList clist = element.getChildNodes();
	    	  
	    	  //問題文を取得
	    	  for(int j=0;j<clist.getLength();j++){
	    		  if(clist.item(j).getNodeValue()!=null){
	    			  questiontext = questiontext+clist.item(j).getNodeValue();
	    			  //if(questiontext.length()>15){
	    			  //  String div = questiontext;
	    			  //}
	    		  }
	    	  }
	    	  //pre以下の問題のソースコードを取得
	    	  NodeList pre = element.getElementsByTagName("pre");
	    	  for(int k=0;k<pre.getLength();k++){
	    		  //System.out.println("pre.getLength():"+pre.getLength());
	    		  questiontext = questiontext + pre.item(k).getFirstChild().getNodeValue();
	    	  }
	      }
	      
		  question.add(questiontext); 
	      
		  //-----ここまで問題文-----------
		  
	      //------回答欄-------------
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
	      //-----ここまで回答欄----------------
	      //-------とりあえず解答---------------
	      list = root.getElementsByTagName("correct");
	      for(i=0;i<list.getLength();i++){
	    	  answer.add(list.item(i).getFirstChild().getNodeValue());
	      }
	      //-------ここまで解答---------------
	      //-------補足説明----------------------
	      list = root.getElementsByTagName("explanation");
	      for(i=0;i<list.getLength();i++){
	    	  supplementation.add(list.item(i).getFirstChild().getNodeValue());
	      }	      
	      //-------ここまで補足説明-----------------
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }*/
	    
	    
	    //DBからの呼び出し
	    String driver = "org.postgresql.Driver";
	    String url = "jdbc:postgresql://127.0.0.1:5432/problem_DB";
	    String user = "postgres";
	    String pass = "postgres";
	    
	    try {
	        // ドライバクラスをロード
	        Class.forName(driver); // PostgreSQLの場合
	        // データベースへ接続

	        Connection con = DriverManager.getConnection(url,user,pass);
	    	//System.out.println("DB conect OK3");//DB接続チェック
	        // ステートメントオブジェクトを生成
	        Statement stmt = con.createStatement();
	        //String sql = "SELECT source FROM question WHERE program_set = \"sample\" AND id= \"1\";";
	        //レベルとプログラムセットを指定して問題のリストを得る
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
	        // クエリーを実行して結果セットを取得
	        
	        // 検索された行数分ループ
	        while (rs.next()){
	        	//System.out.println("DB conect OK3");//DB接続チェック
	        	//System.out.println("p_set:"+rs.getString("program_set"));
	        	//rs.next();
	        	//System.out.println(rs.getString("source"));
	        	
	        	String so = rs.getString("source");

	        	// ドキュメントビルダーファクトリを生成
		        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		        // ドキュメントビルダーを生成
		        DocumentBuilder parser = dbfactory.newDocumentBuilder();	           
		        // パースを実行してDocumentオブジェクトを取得
		        Document doc = parser.parse(new ByteArrayInputStream(so.getBytes("UTF-8")));
		        //Document doc = parser.parse(stream);
		        //System.out.println("DB conect OK8");//DB接続チェック
		        // ルート要素を取得
		        Element root = doc.getDocumentElement();
		        // Do something...
		        //System.out.println("root:"+root.getTagName());
			      
			      //----------問題文--------------
			      //問題文を記述しているquestion要素を探し出す
			      NodeList list = root.getElementsByTagName("question");
			      for(i=0;i<list.getLength();i++){
			    	  Element element = (Element)list.item(i);
			    	  NodeList clist = element.getChildNodes();
			    	  
			    	  //問題文を取得
			    	  for(int j=0;j<clist.getLength();j++){
			    		  if(clist.item(j).getNodeValue()!=null){
			    			  questiontext = questiontext+clist.item(j).getNodeValue();
			    			  //if(questiontext.length()>15){
			    			  //  String div = questiontext;
			    			  //}
			    		  }
			    	  }
			    	  //pre以下の問題のソースコードを取得
			    	  NodeList pre = element.getElementsByTagName("pre");
			    	  for(int k=0;k<pre.getLength();k++){
			    		  //System.out.println("pre.getLength():"+pre.getLength());
			    		  questiontext = questiontext + pre.item(k).getFirstChild().getNodeValue();
			    	  }
			      }
			      
				  question.add(questiontext); 
			      
				  //-----ここまで問題文-----------
				  
			      //------回答欄-------------
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
			      //-----ここまで回答欄----------------
			      //-------とりあえず解答---------------
			      list = root.getElementsByTagName("correct");
			      for(i=0;i<list.getLength();i++){
			    	  answer.add(list.item(i).getFirstChild().getNodeValue());
			      }
			      //-------ここまで解答---------------
			      //-------補足説明----------------------
			      list = root.getElementsByTagName("explanation");
			      for(i=0;i<list.getLength();i++){
			    	  supplementation.add(list.item(i).getFirstChild().getNodeValue());
			      }	      
			      //-------ここまで補足説明-----------------
			      //-----レベルの取得------------------
			      //list = root.getElementsByTagName("score");
			      //for(i=0;i<list.getLength();i++){
			      //  level.add(list.item(i).getFirstChild().getNodeValue());
			      //}
			      //-------ここまでレベル-----------------
			      //-----時間------------------
			      list = root.getElementsByTagName("function");
			      for(i=0;i<list.getLength();i++){
			    	  max_time.add(list.item(i).getFirstChild().getNodeValue());
			      }
	        }	        

	        // データベースから切断
	        stmt.close();
	        con.close();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    
	    
	}
	
	//public void add_question(String aa){
	//}
	
}
