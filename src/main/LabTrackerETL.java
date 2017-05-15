package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import api.JerseyClientGet;
import data.JSONParserCustom;
import db.DBManager;
import html.HTMLCreator;
import models.Lab;
import setup.PropertyManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LabTrackerETL {
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	public static ArrayList<Lab> labs = new ArrayList<Lab>();
	
	public static void main(String[] args) throws IOException {
		
		logger.trace("*-----LabTracker Is Starting!-----*");
		logger.trace("Loading Property Manager");
		// Get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		
		// If you want to hard code path to property file, set here. 
		// Else comment out the line and it will look for property file in default directory		
		//PropertyManager.setPropertyFilePath("/home/developer/Desktop/LabTracker/ITS/Properties/LabTracker.properties");
		propertyManager.loadProps();
		
		logger.trace("Setting up JerseyClient");
		// create REST client
		JerseyClientGet client = new JerseyClientGet();
		
		// make GET request and store JSON reponse
		ArrayList<String> response = client.getMaps();
		
		// parse JSON reponse into objects
		JSONParserCustom jsonParser = new JSONParserCustom();
		jsonParser.parseLabs(response);
				
		// Create HTML Maps
		HTMLCreator creator = new HTMLCreator(labs);
		
		// push data to DB
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
		System.out.println("LabTrackerETL done!");
		logger.trace("LabTrackerETL done!");

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
