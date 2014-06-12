package com.locationService.dba;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.dao.Location;
import com.locationService.dao.Subscibe;
import com.locationService.dao.User;

public class SubscibeDBA extends DBA {
	private static final Log LOG = LogFactory.getLog(LocationDBA.class);

	public boolean createSubscibe(Subscibe subscibe) throws SQLException {

		String sql = "insert into subscibe values('" + subscibe.getUsername()
				+ "','" + subscibe.getTarget() + "'," + subscibe.getLongitude()
				+ "," + subscibe.getLatitude() + ",'"
				+ subscibe.getVisitPasssword() + "'," + subscibe.getNear()
				+ ");";
		DBClient.executeSql(sql);

		return true;
	}

	/**
	 * ����ĳ���û��ı�������Ϣ
	 * 
	 * @param target
	 * @return
	 * @throws SQLException
	 */
	public List<Subscibe> getSubscibe(User target) throws SQLException {
		List<Subscibe> subscibes = new ArrayList<Subscibe>();
		String sql = "select username,longitude,latitude,near from subscibe where target='"
				+ target.getUsername() + "';";
		ResultSet rs = DBClient.executeSqlForResult(sql);
		while (rs.next()) {
			Subscibe subscibe = new Subscibe();
			subscibe.setUsername(rs.getString("username"));
			subscibe.setLongitude(rs.getDouble("longitude"));
			subscibe.setLatitude(rs.getDouble("latitude"));
			subscibe.setNear(rs.getInt("near"));

			subscibes.add(subscibe);
		}
		try {
			rs.close();
		} catch (SQLException e) {
			LOG.warn(e);
		}

		return subscibes;
	}

	/**
	 * ȡ������ĳ�˵�λ�ã�isAll==true,ɾ�����ж���;isAll==false,ֻȡ������subscibeָ����λ��
	 * @param subscibe
	 * @param isAll
	 * @throws SQLException
	 */
	public void cancleSubscibe(Subscibe subscibe,boolean isAll) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from subscibe where username='"+subscibe.getUsername()+"' and target='"+subscibe.getTarget()+"' ");
		if(!isAll){
			sql.append(" and longitude="+subscibe.getLongitude()+" and latitude="+subscibe.getLatitude());
		}
		sql.append(";");
		DBClient.executeSql(sql.toString());
	}
	/**
	 * ����${username}�Ķ�����Ϣ
	 * @param user
	 * @param tag
	 * @return
	 * @throws SQLException 
	 */
	public List<Subscibe> getSubscibe(String username) throws SQLException{
		List<Subscibe> subscibes=new ArrayList<Subscibe>();
		String sql="select target,longitude,latitude,near from subscibe where username='"+username+"';";
		ResultSet rs=DBClient.executeSqlForResult(sql);
		while(rs.next()){
			Subscibe subscibe=new Subscibe();
			subscibe.setUsername(username);
			subscibe.setTarget(rs.getString("target"));
			subscibe.setLongitude(rs.getDouble("longitude"));
			subscibe.setLatitude(rs.getDouble("latitude"));
			subscibe.setNear(rs.getInt("near"));
			subscibes.add(subscibe);
		}
		try {
			rs.close();
		} catch (SQLException e) {
			LOG.warn(e);
		}
		return subscibes;
	}
}
