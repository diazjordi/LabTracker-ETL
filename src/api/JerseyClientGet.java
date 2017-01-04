package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import setup.PropertyManager;

public class JerseyClientGet {

	private String authCode;
	private String getMapURL;
	private static Map<String, String> apiClientProps = new HashMap<String, String>();

	public JerseyClientGet() {
		super();
		apiClientProps = PropertyManager.getApiClientProps();
		authCode = apiClientProps.get("api.AuthCode");
		getMapURL = apiClientProps.get("api.GetMapURL");
	}

	public String getMap(int mapID) {
		String json = null;
		try {

			Client client = Client.create();

			WebResource webResource = client.resource(getMapURL + mapID);

			ClientResponse response = webResource.accept("application/json").header("Authorization", authCode)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			json = response.getEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public ArrayList<String> getMaps(String[] mapIDs) {
		ArrayList<String> json = new ArrayList<String>();
		for (int i = 0; i < mapIDs.length; i++) {

			try {

				Client client = Client.create();

				WebResource webResource = client.resource(getMapURL + mapIDs[i]);

				ClientResponse response = webResource.accept("application/json").header("Authorization", authCode)
						.get(ClientResponse.class);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}

				json.add(response.getEntity(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return json;

	}

}
