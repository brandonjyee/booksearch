package booksearch.util;

import java.util.Properties;

/**
 * Meant to be read-only for now. If want it to pick up dynamic changes or make it not read-only,
 * will need to consider more synchronization issues.
 * 
 * @author Brandon
 *
 */
public class ConfigProps {
	private Properties props;
	private static ConfigProps myInstance = null;
	
	private ConfigProps() {
		props = new Properties();
		try {
			// Load the config.properties file from the classpath
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			props.load(classLoader.getResourceAsStream("config/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load config.properties file. Check classpath.");
		}
	}
	
	public synchronized static ConfigProps getInstance() {
		if (myInstance == null) {
			myInstance = new ConfigProps();
		}
		return myInstance;
	}
	
	public String get(String field) {
		return props.getProperty(field);
	}
	
}
