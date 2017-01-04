package data;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.LabTrackerETL;
import models.Lab;
import models.Station;

public class JSONParserCustom {

	private Lab lab = new Lab();

	public JSONParserCustom() {
		super();
	}

	public void parseLab(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			lab = mapper.readValue(json, Lab.class);
			lab.setUnitCounts();
			LabTrackerETL.addLab(lab);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseLabs(ArrayList<String> json) {
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i = 0; i < json.size(); i++){
			try {
				// Convert JSON string to JSON-Mapped Object
				lab = mapper.readValue(json.get(i), Lab.class);
				lab.setUnitCounts();
				LabTrackerETL.addLab(lab);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
