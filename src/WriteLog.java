import java.util.*;
import java.sql.*;

public class WriteLog {
//DBへのログ書き込みクラス	
	
	WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,java.sql.Timestamp start,java.sql.Timestamp end){
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
        String sql = "INSERT INTO user_log VALUES('"
        	+ username + "','" + judg + "','" + seq + "','"  + order + "','" 
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
