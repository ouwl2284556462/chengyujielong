package base;

import dao.RankDao;
import dao.WordDao;

public class DaoFactory {
	private static RankDao rankDao = new RankDao();
	private static WordDao wordDao = new WordDao();
	
	public static RankDao getRankDao(){
		return rankDao;
	}
	
	public static WordDao getWordDao(){
		return wordDao;
	}
}
