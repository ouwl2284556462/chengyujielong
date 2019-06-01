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
	//排行榜只显示前十名
	public static final int RANK_MAX_COUNT = 10;
	
	//每次回答最长时间(秒)
	public static final int PER_ANSWER_TIME = 20;
	//当前成语
	private static String curWord;
	//分数
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
	 * 退出程序
	 */
	public static void exit() {
		//释放音频资源
		MusicManager.dispose();
		
		//释放数据库资源
		try {
			DbFactory.releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * 获取排行榜
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<RankInfo> getRankList() {
		return DaoFactory.getRankDao().getRankList(RANK_MAX_COUNT);
	}

	/**
	 * 是否成语
	 * 
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public static boolean isWordValid(String word) {
		return DaoFactory.getWordDao().isWordExist(word);
	}
	

	/**
	 * 设置当前成语
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
	 * 提交答案
	 * @param word
	 * @return
	 */
	public static String submitAnswerRight(String word) {
		//已经回答过
		if(hasAnswerWord.contains(word)){
			return "该成语已用过!";
		}
		
		//不存在该单词
		if(!isWordValid(word)){
			return "该成语不存在!";
		}
		
		//截取当前成语最后一个字
		char curLast = curWord.toCharArray()[3];
		//截取输入成语第一个字
		char inputFirst = word.toCharArray()[0];
		if(curLast != inputFirst && !checkPinYin(curLast, inputFirst)){
			return "成语接不对!";
		}
		
		//加分
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
	 * @return 返回是否能进榜
	 */
	public static boolean gameOver() {
		//判断能不能进榜
		if(!canListInRank()){
			return false;
		}
		
		return true;
	}

	/**
	 * 判断能不能进榜，不能则不保存数据到数据库
	 * @return
	 */
	private static boolean canListInRank() {
		int curScore = getScore();
		if(curScore < 1){
			return false;
		}
		
		List<RankInfo> rankList = getRankList();
		//排行榜为空，可以排进
		if(rankList.isEmpty()){
			return true;
		}
		
		int rankSize = rankList.size();
		//排行榜未满，可以排进
		if(rankSize < RANK_MAX_COUNT){
			return true;
		}
		
		//和最后一名比较
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
		//初始化成语表
		wordDao.createWordTable();
		for(int i = 0; i < 10; ++i){
			Thread.sleep(100);
			++curProgress;
			func.notify(curProgress);
		}
		
		//初始化成语数据
		wordDao.deleteAllWord();
		for(int i = 0; i < 10; ++i){
			Thread.sleep(100);
			++curProgress;
			func.notify(curProgress);
		}
		
		
		int remainProgress = 90 - curProgress;
		int insertCount = 0;
		//这个文件一共有这么多个成语
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
		
		
		//初始化排行榜表
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
