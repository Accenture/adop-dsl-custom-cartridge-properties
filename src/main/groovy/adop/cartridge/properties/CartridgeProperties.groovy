
import java.util.*;

public class CartridgeProperties {
    
    private static final Map<String,String> properties = new HashMap<>();
    
    public CartridgeProperties(String properties){
        this.loadProperties(properties);
    }
    
    private void loadProperties(String properties){
        String[] lines = properties.split("\r?\n");
        
        for(line in lines){
            String[] property = line.split("=")
            this.properties.put(property[0],property[1]) 	
        }
    }
    
    public String get(String key){
        return this.properties.get(key);
    }
}