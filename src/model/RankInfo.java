package model;

/**
 * ������Ϣ 
 */
public class RankInfo {
	//�������
	private String playerName;
	//����
	private String score;
	
	
	public RankInfo(String playerName, String score) {
		this.playerName = playerName;
		this.score = score;
	}
	
	public RankInfo() {
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
}
