import java.awt.*;
//import java.awt.Color;
//import java.awt.BorderLayout;
//import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.io.*;

import javax.swing.Timer;
import java.lang.System;
//import java.sql.*;



public class Take_Exam_main extends JFrame implements ActionListener{
//���C���̒񎦁E�������s���N���X
	
	//�񎦗p�p�l��
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;//�X�^�[�g��ʒ񎦗p�p�l��
	private JPanel setQuestionPane = null;//���񎦗p�p�l��
	private JPanel markingAnswerPane = null;//�𓚒񎦗p�p�l��
	private JPanel giveupPane = null;//Give Up��ʒ񎦗p�p�l��
	private JPanel endPane = null;//�I����ʒ񎦗p�p�l��
	
	//���N���X�Ăяo���p
	public static question_load QL;  //  ���ǂݏo��  //  @jve:decl-index=0:
	public static WriteLog WL; //���O��������
	
	//�f�t�H���g�t�H���g
	//Font defFont = new Font("monospaced", Font.PLAIN, 16);
	Font defFont = new Font("monospaced", Font.BOLD, 16);
	
	//�o��֌W
	JTextField[] answer = new JTextField[10];//�񓚗�
	JTextField get_user_name = new JTextField(15);//���[�U�[�l�[���擾��
	ArrayList getAns;  // �񓚂̊i�[��  //  @jve:decl-index=0:
	String user_name = new String();  // ���[�U���̊i�[��  //  @jve:decl-index=0:
	String nextQuestion;
	int nextID;
	int ord; //����ڂ�
	int small_ord;
	int max_ord = 7;//�S�o�萔
	int level = 1; //���x��
	int max_level = 3;//�ő僌�x��
	ArrayList ready;  //  @jve:decl-index=0:
	JLabel time = new JLabel();
	Timer timer;  //  @jve:decl-index=0:
	int sec=0;

	//�V�[�P���X����
	String seqence = new String();  //  @jve:decl-index=0:
	//��r��
	int cont_correct = 2;// �A������
	int cont_mistake = 2;//�A���ԈႢ��
	int total_correct = 3;//���v����
	int total_mistake = 3;//���v�ԈႢ��
	//�ϐ��l
	int co_co =0;// �A������
	int co_mi =0;//�A���ԈႢ��
	int to_co =0;//���v����
	int to_mi =0;//���v�ԈႢ��
	
	//�ߑS�̂̐���
	int full_co = 0;
	int full_mi = 0;
	
	//���Ԋ֌W
	int max = 10;
	java.sql.Timestamp stDate;  //  ���̏o�莞��  //  @jve:decl-index=0:
	java.sql.Timestamp maxtime;  //�ő�𓚎���
	java.sql.Timestamp endDate;  // �𓚊�������
	java.sql.Timestamp startTime;
	java.sql.Timestamp endTime;
	java.sql.Timestamp takeTime;  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public Take_Exam_main() {
	//public void main(){
		//super();
		/*try {
			System.setErr(new PrintStream(new FileOutputStream("errorLog.txt")));
			//System.setOut(new PrintStream(new FileOutputStream("outputLog.txt")));
		} catch (FileNotFoundException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}*/
		initialize();
		this.setVisible(true);
	}
	
	/*public void main(String[] args) {
	//public void main(){
		Take_Exam_main TE = new Take_Exam_main();
		//initialize();
		TE.setVisible(true);
	}*/
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {//������
		this.setSize(1000, 750);
		this.setContentPane(getJContentPane());

		//this.setContentPane(setQuestionPane());
		this.setTitle("���K�V�X�e��");
		//this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {//������ʂ̒�
		//if (jContentPane == null) {
		jContentPane = new JPanel();
		//jContentPane.setSize(350, 400);
		jContentPane.setLayout(new BorderLayout());
		//}
		JPanel startPanel= new JPanel();
		JPanel userPanel = new JPanel();
		JPanel seqPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());
		seqPanel.setLayout(new GridLayout(7,1));
		
