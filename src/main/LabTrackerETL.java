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
		
		// get props
		PropertyManager propertyManager = PropertyManager.getPropertyManagerInstance();
		//propertyManager.setPropertyFilePath("/home/developer/Desktop/LabTracker-v2/Properties/LabTracker.properties");
		propertyManager.loadProps();

		// create REST client
		JerseyClientGet client = new JerseyClientGet();
		
		// make GET request and store JSON reponse
		ArrayList<String> response = client.getMaps();
		
		// parse JSON reponse into objects
		JSONParserCustom jsParser = new JSONParserCustom();
		jsParser.parseLabs(response);
		System.out.println(labs);		
		
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
