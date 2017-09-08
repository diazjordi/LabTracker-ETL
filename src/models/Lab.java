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
	
	private String properName;
	
	private int unitsAvailable;
	private int unitsInUse;
	private int unitsOffline;
	
	private int unitsSuppressed;	
	
	private int unitsWindows;
	private int availWindows;
	private int inUseWindows;
	private int offWindows;
	private int suppWindows;
	
	private int unitsMac;
	private int availMac;
	private int inUseMac;
	private int offMac;
	private int suppMac;
	
	private int unitsOtherOS;
	private int availOtherOS;
	private int inUseOtherOS;
	private int offOtherOS;
	private int suppOtherOS;	

	public Lab() {
		super();
	}

	public Lab(String properName, int id, int mapId, String mapName, String mapDesc, int width, int height, float scale, String label,
			float fontSize, String showOS, String showTT, String customerFileId, String backColor, String foreColor,
			int grid, ArrayList<Station> mapStations) {
		super();
		this.properName = properName;
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
	
	public String getProperName() {
		return properName;
	}

	public void setProperName(String properName) {
		this.properName = properName;
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
	
	public int getUnitsOtherOS() {
		return unitsOtherOS;
	}

	public void setUnitsOtherOS(int unitsOtherOS) {
		this.unitsOtherOS = unitsOtherOS;
	}	

	public int getAvailWindows() {
		return availWindows;
	}

	public void setAvailWindows(int availWindows) {
		this.availWindows = availWindows;
	}

	public int getInUseWindows() {
		return inUseWindows;
	}

	public void setInUseWindows(int inUseWindows) {
		this.inUseWindows = inUseWindows;
	}

	public int getOffWindows() {
		return offWindows;
	}

	public void setOffWindows(int offWindows) {
		this.offWindows = offWindows;
	}

	public int getSuppWindows() {
		return suppWindows;
	}

	public void setSuppWindows(int suppWindows) {
		this.suppWindows = suppWindows;
	}

	public int getAvailMac() {
		return availMac;
	}

	public void setAvailMac(int availMac) {
		this.availMac = availMac;
	}

	public int getInUseMac() {
		return inUseMac;
	}

	public void setInUseMac(int inUseMac) {
		this.inUseMac = inUseMac;
	}

	public int getOffMac() {
		return offMac;
	}

	public void setOffMac(int offMac) {
		this.offMac = offMac;
	}

	public int getSuppMac() {
		return suppMac;
	}

	public void setSuppMac(int suppMac) {
		this.suppMac = suppMac;
	}

	public int getAvailOtherOS() {
		return availOtherOS;
	}

	public void setAvailOtherOS(int availOtherOS) {
		this.availOtherOS = availOtherOS;
	}

	public int getInUseOtherOS() {
		return inUseOtherOS;
	}

	public void setInUseOtherOS(int inUseOtherOS) {
		this.inUseOtherOS = inUseOtherOS;
	}

	public int getOffOtherOS() {
		return offOtherOS;
	}

	public void setOffOtherOS(int offOtherOS) {
		this.offOtherOS = offOtherOS;
	}

	public int getSuppOtherOS() {
		return suppOtherOS;
	}

	public void setSuppOtherOS(int suppOtherOS) {
		this.suppOtherOS = suppOtherOS;
	}

	public void setUnitCounts() {
		for (Station stat : mapStations) {
			if (stat.getOs().matches("Windows")) {
				unitsWindows++;
				if (stat.getStatus().matches("Available")) {
					availWindows++;
				} else if (stat.getStatus().matches("In Use")) {
					inUseWindows++;
				} else if (stat.getStatus().matches("Offline")) {
					offWindows++;
				} else if (stat.getStatus().matches("Suppressed")){
					suppWindows++;
				}
			} 
			if (stat.getOs().matches("Macintosh")) {
				unitsMac++;
				if (stat.getStatus().matches("Available")) {
					availMac++;
				} else if (stat.getStatus().matches("In Use")) {
					inUseMac++;
				} else if (stat.getStatus().matches("Offline")) {
					offMac++;
				} else if (stat.getStatus().matches("Suppressed")){
					suppMac++;
				}
			} 
			if (!stat.getOs().matches("Windows") && !stat.getOs().matches("Macintosh")){
				unitsOtherOS++;
				if (stat.getStatus().matches("Available")) {
					availOtherOS++;
				} else if (stat.getStatus().matches("In Use")) {
					inUseOtherOS++;
				} else if (stat.getStatus().matches("Offline")) {
					offOtherOS++;
				} else if (stat.getStatus().matches("Suppressed")){
					suppOtherOS++;
				}
			}
		}
		unitsAvailable = availWindows + availMac + availOtherOS;
		unitsInUse = inUseWindows + inUseMac + inUseOtherOS;
		unitsOffline = offWindows + offMac + offOtherOS;
		unitsSuppressed = suppWindows + suppMac + suppOtherOS;
	}

	@Override
	public String toString() {
		return "Lab [id=" + id + ", mapId=" + mapId + ", mapName=" + mapName + ", mapDesc=" + mapDesc + ", width="
				+ width + ", height=" + height + ", scale=" + scale + ", label=" + label + ", fontSize=" + fontSize
				+ ", showOS=" + showOS + ", showTT=" + showTT + ", customerFileId=" + customerFileId + ", backColor="
				+ backColor + ", foreColor=" + foreColor + ", grid=" + grid + ", mapStations=" + mapStations
				+ ", mapCustomItems=" + mapCustomItems + ", properName=" + properName + ", unitsAvailable="
				+ unitsAvailable + ", unitsInUse=" + unitsInUse + ", unitsOffline=" + unitsOffline
				+ ", unitsSuppressed=" + unitsSuppressed + ", unitsWindows=" + unitsWindows + ", availWindows="
				+ availWindows + ", inUseWindows=" + inUseWindows + ", offWindows=" + offWindows + ", suppWindows="
				+ suppWindows + ", unitsMac=" + unitsMac + ", availMac=" + availMac + ", inUseMac=" + inUseMac
				+ ", offMac=" + offMac + ", suppMac=" + suppMac + ", unitsOtherOS=" + unitsOtherOS + ", availOtherOS="
				+ availOtherOS + ", inUseOtherOS=" + inUseOtherOS + ", offOtherOS=" + offOtherOS + ", suppOtherOS="
				+ suppOtherOS + "]";
	}

	
	
	
	
	
	
	
	
	
}
