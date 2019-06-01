package ui;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import base.GameManager;
import model.RankInfo;

public class RankPanel  extends JPanel{
	public RankPanel(){
		this(null);
	}

	public RankPanel(RankInfo markRankInfo) {
		//���Ĭ�ϲ���
		setLayout(null);		
		
		JLabel title = new JLabel("���а�");
		title.setFont(new Font("����",Font.BOLD, 16));
		
		int titleWidth = 60;
		int titleX = RankFrame.WIDTH / 2 - titleWidth / 2;
		title.setBounds(titleX, 10, titleWidth, 30);
		add(title);
		
		
		List<RankInfo> rankList = GameManager.getRankList();
		Object[][] data = new Object[10][3];
		for(int i = 0; i < rankList.size(); ++i){
			RankInfo rank = rankList.get(i);
			data[i][0] = i + 1;
			data[i][1] = rank.getPlayerName();
			data[i][2] = rank.getScore();
		}
		
		
		int markRow = getMarkRow(markRankInfo, rankList);
		
		Object[] columnNames = new Object[3];
		columnNames[0] = "����";
		columnNames[1] = "���";
		columnNames[2] = "����";
		RankTable rankTable = new RankTable(data, columnNames, markRow);
		

		int rankTableWidth = 200;
		int rankTableX = RankFrame.WIDTH / 2 - rankTableWidth / 2;
		JScrollPane scrollPane = new JScrollPane(rankTable);
		scrollPane.setBounds(rankTableX, 40, rankTableWidth, 190);
		
		add(scrollPane);
	}

	private int getMarkRow(RankInfo markRankInfo, List<RankInfo> rankList) {
		if(null == markRankInfo){
			return -1;
		}
		
		for (int i = 0; i < rankList.size(); ++i) {
			RankInfo rank = rankList.get(i);
			if (rank.getPlayerName().equals(markRankInfo.getPlayerName()) && rank.getScore().equals(markRankInfo.getScore())) {
				return i;
			}
		}
		
		return -1;
	}
	
}

