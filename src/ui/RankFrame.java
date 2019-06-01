package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.RankInfo;

public class RankFrame extends JFrame {

	// 窗口宽度
	public static final int WIDTH = 400;
	// 窗口高度
	public static final int HEIGHT = 400;

	public RankFrame(JFrame parentFrame) {
		this(parentFrame, null);

	}

	public RankFrame(JFrame parentFrame, RankInfo rankInfo) {
		// 隐藏父窗口
		parentFrame.setVisible(false);

		// 设置标题
		setTitle("成语接龙!");
		// 设置大小
		setSize(WIDTH, HEIGHT);
		// 创建窗口面板内容
		setContentPane(new RankPanel(rankInfo));

		// 设置窗口屏幕居中
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// 让父窗口显示
				parentFrame.setVisible(true);
			}
		});

		// 设置可见
		setVisible(true);
		// 不可改变大小
		setResizable(false);
	}
}
