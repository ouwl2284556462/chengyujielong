package test;

import java.util.List;

import dao.RankDao;
import dao.WordDao;
import model.RankInfo;

public class TestDao {
	public static void main(String[] args) throws Exception {
		//testRank();
		
		testWordDao(); 
	}

	private static void testWordDao() throws Exception {
		WordDao dao = new WordDao();
		System.out.println(dao.getRandomWord());
		System.out.println(dao.isWordExist("��Գ����")); 
		System.out.println(dao.isWordExist("��������"));
	}

	private static void testRank() throws Exception {
		RankDao dao = new RankDao();
		List<RankInfo> list = dao.getRankList(10);
		for(RankInfo rank : list){
			System.out.println(rank.getPlayerName() + "_" + rank.getScore());
		}
	}
}
