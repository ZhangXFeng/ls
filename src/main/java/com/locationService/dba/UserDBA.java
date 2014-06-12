package com.locationService.dba;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.dao.User;

public class UserDBA extends DBA {
	private static final Log LOG = LogFactory.getLog(DBA.class);

	/**
	 * 注册一个用户，成功返回true，已经存在返回false
	 * 
	 * @param user
	 * @return
	 */
	public boolean createUser(User user) throws SQLException {
		if (isExist(user)) {
			return false;
		}
		String sql =null;
		if(user.getRegistrationID()!=null){
			sql = "insert into userinfo values('" + user.getUsername()
					+ "','" + user.getPassword() + "','" + user.getVisitPassword()
					+ "','"+user.getRegistrationID()+"');";
		}else{
			sql = "insert into userinfo values('" + user.getUsername()
					+ "','" + user.getPassword() + "','" + user.getVisitPassword()
					+ "',null);";
		}
		
		DBClient.executeSql(sql);
		return true;
	}

	/**
	 * 判断某个用户名是否已被注册 已被注册返回true;没被注册返回false
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean isExist(User user) throws SQLException {
		boolean result = true;

		String sql = "select username from userinfo where username='"
				+ user.getUsername() + "'";
		ResultSet rs = DBClient.executeSqlForResult(sql);
		if (!rs.next()) {
			result = false;
		}

		return result;
	}
	
	/**
	 * 处理用户登录</br>
	 * 登录成功返回true,失败返回false
	 * 
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public boolean login(User user) throws SQLException{
		String sql="select * from userinfo where username='"+user.getUsername()+"' and password='"+user.getPassword()+"';";
		ResultSet rs=DBClient.executeSqlForResult(sql);
		if(rs.next()){
			if(user.getRegistrationID()!=null&&!user.getRegistrationID().equals("")){
				sql="update userinfo set registrationID='"+user.getRegistrationID()+"' where username='"+user.getUsername()+"';";
				DBClient.executeSql(sql);
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断用户名和访问密码是否匹配，匹配返回true,不匹配返回false
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean isVisitPasswordMatchUsername(User user) throws SQLException{
		String sql="select username from userinfo where username='"+user.getUsername()+"' and visitpassword='"+user.getVisitPassword()+"';";
		ResultSet rs=DBClient.executeSqlForResult(sql);
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 返回某个用户的registrationID
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public String getRegistrationID(User user) throws SQLException{
		String sql="select registrationID from userinfo where username='"+user.getUsername()+"';";
		ResultSet rs=DBClient.executeSqlForResult(sql);
		while(rs.next()){
			return rs.getString("registrationID");
		}
		
		return null;
	}
}
