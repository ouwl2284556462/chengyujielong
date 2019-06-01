package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import model.RankInfo;

public class RankFrame extends JFrame {

	// ���ڿ��
	public static final int WIDTH = 400;
	// ���ڸ߶�
	public static final int HEIGHT = 400;

	public RankFrame(JFrame parentFrame) {
		this(parentFrame, null);

	}

	public RankFrame(JFrame parentFrame, RankInfo rankInfo) {
		// ���ظ�����
		parentFrame.setVisible(false);

		// ���ñ���
		setTitle("�������!");
		// ���ô�С
		setSize(WIDTH, HEIGHT);
		// ���������������
		setContentPane(new RankPanel(rankInfo));

		// ���ô�����Ļ����
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// �ø�������ʾ
				parentFrame.setVisible(true);
			}
		});

		// ���ÿɼ�
		setVisible(true);
		// ���ɸı��С
		setResizable(false);
	}
}
