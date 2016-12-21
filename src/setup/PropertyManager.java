package setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

//When ready for production, update Property file path in PropertyManager class
public class PropertyManager {

	private static PropertyManager propertyManagerInstance;

	// General properties path
	private static String propertyFilePath = "/home/developer/Desktop/LabTracker-v2/Properties/LabTracker.properties";
	private static Properties mainProperties = new Properties();

	// Jersey Client properties
	private static Map<String, String> apiClientProps = new HashMap<String, String>();
	
	// Map code
	private static Map<String, String> mapCodes = new HashMap<String, String>();

	// Parser properties
	private static Map<String, String> jsonParserProperties = new HashMap<String, String>();

	// Database properties
	private static Map<String, String> databaseProperties = new HashMap<String, String>();

	// Logger
	//private static final Logger logger = LogManager.getLogger("LabTracker");

	private PropertyManager() {
	}

	public static PropertyManager getPropertyManagerInstance() {
		if (null == propertyManagerInstance) {
			propertyManagerInstance = new PropertyManager();
		}
		return propertyManagerInstance;
	}

	public void loadProps() throws IOException {
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
			//error.fatalError("No Properties Found!");
		}
	}

	private void setProps() throws IOException {
		Set<Object> keys = mainProperties.keySet();
		// Iterate main property object and parse
		// properties into their respective maps
		// based on individual key value
		for (Object k : keys) {
			String key = (String) k;
			if (key.startsWith("api")) {
				apiClientProps.put(key, mainProperties.getProperty(key));
			} else if (key.startsWith("map")) {
				mapCodes.put(key, mainProperties.getProperty(key));
			} else if (key.startsWith("parser")) {
				jsonParserProperties.put(key, mainProperties.getProperty(key));
			} else if (key.startsWith("db")) {
				databaseProperties.put(key, mainProperties.getProperty(key));
			} 
		}
	}

	public static String getPropertyFilePath() {
		return propertyFilePath;
	}
	
	// Getters for property maps

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

	public static Map<String, String> getMapCodes() {
		return mapCodes;
	}

	public static void setMapCodes(Map<String, String> mapCodes) {
		PropertyManager.mapCodes = mapCodes;
	}

	public static Map<String, String> getJsonParserProperties() {
		return jsonParserProperties;
	}

	public static void setJsonParserProperties(Map<String, String> jsonParserProperties) {
		PropertyManager.jsonParserProperties = jsonParserProperties;
	}

	public static Map<String, String> getDatabaseProperties() {
		return databaseProperties;
	}

	public static void setDatabaseProperties(Map<String, String> databaseProperties) {
		PropertyManager.databaseProperties = databaseProperties;
	}

	public static void setPropertyManagerInstance(PropertyManager propertyManagerInstance) {
		PropertyManager.propertyManagerInstance = propertyManagerInstance;
	}
}