package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import base.GameManager;

/**
 * 第一次运行时，初始化数据库
 */
public class InitPanel extends JPanel {
	public InitPanel() {
		//清空默认布局
		setLayout(null);
		
		JLabel title = new JLabel("初始化数据...");
		title.setFont(new Font("宋体", Font.BOLD, 12));
		int titleWidth = 90;
		int titleX = EntryFrame.WIDTH / 2 - titleWidth / 2;
		title.setBounds(titleX, 110, titleWidth, 50);
		add(title);		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		int barWidth = 300;
		int barX = EntryFrame.WIDTH / 2 - barWidth / 2;
		Rectangle rect = new Rectangle(barX, 150, barWidth, 30);
		progressBar.setBounds(rect);
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					GameManager.initGameData(val -> {progressBar.setValue(val); 
					                                 progressBar.paintImmediately(rect);});
					progressBar.setString("初始化完成！");
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					EntryFrame.instance.changeToEntryPanel();
				} catch (Exception e1) {
					progressBar.setString("初始化失败！");
					e1.printStackTrace();
				}
			}
		});
		
		add(progressBar);
	}
}