		//���𐔓��̏�����
		full_co=0;
		full_mi=0;
		ord = 1;
		small_ord=1;
		level=1;
		co_co =0;// �A������
		co_mi =0;//�A���ԈႢ��
		to_co =0;//���v����
		to_mi =0;//���v�ԈႢ��
		ready = new ArrayList();
		
		//���[�U������
		JLabel un = new JLabel("���[�U�[��");
		userPanel.add(un);
		//get_user_name.setText(user_name);
		userPanel.add(get_user_name);
		startPanel.add(userPanel,BorderLayout.NORTH);

		JPanel s = new JPanel();
		JLabel sLab = new JLabel("�w�K����");
		s.add(sLab);
		seqPanel.add(s);
		
		/*//�V�[�P���X�{�^��1
		s = new JPanel();
		//JLabel sLab = new JLabel("�z��");
		sLab = new JLabel("�\����1");
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
		sLab = new JLabel("�\����2");
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
		sLab = new JLabel("�\����3");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START3");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��4
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�|�C���^1");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START4");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��5
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�|�C���^2");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START5");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��6
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�|�C���^3");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START6");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);*/
		
		//�V�[�P���X�{�^��7
		s = new JPanel();
		//JLabel sLab = new JLabel("�z��");
		sLab = new JLabel("�e�X�g1��ځF���̂P");
		//sLab = new JLabel("For��");
		s.add(sLab);
		JButton startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START7");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��8
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�e�X�g1��ځF����2");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START8");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��9
		s = new JPanel();
		//JLabel sLab = new JLabel("�z��");
		sLab = new JLabel("�e�X�g2��ځF���̂P");
		//sLab = new JLabel("For��");
		s.add(sLab);
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START9");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��10
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�e�X�g2��ځF����2");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START10");
		startB.addActionListener(this);
		//qPanel.setSize(300, 400);
		s.add(startB);
		seqPanel.add(s);
		
		//�V�[�P���X�{�^��11
		//���̎��������̃f�t�H���g�l�p
		//������p�̈ꎞ�I�Ȓǉ�
		s = new JPanel();
		//JLabel sLab = new JLabel("�|�C���^");
		sLab = new JLabel("�f���p");
		s.add(sLab);
		//JButton startB = new JButton("�w�K���n�߂�");
		startB = new JButton("�w�K���n�߂�");
		startB.setActionCommand("START11");
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
		
		//QL = new question_load(level,p_set,small_ord,ord,ready);
		QL = new question_load(level,p_set,small_ord,ord,ready);
		
		//�����̒�
		JLabel order = new JLabel ("��"+ord+"���  ");
		order.setFont(defFont);//demo
		infoPanel.add(order);
		//level = Integer.parseInt(QL.level.get(0).toString());
		JLabel l = new JLabel("Level:"+level);
		l.setFont(defFont);//demo
		infoPanel.add(l);
		//JLabel qid = new JLabel("���ID�F"+QL.qID.get(0).toString());
		//infoPanel.add(qid);
		//JLabel ps = new JLabel("Program_set:"+QL.program_set.get(0).toString());
		//infoPanel.add(ps);
		time.setFont(defFont);//demo
		infoPanel.add(time);
		timer = new Timer(1000,this);
		timer.start();
		
		
		//��蕶�̔z�u
		//System.out.println("question:"+QL.question.get(0).toString());
		//JTextArea qTA = new JTextArea(QL.question.get(0).toString());//��蕶���
		//JTextArea qTA = new JTextArea(27, 80);//��蕶���
		JTextArea qTA = new JTextArea(27, 65);//��蕶��񎦁i�f���p�j
		
		//scrollpane.setLocation(0,0);
		//scrollpane.setBounds(10,10,50,50);
		qTA.setText(QL.question.get(0).toString());
		//qTA.setFont(new Font("BOLD", Font.PLAIN,16));
		qTA.setFont(defFont);
		//qTA.setSize(500, 400);
		//qTA.setSize(300,200);
		qTA.setEditable(false);//�������ݕs�ɂ���
		//qTA.setBackground(UIManager.getColor("control"));//BGColor������Ɠ�����
		//qPanel.add(qTA);//�e�L�X�g�G���A���p�l���ɒǉ�
		JScrollPane scrollpane = new JScrollPane(qTA);//�X�N���[���o�[
		//JViewport view = scrollpane.getViewport();
		//view.setViewPosition(new Point(0,0));
		qPanel.add(scrollpane);//�e�L�X�g�G���A���p�l���ɒǉ�
		

		
		//�𓚗��̔z�u
		//aPanel.setLayout(new GridLayout(QL.blank.size(),1));
		aPanel.setLayout(new GridLayout(10,1));
		for(int i=0;i<QL.blank.size();i++){
			JPanel form = new JPanel();
			JLabel resp = new JLabel(QL.blank.get(i).toString());
			form.add(resp);
			answer[i] = new JTextField(15);
			answer[i].setName(QL.blank.get(i).toString());
			form.add(answer[i]);
			//aPanel.add(form,i,0);
			aPanel.add(form);
		}

		JLabel space = new JLabel("                     ");
		//bPanel.add(space);
		aPanel.add(space);
		
		//�{�^���̔z�u
		JButton ans = new JButton("�̓_");//�̓_�{�^��
		ans.setActionCommand("ANSWER");
		ans.addActionListener(this);
		//bPanel.add(ans);
		JPanel ansPanel = new JPanel();
		ansPanel.add(ans);
		aPanel.add(ansPanel);
		
		aPanel.add(space);
		
		JButton g_up = new JButton("GIVE UP");//Give up �{�^��
		g_up.setActionCommand("GIVEUP");
		g_up.addActionListener(this);
		//bPanel.add(g_up);
		JPanel g_upPanel = new JPanel();
		g_upPanel.add(g_up);
		aPanel.add(g_upPanel);
		/*JButton hints = new JButton("HINT");//�q���g�{�^��
		hints.setActionCommand("HINT");
		hints.addActionListener(this);
		bPanel.add(hints);*/
		
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
			small_ord++;
			
			JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
			//infoPanel.setFont(defFont);
			JPanel mPanel = new JPanel();//��蕶�񎦗p�p�l��
			
			JPanel aPanel = new JPanel();//�𓚒񎦗p�̃p�l��
			aPanel.setLayout(new GridLayout(10,1));
			//aPanel.setFont(defFont);
			//aPanel.setLayout(new GridLayout(4,1));
			JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
			int flag = 0;
			
			int l = Integer.parseInt(QL.level.get(0).toString());
			//�����̒�
			JLabel order = new JLabel ("��"+ord+"���  ");
			order.setFont(defFont);//demo
			infoPanel.add(order);
			//JLabel l = new JLabel("Level:"+QL.level.get(0).toString());
			JLabel lv = new JLabel("Level:"+l);
			lv.setFont(defFont);//demo
			infoPanel.add(lv);
			
			
			//��蕶�̔z�u
			//System.out.println("question:"+QL.question.get(0).toString());
			//JTextArea qTA = new JTextArea(QL.question.get(0).toString());//��蕶���
			//JTextArea qTA = new JTextArea(27, 80);//��蕶���
			JTextArea qTA = new JTextArea(27, 65);//��蕶���(demo)
			
			JScrollPane scrollpane = new JScrollPane(qTA);//�X�N���[���o�[
			//scrollpane.setLocation(0,0);
			//scrollpane.setBounds(10,10,50,50);
			qTA.setText(QL.question.get(0).toString());
			//qTA.setFont(new Font("BOLD", Font.PLAIN,16));
			qTA.setFont(defFont);
			//qTA.setSize(500, 400);
			//qTA.setSize(300,200);
			qTA.setEditable(false);//�������ݕs�ɂ���
			//qTA.setBackground(UIManager.getColor("control"));//BGColor������Ɠ�����
			//qPanel.add(qTA);//�e�L�X�g�G���A���p�l���ɒǉ�
			mPanel.add(scrollpane);//�e�L�X�g�G���A���p�l���ɒǉ�
			
			//JLabel qid = new JLabel("���ID�F"+QL.qID.get(0).toString());
			//JLabel qid = new JLabel("���ID�F"+ID);
			//infoPanel.add(qid);
			//JLabel ps = new JLabel("Program_set:"+QL.program_set.get(0).toString());
			//JLabel ps = new JLabel("Program_set:"+p_set);
			//infoPanel.add(ps);
			
			//int end1 = Integer.parseInt(QL.length.get(0).toString());
			int end = ans.size();
			for(int i=0;i<end;i++){
				if((QL.answer.get(i).toString()).equals(ans.get(i).toString())){
					
				}
				else {
					flag =1;
				}
			}
			//JLabel hantei = new JLabel("����F");
			//aPanel.add(hantei);
			String hantei = new String ("����F");
			//System.out.println("level:"+level);
			if(flag == 0){
				//JLabel sei = new JLabel("���F�����I�I");
				
				hantei = hantei + "��  �����I�I";
				JLabel sei = new JLabel(hantei,SwingConstants.CENTER);
				sei.setFont(defFont);
				sei.setForeground(Color.red);
				//sei.setAlignment(new SWT.CENTER); 
				aPanel.add(sei);
				//JLabel a = new JLabel(QL.supplementation.get(0).toString());
				//aPanel.add(a);
				
				//
				co_co++;//�A�����𐔉��Z
				to_co++;//���v���𐔉��Z
				co_mi=0;//�A����萔���O��
				full_co++;//�ߑS�̂̐��𐔂ɉ��Z
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
						small_ord=1;
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
						small_ord=1;
					}
				}
				
			}else{
				hantei = hantei + "�~  �s�����I�I";
				JLabel go = new JLabel(hantei,SwingConstants.CENTER);
				go.setFont(defFont);
				go.setForeground(Color.blue);
				aPanel.add(go);

				//System.out.println("�~:�s�����I�I");
				
				co_mi++;//�A����萔���Z
				to_mi++;//���v��萔���Z
				co_co=0;//�A�����𐔂��O��
				full_mi++;//�ߑS�̂̊ԈႢ���ɉ��Z
				
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
			String you = new String();
			JLabel y = new JLabel("���Ȃ��̉�",SwingConstants.CENTER);
			y.setFont(defFont);
			aPanel.add(y);
			for(int i=0;i<end;i++){
				you = you + (i+1)+":"+ans.get(i).toString()+"  ";
				//JLabel yourA = new JLabel((i+1)+":"+ans.get(i).toString());
				//aPanel.add(yourA);
			}
			JLabel yourA = new JLabel(you,SwingConstants.CENTER);
			yourA.setFont(defFont);
			aPanel.add(yourA);
			
			//�͔͉𓚒�
			String kai = new String();
			JLabel k = new JLabel("�͔͉�",SwingConstants.CENTER);
			k.setFont(defFont);
			aPanel.add(k);
			for(int i=0;i<end;i++){
				kai = kai + (i+1) + ":" + QL.answer.get(i).toString() + "  ";
				//JLabel mohan = new JLabel((i+1)+":"+QL.answer.get(i).toString());
				//aPanel.add(mohan);
			}
			JLabel mohan = new JLabel(kai,SwingConstants.CENTER);
			mohan.setFont(defFont);
			aPanel.add(mohan);
			
			//�񓚂ɂ�����������
			//JLabel time = new JLabel("�񓚂ɂ�����������");
			//aPanel.add(time);
			//int t = endTime.compareTo(startTime);
			//String ti = Integer.toString(t);
			//JLabel timeL = new JLabel(ti);
			//aPanel.add(timeL);
			
			JTextArea a = new JTextArea(QL.supplementation.get(0).toString());
			a.setLineWrap(true);  
			a.setFont(defFont);
			a.setBackground(UIManager.getColor("control"));
			aPanel.add(a);
			//QL.answer.get(0).toString();
			
			//�����s�̃R�����g�ɑΉ����邽�ߍ�ƒ�
			//JTextArea com = new JTextArea(30, 20);//�R�����g���
			//JScrollPane scrollpane = new JScrollPane(com);//�X�N���[���o�[
			//scrollpane.setLocation(0,0);
			//scrollpane.setBounds(10,10,50,50);
			//com.setText(QL.supplementation.get(0).toString());
			//qTA.setFont(new Font("BOLD", Font.PLAIN,16));
			//com.setFont(defFont);
			//qTA.setSize(500, 400);
			//qTA.setSize(300,200);
			//com.setEditable(false);//�������ݕs�ɂ���
			//qTA.setBackground(UIManager.getColor("control"));//BGColor������Ɠ�����
			//qPanel.add(qTA);//�e�L�X�g�G���A���p�l���ɒǉ�
			//aPanel.add(com);//�e�L�X�g�G���A���p�l���ɒǉ�

			
			//DB�ւ̏�������
			//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
			WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,l,ans,QL.answer,stDate,endDate);
			
			
			//�{�^���̔z�u
			if(ord<max_ord){
				JButton nextb = new JButton("���̖��");//�̓_�{�^��
				nextb.setActionCommand("NEXT");
				nextb.addActionListener(this);
				//bPanel.add(nextb);
				JPanel nxP = new JPanel();
				nxP.add(nextb);
				aPanel.add(nxP);
			}
			else{
				JButton endb = new JButton("�I��");
				endb.setActionCommand("END");
				endb.addActionListener(this);
				bPanel.add(endb);
			}
			markingAnswerPane.add(bPanel, BorderLayout.SOUTH);
			markingAnswerPane.add(infoPanel, BorderLayout.NORTH);
			markingAnswerPane.add(mPanel, BorderLayout.WEST);
			markingAnswerPane.add(aPanel, BorderLayout.CENTER);
			
		return markingAnswerPane;
	}

	private JPanel giveupPane(int ID,String p_set,ArrayList ans){
		//giveup���
				small_ord++;
				giveupPane = new JPanel();
				//jContentPane.setSize(350, 400);
				giveupPane.setLayout(new BorderLayout());

				nextQuestion = new String ();
				nextID = 0;
				
				JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
				JPanel mPanel = new JPanel();//��蕶�񎦗p�p�l��
				JPanel aPanel = new JPanel();//�𓚒񎦗p�̃p�l��
				aPanel.setLayout(new GridLayout(10,1));
				JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
				int flag = 1;
				
				int l = Integer.parseInt(QL.level.get(0).toString());
				
				//�����̒�
				JLabel order = new JLabel ("��"+ord+"���  ");
				infoPanel.add(order);
				JLabel lv = new JLabel("Level:"+l);
				infoPanel.add(lv);
				//JLabel qid = new JLabel("���ID�F"+ID);
				//infoPanel.add(qid);
				//JLabel ps = new JLabel("Program_set:"+p_set);
				//infoPanel.add(ps);

				//��蕶�̔z�u
				//System.out.println("question:"+QL.question.get(0).toString());
				//JTextArea qTA = new JTextArea(QL.question.get(0).toString());//��蕶���
				//JTextArea qTA = new JTextArea(27, 80);//��蕶���
				JTextArea qTA = new JTextArea(27, 65);//��蕶���(demo)
				
				JScrollPane scrollpane = new JScrollPane(qTA);//�X�N���[���o�[
				//scrollpane.setLocation(0,0);
				//scrollpane.setBounds(10,10,50,50);
				qTA.setText(QL.question.get(0).toString());
				//qTA.setFont(new Font("BOLD", Font.PLAIN,16));
				qTA.setFont(defFont);
				//qTA.setSize(500, 400);
				//qTA.setSize(300,200);
				qTA.setEditable(false);//�������ݕs�ɂ���
				//qTA.setBackground(UIManager.getColor("control"));//BGColor������Ɠ�����
				//qPanel.add(qTA);//�e�L�X�g�G���A���p�l���ɒǉ�
				mPanel.add(scrollpane);//�e�L�X�g�G���A���p�l���ɒǉ�
				
				
				
				int end = QL.answer.size();
				//JLabel kai = new JLabel("�͔͉�");
				//kai.setFont(defFont);
				//aPanel.add(kai);
				//for(int i=0;i<end;i++){
				//	JLabel mohan = new JLabel((i+1)+":"+QL.answer.get(i).toString());
				//	mohan.setFont(defFont);
				//	aPanel.add(mohan);
				//}
				//�͔͉𓚒�
				String kai = new String();
				JLabel k = new JLabel("�͔͉�",SwingConstants.CENTER);
				k.setFont(defFont);
				aPanel.add(k);
				for(int i=0;i<end;i++){
					kai = kai + (i+1) + ":" + QL.answer.get(i).toString() + "  ";
					//JLabel mohan = new JLabel((i+1)+":"+QL.answer.get(i).toString());
					//aPanel.add(mohan);
				}
				JLabel mohan = new JLabel(kai,SwingConstants.CENTER);
				mohan.setFont(defFont);
				aPanel.add(mohan);
				
				JLabel kaisetu = new JLabel("���",SwingConstants.CENTER);
				kaisetu.setFont(defFont);
				aPanel.add(kaisetu);
				//JLabel a = new JLabel(QL.supplementation.get(0).toString(),SwingConstants.CENTER);
				JTextArea a = new JTextArea(QL.supplementation.get(0).toString());
				a.setLineWrap(true);  
				a.setFont(defFont);
				a.setBackground(UIManager.getColor("control"));
				aPanel.add(a);
				
				
				endDate = maxtime;
				//System.out.println(endDate);
				//QL.answer.get(0).toString();
				//�����Ŏ��̖��̔��肵�Ƃ��H�H
				
				//DB�ւ̏�������
				//WriteLog(String username,int judg,String seq,int order,String program_set,int p_ID,int q_lev,Timestapm start,Timestamp end)
				WL = new WriteLog(user_name,flag,seqence,ord,p_set,ID,l,ans,QL.answer,stDate,endDate);
				
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
				if(ord<max_ord){
					JButton nextb = new JButton("���̖��");//�̓_�{�^��
					nextb.setActionCommand("NEXT");
					nextb.addActionListener(this);
					//bPanel.add(nextb);
					JPanel nxP = new JPanel();
					nxP.add(nextb);
					aPanel.add(nxP);
				}
				else{
					JButton endb = new JButton("�I��");
					endb.setActionCommand("END");
					endb.addActionListener(this);
					bPanel.add(endb);
				}
				giveupPane.add(bPanel, BorderLayout.SOUTH);
				giveupPane.add(mPanel, BorderLayout.WEST);
				giveupPane.add(infoPanel, BorderLayout.NORTH);
				giveupPane.add(aPanel, BorderLayout.CENTER);
				
			return giveupPane;
		}
	
	private JPanel endPane(int ID,String p_set){
			//�ߏI�����
				endPane = new JPanel();
				//jContentPane.setSize(350, 400);
				endPane.setLayout(new BorderLayout());
				
				JPanel infoPanel = new JPanel();//���񎦗p�̃p�l��
				JPanel cPanel = new JPanel();//�R�����g�񎦗p�̃p�l��
				cPanel.setLayout(new GridLayout(5,1));
				JPanel bPanel = new JPanel();//�{�^���񎦗p�p�l��
				
				//�����̒�
				JLabel order = new JLabel ("��"+ord+"���  ");
				infoPanel.add(order);
				JLabel l = new JLabel("Level:"+level);
				infoPanel.add(l);
				//JLabel qid = new JLabel("���ID�F"+ID);
				//infoPanel.add(qid);
				//JLabel ps = new JLabel("Program_set:"+p_set);
				//infoPanel.add(ps);
				
				JLabel kai = new JLabel("��"+seqence+"�͏I�����܂����B����ꂳ�܂ł����B",SwingConstants.CENTER);
				kai.setFont(defFont);//�t�H���g�ݒ�
				cPanel.add(kai);
				JLabel sei = new JLabel("����:"+full_co+"/"+max_ord,SwingConstants.CENTER);//���𐔂̕\��
				int r = (int)(((double)full_co/(double)max_ord)*100);//���𗦂̌v�Z
				System.out.println("r:"+r);
				JLabel ritu = new JLabel("������:"+r+"%",SwingConstants.CENTER);
				
				sei.setFont(defFont);//�t�H���g�ݒ�
				ritu.setFont(defFont);
				
				cPanel.add(sei);//�p�l���֔z�u
				cPanel.add(ritu);
				
				JButton endb = new JButton("�n�߂ɖ߂�");
				endb.setActionCommand("RETURN");
				endb.addActionListener(this);
				bPanel.add(endb);

				endPane.add(bPanel, BorderLayout.SOUTH);
				endPane.add(infoPanel, BorderLayout.NORTH);
				endPane.add(cPanel, BorderLayout.CENTER);
				
				return endPane;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	//�{�^���̓���

		if(e.getActionCommand()!=null){//����if���������������󂯕t���Ȃ����߂̉��}���u�i�^�C�}�[��null�̔�����s��Ȃ��Ƃ����Ȃ��悤�j
			if (e.getActionCommand().matches("START[0-9]+")){
				System.out.println(e.getActionCommand());
				sec=0;
				//ord = 1;

				if(e.getActionCommand() == "START1"){
					seqence = "point1";
				}
				if(e.getActionCommand() == "START2"){
					seqence = "struct2";
				}
				if(e.getActionCommand() == "START3"){
					seqence = "struct3";
				}
				if(e.getActionCommand() == "START4"){
					seqence = "pointer";
				}
				if(e.getActionCommand() == "START5"){
					seqence = "pointer2";
				}
				if(e.getActionCommand() == "START6"){
					seqence = "pointer3";
				}
				if(e.getActionCommand() == "START7"){
					seqence = "point11";
				}
				if(e.getActionCommand() == "START8"){
					seqence = "point21";
				}
				if(e.getActionCommand() == "START9"){
					seqence = "point12";
				}
				if(e.getActionCommand() == "START10"){
					//System.out.println("load");
					seqence = "point22";
				}
				if(e.getActionCommand() == "START11"){
					seqence = "test_set001";
				}
			
				this.setContentPane(setQuestionPane(level,seqence));
				//���Ԏ擾
				Calendar now = Calendar.getInstance(); //�C���X�^���X��
			
				//int Y = now.get(now.YEAR);
				//int M = now.get(now.MONTH);//���̒l��0~11�Ŏ擾
				//M = M+1;//1~12�ɂ��邽�߂ɂP�����Z
				//int D = now.get(now.DATE);
				//int h = now.get(now.HOUR_OF_DAY);//�����擾
				//int m = now.get(now.MINUTE);     //�����擾
				//int s = now.get(now.SECOND);      //�b���擾
				//startTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			
			
				//�J�n���Ԃ̎擾
				//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
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
				//this.setContentPane(setQuestionPane(level,seqence));
				this.setVisible(true);
			
			}
		}
		
		
		
		//----------�w�K�J�n�ȊO�̃{�^��------------
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
			//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
			endDate = new java.sql.Timestamp(now.getTimeInMillis());
			//System.out.println("endTime:"+sdf.format(endDate));
			
			//endTime = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;
			//System.out.println("endTime:"+endTime);
			
			
			this.setContentPane(markingAnswerPane(Integer.parseInt(QL.qID.get(0).toString()),seqence,getAns));
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
			ArrayList getAns = new ArrayList();
			getAns.add("GIVEUP");
			this.setContentPane(giveupPane(Integer.parseInt(QL.qID.get(0).toString()),seqence,getAns));
			this.setVisible(true);
		}
		if (e.getActionCommand() == "HINT" ){
			timer.stop();
			this.setContentPane(setQuestionPane(level,seqence));
			this.setVisible(true);
		}
		if (e.getActionCommand() == "END" ){//���삪��������
			//System.out.println("END!!");
			this.setContentPane(endPane(Integer.parseInt(QL.qID.get(0).toString()),seqence));
			this.setVisible(true);
		}
		
		if (e.getActionCommand() == "RETURN" ){
			this.setContentPane(getJContentPane());
			this.setVisible(true);
		}
		
		
		//-----------------------------------------------
		time.setText("�������ԁF"+max+"sec �o�ߎ��ԁF"+ sec + " sec");
	
	    if (sec >= max){
		      timer.stop();
		      ArrayList getAns = new ArrayList();
		      getAns.add("TIMEUP");
		      this.setContentPane(giveupPane(Integer.parseInt(QL.qID.get(0).toString()),seqence,getAns));
		      this.setVisible(true);
	    }else{
	    	sec++;
	    }
	    //----------------------------------------------------------	
	}
	
}
