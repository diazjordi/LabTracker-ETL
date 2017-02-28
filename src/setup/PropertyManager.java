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
	private static String propertyFilePath = null;
	private static Properties mainProperties = new Properties();

	// Jersey Client properties
	private static Map<String, String> apiClientProps = new HashMap<String, String>();
	
	// Map code
	private static Map<String, String> labCodes = new HashMap<String, String>();

	// Parser properties
	private static Map<String, String> jsonParserProperties = new HashMap<String, String>();
	
	// Suppression properties
	private static Map<String, String> suppressionProperties = new HashMap<String, String>();

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
		// Check if user supplied Property file
		findPropertyFiles();
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
	
	public void findPropertyFiles() {
		String defaultPropertyFilePath = new File("../Properties/LabTracker.properties").getAbsolutePath();
		if (propertyFilePath == null) {
			System.out.println("User did not set property file path, using default path.");
			propertyFilePath = defaultPropertyFilePath;
		} else if (propertyFilePath != null) {
			boolean pfExists = new File(propertyFilePath).exists();
			if (pfExists) {
				System.out.println("User set Property file, found at " + propertyFilePath);
			}
		} else {
			System.out.println("Property file not found in Properties directory or set by user");
			System.out.println(propertyFilePath);
			System.exit(1);
		}
	}

	

	private void setProps() throws IOException {
		Set<Object> keys = mainProperties.keySet();
		for (Object k : keys) {
			String key = (String) k;
			if (key.startsWith("api")) {
				apiClientProps.put(key, mainProperties.getProperty(key));
			} else if (key.startsWith("lab")) {
				labCodes.put(key.substring(4), mainProperties.getProperty(key));
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

	public static Map<String, String> getJsonParserProperties() {
		return jsonParserProperties;
	}

	public static void setJsonParserProperties(Map<String, String> jsonParserProperties) {
		PropertyManager.jsonParserProperties = jsonParserProperties;
	}
	
	public static Map<String, String> getSuppressionProperties() {
		return suppressionProperties;
	}

	public static void setSuppressionProperties(Map<String, String> suppressionProperties) {
		PropertyManager.suppressionProperties = suppressionProperties;
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