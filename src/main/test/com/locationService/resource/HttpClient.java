package com.locationService.resource;

import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;

import com.locationService.client.LSClient;


public class HttpClient {
	
	private static final String host="192.168.0.101";
//	private static final String host="107.170.199.252";
	public static void main(String[] args) throws JSONException, InterruptedException {
		LSClient client=new LSClient(host, 80);
//		System.out.println(client.register("zxf1105", "55555555", "55555555"));
	
		System.out.println(client.login("zxf1105", "55555555", "0004003e363"));
//		
//		for(;;){
//			System.out.println(client.updateLocation(System.currentTimeMillis(), "sean", 88.8, 99.9));
//			TimeUnit.SECONDS.sleep(300);
//		}
		
//		System.out.println(client.accessLocation("zxf1105", "sean", "55555555", System.currentTimeMillis()-20*60*1000, System.currentTimeMillis()));
	
//		List<LocationWithNear> locationWithNears=new ArrayList<LocationWithNear>();
//		LocationWithNear locationWithNear=new LocationWithNear();
//		locationWithNear.setLongitude(88.8);
//		locationWithNear.setLatitude(99.9);
//		locationWithNear.setNear(100);
//		
//		LocationWithNear locationWithNear2=new LocationWithNear();
//		locationWithNear2.setLongitude(99.9);
//		locationWithNear2.setLatitude(88.8);
//		locationWithNear2.setNear(100);
//		
//		locationWithNears.add(locationWithNear);
//		locationWithNears.add(locationWithNear2);
//		
//		System.out.println(client.subscibeLocation("zxf1105", "sean", "55555555", locationWithNears));
	
	
//		System.out.println(client.getSubscibeInfo("zxf1105"));
		
//		List<Location> locations=new ArrayList<Location>();
//		Location location=new Location();
//		location.setLongitude(99.9);
//		location.setLatitude(88.8);
//		locations.add(location);
//		System.out.println(client.cancleSubscibe("zxf1105", "sean", locations));
	}
}
