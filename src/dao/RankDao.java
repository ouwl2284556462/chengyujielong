package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.RankInfo;

/**
 * 排行榜数据访问层
 */
public class RankDao extends BaseDao{
	public List<RankInfo> getRankList(int lastRank){
		//返回前几条
		String sql = "select name, score from rank_list order by score desc FETCH NEXT ? ROWS ONLY";
		List<RankInfo> result = new ArrayList<RankInfo>(10);
		try {
			doQry(sql, rs -> iterateRankList(rs, result), lastRank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void iterateRankList(ResultSet rs, List<RankInfo> result) throws SQLException{
		RankInfo rankInfo = new RankInfo();
		rankInfo.setPlayerName(rs.getString("name"));
		rankInfo.setScore(rs.getString("score"));
		result.add(rankInfo);
	}

	public void insertRank(RankInfo rankInfo) {
		String sql = "insert into rank_list(name, score) values(?, ?)";
		try {
			doUpdate(sql, rankInfo.getPlayerName(), rankInfo.getScore());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createRankTable() throws ClassNotFoundException, SQLException {
		String sql = "create table rank_list(" 
                + "name varchar(128)," 
		         + "score int" 
                + ")";
		
		excuteSql(sql);
	}
}
