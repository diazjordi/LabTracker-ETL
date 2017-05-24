package setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class PropertyManager {
	
	private static final Logger logger = LogManager.getLogger("LabTrackerETL");
	
	private static PropertyManager propertyManagerInstance;

	// General properties path
	private static String propertyFilePath = null;
	private static Properties mainProperties = new Properties();
	
	// Map codes
	private static Map<String, String> labCodes = new HashMap<String, String>();
		
	// Jersey API Client Properties
	private static Map<String, String> apiClientProps = new HashMap<String, String>();
	
	// Suppression properties
	private static String supFilePath = null;
	private static Map<String, String> suppressionProps = new HashMap<String, String>();
	
	// Database properties
	private static Map<String, String> databaseProps = new HashMap<String, String>();
	
	// HTML Templates & Properties
	private static Map<String, String> mapCreatorProps = new HashMap<String, String>();

	private PropertyManager() {
	}

	public static PropertyManager getPropertyManagerInstance() {
		if (null == propertyManagerInstance) {
			propertyManagerInstance = new PropertyManager();
		}
		return propertyManagerInstance;
	}
	
	public void loadProps() throws IOException {
		logger.trace("Loading Properties");		
		// Check if user supplied Property file
		findPropertyFile();		
		// Load prop file into main property object
		File mainPropertyFile = new File(propertyFilePath);
		FileInputStream mainInputStream = new FileInputStream(mainPropertyFile);
		mainProperties.load(mainInputStream);
		mainInputStream.close();
		// Check Property has actual values
		// if so proceed to retrieve properties
		if (!mainProperties.isEmpty()) {
			this.setProps();
		} else if (mainProperties.isEmpty()) {
			logger.error("No Properties Found!");
		}
	}
	
	public void findPropertyFile() throws IOException {
		logger.trace("Finding Properties File");
		boolean pfExists;		
		if (propertyFilePath == null) {
			String rootDirectory = new File(".").getCanonicalPath();
			String propertiesDir = rootDirectory.replace("JARs","Properties");
			String propertiesFile = propertiesDir.concat("/LabTracker.properties");
			logger.trace(propertiesFile);
			logger.trace("User did not set property file path, using default path.");
			pfExists = new File(propertiesFile).exists();
			if(pfExists){
				logger.trace("Found property file at path: " + propertiesFile);
				propertyFilePath = propertiesFile;
			}
		} else if (propertyFilePath != null) {
			pfExists = new File(propertyFilePath).exists();
			if (pfExists) {
				logger.trace("User set Property file, found at " + propertyFilePath);
			}
		} else {
			logger.trace("Property file not found in Properties directory or set by user");
			logger.trace(propertyFilePath);
			System.exit(1);
		}
	}	

	private void setProps() throws IOException {
		logger.trace("Populating Property Maps");
		Set<Object> keys = mainProperties.keySet();
		for (Object k : keys) {
			String key = (String) k;
			if (key.startsWith("lab")) {
				labCodes.put(key.substring(4), mainProperties.getProperty(key));
			} else if (key.startsWith("api")) {
				apiClientProps.put(key.substring(4), mainProperties.getProperty(key));
			} else if (key.startsWith("sup")) {
				supFilePath = mainProperties.getProperty(key);				
			} else if (key.startsWith("db")) {
				databaseProps.put(key.substring(3), mainProperties.getProperty(key));
			} else if (key.startsWith("html")) {
				mapCreatorProps.put(key.substring(5), mainProperties.getProperty(key));
			}			
		}
		retrieveSuppressionList(supFilePath);
		logger.trace(apiClientProps.toString());
	}
	
	private void retrieveSuppressionList(String filePath) throws IOException {
		logger.trace("Retrieving Suppression Properties");
		// Temp Properties object to load props from file
		Properties tempProps = new Properties();
		File suppressionFile = new File(filePath);
		if(suppressionFile.exists()){
			FileInputStream suppressionFileInput = new FileInputStream(suppressionFile);
			tempProps.load(suppressionFileInput);
			// Iterate through props
			Set<Object> keys = tempProps.keySet();
			for(Object k: keys){
				String key = (String) k;
				suppressionProps.put(key, tempProps.getProperty(key));
			}
		} else {
			logger.trace("Suppression File path not set! Will continue w/o suppression");
		}		
	}	
	
	public static String getPropertyFilePath() {
		return propertyFilePath;
	}
	
	public static void setPropertyFilePath(String propertyFilePath) {
		PropertyManager.propertyFilePath = propertyFilePath;
	}

	public static Properties getMainProperties() {
		return mainProperties;
	}

	public static void setMainProperties(Properties mainProperties) {
		PropertyManager.mainProperties = mainProperties;
	}

	public static Map<String, String> getApiClientProps() {
		return apiClientProps;
	}

	public static void setApiClientProps(Map<String, String> apiClientProps) {
		PropertyManager.apiClientProps = apiClientProps;
	}

	public static Map<String, String> getLabCodes() {
		return labCodes;
	}

	public static void setLabCodes(Map<String, String> labCodes) {
		PropertyManager.labCodes = labCodes;
	}
	
	public static Map<String, String> getSuppressionProperties() {
		return suppressionProps;
	}

	public static void setSuppressionProperties(Map<String, String> suppressionProperties) {
		PropertyManager.suppressionProps = suppressionProperties;
	}

	public static Map<String, String> getDatabaseProperties() {
		return databaseProps;
	}

	public static void setDatabaseProperties(Map<String, String> databaseProperties) {
		PropertyManager.databaseProps = databaseProperties;
	}
	
	public Map<String, String> getHtmlProperties() {
		return mapCreatorProps;
	}
	
	public void setHtmlProperties(Map<String, String> htmlProperties) {
		PropertyManager.mapCreatorProps = htmlProperties;
	}

	public static void setPropertyManagerInstance(PropertyManager propertyManagerInstance) {
		PropertyManager.propertyManagerInstance = propertyManagerInstance;
	}
}