package com.locationService.dba;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.dao.Location;
import com.locationService.dao.User;
import com.locationService.exception.UserNotExistOrVisitPasswordErrorException;
import com.locationService.exception.UsernameOrPasswordError;

public class LocationDBA extends DBA {
	private static final Log LOG = LogFactory.getLog(LocationDBA.class);

	/**
	 * 查询某个用户在某段时间的位置信息，返回的locations按时间降序排序
	 * @param user
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return
	 * @throws SQLException
	 * @throws UserNotExistOrVisitPasswordErrorException 被访问的用户的用户名和访问密码不匹配
	 * @throws UsernameOrPasswordError 
	 */
	public List<Location> getLocations(User user, long startTime, long endTime)
			throws SQLException, UserNotExistOrVisitPasswordErrorException, UsernameOrPasswordError {
		List<Location> locations = new LinkedList<Location>();
		UserDBA userDBA = new UserDBA();
		if (user.getVisitPassword()!=null&&!userDBA.isVisitPasswordMatchUsername(user)) {
			throw new UserNotExistOrVisitPasswordErrorException(
					"visitpassword not match username");
		}
		if(user.getPassword()!=null&&!userDBA.login(user)){
			throw new UsernameOrPasswordError("username or password error.");
		}
		String sql = "select updatetime,longitude,latitude from location where username='"
				+ user.getUsername()
				+ "' and updatetime>="
				+ startTime
				+ " and updatetime<" + endTime + " order by updatetime desc;";
		
		ResultSet rs=DBClient.executeSqlForResult(sql);
		while(rs.next()){
			Location location=new Location();
			long updatetime=rs.getLong("updatetime");
			double longitude=rs.getDouble("longitude");
			double latitude=rs.getDouble("latitude");
			location.setUpdatetime(updatetime);
			location.setLongitude(longitude);
			location.setLatitude(latitude);
			location.setUsername(user.getUsername());
			locations.add(location);
		}
		return locations;
	}
	/**
	 * 添加一个location,成功返回true,否则抛出异常
	 * @param location
	 * @return
	 * @throws SQLException
	 */
	public boolean addLocation(Location location) throws SQLException{
		String sql = "insert into location values('"
				+ location.getUsername() + "',"
				+ location.getUpdatetime() + ","
				+ location.getLongitude() + ","
				+ location.getLatitude() + ");";
		
		DBClient.executeSql(sql);
		return true;
	}
}
