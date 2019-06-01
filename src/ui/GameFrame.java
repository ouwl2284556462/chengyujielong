package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame  extends JFrame {
	
	//窗口宽度
	public static final int WIDTH = 400;
	//窗口高度
	public static final int HEIGHT = 400;
	
	private GameStartPanel gameStartPanel;
	private JPanel selectWordPanel;
	private JFrame parentFrame;
	
	public GameFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		//隐藏父窗口
		parentFrame.setVisible(false);
		//设置标题
		setTitle("成语接龙!");
		//设置大小
		setSize(WIDTH, HEIGHT);
        //创建窗口面板内容
		
		//选择成语面板
		selectWordPanel = new GameSelectWordPanel(this);
		add(selectWordPanel);
		gameStartPanel = new GameStartPanel(this);
		gameStartPanel.setVisible(false);
		add(gameStartPanel);
		//设置窗口屏幕居中
		setLocationRelativeTo(null); 
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//让父窗口显示
				parentFrame.setVisible(true);
				gameStartPanel.dispose();
			}
		});
		
		//设置可见
        setVisible(true);
        //不可改变大小
        setResizable(false);
	}
	
	public void closeFrame(){
		//让父窗口显示
		parentFrame.setVisible(true);
		gameStartPanel.dispose();
		dispose();
	}
	
	public void changeToGameStartPanel(){
		gameStartPanel.setVisible(true);
		gameStartPanel.refreshPanelInfo();
		selectWordPanel.setVisible(false);
	}
	
}
