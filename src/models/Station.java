package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {
	
	@JsonProperty("Id")
    private int id;
	@JsonProperty("MapStationsId")
    private int mapStationsId;
	@JsonProperty("MapId")
    private int mapId;
	@JsonProperty("GroupId")
    private int groupId;
	@JsonProperty("StationId")
    private int stationId;
	@JsonProperty("Flip")
    private String flip;
	@JsonProperty("Rotate")
    private String rotate;
	@JsonProperty("Shape")
    private String shape;
	@JsonProperty("X")
    private int x;
	@JsonProperty("Y")
    private int y;
	@JsonProperty("Os")
    private String os;
	@JsonProperty("HostName")
    private String hostName;
	@JsonProperty("IpAddresses")
    private String ipAddresses;
	@JsonProperty("StationName")
    private String stationName;
	@JsonProperty("Icon")
    private String icon;
	@JsonProperty("Status")
    private String status;
    
    public Station(){
    	super();
    }

	public Station(int id, int mapStationsId, int mapId, int groupId, int stationId, String flip, String rotate,
			String shape, int x, int y, String os, String hostName, String ipAddresses, String stationName, String icon,
			String status) {
		super();
		this.id = id;
		this.mapStationsId = mapStationsId;
		this.mapId = mapId;
		this.groupId = groupId;
		this.stationId = stationId;
		this.flip = flip;
		this.rotate = rotate;
		this.shape = shape;
		this.x = x;
		this.y = y;
		this.os = os;
		this.hostName = hostName;
		this.ipAddresses = ipAddresses;
		this.stationName = stationName;
		this.icon = icon;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMapStationsId() {
		return mapStationsId;
	}

	public void setMapStationsId(int mapStationsId) {
		this.mapStationsId = mapStationsId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getFlip() {
		return flip;
	}

	public void setFlip(String flip) {
		this.flip = flip;
	}

	public String getRotate() {
		return rotate;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(String ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		parseStatus(status);
	}
	
	public void parseStatus(String status){
		if (status.matches("PoweredOn")) {
			this.status = "Available";
		} else if (status.matches("InUse")) {
			this.status = "In Use";
		} else if (status.matches("Offline")) {
			this.status = "Offline";
		} else if (status.matches("Suppressed")) {
			this.status = "Suppressed";
		}
	}

	@Override
	public String toString() {
		return "Station: " +  "\n" +
				"	Id: " + id +  "\n" +
				"	MapStationsId: " + mapStationsId+  "\n" +
				"	MapId: " + mapId+  "\n" +
				"	GroupId: " + groupId +  "\n" +
				"	StationId: " + stationId +  "\n" +
				"	Flip: " + flip +  "\n" +
				"	Rotate: " + rotate +  "\n" +
				"	Shape: " + shape +  "\n" +
				"	Y: " + y +  "\n" +
				"	X: " + x +  "\n" +
				"	HostName: " + hostName +  "\n" +
				"	IpAddresses: " + ipAddresses +  "\n" +
				"	StationName: " + stationName +  "\n" +
				"	Icon: " + icon +  "\n" +
				"	Status: " + status +  "\n";
	}
    
	
    
}
