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
	// ��ť���
	private static final int BTN_GAP = 40;
	// ��ť���
	private static final int BTN_WIDTH = 120;
	// ��ť�߶�
	private static final int BTN_HEIGHT = 30;

	public GameSelectWordPanel(GameFrame parent) {
		setLayout(null);
		JLabel title = new JLabel("ѡ��ʼ����");
		title.setFont(new Font("����", Font.BOLD, 16));

		int titleWidth = 110;
		int titleX = GameFrame.WIDTH / 2 - titleWidth / 2;
		title.setBounds(titleX, 10, titleWidth, 50);
		add(title);

		setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);

		JButton randomBtn = new JButton("���");
		// ���㰴ť��ʼλ��X
		int btnX = GameFrame.WIDTH / 2 - BTN_WIDTH / 2;
		int btnY = 100;
		// ���ð�ťС����λ��
		randomBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);

		// �����ť
		randomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MusicManager.playClick();
				startGame(parent, null);
			}
		});

		JTextField textField = new JTextField(10);
		PromptSupport.setPrompt("�ֶ�����,�س�ȷ��", textField);
		btnY += BTN_GAP;
		textField.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusicManager.playEnter();
				String word = textField.getText().trim();
				if(!GameManager.isWordValid(word)){
					JOptionPane.showMessageDialog(null, "�ó��ﲻ����!", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				startGame(parent, word);
			}
		});

		add(textField);
		add(randomBtn);
	}
	
	/**
	 * ��ʼ��Ϸ
	 * @param parent
	 */
	private void startGame(GameFrame parent, String startWord){
		GameManager.init(startWord);
		parent.changeToGameStartPanel();
	}


}
