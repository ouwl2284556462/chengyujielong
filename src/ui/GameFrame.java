package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame  extends JFrame {
	
	//���ڿ��
	public static final int WIDTH = 400;
	//���ڸ߶�
	public static final int HEIGHT = 400;
	
	private GameStartPanel gameStartPanel;
	private JPanel selectWordPanel;
	private JFrame parentFrame;
	
	public GameFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		//���ظ�����
		parentFrame.setVisible(false);
		//���ñ���
		setTitle("�������!");
		//���ô�С
		setSize(WIDTH, HEIGHT);
        //���������������
		
		//ѡ��������
		selectWordPanel = new GameSelectWordPanel(this);
		add(selectWordPanel);
		gameStartPanel = new GameStartPanel(this);
		gameStartPanel.setVisible(false);
		add(gameStartPanel);
		//���ô�����Ļ����
		setLocationRelativeTo(null); 
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//�ø�������ʾ
				parentFrame.setVisible(true);
				gameStartPanel.dispose();
			}
		});
		
		//���ÿɼ�
        setVisible(true);
        //���ɸı��С
        setResizable(false);
	}
	
	public void closeFrame(){
		//�ø�������ʾ
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
