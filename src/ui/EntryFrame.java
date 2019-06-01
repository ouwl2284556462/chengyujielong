package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import base.GameManager;
import base.MusicManager;
import javazoom.jl.decoder.JavaLayerException;
import model.RankInfo;

/**
 * 游戏入口界面
 */
public class EntryFrame extends JFrame {
	public static EntryFrame instance;
	
	//窗口宽度
	public static final int WIDTH = 400;
	//窗口高度
	public static final int HEIGHT = 400;
	private EntryPanel entryPanel;
	
	private InitPanel initPanel;
	
	public EntryFrame(){
		instance = this;
		//设置标题
		setTitle("成语接龙!");
		//设置大小
		setSize(WIDTH, HEIGHT);
		//关闭窗口
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				GameManager.exit();
			}
		});        
        
        
		//创建窗口面板内容
		entryPanel = new EntryPanel();
		add(entryPanel);
		
		//显示初始化界面
		if(GameManager.isNeedShowInitPanel()){
			entryPanel.setVisible(false);
			initPanel = new InitPanel();
			add(initPanel);
		}
		
		//设置窗口屏幕居中
		setLocationRelativeTo(null); 
		
		//设置可见
        setVisible(true);
        //不可改变大小
        setResizable(false);
        
        MusicManager.playBgm();
	}
	
	/**
	 * 打开排行榜界面
	 */
	public void openRankFrame(RankInfo rankInfo){
		entryPanel.openRankFrame(rankInfo);
	}
	
	public void changeToEntryPanel(){
		initPanel.setVisible(false);
		entryPanel.setVisible(true);
	}

}
