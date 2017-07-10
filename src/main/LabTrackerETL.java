package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import api.JerseyClient;
import data.JSONParserCustom;
import db.DBManager;
import maps.MapCreator;
import models.Lab;
import setup.PropertyManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LabTrackerETL {
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	public static ArrayList<Lab> labs = new ArrayList<Lab>();
	
	public static void main(String[] args) throws IOException {
		
		logger.trace("");
		logger.trace("");
		logger.trace("");
		logger.trace("");
		logger.trace("");
		logger.trace("*-----LabTracker Is Starting!-----*");
		
		logger.trace("*-----Setting up Property Manager!-----*");
		// Get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		
		PropertyManager.setPropertyFilePath("/home/developer/Desktop/LabTracker/LibraryNorth/Properties/LabTracker.properties");
		propertyManager.loadProps();
		
		// create REST client
		logger.trace("*-----Setting up JerseyClient!-----*");
		JerseyClient client = new JerseyClient();
		
		// make GET request and store JSON response
		logger.trace("*-----Connecting to LabStats API!-----*");
		HashMap<String, String> response = client.getMaps();
		
		// parse JSON response into Lab objects
		logger.trace("*-----Setting up JSONParser!-----*");
		JSONParserCustom jsonParser = new JSONParserCustom();
		jsonParser.parseLabs(response);
				
		// Create HTML Maps
		logger.trace("*-----Setting up MapCreator!-----*");
		MapCreator creator = new MapCreator();
		//creator.createMaps();
		
		// push data to DB
		logger.trace("*-----Setting up DBManager!-----*");
		DBManager db = new DBManager();
		try {
			db.insertIntoLabTable(labs);
			db.insertIntoLabStatusTable(labs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.closeConnection();
		}
		db.finalize();
		logger.trace("LabTrackerETL done!");
		logger.trace("");
		logger.trace("");
		logger.trace("");
		logger.trace("");
		logger.trace("");
		System.exit(0);

	}

	public static ArrayList<Lab> getLabs() {
		return labs;
	}

	public static void setLabs(ArrayList<Lab> labs) {
		LabTrackerETL.labs = labs;
	}
	
	public static void addLab(Lab lab){
		labs.add(lab);
	}

}
