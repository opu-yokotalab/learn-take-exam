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
//���C���̒񎦁E�������s���N���X
	
	//�񎦗p�p�l��
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;//�X�^�[�g��ʒ񎦗p�p�l��
	private JPanel setQuestionPane = null;//���񎦗p�p�l��
	private JPanel markingAnswerPane = null;//�𓚒񎦗p�p�l��
	private JPanel giveupPane = null;//Give Up��ʒ񎦗p�p�l��
	
	//���N���X�Ăяo���p
	public static question_load QL;  //  ���ǂݏo��  //  @jve:decl-index=0:
	public static WriteLog WL; //���O��������
	
	//�o��֌W
	JTextField[] answer = new JTextField[10];//�񓚗�
	JTextField get_user_name = new JTextField(15);//���[�U�[�l�[���擾��
	ArrayList getAns;  // �񓚂̊i�[��
	String user_name = new String();  // ���[�U���̊i�[��  //  @jve:decl-index=0:
	String nextQuestion;
	int nextID;
	int ord; //����ڂ�
	int level = 1; //���x��
	int max_level = 3;//�ő僌�x��
	JLabel time = new JLabel();
	Timer timer;  //  @jve:decl-index=0:
	int sec=0;

	//�V�[�P���X����
	String seqence = new String();  //  @jve:decl-index=0:
	//��r��
	int cont_correct = 1;// �A������
	int cont_mistake = 1;//�A���ԈႢ��
	int total_correct = 3;//���v����
	int total_mistake = 3;//���v�ԈႢ��
	//�ϐ��l
	int co_co =0;// �A������
	int co_mi =0;//�A���ԈႢ��
	int to_co =0;//���v����
	int to_mi =0;//���v�ԈႢ��
	
	//���Ԋ֌W
	int max = 10;
	java.sql.Timestamp stDate;  //  ���̏o�莞��
	java.sql.Timestamp maxtime;  //�ő�𓚎���
	java.sql.Timestamp endDate;  // �񓚊�������
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
	private void initialize() {//������
		this.setSize(600, 500);
		this.setContentPane(getJContentPane());
		//this.setContentPane(setQuestionPane());
		this.setTitle("���K�V�X�e��");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {//������ʂ̒�
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
		
		//���[�U������
		JLabel un = new JLabel("���[�U�[��");
		userPanel.add(un);
		userPanel.add(get_user_name);
		startPanel.add(userPanel,BorderLayout.NORTH);

		JPanel s = new JPanel();
		JLabel sLab = new JLabel("�w�K����");
		s.add(sLab);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��1
		s = new JPanel();
		//JLabel sLab = new JLabel("�z��");
		sLab = new JLabel("�z��");
		//sLab = new JLabel("For��");
		s.add(sLab);
		JButton startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START1");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��2
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�\����");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START2");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��3
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�|�C���^");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
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
	//�o����
		//if (setQuestionPane == null) {
			setQuestionPane = new JPanel();
			//jContentPane.setSize(350, 400);
			setQuestionPane.setLayout(new BorderLayout());
		//}
		
		JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
		JPanel qPanel = new JPanel();//��蕶�񎦗p�̃p�l��
		JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
		JPanel aPanel = new JPanel();//�𓚗��񎦗p�p�l��
		
		QL = new question_load(level,p_set);
		
		//�����̒�
		JLabel order = new JLabel ("��"+ord+"���  ");
		infoPanel.add(order);
		//level = Integer.parseInt(QL.level.get(0).toString());
		JLabel l = new JLabel("Level:"+level);
		infoPanel.add(l);
		JLabel qid = new JLabel("���ID�F"+QL.qID.get(0).toString());
		infoPanel.add(qid);
		JLabel ps = new JLabel("Program_set:"+QL.program_set.get(0).toString());
		infoPanel.add(ps);
		infoPanel.add(time);
		timer = new Timer(1000,this);
		timer.start();
		
		
		//��蕶�̔z�u
		//System.out.println("question:"+QL.question.get(0).toString());
		JTextArea qTA = new JTextArea(QL.question.get(0).toString());//��蕶���
		qTA.setSize(200, 300);
		qTA.setEditable(false);//�������ݕs�ɂ���
		//qTA.setBackground(UIManager.getColor("control"));//BGColor������Ɠ�����
		qPanel.add(qTA);//�e�L�X�g�G���A���p�l���ɒǉ�
		
		//�{�^���̔z�u
		JButton ans = new JButton("�̓_");//�̓_�{�^��
		ans.setActionCommand("ANSWER");
		ans.addActionListener(this);
		bPanel.add(ans);
		JButton g_up = new JButton("GIVE UP");//Give up �{�^��
		g_up.setActionCommand("GIVEUP");
		g_up.addActionListener(this);
		bPanel.add(g_up);		
		JButton hints = new JButton("HINT");//�q���g�{�^��
		hints.setActionCommand("HINT");
		hints.addActionListener(this);
		bPanel.add(hints);
		
		//�𓚗��̔z�u
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

		//���x���̎擾
		//level = Integer.parseInt(QL.level.get(0).toString());
		//System.out.println("blank:"+QL.blank.size());
		
		
		setQuestionPane.add(infoPanel, BorderLayout.NORTH);
		setQuestionPane.add(qPanel, BorderLayout.WEST);
		setQuestionPane.add(bPanel, BorderLayout.SOUTH);
		setQuestionPane.add(aPanel, BorderLayout.CENTER);
		
		return setQuestionPane;
	}
	
	private JPanel markingAnswerPane(int ID,String p_set,ArrayList ans){
	//�̓_�񎦉��
		
			markingAnswerPane = new JPanel();
			//jContentPane.setSize(350, 400);
			markingAnswerPane.setLayout(new BorderLayout());

			nextQuestion = new String ();
			nextID = 0;
			
			JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
			JPanel aPanel = new JPanel();//�𓚒񎦗p�̃p�l��
			//aPanel.setLayout(new GridLayout(4,1));
			JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
			int flag = 0;
			
			//�����̒�
			JLabel order = new JLabel ("��"+ord+"���  ");
			infoPanel.add(order);
			//JLabel l = new JLabel("Level:"+QL.level.get(0).toString());
			JLabel l = new JLabel("Level:"+level);
			infoPanel.add(l);
			//JLabel qid = new JLabel("���ID�F"+QL.qID.get(0).toString());
			JLabel qid = new JLabel("���ID�F"+ID);
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
			JLabel hantei = new JLabel("����F");
			aPanel.add(hantei);
			//System.out.println("level:"+level);
			if(flag == 0){
				JLabel sei = new JLabel("���F�����I�I");
				aPanel.add(sei);
				//JLabel a = new JLabel(QL.supplementation.get(0).toString());
				//aPanel.add(a);
				
				//
				co_co++;//�A�����𐔉��Z
				to_co++;//���v���𐔉��Z
				co_mi=0;//�A����萔���O��
				//System.out.println("���F�����I�I");
				
				//���x�����グ�邩�ǂ����̔���
				if(co_co >= cont_correct){
					//�A�����𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level<max_level){
						level++;//���x�����グ��
						//System.out.println("levelUP1:"+level);
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_co >= total_correct){
					//���v���𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level<max_level){
						level++;//���x�����グ��
						//System.out.println("levelUP2:"+level);
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
			}else{
				JLabel go = new JLabel("�~:�s�����I�I");
				aPanel.add(go);

				//System.out.println("�~:�s�����I�I");
				
				co_mi++;//�A����萔���Z
				to_mi++;//���v��萔���Z
				co_co=0;//�A�����𐔂��O��
				
				if(co_mi >= cont_mistake){
					//�A�����𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level>1){
						level--;//���x����������
						//System.out.println("levelDOWN1:"+level);
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_mi >= total_mistake){
					//���v���𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level>1){
						level--;//���x����������
						//System.out.println("levelDOWN2:"+level);
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
			}
			//���Ȃ��̉񓚒�
			JLabel you = new JLabel("���Ȃ��̉�");
			aPanel.add(you);
			for(int i=0;i<end;i++){
				JLabel yourA = new JLabel((i+1)+":"+ans.get(i).toString());
				aPanel.add(yourA);
			}
			//�͔͉𓚒�
			JLabel kai = new JLabel("�͔͉�");
			aPanel.add(kai);
			for(int i=0;i<end;i++){
				JLabel mohan = new JLabel((i+1)+":"+QL.answer.get(i).toString());
				aPanel.add(mohan);
			}
			JLabel a = new JLabel(QL.supplementation.get(0).toString());
			aPanel.add(a);
			//QL.answer.get(0).toString();

			
			//DB�ւ̏�������
			//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
			WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,level,stDate,endDate);
			
			
			//�{�^���̔z�u
			JButton nextb = new JButton("���̖��");//�̓_�{�^��
			nextb.setActionCommand("NEXT");
			nextb.addActionListener(this);
			bPanel.add(nextb);
			
			JButton endb = new JButton("�I��");
			endb.setActionCommand("END");
			endb.addActionListener(this);
			bPanel.add(endb);

			markingAnswerPane.add(bPanel, BorderLayout.SOUTH);
			markingAnswerPane.add(infoPanel, BorderLayout.NORTH);
			markingAnswerPane.add(aPanel, BorderLayout.CENTER);
			
		return markingAnswerPane;
	}

	private JPanel giveupPane(int ID,String p_set){
		//giveup���
			
				giveupPane = new JPanel();
				//jContentPane.setSize(350, 400);
				giveupPane.setLayout(new BorderLayout());

				nextQuestion = new String ();
				nextID = 0;
				
				JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
				JPanel aPanel = new JPanel();//�𓚒񎦗p�̃p�l��
				//aPanel.setLayout(new GridLayout(4,1));
				JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
				int flag = 1;
				
				//�����̒�
				JLabel order = new JLabel ("��"+ord+"���  ");
				infoPanel.add(order);
				JLabel l = new JLabel("Level:"+level);
				infoPanel.add(l);
				JLabel qid = new JLabel("���ID�F"+ID);
				infoPanel.add(qid);
				JLabel ps = new JLabel("Program_set:"+p_set);
				infoPanel.add(ps);

				JLabel kai = new JLabel("���");
				aPanel.add(kai);
				JLabel a = new JLabel(QL.supplementation.get(0).toString());
				aPanel.add(a);
				
				
				endDate = maxtime;
				//System.out.println(endDate);
				//QL.answer.get(0).toString();
				//�����Ŏ��̖��̔��肵�Ƃ��H�H
				
				//DB�ւ̏�������
				//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
				WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,level,stDate,endDate);
				
				co_mi++;//�A����萔���Z
				to_mi++;//���v��萔���Z
				co_co=0;//�A�����𐔂��O��
				
				if(co_mi >= cont_mistake){
					//�A�����𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level>1){
						level--;//���x����������
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}else if(to_mi >= total_mistake){
					//���v���𐔂�����l�ȏ�Ȃ烌�x�����グ��
					if(level>1){
						level--;//���x����������
						co_co=0;//�e�l��������
						to_co=0;
						co_mi=0;
						to_mi=0;
					}
				}
				
				
				//�{�^���̔z�u
				JButton nextb = new JButton("���̖��");//�̓_�{�^��
				nextb.setActionCommand("NEXT");
				nextb.addActionListener(this);
				bPanel.add(nextb);
				
				JButton endb = new JButton("�I��");
				endb.setActionCommand("END");
				endb.addActionListener(this);
				bPanel.add(endb);

				giveupPane.add(bPanel, BorderLayout.SOUTH);
				giveupPane.add(infoPanel, BorderLayout.NORTH);
				giveupPane.add(aPanel, BorderLayout.CENTER);
				
			return giveupPane;
		}
	
	public void actionPerformed(ActionEvent e) {
		
	//�{�^���̓���
		if (e.getActionCommand() == "START1" ){
			sec=0;
			ord = 1;
			//seqence = "array";
			seqence = "test_set";
			//this.setContentPane(setQuestionPane(level,"test_set"));
			this.setContentPane(setQuestionPane(level,seqence));

			//���Ԏ擾
			Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
			M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//�����擾
			int m = now.get(now.MINUTE);     //�����擾
			int s = now.get(now.SECOND);      //�b���擾
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//�J�n���Ԃ̎擾
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			
			//System.out.println("stdate:"+stDate);
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("eddate:"+maxtime);
			//System.out.println("startTime:"+sdf.format(stDate));
			
			//System.out.println("startTime:"+startTime);
			
			//���[�U���̎擾
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
			
			//���Ԏ擾
			Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
			M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//�����擾
			int m = now.get(now.MINUTE);     //�����擾
			int s = now.get(now.SECOND);      //�b���擾
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//�J�n���Ԃ̎擾
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("startTime:"+sdf.format(stDate));
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("startTime:"+startTime);
			
			//���[�U���̎擾
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

			//���Ԏ擾
			Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
			M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//�����擾
			int m = now.get(now.MINUTE);     //�����擾
			int s = now.get(now.SECOND);      //�b���擾
			startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			*/
			
			//�J�n���Ԃ̎擾
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			stDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("startTime:"+sdf.format(stDate));
			max = Integer.parseInt(QL.max_time.get(0).toString());
			maxtime = new java.sql.Timestamp(now.getTimeInMillis()+max*1000);
			//System.out.println("startTime:"+startTime);
			
			//���[�U���̎擾
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
			
			//���Ԏ擾
			Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
			M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//�����擾
			int m = now.get(now.MINUTE);     //�����擾
			int s = now.get(now.SECOND);      //�b���擾
			 */
			//�I�����Ԃ̎擾
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
			//���Ԏ擾
			Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
			/*int Y = now.get(now.YEAR);
			int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
			M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
			int D = now.get(now.DATE);
			int h = now.get(now.HOUR_OF_DAY);//�����擾
			int m = now.get(now.MINUTE);     //�����擾
			int s = now.get(now.SECOND);      //�b���擾
			int n = now.get(now.MILLISECOND); //�~���b���擾
			
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
		time.setText("�������ԁF"+max+"sec �o�ߎ��ԁF"+ sec + " sec");

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
