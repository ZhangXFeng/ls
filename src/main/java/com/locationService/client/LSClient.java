package com.locationService.client;

import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class LSClient implements LocationServiceInterface {

	private static final Client client = new Client();
	private String host;
	private int port;

	public LSClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public JSONObject register(String username, String password,
			String visitPassword) {
		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/register");

		JSONObject request = new JSONObject();
		try {
			request.put("username", username).put("password", password)
					.put("visitPassword", visitPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject login(String username, String password,
			String registrationID) {

		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/login");

		JSONObject request = new JSONObject();
		try {

			request.put("username", username).put("password", password);
			if (registrationID != null) {
				request.put("registrationID", registrationID);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject updateLocation(long updateTime, String username,
			double longitude, double latitude) {

		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/update");

		JSONObject request = new JSONObject();
		try {
			request.put("updateTime", updateTime).put("username", username)
					.put("longitude", longitude).put("latitude", latitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject subscibeLocation(String username, String target,
			String visitPassword, List<LocationWithNear> locationWithNears) {
		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/subscibe");

		JSONObject request = new JSONObject();
		try {
			request.put("username", username)
					.put("visitPassword", visitPassword).put("target", target);
			JSONArray array = new JSONArray();

			for (LocationWithNear locationWithNear : locationWithNears) {
				JSONObject object = new JSONObject();
				object.put("longitude", locationWithNear.getLongitude())
						.put("latitude", locationWithNear.getLatitude())
						.put("near", locationWithNear.getNear());
				array.put(object);
			}

			request.put("targetlocations", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject accessLocation(String visitUserName, String username,
			String visitPassword, long startTime, long endTime) {
		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/access");

		JSONObject request = new JSONObject();
		try {
			request.put("visitUserName", visitUserName)
					.put("username", username)
					.put("visitPassword", visitPassword)
					.put("startTime", startTime).put("endTime", endTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject cancleSubscibe(String username, String target,
			List<Location> locations) {

		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/cancleSubscibe");

		JSONObject request = new JSONObject();
		try {
			request.put("username", username).put("target", target);
			JSONArray array = new JSONArray();

			for (Location location : locations) {
				JSONObject object = new JSONObject();
				object.put("longitude", location.getLongitude()).put(
						"latitude", location.getLatitude());
				array.put(object);
			}

			request.put("locationInfos", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

	@Override
	public JSONObject getSubscibeInfo(String username) {

		WebResource resource = client.resource("http://" + host + ":" + port
				+ "/location/getSubscibe");

		JSONObject request = new JSONObject();
		try {
			request.put("username", username);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject object = resource.type(MediaType.APPLICATION_JSON_TYPE)
				.post(JSONObject.class, request);

		return object;
	}

}
