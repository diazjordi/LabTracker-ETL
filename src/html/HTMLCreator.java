package html;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

import models.Lab;
import models.Station;
import setup.PropertyManager;


public class HTMLCreator {
	
	private ArrayList<Lab> labs = new ArrayList<Lab>();
	private Lab currentLab = null;

	private Map<String, String> htmlProperties = new HashMap<String, String>();

	private String htmlMapTemplateFilePath = null;
	private String htmlMapOutputPath = null;

	//private static final Logger logger = LogManager.getLogger("LabTracker");
	
	public HTMLCreator() throws IOException{
		getProps();
	}
	
	public HTMLCreator(ArrayList<Lab> labs) throws IOException{
		this.labs = labs;		
		for(Lab lab: labs){
			currentLab = lab;
			getProps();
			writeMapOfStationsToHTML();
		}		
	}

	public void getProps() throws IOException {
		PropertyManager propManager = PropertyManager.getPropertyManagerInstance();
		// Get props
		this.htmlProperties = propManager.getHtmlProperties();
		//System.out.println(htmlProperties.get("htmlMapTemplateFilePath") + lab.getMapDesc());
		// Retrieve HTML props
		this.htmlMapTemplateFilePath = htmlProperties.get("htmlMapTemplateFilePath") + currentLab.getMapDesc() + ".html";
		this.htmlMapOutputPath = htmlProperties.get("htmlMapOutputPath") + currentLab.getMapDesc() + ".php";
		// Eventually log all of these out
		//logger.trace("htmlMapTemplateFilePath:  " + htmlMapTemplateFilePath);
		//logger.trace("htmlMapOutputPath:        " + htmlMapOutputPath);
	}
	
	// Writes stations to HTML Map File
	@SuppressWarnings({ "deprecation", "unused" })
	public void writeMapOfStationsToHTML() throws IOException{
		File htmlMapTemplateFile = new File(htmlMapTemplateFilePath);
		String htmlString = FileUtils.readFileToString(htmlMapTemplateFile);			
		// Color Strings
		String availColor      = "<FONT COLOR=\"#FFCB2F\">";
		String inUseColor      = "<FONT COLOR=\"#665113\">";
		String offlineColor    = "<FONT COLOR=\"#595138\">";
		String suppressedColor =  "<FONT COLOR=\"#000000\">";
		// HTML Match Strings
		String begMatch = "<!--$";
		String endMatch = "-->";
		Integer numAvail = currentLab.getUnitsAvail();
		Integer numInUse = currentLab.getUnitsInUse();
		Integer numOffline = currentLab.getUnitsOff();
		Integer numUnits = currentLab.getUnitsAvail() + currentLab.getUnitsInUse() + currentLab.getUnitsOff();
		for (Station station : currentLab.getMapStations()) {
			if (station.getStatus().matches("Available")) {
				String completeMatch = begMatch + station.getStationNameShort()	+ endMatch;
				if (htmlString.contains(completeMatch)) {
					htmlString = htmlString.replace(completeMatch, availColor);
				}
			}
			
			else if (station.getStatus().matches("InUse")) {
				String completeMatch = begMatch + station.getStationNameShort()	+ endMatch;
			} 
						
			else if (station.getStatus().matches("Offline")){
				String completeMatch = begMatch + station.getStationNameShort()	+ endMatch + station.getStationNameShort().toUpperCase();
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
//		logger.trace(avail);
//		logger.trace(inUse);
//		logger.trace(off);
		
		htmlString = htmlString.replace("$availSummary", avail);
		htmlString = htmlString.replace("$inUseSummary", inUse);
		htmlString = htmlString.replace("$offSummary", off);
		File newHtmlFile = new File(htmlMapOutputPath);
		FileUtils.writeStringToFile(newHtmlFile, htmlString);			
	}


	public ArrayList<Lab> getLabs() {
		return labs;
	}

	public void setLabs(ArrayList<Lab> labs) {
		this.labs = labs;
	}
	
	
	
}
