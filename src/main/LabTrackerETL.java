package main;

import java.io.IOException;
import java.util.ArrayList;

import api.JerseyClientGet;
import data.JSONParserCustom;
import models.Lab;
import setup.PropertyManager;

public class LabTrackerETL {
	
	private static ArrayList<Lab> labs;

	public static void main(String[] args) throws IOException {
		
		// Get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		propertyManager.loadProps();

		// Create REST client
		JerseyClientGet client = new JerseyClientGet();

		// make GET request
		String response = client.getMap(1002);
		
		// load JSON into object
		JSONParserCustom jsParser = new JSONParserCustom();
		jsParser.parseLab(response);
		
		// parse object into Lab, Stations

		// push data to DB

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
