package dao;

import java.sql.SQLException;
import java.util.Random;

/**
 * 成语数据访问层
 */
public class WordDao extends BaseDao{
	
	/**
	 * 随机一个成语
	 * @return
	 * @throws Exception 
	 */
	public String getRandomWord(){
		Random rand = new Random();
		int offset = rand.nextInt(40000);
		String sql = "select word from cheng_yu OFFSET ? ROWS FETCH NEXT 1 ROWS ONLY";
		
		String[] result = new String[1];
		try {
			doQry(sql, rs -> result[0] = rs.getString("word"), offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result[0];
	}
	
	/**
	 * 成语是否存在
	 * @param word
	 * @return
	 * @throws Exception 
	 */
	public boolean isWordExist(String word){
		String sql = "select count(1) cnt from cheng_yu where word = ?";
		
		boolean[] result = new boolean[1];
		try {
			doQry(sql, rs -> result[0] = !rs.getString("cnt").equals("0"), word);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result[0];
	}

	public void createWordTable() throws ClassNotFoundException, SQLException {
		String sql = "create table cheng_yu(" + "word varchar(10)," + "constraint P_KEY_1 primary key (word) " + ")";
		excuteSql(sql);
	}

	public void deleteAllWord() throws ClassNotFoundException, SQLException {
		String delSql = "delete from cheng_yu";
		excuteSql(delSql);
	}

	public void insertWord(String word) throws Exception {
		String sql = "insert into cheng_yu(word) values(?)";
		doUpdate(sql, word);
	}
}
