package main;

import java.io.IOException;
import java.util.ArrayList;

import api.JerseyClientGet;
import data.JSONParserCustom;
import models.Lab;
import setup.PropertyManager;

public class LabTrackerETL {
	
	private static ArrayList<Lab> labs = new ArrayList<Lab>();

	public static void main(String[] args) throws IOException {
		
		// Get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		propertyManager.loadProps();

		// Create REST client
		JerseyClientGet client = new JerseyClientGet();
		
		String[] ids = {"1002","1010","1012","1016","1015"};
		// make GET request
		ArrayList<String> response = client.getMaps(ids);
		
		// load JSON into object
		JSONParserCustom jsParser = new JSONParserCustom();
		jsParser.parseLabs(response);
		System.out.println(labs);
		
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
