package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.prompt.PromptSupport;

import base.GameManager;
import base.MusicManager;

public class GameSelectWordPanel extends JPanel {
	// 按钮间隔
	private static final int BTN_GAP = 40;
	// 按钮宽度
	private static final int BTN_WIDTH = 120;
	// 按钮高度
	private static final int BTN_HEIGHT = 30;

	public GameSelectWordPanel(GameFrame parent) {
		setLayout(null);
		JLabel title = new JLabel("选择开始成语");
		title.setFont(new Font("宋体", Font.BOLD, 16));

		int titleWidth = 110;
		int titleX = GameFrame.WIDTH / 2 - titleWidth / 2;
		title.setBounds(titleX, 10, titleWidth, 50);
		add(title);

		setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);

		JButton randomBtn = new JButton("随机");
		// 计算按钮起始位置X
		int btnX = GameFrame.WIDTH / 2 - BTN_WIDTH / 2;
		int btnY = 100;
		// 设置按钮小，和位置
		randomBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);

		// 随机按钮
		randomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MusicManager.playClick();
				startGame(parent, null);
			}
		});

		JTextField textField = new JTextField(10);
		PromptSupport.setPrompt("手动输入,回车确认", textField);
		btnY += BTN_GAP;
		textField.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusicManager.playEnter();
				String word = textField.getText().trim();
				if(!GameManager.isWordValid(word)){
					JOptionPane.showMessageDialog(null, "该成语不存在!", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				startGame(parent, word);
			}
		});

		add(textField);
		add(randomBtn);
	}
	
	/**
	 * 开始游戏
	 * @param parent
	 */
	private void startGame(GameFrame parent, String startWord){
		GameManager.init(startWord);
		parent.changeToGameStartPanel();
	}


}
