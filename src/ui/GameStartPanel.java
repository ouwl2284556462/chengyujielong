package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jdesktop.swingx.prompt.PromptSupport;

import base.GameManager;
import base.MusicManager;
import model.RankInfo;

public class GameStartPanel extends JPanel{
	
	private JLabel scoreTitle;
	private JLabel wordContent;
	private JLabel remainTimeTxt;
	private JTextArea record;
	private Timer gameTimer;
	private GameFrame parentFrame;
	
	private int remainTime;
	
	public GameStartPanel(GameFrame parent){
		parentFrame = parent;
		setLayout(null);
		scoreTitle = new JLabel();
		scoreTitle.setFont(new Font("宋体",Font.BOLD, 16));
		scoreTitle.setBounds(10, 10, 200, 50);
		add(scoreTitle);
		
		
		JLabel wordTitle = new JLabel("当前成语:");
		wordTitle.setFont(new Font("宋体",Font.BOLD, 16));
		wordTitle.setBounds(10, 140, 200, 50);
		add(wordTitle);
		
		
		wordContent = new JLabel();
		wordContent.setFont(new Font("宋体",Font.BOLD, 50));
		wordContent.setBounds(10, 175, 300, 100);
		add(wordContent);
		
		
		JLabel inputTips = new JLabel("请输入，回车确认:");
		inputTips.setFont(new Font("宋体",Font.BOLD, 16));
		inputTips.setBounds(10, 260, 200, 50);
		add(inputTips);
		
		//输入框
		JTextField inputText = new JTextField(10);
		PromptSupport.setPrompt("输入成语,回车确认", inputText);
		inputText.setBounds(10, 300, 220, 50);
		inputText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusicManager.playEnter();
				String word = inputText.getText().trim();
				
				String errMsg = GameManager.submitAnswerRight(word);
				if(errMsg != null){
					gameOver(errMsg);
					return;
				}
				
				//清空
				inputText.setText("");
				refreshPanelInfo();
			}
		});

		add(inputText);
		
		
		record = new JTextArea();
		record.setEditable(false);
		JScrollPane recordPane = new JScrollPane(record);
		recordPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		recordPane.setBounds(250, 20, 140, 230);
		add(recordPane);
		
		JLabel remainTimeLabel = new JLabel("剩余时间:");
		remainTimeLabel.setFont(new Font("宋体",Font.BOLD, 16));
		remainTimeLabel.setBounds(10, 50, 200, 50);
		add(remainTimeLabel);
		
		remainTimeTxt = new JLabel();
		remainTimeTxt.setFont(new Font("宋体",Font.BOLD, 40));
		remainTimeTxt.setBounds(30, 90, 200, 50);
		add(remainTimeTxt);
		
		//设置定时器
		initTimer();
	}

	private void initTimer() {
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				--remainTime;
				if(remainTime <= 5){
					remainTimeTxt.setForeground(Color.RED);
					MusicManager.playTimeout();
				}else{
					remainTimeTxt.setForeground(Color.BLACK);
				}
				
				refreshRemainTimeTxt();
				
				if (remainTime <= 0){
					gameOver("回答超时");
				}
				
			}

		});
	}
	
	private void gameOver(String msg){
		gameTimer.stop();
		//能否进排行榜
		boolean canInRank = GameManager.gameOver();
		JOptionPane.showOptionDialog(null, msg, "结束", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"确定"}, "确定");
		MusicManager.playLose();
		if(!canInRank){
			//返回到主界面
			parentFrame.closeFrame();
			return;
		}
		
		//获取玩家名字
		String playerName = getPlayerName();
		RankInfo rankInfo = GameManager.insertRank(playerName);
		//返回到主界面
		parentFrame.closeFrame();
		//打开排行榜界面
		EntryFrame.instance.openRankFrame(rankInfo);
		MusicManager.playWin();
	}

	private String getPlayerName() {
		String playerName = null;
		while(null == playerName || "".equals(playerName)){
			playerName = JOptionPane.showInputDialog(null, "恭喜成功进入排行榜，请输入你的名字:");
			MusicManager.playEnter();
			if(playerName != null){
				playerName = playerName.trim();
			}
		}
		return playerName;
	}

	public void refreshPanelInfo() {
		showCurScore();
		showCurWord();
		writeRecord();
		refreshRemainTime();
	}

	private void refreshRemainTime() {
		remainTime = GameManager.PER_ANSWER_TIME;
		gameTimer.start();
		refreshRemainTimeTxt();
	}
	
	private void refreshRemainTimeTxt(){
		remainTimeTxt.setText(String.valueOf(remainTime));
	}
	
	private void writeRecord(){
		String curText = record.getText();
		if("".equals(curText)){
			record.setText(GameManager.getCurWord());
		}else{
			record.setText(curText + "\n" + GameManager.getCurWord());
		}
		
		record.setCaretPosition(record.getText().length()); 
	}

	private void showCurWord() {
		wordContent.setText(GameManager.getCurWord());
	}
	
	private void showCurScore(){
		scoreTitle.setText("分数:" + GameManager.getScore());
	}
	
	public void dispose(){
		gameTimer.stop();
	}
	
}
