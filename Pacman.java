import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;	

public class Pacman  extends JFrame{
	
	/**
	 시리얼 넘버
	 */
	private static final long serialVersionUID = 1L;
	
	public String user;
	public void setUser(String s) { user = s;}
	public String getUser() {return user;}
	
	//좌표 관련 필드
	static Random random;
	static int pacmanH, pacmanW, enemyH, enemyW, numOfDot, where, start;
	static int fieldMin=0, fieldMax=13;
	JLabel[][] f = new JLabel[14][14];
	
	//점수, 목숨 UI관련
	JPanel inform_Panel;
	JLabel score_Label;
	JLabel life_Label;
	public static int score;
	int life;
	
	//필요 컴포넌트
	static Icon temp1, temp2, temp3, temp4, temp;
	
	//게임 시작했을 때 관련한 컴포넌트
	JDialog first_Dialog;
	JPanel first_Panel;
	JLabel first_Label;
	JTextField first_TF;
	JButton first_Button;
	
	
	//게임 이겼을 때 관련된 컴포넌트
	JDialog dialog1;
	JPanel success_Panel ;
	JLabel success_Label;
	JButton success_Button;
	
	//게임 졌을때 관련된 컴포넌트
	JDialog dialog;
	JLabel fail_M;
	JPanel fail_Panel;
	JButton close_Button;
	JButton replay_Button;

	//ImeageIcon객체 생성
	final ImageIcon successIcon;
	final ImageIcon smallDot;
	final ImageIcon bigDot;
	final ImageIcon wall;
	final ImageIcon enemy;
	final ImageIcon pacman;
	final ImageIcon empty;
	
	//프레임 크기
	private static final int FRAME_WIDTH = 690;
	private static final int FRAME_HEIGHT = 650;
	
	//초기  이미지 아이콘 설정하는 함수
	public void setGameIcon(JLabel[][] f,  JPanel panel) 
	{
		//바꿀 부분을 바꿈
		f[1][1].setIcon(bigDot);	f[2][1].setIcon(smallDot);	f[3][1].setIcon(smallDot);	f[4][1].setIcon(smallDot);	f[5][1].setIcon(smallDot);
		f[5][2].setIcon(smallDot);	f[5][3].setIcon(smallDot);	f[1][3].setIcon(smallDot);	f[2][3].setIcon(smallDot);	f[3][3].setIcon(smallDot);
		f[4][3].setIcon(smallDot);	f[1][4].setIcon(smallDot);	f[1][5].setIcon(smallDot);	f[1][6].setIcon(smallDot);	f[1][7].setIcon(smallDot);
		f[1][8].setIcon(smallDot);	f[1][9].setIcon(smallDot);	f[1][10].setIcon(smallDot);	f[1][11].setIcon(smallDot);	f[1][12].setIcon(smallDot);
		f[2][9].setIcon(smallDot);	f[2][12].setIcon(smallDot);	f[3][12].setIcon(smallDot);	f[4][12].setIcon(smallDot);	f[5][12].setIcon(smallDot);
		f[3][4].setIcon(smallDot);	f[3][5].setIcon(smallDot);	f[3][10].setIcon(smallDot);	f[3][11].setIcon(smallDot);
		f[4][5].setIcon(smallDot);	f[4][6].setIcon(smallDot);	f[4][7].setIcon(bigDot);	f[4][8].setIcon(smallDot);	f[4][9].setIcon(smallDot);
		f[4][10].setIcon(smallDot);	f[2][7].setIcon(smallDot);	f[3][7].setIcon(smallDot);	f[4][10].setIcon(smallDot);	f[4][11].setIcon(smallDot);
		f[5][11].setIcon(smallDot);	f[6][11].setIcon(smallDot);	f[7][11].setIcon(smallDot);	f[7][12].setIcon(smallDot);	f[8][12].setIcon(smallDot);
		f[9][12].setIcon(smallDot);	f[10][12].setIcon(smallDot);f[11][12].setIcon(smallDot);f[5][7].setIcon(empty);	f[6][2].setIcon(smallDot);
		f[7][1].setIcon(smallDot);	f[9][11].setIcon(smallDot);	f[11][1].setIcon(smallDot);	f[11][3].setIcon(smallDot);	f[11][11].setIcon(smallDot);
		f[7][2].setIcon(smallDot);	f[7][3].setIcon(smallDot);	f[8][1].setIcon(smallDot);	f[9][1].setIcon(smallDot);	f[9][2].setIcon(smallDot);
		f[9][3].setIcon(smallDot);	f[9][4].setIcon(smallDot);	f[9][5].setIcon(smallDot);	f[9][6].setIcon(smallDot);	f[12][1].setIcon(smallDot);
		f[12][2].setIcon(smallDot);	f[12][3].setIcon(smallDot);	f[12][4].setIcon(smallDot);	f[12][5].setIcon(smallDot);	f[12][9].setIcon(smallDot);
		f[12][10].setIcon(smallDot);f[12][11].setIcon(smallDot); f[10][5].setIcon(smallDot); f[11][5].setIcon(smallDot); f[10][6].setIcon(smallDot);
		f[10][7].setIcon(smallDot);	f[10][8].setIcon(smallDot); f[10][9].setIcon(smallDot); f[11][7].setIcon(smallDot); f[12][7].setIcon(pacman);
		f[9][8].setIcon(smallDot); f[9][9].setIcon(smallDot); f[11][9].setIcon(smallDot); f[6][5].setIcon(empty);f[6][6].setIcon(empty);
		f[6][7].setIcon(empty); f[6][8].setIcon(empty);f[6][9].setIcon(empty); f[7][5].setIcon(empty);f[7][6].setIcon(empty);
		f[7][7].setIcon(enemy); f[7][8].setIcon(empty);f[7][9].setIcon(empty);
	}
	
