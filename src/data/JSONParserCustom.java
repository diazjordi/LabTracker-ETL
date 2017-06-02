package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.LabTrackerETL;
import models.Lab;
import models.Station;
import setup.PropertyManager;

public class JSONParserCustom {

	private Lab lab = new Lab();
	
	// Suppression properties
	private static Map<String, String> suppressionProperties = new HashMap<String, String>();
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	public JSONParserCustom() {
		super();
		logger.trace("*-----Loading JSONParser Properties!-----*");
		suppressionProperties = PropertyManager.getSuppressionProperties();		
	}

	public void parseLab(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			lab = mapper.readValue(json, Lab.class);
			// Set suppression
			setSuppressedStations(lab);
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
	
	public void parseLabs(HashMap<String, String> responseMap) {
		logger.trace("JSONParser is Parsing JSON into Lab Objects");
		
		ObjectMapper mapper = new ObjectMapper();
		
		for (Entry<String, String> entry : responseMap.entrySet()) {
			logger.trace("Parsing " + entry.getKey());
			try {
				// Convert JSON string to JSON-Mapped Object
				lab = mapper.readValue(entry.getValue(), Lab.class);
				// Set suppression
				setSuppressedStations(lab);
				lab.setUnitCounts();
				lab.setProperName(entry.getKey());
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
	
	public void setSuppressedStations(Lab lab){
		logger.trace("JSONParser is setting suppressed stations");
		for(String key: suppressionProperties.keySet()){
			for(Station station: lab.getMapStations()){
				if(key.toLowerCase().matches(station.getHostName().toLowerCase())){
					logger.trace(station.getHostName() + " is suppressed");
					station.setStatus("Suppressed");
				}
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
