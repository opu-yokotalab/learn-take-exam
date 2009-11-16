import java.awt.BorderLayout;
import java.awt.GridLayout;
//import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
//import java.sql.*;

public class Take_Exam_main extends JFrame implements ActionListener{
//メインの提示・処理を行うクラス
	
	//提示用パネル
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;//スタート画面提示用パネル
	private JPanel setQuestionPane = null;//問題提示用パネル
	private JPanel markingAnswerPane = null;//解答提示用パネル
	private JPanel giveupPane = null;//Give Up画面提示用パネル
	
	//他クラス呼び出し用
	public static question_load QL;  //  問題読み出し  //  @jve:decl-index=0:
	public static WriteLog WL; //ログ書きこみ
	
	//出題関係
	JTextField[] answer = new JTextField[10];//回答欄
	JTextField get_user_name = new JTextField(15);//ユーザーネーム取得欄
	ArrayList getAns;  // 回答の格納先
	String user_name = new String();  // ユーザ名の格納先  //  @jve:decl-index=0:
	String nextQuestion;
	int nextID;
	int ord; //何問目か
	int level = 1; //レベル
	int max_level = 3;//最大レベル
	JLabel time = new JLabel();
	Timer timer;  //  @jve:decl-index=0:
	int sec=0;

	//シーケンス判定
	String seqence = new String();  //  @jve:decl-index=0:
	//比較元
	int cont_correct = 1;// 連続正解数
	int cont_mistake = 1;//連続間違い数
	int total_correct = 3;//合計正解数
	int total_mistake = 3;//合計間違い数
	//変数値
	int co_co =0;// 連続正解数
	int co_mi =0;//連続間違い数
	int to_co =0;//合計正解数
	int to_mi =0;//合計間違い数
	
