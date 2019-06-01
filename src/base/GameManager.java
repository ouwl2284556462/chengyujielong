package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.stuxuhai.jpinyin.PinyinHelper;

import dao.WordDao;
import model.RankInfo;

public class GameManager {
	//���а�ֻ��ʾǰʮ��
	public static final int RANK_MAX_COUNT = 10;
	
	//ÿ�λش��ʱ��(��)
	public static final int PER_ANSWER_TIME = 20;
	//��ǰ����
	private static String curWord;
	//����
	private static int score;
	
	private static Set<String> hasAnswerWord;
	
	public static void init(String word){
		score = 0;
		hasAnswerWord = new HashSet<String>();
		setCurWord(word);
	}
	
	public static int getScore(){
		return score;
	}

	/**
	 * �˳�����
	 */
	public static void exit() {
		//�ͷ���Ƶ��Դ
		MusicManager.dispose();
		
		//�ͷ����ݿ���Դ
		try {
			DbFactory.releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * ��ȡ���а�
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<RankInfo> getRankList() {
		return DaoFactory.getRankDao().getRankList(RANK_MAX_COUNT);
	}

	/**
	 * �Ƿ����
	 * 
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static boolean isWordValid(String word) {
		return DaoFactory.getWordDao().isWordExist(word);
	}
	

	/**
	 * ���õ�ǰ����
	 */
	private static void setCurWord(String word) {
		curWord = word;
		hasAnswerWord.add(curWord);
	}

	public static String getCurWord() {
		if (null == curWord || "".equals(curWord)) {
			setCurWord(DaoFactory.getWordDao().getRandomWord());
		}

		return curWord;
	}
	
	/**
	 * �ύ��
	 * @param word
	 * @return
	 */
	public static String submitAnswerRight(String word) {
		//�Ѿ��ش��
		if(hasAnswerWord.contains(word)){
			return "�ó������ù�!";
		}
		
		//�����ڸõ���
		if(!isWordValid(word)){
			return "�ó��ﲻ����!";
		}
		
		//��ȡ��ǰ�������һ����
		char curLast = curWord.toCharArray()[3];
		//��ȡ��������һ����
		char inputFirst = word.toCharArray()[0];
		if(curLast != inputFirst && !checkPinYin(curLast, inputFirst)){
			return "����Ӳ���!";
		}
		
		//�ӷ�
		++score;
		setCurWord(word);
		return null;
	}

	private static boolean checkPinYin(char curLast, char inputFirst) {
		String[] curLastPinYinList = getPinYinList(curLast);
		String[] inputFirstPinYinList = getPinYinList(inputFirst);
		for(String curLastPinYin : curLastPinYinList){
			for(String inputFirstPinYin : inputFirstPinYinList){
				if(curLastPinYin.equals(inputFirstPinYin)){
					return true;
				}
			}
		}
		
		return false;
	}

	private static String[] getPinYinList(char zi) {
		String[] list = PinyinHelper.convertToPinyinArray(zi);
		if(null == list){
			list = new String[0];
		}
		
		return list;
	}

	/**
	 * @return �����Ƿ��ܽ���
	 */
	public static boolean gameOver() {
		//�ж��ܲ��ܽ���
		if(!canListInRank()){
			return false;
		}
		
		return true;
	}

	/**
	 * �ж��ܲ��ܽ��񣬲����򲻱������ݵ����ݿ�
	 * @return
	 */
	private static boolean canListInRank() {
		int curScore = getScore();
		if(curScore < 1){
			return false;
		}
		
		List<RankInfo> rankList = getRankList();
		//���а�Ϊ�գ������Ž�
		if(rankList.isEmpty()){
			return true;
		}
		
		int rankSize = rankList.size();
		//���а�δ���������Ž�
		if(rankSize < RANK_MAX_COUNT){
			return true;
		}
		
		//�����һ���Ƚ�
		RankInfo lastRank = rankList.get(rankSize - 1);
		if(Integer.parseInt(lastRank.getScore()) >= curScore){
			return false;
		}
		
		return true;
	}

	public static RankInfo insertRank(String playerName) {
		RankInfo rankInfo = new RankInfo(playerName, String.valueOf(getScore()));
		DaoFactory.getRankDao().insertRank(rankInfo);
		return rankInfo;
	}
	
	public static boolean isNeedShowInitPanel(){
		return !DbFactory.hasDatabaseInit();
	}

	public static void initGameData(InitProgressNotify func) throws Exception {
		WordDao wordDao = DaoFactory.getWordDao();
		int curProgress = 0;
		//��ʼ�������
		wordDao.createWordTable();
		for(int i = 0; i < 10; ++i){
			Thread.sleep(100);
			++curProgress;
			func.notify(curProgress);
		}
		
		//��ʼ����������
		wordDao.deleteAllWord();
		for(int i = 0; i < 10; ++i){
			Thread.sleep(100);
			++curProgress;
			func.notify(curProgress);
		}
		
		
		int remainProgress = 90 - curProgress;
		int insertCount = 0;
		//����ļ�һ������ô�������
		int totalCount = 54070;
		try (BufferedReader bReader = new BufferedReader(new InputStreamReader(GameManager.class.getResourceAsStream("/res/ci_ku_export.txt"), "utf-8"))) {
			String word = null;
			while ((word = bReader.readLine()) != null) {
				word = word.trim();
				if ("".equals(word)) {
					continue;
				}
				wordDao.insertWord(word);
				++insertCount;
				func.notify(curProgress + (int)(((float) insertCount / totalCount) * remainProgress));
			}
		}		
		
		
		//��ʼ�����а��
		DaoFactory.getRankDao().createRankTable();
		curProgress = 90;
		func.notify(curProgress);
		for (int i = 0; i < 10; ++i) {
			Thread.sleep(100);
			++curProgress;
			func.notify(curProgress);
		}
		func.notify(100);
	}
	
}
