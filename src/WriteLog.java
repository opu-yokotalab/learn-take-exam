import java.util.*;
import java.sql.*;

public class WriteLog {
//DBへのログ書き込みクラス	
	
	WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,java.sql.Timestamp start,java.sql.Timestamp end){
    
	String uname = username;
		
	int DB_C=1;
		
    String driver = "org.postgresql.Driver";
    
    String url = "jdbc:postgresql://127.0.0.1:5432/problem_DB";
    String user = "postgres";
    String pass = "postgres";
    
    if(DB_C == 1){
    	//サーバ(emerald.c.oka-pu.ac.jp)へのアクセス
    	url = "jdbc:postgresql://163.225.223.42:5432/problem_DB";
    	user = "ariyasu";
    	pass = "flyinggarnet";
    	

    }
    
    try {
    	//if(DB_C ==1){
    		//byte[] eucBytes = username.getBytes("EUC_JP");
    		//System.out.println(eucBytes);
    		//String in = new String(eucBytes,"EUC_JP");
    		//String in = new String(username.getBytes("EUC_JP"));
    		//uname = in;
    	//}
    	// ドライバクラスをロード
        Class.forName(driver); // PostgreSQLの場合
        // データベースへ接続

        Connection con = DriverManager.getConnection(url,user,pass);
    	//System.out.println("DB conect OK3");//DB接続チェック
        // ステートメントオブジェクトを生成
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO user_log VALUES('"
        	+ uname + "','" + judg + "','" + seq + "','"  + order + "','" 
        	+ program_set +"','"+ p_ID +"','"+q_lev +"',to_TIMESTAMP('" + start+ "','yyyy/mm/dd hh24:mi:ss:nnn'),to_TIMESTAMP('" 
        	+ end +"','yyyy/mm/dd hh24:mi:ss:nnn'));";
        //System.out.println(sql);
       /*-----------------------+---------+
        *username              | text    |
        *judgment              | integer |
        *seq                   | text    |
        *q_order               | integer |
        *program_set           | text    |
        *p_id                  | integer |
        *q_level               | integer |
        *start_time            | date    |
        *end_time              | date    |
        */
        
        // クエリーを実行して結果セットを取得
        int result = stmt.executeUpdate(sql);
        //System.out.println("DB conect OK");//DB接続チェック


        // データベースから切断
        stmt.close();
        con.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
	}
	
}
