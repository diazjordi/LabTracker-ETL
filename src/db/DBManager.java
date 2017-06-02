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
	private String ip;
	private String port;
	private String database;
	private String username;
	private String password;
	private boolean pushToDB;
	private Map<String, String> databaseProps = new HashMap<String, String>();		
	
	public DBManager(){
		logger.trace("*-----Loading DBManager Properties!-----*");
		databaseProps = PropertyManager.getDatabaseProperties();
		if(databaseProps.isEmpty()){
			logger.error("DBManager could not load necessary properties");
		} else {
			this.ip = databaseProps.get("ip");
			this.port = databaseProps.get("port");
			this.database = databaseProps.get("database");
			this.username = databaseProps.get("username");
			this.password = databaseProps.get("password");
			this.pushToDB = databaseProps.get("PushToDB").matches("true");
			logger.trace("IP:   "       + ip);
			logger.trace("Port: "       + port);
			logger.trace("Database: "   + database);
			logger.trace("Username: "   + username);
			logger.trace("Password: "   + password);
			logger.trace("Push to DB: " + pushToDB);
			createConnection();
		}
	}
	
	public void createConnection(){
		logger.trace("DBConnector is Creating DB Connection");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//this.con = DriverManager.getConnection("jdbc:mysql://10.82.244.203:3306/" + database, username, password);
			this.con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public void closeConnection(){
		logger.trace("DBConnector is Closing DB Connection");
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
		
	public void insertIntoLabTable(ArrayList<Lab> labs) throws SQLException{
		logger.trace("DBConnector is Writing to Lab specific table");
		if(pushToDB){
			for(Lab lab: labs){
				logger.trace("Updating table for: " + lab.getProperName());
				try {
					Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
					for (Station station : lab.getMapStations()) {
						String query = "INSERT INTO "
								+ lab.getProperName()
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
						stmt.executeUpdate(query);
						//logger.trace(query);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			}
		} else {
			logger.trace("Push to DB, set to false. Will not update DB. See LabTracker.properties file.");
		}
				
	}
	
	public void insertIntoLabStatusTable(ArrayList<Lab> labs) throws SQLException{
		logger.trace("DBConnector is Writing to Lab Status Table");
		if(pushToDB){
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
					stmt.executeUpdate(query);
					logger.trace(query.substring(query.indexOf("VALUES")));
				} catch (SQLException ex) {
					ex.printStackTrace();
					logger.error(ex);
				}
			} 
		} else {
			logger.trace("Push to DB, set to false. Will not update DB. See LabTracker.properties file.");
		}
	}
	
	public void finalize(){
		closeConnection();
	}

	@Override
	public String toString() {
		return "DBManager [con=" + con + ", ip=" + ip + ", port=" + port + ", database=" + database + ", username="
				+ username + ", password=" + password + ", pushToDB=" + pushToDB + ", databaseProps=" + databaseProps
				+ "]";
	}

	
	
	

}
