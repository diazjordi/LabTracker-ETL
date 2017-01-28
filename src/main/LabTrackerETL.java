package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import api.JerseyClientGet;
import data.JSONParserCustom;
import db.DBManager;
import models.Lab;
import setup.PropertyManager;

public class LabTrackerETL {
	
	private static ArrayList<Lab> labs = new ArrayList<Lab>();

	public static void main(String[] args) throws IOException {
		
		// Get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		// If you want to hard code path to property file, set here. 
		// Else comment out the line and it will look for property file in default directory
		//propertyManager.setPropertyFilePath("/home/developer/Desktop/LabTracker-v2/Properties/LabTracker.properties");
		propertyManager.loadProps();

		// create REST client
		JerseyClientGet client = new JerseyClientGet();
		
		// make GET request and store JSON reponse
		ArrayList<String> response = client.getMaps();
		
		// parse JSON reponse into objects
		JSONParserCustom jsParser = new JSONParserCustom();
		jsParser.parseLabs(response);
					
		// push data to DB
		DBManager db = new DBManager();
		try {
			db.updateLabTables(labs);
			db.updateLabStatusTable(labs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.closeConnection();
		}
		db.finalize();

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
