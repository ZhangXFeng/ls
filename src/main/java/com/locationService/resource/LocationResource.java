package com.locationService.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.google.gson.JsonObject;
import com.locationService.actor.ActorSystemWrapper;
import com.locationService.actor.LocationUpdateActor;
import com.locationService.actor.RegisterActor;
import com.locationService.dao.Location;
import com.locationService.dao.Subscibe;
import com.locationService.dao.User;
import com.locationService.dba.LocationDBA;
import com.locationService.dba.DBClient;
import com.locationService.dba.SubscibeDBA;
import com.locationService.dba.UserDBA;
import com.locationService.exception.InvalidInputException;
import com.locationService.exception.UserNotExistOrVisitPasswordErrorException;
import com.locationService.exception.UsernameOrPasswordError;
import com.locationService.model.inputModel.CancleSubscibeInputModel;
import com.locationService.model.inputModel.GetSubscibeInfoInputModel;
import com.locationService.model.inputModel.LocationAccessInputModel;
import com.locationService.model.inputModel.LocationUpdateInputModel;
import com.locationService.model.inputModel.LoginInputModel;
import com.locationService.model.inputModel.RegisterInputModel;
import com.locationService.model.inputModel.SubscibeLocationInputModel;
import com.locationService.model.outputModel.BaseReturnModel;
import com.locationService.model.outputModel.CancleSubscibeOutputModel;
import com.locationService.model.outputModel.GetSubscibeInfoOutputModel;
import com.locationService.model.outputModel.GetSubscibeInfoOutputModel.SubscibeInfo;
import com.locationService.model.outputModel.LocationAccessOutputModel;
import com.locationService.model.outputModel.LocationUpdateOutputModel;
import com.locationService.model.outputModel.LoginOutputModel;
import com.locationService.model.outputModel.RegisterOutputModel;
import com.locationService.model.outputModel.LocationAccessOutputModel.LocationInfo;
import com.locationService.model.outputModel.SubscibeLocationOutputModel;

@Path("/location")
public class LocationResource {
	private final static Log LOG = LogFactory.getLog(LocationResource.class);
	private static final ActorRef locationUpdateActor = ActorSystemWrapper
			.getInstance().actorOf(LocationUpdateActor.class,
					"locationUpdateActor");

	private static final UserDBA USER_DBA = new UserDBA();
	private static final LocationDBA LOCATION_DBA = new LocationDBA();
	private static final SubscibeDBA SUBSCIBE_DBA = new SubscibeDBA();

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationUpdateOutputModel updateLocation(
			LocationUpdateInputModel updateInfo) {

		LocationUpdateOutputModel returnModel = new LocationUpdateOutputModel();
		try {
			LOG.info(updateInfo);
			updateInfo.checkInput();
			Location location = new Location();
			location.setUsername(updateInfo.getUsername());
			location.setUpdatetime(updateInfo.getUpdateTime());
			location.setLongitude(updateInfo.getLongitude());
			location.setLatitude(updateInfo.getLatitude());
			locationUpdateActor.tell(location, ActorRef.noSender());
			returnModel.setStatus(0);
			returnModel.setDescription("successfully update.");
		} catch (InvalidInputException e) {
			LOG.error("[INVALID INPUT] INPUT=" + updateInfo, e);
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input.");
		}

		return returnModel;
	}

