package com.locationService.actor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;

import com.locationService.dao.Location;
import com.locationService.dao.Subscibe;
import com.locationService.dao.User;
import com.locationService.dba.SubscibeDBA;
import com.locationService.dba.UserDBA;
import com.locationService.util.LocationUtil;

import akka.actor.UntypedActor;

public class NotifyActor extends UntypedActor {
	private static final Log LOG = LogFactory.getLog(NotifyActor.class);
	private static final SubscibeDBA SUBSCIBE_DBA = new SubscibeDBA();
	private static final UserDBA USER_DBA=new UserDBA();
	private static final String appKey ="fc1b8887a13e0e1fab9f68be";
	private static final String masterSecret = "0302be2675950813e2818cd7";
	private static final JPushClient PUSH_CLIENT=new JPushClient(masterSecret, appKey);
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0 instanceof Location){
			Location location=(Location) arg0;
			User target=new User();
			target.setUsername(location.getUsername());
			List<Subscibe> subscibes=SUBSCIBE_DBA.getSubscibe(target);
			for (Subscibe subscibe : subscibes) {
				
				if(LocationUtil.getDistance(location.getLatitude(), location.getLongitude(), subscibe.getLatitude(), subscibe.getLongitude())<subscibe.getNear()){
					
					User user=new User();
					user.setUsername(subscibe.getUsername());
					String registrationID=USER_DBA.getRegistrationID(user);
					
					if(registrationID!=null){
//						CustomMessageParams params = new CustomMessageParams();
//						params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
//						params.setReceiverValue("010d2762e16");
						NotificationParams params2=new NotificationParams();
						params2.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
						params2.setReceiverValue(registrationID);
//						PUSH_CLIENT.sendCustomMessage(location.getUsername(), "longitude:"+location.getLongitude()+",latitude:"+location.getLatitude(), params, null);
						PUSH_CLIENT.sendNotification("username="+location.getUsername(), params2, null);
					}
					
				}
			}
		} else {
			unhandled(arg0);
		}
	}

}
