package utils;

import org.apache.commons.configuration.PropertiesConfiguration;

public class PropReader {

  private static final String path =
      System.getProperty("user.dir") + "/src/main/resources/startup.properties";

  public static String read(String key) {

    PropertiesConfiguration conf;
    String value = null;
    try {
      conf = new PropertiesConfiguration(path);
      value = conf.getString(key);
    } catch (org.apache.commons.configuration.ConfigurationException e) {
      e.printStackTrace();
    }
    System.out.println("Key " + key + " value " + value);
    return value;
  }

  public static void write(String key, String value) {

    org.apache.commons.configuration.PropertiesConfiguration conf;
    try {
      conf = new PropertiesConfiguration(path);
      conf.setProperty(key, value);
      conf.save();
    } catch (org.apache.commons.configuration.ConfigurationException e) {
      e.printStackTrace();
    }
  }
}
