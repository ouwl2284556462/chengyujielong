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
 * ��Ϸ��ڽ���
 */
public class EntryFrame extends JFrame {
	public static EntryFrame instance;
	
	//���ڿ��
	public static final int WIDTH = 400;
	//���ڸ߶�
	public static final int HEIGHT = 400;
	private EntryPanel entryPanel;
	
	private InitPanel initPanel;
	
	public EntryFrame(){
		instance = this;
		//���ñ���
		setTitle("�������!");
		//���ô�С
		setSize(WIDTH, HEIGHT);
		//�رմ���
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				GameManager.exit();
			}
		});        
        
        
		//���������������
		entryPanel = new EntryPanel();
		add(entryPanel);
		
		//��ʾ��ʼ������
		if(GameManager.isNeedShowInitPanel()){
			entryPanel.setVisible(false);
			initPanel = new InitPanel();
			add(initPanel);
		}
		
		//���ô�����Ļ����
		setLocationRelativeTo(null); 
		
		//���ÿɼ�
        setVisible(true);
        //���ɸı��С
        setResizable(false);
        
        MusicManager.playBgm();
	}
	
	/**
	 * �����а����
	 */
	public void openRankFrame(RankInfo rankInfo){
		entryPanel.openRankFrame(rankInfo);
	}
	
	public void changeToEntryPanel(){
		initPanel.setVisible(false);
		entryPanel.setVisible(true);
	}

}
