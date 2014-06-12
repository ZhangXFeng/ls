package com.locationService.actor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.locationService.resource.LocationResource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorSystemWrapper {
	private final static Log LOG = LogFactory.getLog(LocationResource.class);
	private static ActorSystem actorSystem;
	private final static ActorSystemWrapper instance=new ActorSystemWrapper("locationActorSys");
	private ActorSystemWrapper(String name){
		actorSystem =ActorSystem
				.create(name);
	}
	public static ActorSystemWrapper getInstance(){
		return instance;
	}
	public ActorRef actorOf(Class actor,String arg1){
		return actorSystem.actorOf(Props.create(actor), arg1);
	}
}