	//時間関係
	int max = 10;
	java.sql.Timestamp stDate;  //  問題の出題時間
	java.sql.Timestamp maxtime;  //最大解答時間
	java.sql.Timestamp endDate;  // 回答完了時間
	/**
	 * This is the default constructor
	 */
	public Take_Exam_main() {
		//super();
		initialize();
		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {//初期化
		this.setSize(600, 500);
		this.setContentPane(getJContentPane());
		//this.setContentPane(setQuestionPane());
		this.setTitle("演習システム");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {//初期画面の提示
		if (jContentPane == null) {
			jContentPane = new JPanel();
			//jContentPane.setSize(350, 400);
			jContentPane.setLayout(new BorderLayout());
		}
		JPanel startPanel= new JPanel();
		JPanel userPanel = new JPanel();
		JPanel seqPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());
		seqPanel.setLayout(new GridLayout(4,1));
		
		//ユーザ名入力
		JLabel un = new JLabel("ユーザー名");
		userPanel.add(un);
		userPanel.add(get_user_name);
		startPanel.add(userPanel,BorderLayout.NORTH);

		JPanel s = new JPanel();
		JLabel sLab = new JLabel("学習項目");
		s.add(sLab);
		seqPanel.add(s);
		
		//シーケンスボタン1
		s = new JPanel();
		//JLabel sLab = new JLabel("配列");
		sLab = new JLabel("配列");
		//sLab = new JLabel("For文");
		s.add(sLab);
		JButton startB = new JButton("学習を始める");
		startB.setActionCommand("START1");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//シーケンスボタン2
		s = new JPanel();
		//JLabel sLab = new JLabel("ポインタ");
		sLab = new JLabel("構造体");
		s.add(sLab);
		//JButton startB = new JButton("学習を始める");
		startB = new JButton("学習を始める");
		startB.setActionCommand("START2");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//シーケンスボタン3
		s = new JPanel();
		//JLabel sLab = new JLabel("ポインタ");
		sLab = new JLabel("ポインタ");
		s.add(sLab);
		//JButton startB = new JButton("学習を始める");
		startB = new JButton("学習を始める");
		startB.setActionCommand("START3");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
			
		startPanel.add(seqPanel,BorderLayout.CENTER);
		jContentPane.add(startPanel, BorderLayout.CENTER);
		return jContentPane;
	}
	
	private JPanel setQuestionPane(int level,String p_set){
	//出題画面
		//if (setQuestionPane == null) {
			setQuestionPane = new JPanel();
			//jContentPane.setSize(350, 400);
			setQuestionPane.setLayout(new BorderLayout());
		//}
		
		JPanel infoPanel = new JPanel();//情報提示用のパネル
		JPanel qPanel = new JPanel();//問題文提示用のパネル
		JPanel bPanel = new JPanel();//ボタン提示用パネル
		JPanel aPanel = new JPanel();//解答欄提示用パネル
		
		QL = new question_load(level,p_set);
		
		//問題情報の提示
		JLabel order = new JLabel ("第"+ord+"問目  ");
		infoPanel.add(order);
		//level = Integer.parseInt(QL.level.get(0).toString());
		JLabel l = new JLabel("Level:"+level);
		infoPanel.add(l);
		JLabel qid = new JLabel("問題ID："+QL.qID.get(0).toString());
		infoPanel.add(qid);
		JLabel ps = new JLabel("Program_set:"+QL.program_set.get(0).toString());
		infoPanel.add(ps);
		infoPanel.add(time);
		timer = new Timer(1000,this);
		timer.start();
		
		
		//問題文の配置
		//System.out.println("question:"+QL.question.get(0).toString());
		JTextArea qTA = new JTextArea(QL.question.get(0).toString());//問題文を提示
		qTA.setSize(200, 300);
		qTA.setEditable(false);//書き込み不可にする
		//qTA.setBackground(UIManager.getColor("control"));//BGColorを周りと同じに
		qPanel.add(qTA);//テキストエリアをパネルに追加
		
		//ボタンの配置
		JButton ans = new JButton("採点");//採点ボタン
		ans.setActionCommand("ANSWER");
		ans.addActionListener(this);
		bPanel.add(ans);
		JButton g_up = new JButton("GIVE UP");//Give up ボタン
		g_up.setActionCommand("GIVEUP");
		g_up.addActionListener(this);
		bPanel.add(g_up);		
		JButton hints = new JButton("HINT");//ヒントボタン
		hints.setActionCommand("HINT");
		hints.addActionListener(this);
		bPanel.add(hints);
		
		//解答欄の配置
		aPanel.setLayout(new GridLayout(QL.blank.size(),1));
		for(int i=0;i<QL.blank.size();i++){
			JPanel form = new JPanel();
			JLabel resp = new JLabel(QL.blank.get(i).toString());
			form.add(resp);
			answer[i] = new JTextField(15);
			answer[i].setName(QL.blank.get(i).toString());
			form.add(answer[i]);
			aPanel.add(form,i,0);
		}

		//レベルの取得
		//level = Integer.parseInt(QL.level.get(0).toString());
		//System.out.println("blank:"+QL.blank.size());
		
		
		setQuestionPane.add(infoPanel, BorderLayout.NORTH);
		setQuestionPane.add(qPanel, BorderLayout.WEST);
		setQuestionPane.add(bPanel, BorderLayout.SOUTH);
		setQuestionPane.add(aPanel, BorderLayout.CENTER);
		
		return setQuestionPane;
	}
	
	private JPanel markingAnswerPane(int ID,String p_set,ArrayList ans){
	//採点提示画面
		
			markingAnswerPane = new JPanel();
			//jContentPane.setSize(350, 400);
			markingAnswerPane.setLayout(new BorderLayout());

			nextQuestion = new String ();
			nextID = 0;
			
			JPanel infoPanel = new JPanel();//情報提示用のパネル
			JPanel aPanel = new JPanel();//解答提示用のパネル
			//aPanel.setLayout(new GridLayout(4,1));
			JPanel bPanel = new JPanel();//ボタン提示用パネル
			int flag = 0;
			
			//問題情報の提示
			JLabel order = new JLabel ("第"+ord+"問目  ");
			infoPanel.add(order);
			//JLabel l = new JLabel("Level:"+QL.level.get(0).toString());
			JLabel l = new JLabel("Level:"+level);
			infoPanel.add(l);
			//JLabel qid = new JLabel("問題ID："+QL.qID.get(0).toString());
			JLabel qid = new JLabel("問題ID："+ID);
			infoPanel.add(qid);
			//JLabel ps = new JLabel("Program_set:"+QL.program_set.get(0).toString());
			JLabel ps = new JLabel("Program_set:"+p_set);
			infoPanel.add(ps);
			
			//int end1 = Integer.parseInt(QL.length.get(0).toString());
			int end = ans.size();
			for(int i=0;i<end;i++){
				if((QL.answer.get(i).toString()).equals(ans.get(i).toString())){
					
				}
				else {
					flag =1;
				}
			}
			JLabel hantei = new JLabel("判定：");
			aPanel.add(hantei);
			//System.out.println("level:"+level);
			if(flag == 0){
				JLabel sei = new JLabel("○：正解！！");
				aPanel.add(sei);
				//JLabel a = new JLabel(QL.supplementation.get(0).toString());
				//aPanel.add(a);
				
				//
				co_co++;//連続正解数加算
				to_co++;//合計正解数加算
				co_mi=0;//連続誤り数を０に
				//System.out.println("○：正解！！");
				
				//レベルを上げるかどうかの判定
				if(co_co >= cont_correct){
					//連続正解数がある値以上ならレベルを上げる
					if(level<max_level){
						level++;//レベルを上げる
						//System.out.println("levelUP1:"+level);
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_co >= total_correct){
					//合計正解数がある値以上ならレベルを上げる
					if(level<max_level){
						level++;//レベルを上げる
						//System.out.println("levelUP2:"+level);
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
			}else{
				JLabel go = new JLabel("×:不正解！！");
				aPanel.add(go);

				//System.out.println("×:不正解！！");
				
				co_mi++;//連続誤り数加算
				to_mi++;//合計誤り数加算
				co_co=0;//連続正解数を０に
				
				if(co_mi >= cont_mistake){
					//連続正解数がある値以上ならレベルを上げる
					if(level>1){
						level--;//レベルを下げる
						//System.out.println("levelDOWN1:"+level);
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_mi >= total_mistake){
					//合計正解数がある値以上ならレベルを上げる
					if(level>1){
						level--;//レベルを下げる
						//System.out.println("levelDOWN2:"+level);
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
			}
			//あなたの回答提示
			JLabel you = new JLabel("あなたの回答");
			aPanel.add(you);
			for(int i=0;i<end;i++){
				JLabel yourA = new JLabel((i+1)+":"+ans.get(i).toString());
				aPanel.add(yourA);
			}
			//模範解答提示
			JLabel kai = new JLabel("模範解答");
			aPanel.add(kai);
			for(int i=0;i<end;i++){
				JLabel mohan = new JLabel((i+1)+":"+QL.answer.get(i).toString());
				aPanel.add(mohan);
			}
			JLabel a = new JLabel(QL.supplementation.get(0).toString());
			aPanel.add(a);
			//QL.answer.get(0).toString();

			
			//DBへの書き込み
			//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
			WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,level,stDate,endDate);
			
			
			//ボタンの配置
			JButton nextb = new JButton("次の問題");//採点ボタン
			nextb.setActionCommand("NEXT");
			nextb.addActionListener(this);
			bPanel.add(nextb);
			
			JButton endb = new JButton("終了");
			endb.setActionCommand("END");
			endb.addActionListener(this);
			bPanel.add(endb);

			markingAnswerPane.add(bPanel, BorderLayout.SOUTH);
			markingAnswerPane.add(infoPanel, BorderLayout.NORTH);
			markingAnswerPane.add(aPanel, BorderLayout.CENTER);
			
		return markingAnswerPane;
	}

	private JPanel giveupPane(int ID,String p_set){
		//giveup画面
			
				giveupPane = new JPanel();
				//jContentPane.setSize(350, 400);
				giveupPane.setLayout(new BorderLayout());

				nextQuestion = new String ();
				nextID = 0;
				
				JPanel infoPanel = new JPanel();//情報提示用のパネル
				JPanel aPanel = new JPanel();//解答提示用のパネル
				//aPanel.setLayout(new GridLayout(4,1));
				JPanel bPanel = new JPanel();//ボタン提示用パネル
				int flag = 1;
				
				//問題情報の提示
				JLabel order = new JLabel ("第"+ord+"問目  ");
				infoPanel.add(order);
				JLabel l = new JLabel("Level:"+level);
				infoPanel.add(l);
				JLabel qid = new JLabel("問題ID："+ID);
				infoPanel.add(qid);
				JLabel ps = new JLabel("Program_set:"+p_set);
				infoPanel.add(ps);

				JLabel kai = new JLabel("解説");
				aPanel.add(kai);
				JLabel a = new JLabel(QL.supplementation.get(0).toString());
				aPanel.add(a);
				
				
				endDate = maxtime;
				//System.out.println(endDate);
				//QL.answer.get(0).toString();
				//ここで次の問題の判定しとく？？
				
				//DBへの書き込み
				//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
				WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,level,stDate,endDate);
				
				co_mi++;//連続誤り数加算
				to_mi++;//合計誤り数加算
				co_co=0;//連続正解数を０に
				
				if(co_mi >= cont_mistake){
					//連続正解数がある値以上ならレベルを上げる
					if(level>1){
						level--;//レベルを下げる
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_mi >= total_mistake){
					//合計正解数がある値以上ならレベルを上げる
					if(level>1){
						level--;//レベルを下げる
						co_co=0;//各値を初期化
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
				
				//ボタンの配置
				JButton nextb = new JButton("次の問題");//採点ボタン
				nextb.setActionCommand("NEXT");
				nextb.addActionListener(this);
				bPanel.add(nextb);
				
				JButton endb = new JButton("終了");
				endb.setActionCommand("END");
				endb.addActionListener(this);
				bPanel.add(endb);

				giveupPane.add(bPanel, BorderLayout.SOUTH);
				giveupPane.add(infoPanel, BorderLayout.NORTH);
				giveupPane.add(aPanel, BorderLayout.CENTER);
				
			return giveupPane;
		}
	
	public void actionPerformed(ActionEvent e) {
		
	//ボタンの動作
		if (e.getActionCommand() == "START1" ){
			sec=0;
			ord = 1;
			//seqence = "array";
			seqence = "test_set";
			//this.setContentPane(setQuestionPane(level,"test_set"));
			this.setContentPane(setQuestionPane(level,seqence));

			//時間取得
			Calendar now = Calendar.getInstance(); //インスタンス化
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//月の値を0~11で取得
			M = M+1;//1~12にするために１を加算
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//時を取得
			int m = now.get(now.MINUTE);     //分を取得
			int s = now.get(now.SECOND);      //秒を取得
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//開始時間の取得
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			
			//System.out.println("stdate:"+stDate);
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("eddate:"+maxtime);
			//System.out.println("startTime:"+sdf.format(stDate));
			
			//System.out.println("startTime:"+startTime);
			
			//ユーザ名の取得
			user_name = get_user_name.getText();
			//System.out.println("user_name:"+user_name); 
			this.setVisible(true);
		}
		if (e.getActionCommand() == "START2" ){
			sec = 0;
			ord = 1;
			seqence = "struct";
			//this.setContentPane(setQuestionPane(level,"test_set"));
			this.setContentPane(setQuestionPane(level,seqence));
			
			//時間取得
			Calendar now = Calendar.getInstance(); //インスタンス化
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//月の値を0~11で取得
			M = M+1;//1~12にするために１を加算
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//時を取得
			int m = now.get(now.MINUTE);     //分を取得
			int s = now.get(now.SECOND);      //秒を取得
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//開始時間の取得
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("startTime:"+sdf.format(stDate));
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("startTime:"+startTime);
			
			//ユーザ名の取得
			user_name = get_user_name.getText();
			//System.out.println("user_name:"+user_name); 
			this.setVisible(true);
		}
		
		if (e.getActionCommand() == "START3" ){
			sec =0;
			ord = 1;
			//seqence = "pointer";
			seqence = "test_set";
			//this.setContentPane(setQuestionPane(level,"test_set"));
			this.setContentPane(setQuestionPane(level,seqence));

			//時間取得
			Calendar now = Calendar.getInstance(); //インスタンス化
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//月の値を0~11で取得
			M = M+1;//1~12にするために１を加算
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//時を取得
			int m = now.get(now.MINUTE);     //分を取得
			int s = now.get(now.SECOND);      //秒を取得
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//開始時間の取得
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("startTime:"+sdf.format(stDate));
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("startTime:"+startTime);
			
			//ユーザ名の取得
			user_name = get_user_name.getText();
			//System.out.println("user_name:"+user_name); 
			this.setVisible(true);
		}
		
		if (e.getActionCommand() == "ANSWER" ){
			timer.stop();
			getAns = new ArrayList();
			int end = Integer.parseInt(QL.length.get(0).toString());
			for(int i=0;i<end;i++){
				//System.out.println("ans"+i+":"+answer[i].getText());
				getAns.add(answer[i].getText());
				//System.out.println("answer:"+QL.answer.get(0).toString());
			}
			
			//時間取得
			Calendar now = Calendar.getInstance(); //インスタンス化
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//月の値を0~11で取得
			M = M+1;//1~12にするために１を加算
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//時を取得
			int m = now.get(now.MINUTE);     //分を取得
			int s = now.get(now.SECOND);      //秒を取得
			 */
			//終了時間の取得
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			endDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("endTime:"+sdf.format(endDate));
			
			//endTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			//System.out.println("endTime:"+endTime);
			
			
			this.setContentPane(markingAnswerPane(Integer.parseInt(QL.qID.get(0).toString()),"test_set",getAns));
			//this.setContentPane(setQuestionPane(1,"outputtest_set01_1"));
			this.setVisible(true);
		}
		if (e.getActionCommand() == "NEXT" ){

			sec=0;
			ord++;
			//時間取得
			Calendar now = Calendar.getInstance(); //インスタンス化
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//月の値を0~11で取得
			M = M+1;//1~12にするために１を加算
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//時を取得
			int m = now.get(now.MINUTE);     //分を取得
			int s = now.get(now.SECOND);      //秒を取得
			int n = now.get(now.MILLISECOND); //ミリ秒を取得
			
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s+":"+n;*/
			
			//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("startTime:"+sdf.format(stDate));
			//System.out.println("startTime:"+stDate);
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("startTime:"+startTime);
			//System.out.println("l:"+level);
			//this.setContentPane(setQuestionPane(level,"test_set"));
			this.setContentPane(setQuestionPane(level,seqence));
			this.setVisible(true);
		}
		if (e.getActionCommand() == "GIVEUP" ){
			timer.stop();
			this.setContentPane(giveupPane(Integer.parseInt(QL.qID.get(0).toString()),"test_set"));
			this.setVisible(true);
		}
		if (e.getActionCommand() == "HINT" ){
			timer.stop();
			this.setContentPane(setQuestionPane(level,"test_set"));
			this.setVisible(true);
		}
		//-----------------------------------------------
		time.setText("制限時間："+max+"sec 経過時間："+ sec + " sec");

	    if (sec >= max){
	      timer.stop();
	      this.setContentPane(giveupPane(Integer.parseInt(QL.qID.get(0).toString()),"test_set"));
	      this.setVisible(true);
	    }else{
	      sec++;
	    }
	    //----------------------------------------------------------
	}
	
}
