
package adop.cartridge.properties;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import adop.cartridge.properties.exceptions.*;
import java.lang.IllegalArgumentException;

public class CartridgeProperties {
    
    private static final String LINE_ENDINGS_REGEX = "\r?\n";
    private static final String PROPERTY_DELIMITER = "=";
    
    private Properties properties = new Properties();
    
    public CartridgeProperties(String properties){
        this.loadProperties(properties);
    }
    
    /**
    * @param key key of property retrieve.
    * @return property, if key exists else throws MissingPropertyException. If
    *         key is null IllegalArgumentException is thrown.
    **/
    public String getProperty(String key){

        if(key == null){
            throw new IllegalArgumentException("Key cannot be null.");
        }

        if(!this.hasProperty(key)){
            throw new MissingPropertyException(//
                String.format("Property %s not found!", key));
        }else{
            return this.properties.getProperty(key);
        }
    }
    
    /**
    * @param key key of property retrieve.
    * @param defaultValue default value to return if property for key is missing.
    * @return property, if key exists else return default.
    **/
    public String getProperty(String key, String defaultValue){

        if(key == null || defaultValue == null){
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        
        return !this.hasProperty(key) ? defaultValue : this.getProperty(key);
    }
    
    /**
    * @param key key to lookup.
    * @return true if properties exists else false.
    **/
    private boolean hasProperty(String key){
        return this.properties.getProperty(key) != null;
    }
    
    /**
    * Load properties from a multi-line string.
    * 
    * @param properties properties seperated by \r\n.
    **/
    private void loadProperties(String properties){
        if (!properties.equals("") {   
            String[] lines = properties.split(LINE_ENDINGS_REGEX);
            for(line in lines){
                String[] property = line.split(PROPERTY_DELIMITER);
                if (property.length == 2) {
                    this.properties.setProperty(property[0],property[1]); 	
                } else {
                    throw new IllegalArgumentException( "line is not key value pair: " + line)
                }
            }
        }
    }
}