	@POST
	@Path("/access")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LocationAccessOutputModel getLocation(
			LocationAccessInputModel accessInfo) {
		LOG.info(accessInfo);
		LocationAccessOutputModel returnModel = new LocationAccessOutputModel();

		try {

			accessInfo.checkInput();
			String username = accessInfo.getUsername();
			String visitpassword = accessInfo.getVisitPassword();
			long starttime = accessInfo.getStartTime();
			long endtime = accessInfo.getEndTime();
			User user = new User();
			user.setUsername(username);
			if(accessInfo.getVisitUserName()!=null&&!accessInfo.getVisitUserName().equals("")){
				user.setVisitPassword(visitpassword);
			}else{
				user.setPassword(visitpassword);;
			}
			
			List<Location> locations = LOCATION_DBA.getLocations(user,
					starttime, endtime);
			returnModel.setUsername(username);
			returnModel.setStartTime(starttime);
			returnModel.setEndTime(endtime);

			List<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
			for (Location location : locations) {
				LocationInfo locationInfo = new LocationInfo();
				locationInfo.setUpdateTime(location.getUpdatetime());
				locationInfo.setLongitude(location.getLongitude());
				locationInfo.setLatitude(location.getLatitude());
				locationInfos.add(locationInfo);
			}
			returnModel.setLocations(locationInfos);
			returnModel.setStatus(0);
			returnModel.setDescription("successfully call.");
		} catch (SQLException e) {
			LOG.error("[GET LOCATION ERROR] INPUT=" + accessInfo, e);
			returnModel.setStatus(2);
			returnModel.setDescription("internal service error.");
		} catch (UserNotExistOrVisitPasswordErrorException e) {
			returnModel.setStatus(1);
			returnModel.setDescription("user not exit or visitpassword error.");
		} catch (InvalidInputException e) {
			LOG.error("[INVALID INPUT] INPUT=" + accessInfo, e);
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input.");
		} catch (UsernameOrPasswordError e) {
			
			returnModel.setStatus(1);
			returnModel.setDescription("user not exit or visitpassword error.");
		}
		return returnModel;
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RegisterOutputModel register(RegisterInputModel registerInfo) {

		RegisterOutputModel returnModel = new RegisterOutputModel();
		try {

			LOG.info(registerInfo);
			registerInfo.checkInput();
			User user = new User();
			user.setUsername(registerInfo.getUsername());
			user.setPassword(registerInfo.getPassword());
			user.setVisitPassword(registerInfo.getVisitPassword());
			boolean result = USER_DBA.createUser(user);
			if (result) {
				returnModel.setStatus(0);
				returnModel.setDescription("create "
						+ registerInfo.getUsername() + " success.");
			} else {
				returnModel.setStatus(1);
				returnModel.setDescription(user.getUsername() + " has existed");
			}

		} catch (SQLException e) {
			LOG.error(
					"[CREATE USER ERROR] username="
							+ registerInfo.getUsername(), e);
			returnModel.setStatus(2);
			returnModel.setDescription("create " + registerInfo.getUsername()
					+ " fail.");
		} catch (InvalidInputException e) {
			LOG.error("[INVALID INPUT] input=" + registerInfo, e);
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input");
		}

		return returnModel;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LoginOutputModel login(LoginInputModel loginInfo) {

		LoginOutputModel returnModel = new LoginOutputModel();
		try {
			LOG.info(loginInfo);
			loginInfo.checkInput();
			User user = new User();
			user.setUsername(loginInfo.getUsername());
			user.setPassword(loginInfo.getPassword());
			user.setRegistrationID(loginInfo.getRegistrationID());
			if (USER_DBA.login(user)) {
				returnModel.setStatus(0);
				returnModel.setDescription("login success");
			} else {
				returnModel.setStatus(1);
				returnModel.setDescription("login fail");
			}
		} catch (SQLException e) {
			LOG.error("[LOGIN ERROR] username=" + loginInfo.getUsername(), e);
			returnModel.setStatus(2);
			returnModel.setDescription("internal service error");
		} catch (InvalidInputException e) {
			LOG.error("[INVALID INPUT] input=" + loginInfo, e);
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input");
		}

		return returnModel;
	}

	@POST
	@Path("/subscibe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SubscibeLocationOutputModel scribeLocation(
			SubscibeLocationInputModel subInfo) {
		SubscibeLocationOutputModel returnModel = new SubscibeLocationOutputModel();

		LOG.info(subInfo);
		try {
			subInfo.checkInput();
			User user = new User();
			user.setUsername(subInfo.getTarget());
			user.setVisitPassword(subInfo.getVisitPassword());
			if (USER_DBA.isVisitPasswordMatchUsername(user)) {
				List<com.locationService.model.inputModel.SubscibeLocationInputModel.LocationInfo> locationInfos = subInfo
						.getTargetlocations();

				for (Iterator iterator = locationInfos.iterator(); iterator
						.hasNext();) {
					com.locationService.model.inputModel.SubscibeLocationInputModel.LocationInfo locationInfo = (com.locationService.model.inputModel.SubscibeLocationInputModel.LocationInfo) iterator
							.next();
					Subscibe subscibe = new Subscibe();
					subscibe.setUsername(subInfo.getUsername());
					subscibe.setTarget(subInfo.getTarget());
					subscibe.setLongitude(locationInfo.getLongitude());
					subscibe.setLatitude(locationInfo.getLatitude());
					subscibe.setVisitPasssword(subInfo.getVisitPassword());
					subscibe.setNear(locationInfo.getNear());
					SUBSCIBE_DBA.createSubscibe(subscibe);
				}
				returnModel.setStatus(0);
				returnModel.setDescription("call successfully.");
			} else {
				returnModel.setStatus(1);
				returnModel.setDescription("visitPassword not match username.");
			}

		} catch (InvalidInputException e) {
			LOG.error("[INVALID INPUT] input=" + subInfo, e);
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input");
		} catch (SQLException e) {
			LOG.error("[SUBSCIBE ERROR] username=" + subInfo.getUsername(), e);
			returnModel.setStatus(2);
			returnModel.setDescription("internal service error");
		}

		return returnModel;

	}

	@POST
	@Path("/cancleSubscibe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  CancleSubscibeOutputModel cancelSubscribe(CancleSubscibeInputModel cancleInfo){
		CancleSubscibeOutputModel returnModel=new CancleSubscibeOutputModel();
		try{
			LOG.info(cancleInfo);
			cancleInfo.checkInput();
			String username=cancleInfo.getUsername();
			String target=cancleInfo.getTarget();
			List<com.locationService.model.inputModel.CancleSubscibeInputModel.LocationInfo> locationInfos=cancleInfo.getLocationInfos();
			if(locationInfos.isEmpty()){
				Subscibe subscibe=new Subscibe();
				subscibe.setUsername(username);
				subscibe.setTarget(target);
				SUBSCIBE_DBA.cancleSubscibe(subscibe,true);
			}else{
				for (Iterator iterator = locationInfos.iterator(); iterator
						.hasNext();) {
					com.locationService.model.inputModel.CancleSubscibeInputModel.LocationInfo locationInfo = (com.locationService.model.inputModel.CancleSubscibeInputModel.LocationInfo) iterator.next();
					Subscibe subscibe=new Subscibe();
					subscibe.setUsername(username);
					subscibe.setTarget(target);
					subscibe.setLongitude(locationInfo.getLongitude());
					subscibe.setLatitude(locationInfo.getLatitude());
					SUBSCIBE_DBA.cancleSubscibe(subscibe,false);
				}
			}
			
			returnModel.setStatus(0);
			returnModel.setDescription("successfully call.");
		}catch(InvalidInputException e){
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input.");
		} catch (SQLException e) {
			returnModel.setStatus(2);
			returnModel.setDescription("internal service error.");
		}
		return returnModel;
		
	}
	@POST
	@Path("/getSubscibe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GetSubscibeInfoOutputModel getSubscibeInfo(GetSubscibeInfoInputModel inputModel){
		GetSubscibeInfoOutputModel returnModel=new GetSubscibeInfoOutputModel();
		try {
			inputModel.checkInput();
			List<Subscibe> subscibes=SUBSCIBE_DBA.getSubscibe(inputModel.getUsername());
			
			List<SubscibeInfo> subscibeInfos=new ArrayList<SubscibeInfo>();
			for (Iterator iterator = subscibes.iterator(); iterator
					.hasNext();) {
				Subscibe subscibe = (Subscibe) iterator.next();
				SubscibeInfo subscibeInfo=new SubscibeInfo();
				subscibeInfo.setTarget(subscibe.getTarget());
				subscibeInfo.setLongitude(subscibe.getLongitude());
				subscibeInfo.setLatitude(subscibe.getLatitude());
				subscibeInfo.setNear(subscibe.getNear());
				subscibeInfos.add(subscibeInfo);
				
			}
			returnModel.setStatus(0);
			returnModel.setDescription("successfully call.");
			returnModel.setUsername(inputModel.getUsername());
			returnModel.setSubscibeInfos(subscibeInfos);
		} catch (InvalidInputException e) {
			returnModel.setStatus(3);
			returnModel.setDescription("invalid input.");
		} catch (SQLException e) {
			returnModel.setStatus(2);
			returnModel.setDescription("internal service error.");
		}
		
		
		return returnModel;
	}
	@GET
	@Produces("text/html")
	public String getString() {
		return "<h1>location webService is running</h1>";

	}

	@GET
	@Path("/version")
	@Produces("text/html")
	public String getHtml() {
		return "<h1>locationWebService-0.0.1</h1>";

	}

	@GET
	@Path("/api")
	@Produces("text/html")
	public String getApi() {
		StringBuffer html = new StringBuffer();
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			JSONObject registerrequest = new JSONObject();
			registerrequest.put("username", "sean").put("password", "ffghk")
					.put("visitPassword", "kasldfk");

			JSONObject registerreturn = new JSONObject();
			registerreturn.put("status", 0).put("description",
					"register success");

			html.append("<h1>1. register api:</h1></br>").append(
					"<text>uri:  http://" + ip + ":8088/Location/register"
							+ "</text></br>");
			html.append("<text> input json like: " + registerrequest
					+ "</text></br>");
			html.append("<text> output json like: " + registerreturn
					+ "</text></br>");
			html.append("<h2>explain:</h2></br>");
			html.append("<text>status=0:register success</text></br>");
			html.append("<text>status=1:user has existed</text></br>");
			html.append("<text>status=2:internal service error</text></br>");
			html.append("<text>status=3:invalid input</text></br>");

			JSONObject loginrequest = new JSONObject();
			loginrequest.put("username", "sean").put("password", "ffghk");
			JSONObject loginoutput = new JSONObject();
			loginoutput.put("status", 0).put("description", "login success");

			html.append("<h1>2. login api:</h1></br>").append(
					"<text>uri:  http://" + ip + ":8088/Location/login"
							+ "</text></br>");
			html.append("<text> input json like: " + loginrequest
					+ "</text></br>");
			html.append("<text> output json like: " + loginoutput
					+ "</text></br>");
			html.append("<h2>explain:</h2></br>");
			html.append("<text>status=0:login success</text></br>");
			html.append("<text>status=1:username or password error</text></br>");
			html.append("<text>status=2:internal service error</text></br>");
			html.append("<text>status=3:invalid input</text></br>");

			JSONObject updatelocationrequest = new JSONObject();
			updatelocationrequest.put("updateTime", System.currentTimeMillis())
					.put("username", "sean").put("longitude", 12345.99)
					.put("latitude", 888888.99);

			html.append("<h1>3. location update api:</h1></br>").append(
					"<text>uri:  http://" + ip + ":8088/Location/update"
							+ "</text></br>");
			html.append("<text> input json like: " + updatelocationrequest
					+ "</text></br>");
			html.append("<text> output json like: no output" + "</text></br>");

			JSONObject accessrequest = new JSONObject();
			accessrequest.put("username", "sean").put("visitPassword", "54321")
					.put("startTime", 99L)
					.put("endTime", System.currentTimeMillis());
			JSONObject accessoutput = new JSONObject();
			accessoutput.put("status", 0).put("description", "login success")
					.put("username", "sean").put("startTime", 99L)
					.put("endTime", System.currentTimeMillis());
			JSONArray array = new JSONArray();
			JSONObject location = new JSONObject();
			location.put("updateTime",
					System.currentTimeMillis() - 60 * 60 * 1000)
					.put("longitude", 8888.88).put("latitude", 8888.7777);
			array.put(location);

			accessoutput.put("locations", array);

			html.append("<h1>4. access location api:</h1></br>").append(
					"<text>uri:  http://" + ip + ":8088/Location/access"
							+ "</text></br>");
			html.append("<text> input json like: " + accessrequest
					+ "</text></br>");
			html.append("<text> output json like: " + accessoutput
					+ "</text></br>");
			html.append("<h2>explain:</h2></br>");
			html.append("<text>status=0:call successfully</text></br>");
			html.append("<text>status=1:username and visitPassword not match</text></br>");
			html.append("<text>status=2:internal service error</text></br>");
			html.append("<text>status=3:invalid input</text></br>");
			
			JSONObject subsciberequest=new JSONObject();
			subsciberequest.put(
					"username", "zxf").put("visitPassword", "54321")
					.put("target", "sean");
			JSONArray array2=new JSONArray();
			JSONObject object=new JSONObject();
			object.put("longitude", 58.9).put("latitude", 88.8).put("near", 10);
			array2.put(object);
			subsciberequest.put("targetlocations", array2);
			
			JSONObject subscibeoutput=new JSONObject();
			subscibeoutput.put("status", 0);
			subscibeoutput.put("description", "successfully call");
			
			html.append("<h1>5. subscibe api:</h1></br>").append(
					"<text>uri:  http://" + ip + ":8088/Location/subscibe"
							+ "</text></br>");
			html.append("<text> input json like: " + subsciberequest
					+ "</text></br>");
			html.append("<text> output json like: " + subscibeoutput
					+ "</text></br>");
			html.append("<h2>explain:</h2></br>");
			html.append("<text>status=0:call successfully.</text></br>");
			html.append("<text>status=1:visitPassword not match username.</text></br>");
			html.append("<text>status=2:internal service error</text></br>");
			html.append("<text>status=3:invalid input</text></br>");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return html.toString();

	}
}
