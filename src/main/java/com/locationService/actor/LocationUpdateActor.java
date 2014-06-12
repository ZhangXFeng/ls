package com.locationService.actor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.dao.Location;
import com.locationService.dba.DBClient;
import com.locationService.dba.LocationDBA;
import com.locationService.model.inputModel.LocationUpdateInputModel;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class LocationUpdateActor extends UntypedActor {
	private static final Log LOG = LogFactory.getLog(RegisterActor.class);
	private static final LocationDBA LOCATION_DBA = new LocationDBA();
	private static final ActorRef NOTIFY_ACTOR=ActorSystemWrapper.getInstance().actorOf(NotifyActor.class, "notifyActor");

	@Override
	public void onReceive(Object arg0) throws Exception {
		LOG.info("locationUpdateActor receive " + arg0);
		if (arg0 instanceof Location) {
			Location location = (Location) arg0;
			LOCATION_DBA.addLocation(location);
			NOTIFY_ACTOR.tell(location, getSelf());
		} else {
			unhandled(arg0);
		}
	}

}
