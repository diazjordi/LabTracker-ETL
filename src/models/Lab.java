package models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lab {

	@JsonProperty("Id")
	private int id;
	@JsonProperty("MapId")
	private int mapId;
	@JsonProperty("MapName")
	private String mapName;
	@JsonProperty("MapDesc")
	private String mapDesc;
	@JsonProperty("Width")
	private int width;
	@JsonProperty("Height")
	private int height;
	@JsonProperty("Scale")
	private float scale;
	@JsonProperty("Label")
	private String label;
	@JsonProperty("FontSize")
	private float fontSize;
	@JsonProperty("ShowOS")
	private String showOS;
	@JsonProperty("ShowTT")
	private String showTT;
	@JsonProperty("CustomerFileId")
	private String customerFileId;
	@JsonProperty("BackColor")
	private String backColor;
	@JsonProperty("ForeColor")
	private String foreColor;
	@JsonProperty("Grid")
	private int grid;
	@JsonProperty("MapStations")
	private ArrayList<Station> mapStations;
	@JsonProperty("MapCustomItems")
	private ArrayList<String> mapCustomItems;
	@JsonProperty("unitsAvail")
	private int unitsAvailable;
	@JsonProperty("unitsInUse")
	private int unitsInUse;
	@JsonProperty("unitsOffline")
	private int unitsOffline;
	
	private int unitsSuppressed;
	private int unitsWindows;
	private int unitsMac;

	public Lab() {
		super();
	}

	public Lab(int id, int mapId, String mapName, String mapDesc, int width, int height, float scale, String label,
			float fontSize, String showOS, String showTT, String customerFileId, String backColor, String foreColor,
			int grid, ArrayList<Station> mapStations) {
		super();
		this.id = id;
		this.mapId = mapId;
		this.mapName = mapName;
		this.mapDesc = mapDesc;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.label = label;
		this.fontSize = fontSize;
		this.showOS = showOS;
		this.showTT = showTT;
		this.customerFileId = customerFileId;
		this.backColor = backColor;
		this.foreColor = foreColor;
		this.grid = grid;
		this.mapStations = mapStations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapDesc() {
		return mapDesc;
	}

	public void setMapDesc(String mapDesc) {
		this.mapDesc = mapDesc;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fntSize) {
		this.fontSize = fntSize;
	}

	public String getShowOS() {
		return showOS;
	}

	public void setShowOS(String showOS) {
		this.showOS = showOS;
	}

	public String getShowTT() {
		return showTT;
	}

	public void setShowTT(String showTT) {
		this.showTT = showTT;
	}

	public String getCustomerFileId() {
		return customerFileId;
	}

	public void setCustomerFileId(String customerFileId) {
		this.customerFileId = customerFileId;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getForeColor() {
		return foreColor;
	}

	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}

	public ArrayList<Station> getMapStations() {
		return mapStations;
	}

	public void setMapStations(ArrayList<Station> mapStations) {
		this.mapStations = mapStations;
	}

	public int getUnitsAvail() {
		return unitsAvailable;
	}

	public void setUnitsAvail(int unitsAvail) {
		this.unitsAvailable = unitsAvail;
	}

	public int getUnitsInUse() {
		return unitsInUse;
	}

	public void setUnitsInUse(int unitsInUse) {
		this.unitsInUse = unitsInUse;
	}

	public int getUnitsOff() {
		return unitsOffline;
	}

	public void setUnitsOff(int unitsOff) {
		this.unitsOffline = unitsOff;
	}
	
	public int getUnitsSuppressed() {
		return unitsSuppressed;
	}

	public void setUnitsSuppressed(int UnitsSuppressed) {
		this.unitsSuppressed = UnitsSuppressed;
	}	
	
	public int getUnitsWindows() {
		return unitsWindows;
	}

	public void setUnitsWindows(int unitsWindows) {
		this.unitsWindows = unitsWindows;
	}

	public int getUnitsMac() {
		return unitsMac;
	}

	public void setUnitsMac(int unitsMac) {
		this.unitsMac = unitsMac;
	}

	public void setUnitCounts() {
		int avail = 0;
		int inuse = 0;
		int off = 0;
		int sup = 0;
		for (Station stat : mapStations) {
			if (stat.getStatus().matches("Available")) {
				avail++;
			} else if (stat.getStatus().matches("In Use")) {
				inuse++;
			} else if (stat.getStatus().matches("Offline")) {
				off++;
			} else if (stat.getStatus().matches("SUPPRESSED")){
				sup++;
			}
		}
		unitsAvailable = avail;
		unitsInUse = inuse;
		unitsOffline = off;
		unitsSuppressed= sup;
		setOSCounts();
	}
	
	public void setOSCounts(){
		int win = 0;
		int mac = 0;
		for (Station stat : mapStations) {
			if (stat.getOs().matches("Windows")) {
				win++;
			} else if (stat.getOs().matches("Macintosh")) {
				mac++;
			} 
		}
		unitsWindows = win;
		unitsMac = mac;
	}


	@Override
	public String toString() {
		return "Lab: " +  "\n" +
				"	Id: " + id +  "\n" +
				"	MapId: " + mapId +  "\n" +
				"	MapName: " + mapName +  "\n" +
				"	MapDesc: " + mapDesc +  "\n" +
				"	Width: " + width +  "\n" +
				"	Height: " + height +  "\n" +
				"	Scale: " + scale +  "\n" +
				"	Label: " + label +  "\n" +
				"	FontSize: " + fontSize +  "\n" +
				"	ShowOS: " + showOS +  "\n" +
				"	ShowTT: " + showTT +  "\n" +
				"	CustomerFileId: " + customerFileId +  "\n" +
				"	Backcolor: " + backColor +  "\n" +
				"	Forecolor: " + foreColor +  "\n" +
				"	Grid: " + grid +  "\n" +
				"	Units Available:" + unitsAvailable +  "\n" +
				"	Units In Use:" + unitsInUse +  "\n" +
				"	Units Offline:" + unitsOffline +  "\n" +
				"	Units Suppressed:" + unitsSuppressed +  "\n" +
				"	MapStations: " +  "\n" + mapStations;
	}
	
}
