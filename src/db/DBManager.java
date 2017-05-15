package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import setup.PropertyManager;

import com.mysql.jdbc.Statement;

import models.Lab;
import models.Station;

public class DBManager {
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	private Connection con;
	private String database;
	private String username;
	private String password;	
	private Map<String, String> databaseProperties = new HashMap<String, String>();		
	
	public DBManager(){
		databaseProperties = PropertyManager.getDatabaseProperties();
		if(databaseProperties.isEmpty()){
			System.out.println("DBManager could not load necessary properties");
		} else {
			database = databaseProperties.get("db.database");
			username = databaseProperties.get("db.username");
			password = databaseProperties.get("db.password");
			createConnection();
		}
	}
	
	public void createConnection(){
		logger.trace("*-----DBConnector is Creating DB Connection!-----*");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://10.82.244.203:3306/" + database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public void closeConnection(){
		logger.trace("*-----DBConnector is Closing DB Connection!-----*");
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
		
	public void insertIntoLabTable(ArrayList<Lab> labs) throws SQLException{
		logger.trace("*-----DBConnector is Writing to Lab table!-----*");
		for(Lab lab: labs){
			try {
				Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
				for (Station station : lab.getMapStations()) {
					String query = "INSERT INTO "
							+ lab.getMapDesc()
							+ " (id, map_station_id, map_id, group_id, station_id, os, host_name, ip, station_name, status, timestamp) "
							+ " VALUES ('" 
							+ station.getId() + "','"
							+ station.getMapStationsId() + "','"
							+ station.getMapId() + "','"
							+ station.getGroupId() + "','"
							+ station.getStationId() + "','"
							+ station.getOs() + "','"
							+ station.getHostName().toUpperCase() + "','"
							+ station.getIpAddresses().toUpperCase() + "','"
							+ station.getStationName().toUpperCase() + "','"
							+ station.getStatus().toUpperCase() + "',"
							+ "NOW())";
					logger.trace(query);
					stmt.executeUpdate(query);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}		
	}
	
	public void insertIntoLabStatusTable(ArrayList<Lab> labs) throws SQLException{
		logger.trace("*-----DBConnector is Writing to Lab Status Table!-----*");
		for(Lab lab: labs){
			try {
				Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
				String query = "INSERT INTO "
						+ "labstatus"
						+ " (id, map_id, map_name, map_desc, units_available, units_in_use, units_offline, units_suppressed, units_windows, units_mac, units_other_os, units_available_windows, units_in_use_windows, units_offline_windows, units_suppressed_windows, units_available_mac, units_in_use_mac, units_offline_mac, units_suppressed_mac,units_available_other_os, units_in_use_other_os, units_offline_other_os, units_suppressed_other_os, timestamp)"
						+ " VALUES ('" 
						+ lab.getId()	+ "','" 
						+ lab.getMapId() + "','"
						+ lab.getMapName().toUpperCase() + "','" 
						+ lab.getMapDesc().toUpperCase()	+ "','" 
						+ lab.getUnitsAvail() + "','"
						+ lab.getUnitsInUse() + "','"
						+ lab.getUnitsOff() + "','"
						+ lab.getUnitsSuppressed() + "','"
						+ lab.getUnitsWindows() + "','"
						+ lab.getUnitsMac() + "','"
						+ lab.getUnitsOtherOS() + "','"
						+ lab.getAvailWindows() + "','"
						+ lab.getInUseWindows() + "','"
						+ lab.getOffWindows() + "','"
						+ lab.getSuppWindows() + "','"
						+ lab.getAvailMac() + "','"
						+ lab.getInUseMac() + "','"
						+ lab.getOffMac() + "','"
						+ lab.getSuppMac() + "','"
						+ lab.getAvailOtherOS() + "','"
						+ lab.getInUseOtherOS() + "','"
						+ lab.getOffOtherOS() + "','"
						+ lab.getSuppOtherOS() + "',"
						+ "NOW())";
					logger.trace(query);
				stmt.executeUpdate(query);					
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}
	
	public void finalize(){
		closeConnection();
	}

	@Override
	public String toString() {
		return "DBManager [con=" + con + ", database=" + database + ", username=" + username + ", password=" + password
				+ ", databaseProperties=" + databaseProperties + "]";
	}
	
	

}