	//생성자------------------------------------------------
	Pacman() {
		
		//프레임 기본 설정
		this.setVisible(false);
		this.setTitle("팩맨게임");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		life = 3;
		//점수, 목숨 UI관련
		inform_Panel = new JPanel(new GridLayout(0,2));
		inform_Panel.setBackground(Color.black);
		
		//score레이블 생성, 설정
		score_Label = new JLabel();
		score_Label.setFont(new Font("휴먼둥근헤드라인",20, 20)); 	 //폰트 설정
		score_Label.setHorizontalAlignment(0);//정렬
		score_Label.setForeground(Color.yellow);
		
		//life레이블 생성, 설정
		life_Label = new JLabel();
		life_Label.setFont( new Font("휴먼둥근헤드라인",20, 20)); 	 //폰트 설정
		life_Label.setHorizontalAlignment(0);
		life_Label.setForeground(Color.yellow);
		
		score_Label.setText("score : " + score);
		life_Label.setText("life : " + life);
		inform_Panel.add(score_Label);
		inform_Panel.add(life_Label);
		
		//패널객체 생성, 그리드레이아웃 설정
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(14,14));
		
		//아이콘이미지 객체 생성
		successIcon = new ImageIcon("successIcon.png");
		smallDot = new ImageIcon("smallDot.png");
		bigDot = new ImageIcon("bigDot.png");
		wall = new ImageIcon("wall.png");
		enemy = new ImageIcon("enemy.png");
		pacman = new ImageIcon("pacman.png");
		empty = new ImageIcon("empty.png");

		
		//랜덤 객체생성
		random = new Random();
		
		//시작할 때 다이얼로그 설정
		first_Dialog = new JDialog();
		first_Dialog.setFocusable(true);
		first_Dialog.setSize(400,300);		
		
		first_Panel = new JPanel(new GridLayout(3,1));
		first_Panel.setSize(400,300);
		first_Label = new JLabel("이름을 입력해 주세요");
		first_TF = new JTextField(10);
		first_Button = new JButton("OK");
		
