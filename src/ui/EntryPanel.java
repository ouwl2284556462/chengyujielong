package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import base.MusicManager;
import model.RankInfo;

public class EntryPanel extends JPanel{
	//按钮间隔
	private static final int BTN_GAP = 40;
	//按钮宽度
	private static final int BTN_WIDTH = 100;
	//按钮高度
	private static final int BTN_HEIGHT = 30;
	
	public EntryPanel(){
		//清空默认布局
		setLayout(null);
		setBounds(0, 0, EntryFrame.WIDTH, EntryFrame.HEIGHT);
		
		//创建按钮
		JButton startGameBtn = new JButton("进入游戏");
		JButton viewRankBtn = new JButton("查看排行榜");
		JButton exitBtn = new JButton("退出");
		
		//计算按钮起始位置X
		int btnX = EntryFrame.WIDTH / 2 - BTN_WIDTH / 2;
		int btnY = 100;
		//设置按钮小，和位置
		startGameBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		//增加垂直间隔
		btnY += BTN_GAP;	
		viewRankBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		//增加垂直间隔
		btnY += BTN_GAP;
		exitBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		
		
		//注册按钮事件
		//进入游戏
		startGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	new GameFrame(EntryFrame.instance);
            }
        });
		
		//查看排行榜
		viewRankBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	openRankFrame();
            }
        });
		
		//退出按钮
		exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	//退出程序
            	System.exit(0);
            }
        });
		
		
		
		//添加按钮到面板
		add(startGameBtn);
		add(viewRankBtn);
		add(exitBtn);
	}
	
	/**
	 * 打开排行榜界面
	 */
	public void openRankFrame(RankInfo rankInfo){
		new RankFrame(EntryFrame.instance, rankInfo);
	}
	
	/**
	 * 打开排行榜界面
	 */
	public void openRankFrame(){
		new RankFrame(EntryFrame.instance);
	}
	
}
