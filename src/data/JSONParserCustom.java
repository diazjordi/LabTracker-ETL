package data;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Lab;
import models.Station;

public class JSONParserCustom {

	private Lab lab = new Lab();

	public JSONParserCustom() {
		super();
	}

	public void parseLab(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Convert JSON string to Object
			String jsonInString = response;
			lab = mapper.readValue(jsonInString, Lab.class);
			lab.setUnitCounts();
			System.out.println(lab);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Station> getStations(JSONObject lab) {
		ArrayList<Station> stations = null;

		return stations;
	}

	public ArrayList<Station> getStations(String json) {
		ArrayList<Station> stations = null;
		return stations;
	}
}