		first_Label.setFont(new Font("휴먼둥근헤드라인",20, 20)); 	//폰트 설정
		first_Label.setHorizontalAlignment(0);					//정렬
		first_TF.setFont(new Font("휴먼둥근헤드라인",20, 20)); 		//폰트 설정
		first_TF.setHorizontalAlignment(0);						//정렬
		first_Button.setFont(new Font("휴먼둥근헤드라인",20, 20)); 	//폰트 설정
		first_Button.setHorizontalAlignment(0);					//정렬
		
		first_Panel.setBackground(Color.BLACK);
		first_Label.setBackground(Color.BLACK);
		first_Label.setForeground(Color.YELLOW);
		first_TF.setBackground(Color.YELLOW);
		first_Button.setBackground(Color.BLACK);
		first_Button.setForeground(Color.YELLOW);
		
		first_Button.addActionListener(e -> {
			user = first_TF.getText();
			first_Dialog.setVisible(false);
			this.setVisible(true);
		});
		
		first_Dialog.setResizable(false);
		first_Panel.add(first_Label);
		first_Panel.add(first_TF);
		first_Panel.add(first_Button); 
		first_Dialog.add(first_Panel);
		first_Dialog.setVisible(true);
		
		//실패할 때 다이어로그 설정
		dialog = new JDialog();
		dialog.setSize(500,500);
		dialog.setVisible(false);
		
		//성공할 때 다이어로그 설정
		dialog1 = new JDialog();
		dialog1.setSize(300,200);
		dialog1.setVisible(false);
		
		//위치 초기화
		pacmanH=12;  pacmanW=7;  enemyH=7;  enemyW=7;  numOfDot=79;  start=2;  temp=empty;

