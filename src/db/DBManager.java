package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import setup.PropertyManager;

import com.mysql.jdbc.Statement;

import models.Lab;
import models.Station;

public class DBManager {
	
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
		//logger.trace("*-----DBConnector is Creating DB Connection!-----*");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			//logger.error(e);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//logger.error(e);
		}
	}
	
	public void closeConnection(){
		//logger.trace("*-----DBConnector is Closing DB Connection!-----*");
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//logger.error(e);
		}
	}
		
	public void updateLabTable(ArrayList<Lab> labs) throws SQLException{
		//logger.trace("*-----DBConnector is Writing to Lab Status table!-----*");
		for(Lab lab: labs){
			try {
				Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
				for (Station station : lab.getMapStations()) {
					String query = "INSERT INTO "
							+ lab.getMapDesc()
							+ " (id, map_stations_id, map_id, group_id, station_id, hostname, ip, station_name, status, timestamp) "
							+ " VALUES ('" 
							+ station.getId() + "','"
							+ station.getMapStationsId() + "','"
							+ station.getMapId() + "','"
							+ station.getGroupId() + "','"
							+ station.getStationId() + "','"
							+ station.getHostName().toUpperCase() + "','"
							+ station.getIpAddresses().toUpperCase() + "','"
							+ station.getStationName().toUpperCase() + "','"
							+ station.getStatus().toUpperCase() + "','"
							+ "', NOW())";
					//logger.trace(query);
					stmt.executeUpdate(query);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				//logger.error(ex);
			}
		}		
	}
	
	public void updateLabStatusTable(ArrayList<Lab> labs) throws SQLException{
		//logger.trace("*-----DBConnector is Writing to Run Status Table!-----*");
		for(Lab lab: labs){
			try {
				Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
				String query = "INSERT INTO "
						+ "labstatus"
						+ " (id, map_id, map_name, map_desc, units_available, units_in_use, units_offline, timestamp) "
						+ " VALUES ('" 
						+ lab.getId()	+ "','" 
						+ lab.getMapId() + "','"
						+ lab.getMapName().toUpperCase() + "','" 
						+ lab.getMapDesc().toUpperCase()	+ "','" 
						+ lab.getUnitsAvail() + "','"
						+ lab.getUnitsInUse() + "',"
						+ lab.getUnitsOff() + "',"
						+ " NOW())";
					//logger.trace(query);
					stmt.executeUpdate(query);			
			} catch (SQLException ex) {
				ex.printStackTrace();
				//logger.error(ex);
			}
		}
	}
	
	public void finalize(){
		closeConnection();
	}

}
