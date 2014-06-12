package com.locationService.actor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.dba.DBClient;
import com.locationService.model.inputModel.LoginInputModel;
import com.locationService.model.inputModel.RegisterInputModel;

import akka.actor.UntypedActor;

public class RegisterActor extends UntypedActor {
	private static final Log LOG=LogFactory.getLog(RegisterActor.class);
	private DBClient mysqlClient;

	@Override
	public void onReceive(Object arg0) throws Exception {
		LOG.info("RegisterActor receive "+arg0);
		if(arg0 instanceof RegisterInputModel){
			RegisterInputModel rim=(RegisterInputModel) arg0;
			String sql="insert into userinfo values('"+rim.getUsername()+"','"+rim.getPassword()+"','"+rim.getVisitPassword()+"');";
			DBClient.executeSql(sql);
		}else{
			unhandled(arg0);
		}
	}

}
