package api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import setup.PropertyManager;

public class JerseyClient {

	private String authCode;
	private String getMapURL;
	private static Map<String, String> apiClientProps = new HashMap<String, String>();
	private static Map<String, String> labCodes = new HashMap<String, String>();
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");

	public JerseyClient() {
		super();
		logger.trace("*-----Loading JerseyClient Properties!-----*");
		apiClientProps = PropertyManager.getApiClientProps();
		labCodes = PropertyManager.getLabCodes();
		if(apiClientProps.isEmpty() || labCodes.isEmpty()){
			logger.error("JerseyClient could not load necessary properties");
			logger.trace(apiClientProps.toString());
			logger.trace(labCodes.toString());
			System.exit(1);
		} else {
			authCode = apiClientProps.get("AuthCode");
			getMapURL = apiClientProps.get("GetMapURL");
			logger.trace("Auth Code: " + authCode);
			logger.trace("Get Map URL: " + getMapURL);
		}
	}

	public String getMap(int mapID) {
		logger.trace("Making API Requests for Map: " + mapID);
		String json = null;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(getMapURL + mapID);
			ClientResponse response = webResource.accept("application/json").header("Authorization", authCode).get(ClientResponse.class);
			if (response.getStatus() != 200) {
				logger.error("Failed : HTTP error code : " + response.getStatus());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			json = response.getEntity(String.class);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return json;
	}
	
	public HashMap<String, String> getMaps() {
		logger.trace("Making API Requests for Maps");
		HashMap<String, String> responseMap = new HashMap<String, String>();
		for (Entry<String, String> entry : labCodes.entrySet()) {
			try {
				Client client = Client.create();
				logger.trace("Requesting Map: " + entry.getValue());
				WebResource webResource = client.resource(getMapURL + entry.getValue());
				ClientResponse response = webResource.accept("application/json").header("Authorization", authCode).get(ClientResponse.class);
				if (response.getStatus() != 200) {
					logger.error("Failed : HTTP error code : " + response.getStatus());
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
				responseMap.put(entry.getKey(), response.getEntity(String.class));
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			}			
		}		
		return responseMap;
	}
	
}