		//라벨 생성
		for (int i=0; i<14; i++) {
			for(int j=0; j<14; j++) {
				f[i][j] = new JLabel();
			}
		}
		//액션리스너 구현(enemy 생성)
		class TListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
					if(start<=0) where = 1+random.nextInt(4);
					else { where = 1; start--; }
						switch(where) {
							case 1:
								if(!(f[enemyH-1][enemyW].getIcon()).equals(wall)) {
									temp1=f[enemyH-1][enemyW].getIcon();
									f[enemyH-1][enemyW].setIcon(enemy);
									f[enemyH][enemyW].setIcon(temp);
									temp=temp1;
									enemyH--;
								}
								break;
							case 2:
								if(!(f[enemyH+1][enemyW].getIcon()).equals(wall)) {
									temp2=f[enemyH+1][enemyW].getIcon();
									f[enemyH+1][enemyW].setIcon(enemy);
									f[enemyH][enemyW].setIcon(temp);
									temp=temp2;
									enemyH++;
								}
								break;
							case 3:
								if(!(f[enemyH][enemyW-1].getIcon()).equals(wall)) {
									temp3=f[enemyH][enemyW-1].getIcon();
									f[enemyH][enemyW-1].setIcon(enemy);
									f[enemyH][enemyW].setIcon(temp);
									temp=temp3;
									enemyW--;
								}
								break;
							case 4:
								if(!(f[enemyH][enemyW+1].getIcon()).equals(wall)) {
									temp4=f[enemyH][enemyW+1].getIcon();
									f[enemyH][enemyW+1].setIcon(enemy);
									f[enemyH][enemyW].setIcon(temp);
									temp=temp4;
									enemyW++;
								}
								break;
						}
						if(enemyH==pacmanH && enemyW==pacmanW) {
							f[enemyH][enemyW].setIcon(enemy);
							dialog.add(fail_Panel);
							dialog.setVisible(true);
						}
					if(enemyH==5 && enemyW==7) { f[6][7].setIcon(wall); }
					System.out.println(where);
			}
		}
		
		//키리스너 구현 (움직임)
		class KListener extends KeyAdapter{
			public void keyPressed(KeyEvent e) {
				if(numOfDot<=0) {
					dialog.add(fail_Panel);
					dialog.setVisible(true);
				}
				int key = e.getKeyCode();
				//누른 키에 대한 반응
				switch(key) {
					//위키를 눌렀을 때
					case KeyEvent.VK_UP:
						//위에 smalldot나 empty 이미지가 있는 경우
						if((f[pacmanH-1][pacmanW].getIcon()).equals(smallDot) || (f[pacmanH-1][pacmanW].getIcon()).equals(empty)) {
							//smalldot가 있으면 numOfDot를 --해라
							if((f[pacmanH-1][pacmanW].getIcon()).equals(smallDot) && (f[pacmanH][pacmanW].getIcon()).equals(pacman)) {
								numOfDot--;
								score_Label.setText("score : " + (10 * score++));
								if(numOfDot == 0) { 
									success_Label.setText(user + "님 Success");
									dialog1.add(success_Panel);
									dialog1.setVisible(true);
									}
								}
						//그리고 원래 있던자리에 empty를 배치한 후 위에 pacman으로 바꾼다
						f[pacmanH-1][pacmanW].setIcon(pacman);
						f[pacmanH][pacmanW].setIcon(empty);
						pacmanH--;
						}
						//bigDot가 있는 경우
						if(f[pacmanH-1][pacmanW].getIcon().equals(bigDot)) {
							numOfDot--;
							score += 2;
							score_Label.setText("score : " + (10 * score));
							if(numOfDot == 0) { 
								success_Label.setText(user + "님 Success");
								dialog1.add(success_Panel);
								dialog1.setVisible(true);
								}
							f[pacmanH-1][pacmanW].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanH--;
							
						}
						//위에 enemy이미지가 있는 경우
						if((f[pacmanH-1][pacmanW].getIcon()).equals(enemy)) {
							//life == 0 인 경우
							if(life == 0) {
								fail_M.setText(user + "님의 점수는 : " + (score*10) +"입니다");
								dialog.add(fail_Panel);
								dialog.setVisible(true);
								
							}
							// 1 < life < 3 인경우							
							else {
								f[pacmanH][pacmanW].setIcon(empty);
								pacmanH = 12;
								pacmanW = 7;
								f[pacmanH][pacmanW].setIcon(pacman);
								life --;
								life_Label.setText("life : " + life);
							}
						}
												
						break;
						
					case KeyEvent.VK_DOWN:
						if((f[pacmanH+1][pacmanW].getIcon()).equals(smallDot) || (f[pacmanH+1][pacmanW].getIcon()).equals(empty)) {
							if((f[pacmanH+1][pacmanW].getIcon()).equals(smallDot) && (f[pacmanH][pacmanW].getIcon()).equals(pacman)) 
								{
								numOfDot--;
								score_Label.setText("score : " + (10 * score++));
								if(numOfDot == 0) { 
									success_Label.setText(user + "님 Success");
									dialog1.add(success_Panel);
									dialog1.setVisible(true);
									}
								}
						f[pacmanH+1][pacmanW].setIcon(pacman);
						f[pacmanH][pacmanW].setIcon(empty);
						pacmanH++;
						}
						
						if(f[pacmanH+1][pacmanW].getIcon().equals(bigDot)) {
							numOfDot--;
							score += 2;
							score_Label.setText("score : " + (10 * score));
							if(numOfDot == 0) { 
								success_Label.setText(user + "님 Success");
								dialog1.add(success_Panel);
								dialog1.setVisible(true);
								}
							f[pacmanH+1][pacmanW].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanH++;
							
						}
						
						if((f[pacmanH+1][pacmanW].getIcon()).equals(enemy)){
							// 1 < life < 3 인경우
							//life == 0 인 경우
							if(life == 0) {
								fail_M.setText(user + "님의 점수는 : " + (score*10) +"입니다");
								dialog.add(fail_Panel);
								dialog.setVisible(true);
								f[pacmanH][pacmanW].setIcon(empty);
							}
							// 1 < life < 3 인경우							
							else {
								life --;
								life_Label.setText("life : " + life);
								f[pacmanH][pacmanW].setIcon(empty);
								pacmanH = 12;
								pacmanW = 7;
								f[pacmanH][pacmanW].setIcon(pacman);
							}
						}
						break;
					case KeyEvent.VK_LEFT:
						if((f[pacmanH][pacmanW-1].getIcon()).equals(smallDot) || (f[pacmanH][pacmanW-1].getIcon()).equals(empty)) {
							if((f[pacmanH][pacmanW-1].getIcon()).equals(smallDot) && (f[pacmanH][pacmanW].getIcon()).equals(pacman)) {
								numOfDot--;
								score_Label.setText("score : " + (10 * score++));
								if(numOfDot == 0) { 
									success_Label.setText(user + "님 Success");
									dialog1.add(success_Panel);
									dialog1.setVisible(true);
									}
							}
							f[pacmanH][pacmanW-1].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanW--;
						}
						if(f[pacmanH][pacmanW-1].getIcon().equals(bigDot)) {
							numOfDot--;
							score += 2;
							score_Label.setText("score : " + (10 * score));
							if(numOfDot == 0) { 
								success_Label.setText(user + "님 Success");
								dialog1.add(success_Panel);
								dialog1.setVisible(true);
								}
							f[pacmanH][pacmanW-1].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanW--;
						}
						if((f[pacmanH][pacmanW-1].getIcon()).equals(enemy)){
							//life == 0 인 경우
							if(life == 0) {
								fail_M.setText(user + "님의 점수는 : " + (score*10) +"입니다");
								dialog.add(fail_Panel);
								dialog.setVisible(true);
								f[pacmanH][pacmanW].setIcon(empty);
							}
							// 1 < life < 3 인경우							
							else {
								life --;
								life_Label.setText("life : " + life);
								f[pacmanH][pacmanW].setIcon(empty);
								pacmanH = 12;
								pacmanW = 7;
								f[pacmanH][pacmanW].setIcon(pacman);
							}
						}
						break;
					case KeyEvent.VK_RIGHT:
						if((f[pacmanH][pacmanW+1].getIcon()).equals(smallDot) || (f[pacmanH][pacmanW+1].getIcon()).equals(empty)) {
							if((f[pacmanH][pacmanW+1].getIcon()).equals(smallDot) && (f[pacmanH][pacmanW].getIcon()).equals(pacman)) {
								numOfDot--;
								score_Label.setText("score : " + (10 * score++));
								if(numOfDot == 0) { 
									success_Label.setText(user + "님 Success");
									dialog1.add(success_Panel);
									dialog1.setVisible(true);
									}
							}
							f[pacmanH][pacmanW+1].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanW++;
						}
						if(f[pacmanH][pacmanW+1].getIcon().equals(bigDot)) {
							numOfDot--;
							score += 2;
							score_Label.setText("score : " + (10 * score));
							if(numOfDot == 0) { 
								success_Label.setText(user + "님 Success");
								dialog1.add(success_Panel);
								dialog1.setVisible(true);
								}
							f[pacmanH][pacmanW+1].setIcon(pacman);
							f[pacmanH][pacmanW].setIcon(empty);
							pacmanW++;
						}
						if((f[pacmanH][pacmanW+1].getIcon()).equals(enemy)) {
							//life == 0 인 경우
							if(life == 0) {
								fail_M.setText(user + "님의 점수는 : " + (score*10) +"입니다");
								dialog.add(fail_Panel);
								dialog.setVisible(true);
								f[pacmanH][pacmanW].setIcon(empty);
							}
							// 1 < life < 3 인경우							
							else {
								life --;
								life_Label.setText("life : " + life);
								f[pacmanH][pacmanW].setIcon(empty);
								pacmanH = 12;
								pacmanW = 7;
								f[pacmanH][pacmanW].setIcon(pacman);
							}
						}
						break;
				}
				System.out.println(numOfDot);
			}
	};
	
	//게임 오버 panel
	fail_Panel = new JPanel(new GridLayout(3,1));
	
	close_Button = new JButton("게임 종료");
	replay_Button = new JButton("다시 하기");
	fail_M = new JLabel("");
	fail_M.setFont( new Font("휴먼둥근헤드라인",35, 35));
	fail_M.setForeground(Color.black);
	fail_M.setHorizontalAlignment(0);
	close_Button.setFont( new Font("휴먼둥근헤드라인",50, 50)); 	 //폰트 설정
	replay_Button.setFont(new Font("휴먼둥근헤드라인",50, 50));
	close_Button.setBackground(Color.black);
	replay_Button.setBackground(Color.black);
	close_Button.setForeground(Color.yellow);
	replay_Button.setForeground(Color.yellow);

	//게임종료
	close_Button.addActionListener(e ->{
		System.exit(0);
		} );
	//다시하기
	replay_Button.addActionListener(e -> {
		dialog.setVisible(false);
		
		//아이콘을 모두 wall로 세팅하고
		for(int i=0; i<14; i++) {
			for(int j=0; j<14; j++) {
				f[i][j].setIcon(wall);
				f[i][j].addKeyListener(new KListener());
				panel.add(f[i][j]);
			}
		}
		//나머지는 아이콘 세팅
		setGameIcon(f, panel);
		f[7][7].setIcon(empty);
		life = 3;
		life_Label.setText("life : " + life);
		pacmanH = 12;
		pacmanW = 7;
		f[pacmanH][pacmanW].setIcon(pacman);
		score = 0;
		score_Label.setText("score : "+ score);
		numOfDot = 79;
		});
	fail_Panel.add(fail_M); 
	fail_Panel.add(replay_Button);
	fail_Panel.add(close_Button);
	
	//게임 우승 panel
		success_Panel = new JPanel();
		success_Label = new JLabel();
		success_Button = new JButton ("나가기");
		success_Button.addActionListener(e -> {
			this.setVisible(false);
			System.exit(0);
		});
		success_Panel.setLayout(new GridLayout(2,1));
		success_Panel.add(success_Label);
		success_Panel.add(success_Button);
		
		success_Label.setFont(new Font("휴먼둥근헤드라인",20, 20)); 		 //폰트 설정
		success_Label.setHorizontalAlignment(0);						//정렬
		success_Button.setFont(new Font("휴먼둥근헤드라인",20, 20)); 		 //폰트 설정
		success_Button.setHorizontalAlignment(0);						//정렬
		
		success_Panel.setBackground(Color.BLACK);
		success_Button.setBackground(Color.BLACK);
		success_Button.setForeground(Color.YELLOW);
		success_Label.setBackground(Color.BLACK);
		success_Label.setForeground(Color.YELLOW);
		
		
		//타이머객체 생성 , 타이머에 따른 이벤트 구현
		Timer t = new Timer(500, new TListener());
		
		//타이머 시작
		t.start();
		
		
		//프레임에 포커스 주고 키이벤트 구현
		this.requestFocus();
		this.addKeyListener(new KListener());
		

		//아이콘을 모두 wall로 세팅하고
				for(int i=0; i<14; i++) {
					for(int j=0; j<14; j++) {
						f[i][j].setIcon(wall);
						f[i][j].addKeyListener(new KListener());
						panel.add(f[i][j]);
					}
				}
		setGameIcon(f, panel);
		this.add(inform_Panel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		pack();
	}
	
	public static void main(String[] args) {
		new Pacman();
		
	}
		
}
