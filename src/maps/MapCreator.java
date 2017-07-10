package maps;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import main.LabTrackerETL;
import models.Lab;
import models.Station;
import setup.PropertyManager;


public class MapCreator {
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	private ArrayList<Lab> labs = new ArrayList<Lab>();
	private Lab currentLab = null;

	private Map<String, String> mapProps = new HashMap<String, String>();
	
	private boolean enableCreator;
	private String mapTemplatesDir = null;
	private String mapOutputDir = null;
	
	public MapCreator() throws IOException{
		logger.trace("*-----Loading MapCreator Properties!-----*");
		getProps();
	}
	
	public void getProps() throws IOException {
		PropertyManager propManager = PropertyManager.getPropertyManagerInstance();
		// Get props
		this.mapProps = propManager.getHtmlProperties();
		// Retrieve HTML props
		this.enableCreator = mapProps.get("EnableCreator").matches("true");
		this.mapTemplatesDir = mapProps.get("MapTemplatesDir");
		this.mapOutputDir = mapProps.get("MapOutputDir");
		// Eventually log all of these out
		logger.trace("EnableCreator:    " + enableCreator);
		logger.trace("MapTemplatesDir:  " + mapTemplatesDir);
		logger.trace("MapOutputDir:     " + mapOutputDir);
	}
	
	public void createMaps() throws IOException{
		this.labs = LabTrackerETL.labs;
		if(enableCreator){
			for(Lab lab: labs){
				currentLab = lab;
				logger.trace("Creating map for " + currentLab.getProperName());
				writeMapOfStationsToTemplate();				
			}
		}			
	}
	
	// Writes stations to HTML Map File
	@SuppressWarnings({ "deprecation", "unused" })
	public void writeMapOfStationsToTemplate() throws IOException{
		logger.trace("Writing map for " + currentLab.getProperName() + " to directory " + mapOutputDir);
		File htmlMapTemplateFile = new File(mapTemplatesDir  + currentLab.getProperName() + ".html");
		String htmlString = FileUtils.readFileToString(htmlMapTemplateFile);			
		// Color Strings
		String availColor      = "<FONT COLOR=\"#FFCE00\">";
		String inUseColor      = "<FONT COLOR=\"#665113\">";
		String offlineColor    = "<FONT COLOR=\"#595138\">";
		String suppressedColor = "<FONT COLOR=\"#000000\">";
		// HTML Match Strings
		String begMatch = "<!--$";
		String endMatch = "-->";
		Integer numAvail = currentLab.getUnitsAvail();
		Integer numInUse = currentLab.getUnitsInUse();
		Integer numOffline = currentLab.getUnitsOff();
		Integer numUnits = currentLab.getUnitsAvail() + currentLab.getUnitsInUse() + currentLab.getUnitsOff();
		for (Station station : currentLab.getMapStations()) {
			
			if (station.getStatus().matches("Available")) {
				String completeMatch = begMatch + station.getStationNameShort().toLowerCase() + endMatch;
				if (htmlString.contains(completeMatch)) {
					htmlString = htmlString.replace(completeMatch, availColor);
				}
			}
			
			else if (station.getStatus().matches("InUse")) {
				String completeMatch = begMatch + station.getStationNameShort()	+ endMatch;
			} 
						
			else if (station.getStatus().matches("Offline")){
				String completeMatch = begMatch + station.getStationNameShort().toLowerCase() + endMatch + station.getStationNameShort().toUpperCase();
				String underLinedColor = offlineColor + "<u>" + station.getStationNameShort().toUpperCase() + "</u>";
				if (htmlString.contains(completeMatch)) {
					htmlString = htmlString.replace(completeMatch,underLinedColor);
				}
			}			

			else if (station.getStatus().matches("Suppressed")){
				String completeMatch = begMatch + station.getStationNameShort()	+ endMatch + station.getStationNameShort().toUpperCase();
				String underLinedColor = suppressedColor + "<u>" + station.getStationNameShort().toUpperCase() + "</u>";
				if (htmlString.contains(completeMatch)) {
					htmlString = htmlString.replace(completeMatch, underLinedColor);
				}
			}
		}
		Date date = new Date();
		DateFormat timeStamp = new SimpleDateFormat("h:mm a");
		DateFormat dateStamp = new SimpleDateFormat("E, MMM dd");
		String time = timeStamp.format(date).toString();
		String date1 = dateStamp.format(date).toString();
		htmlString = htmlString.replace("$time", time);
		htmlString = htmlString.replace("$date", date1);
		htmlString = htmlString.replace("$numAvail", numAvail.toString());
		htmlString = htmlString.replace("$numUnits", numUnits.toString());
		
		float percentAvail = (float) (numAvail / numUnits) * 100;
		float percentInUse = (float) (numInUse / numUnits) * 100;
		float percentOffline = (float) (numOffline / numUnits) * 100;
		int percAvail = (int) percentAvail;
		int percInUse = (int) percentInUse;
		int percOffline = (int) percentOffline;
		String avail = "(Available - " + numAvail   + ", " + numUnits + ")";
		String inUse = "(In Use    - " + numInUse   + ", " + numUnits + ")";
		String off = "(Offline   - "   + numOffline + ", " + numUnits + ")";
		
		htmlString = htmlString.replace("$availSummary", avail);
		htmlString = htmlString.replace("$inUseSummary", inUse);
		htmlString = htmlString.replace("$offSummary", off);
		File newHtmlFile = new File(mapOutputDir + currentLab.getProperName() + ".php");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);			
	}


	public ArrayList<Lab> getLabs() {
		return labs;
	}

	public void setLabs(ArrayList<Lab> labs) {
		this.labs = labs;
	}	
}
