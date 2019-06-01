package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import base.MusicManager;
import model.RankInfo;

public class EntryPanel extends JPanel{
	//��ť���
	private static final int BTN_GAP = 40;
	//��ť���
	private static final int BTN_WIDTH = 100;
	//��ť�߶�
	private static final int BTN_HEIGHT = 30;
	
	public EntryPanel(){
		//���Ĭ�ϲ���
		setLayout(null);
		setBounds(0, 0, EntryFrame.WIDTH, EntryFrame.HEIGHT);
		
		//������ť
		JButton startGameBtn = new JButton("������Ϸ");
		JButton viewRankBtn = new JButton("�鿴���а�");
		JButton exitBtn = new JButton("�˳�");
		
		//���㰴ť��ʼλ��X
		int btnX = EntryFrame.WIDTH / 2 - BTN_WIDTH / 2;
		int btnY = 100;
		//���ð�ťС����λ��
		startGameBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		//���Ӵ�ֱ���
		btnY += BTN_GAP;	
		viewRankBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		//���Ӵ�ֱ���
		btnY += BTN_GAP;
		exitBtn.setBounds(btnX, btnY, BTN_WIDTH, BTN_HEIGHT);
		
		
		//ע�ᰴť�¼�
		//������Ϸ
		startGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	new GameFrame(EntryFrame.instance);
            }
        });
		
		//�鿴���а�
		viewRankBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	openRankFrame();
            }
        });
		
		//�˳���ť
		exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MusicManager.playClick();
            	//�˳�����
            	System.exit(0);
            }
        });
		
		
		
		//��Ӱ�ť�����
		add(startGameBtn);
		add(viewRankBtn);
		add(exitBtn);
	}
	
	/**
	 * �����а����
	 */
	public void openRankFrame(RankInfo rankInfo){
		new RankFrame(EntryFrame.instance, rankInfo);
	}
	
	/**
	 * �����а����
	 */
	public void openRankFrame(){
		new RankFrame(EntryFrame.instance);
	}
	
}
