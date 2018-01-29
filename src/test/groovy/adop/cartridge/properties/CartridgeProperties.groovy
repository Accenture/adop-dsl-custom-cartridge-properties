
import adop.cartridge.properties.*;
import adop.cartridge.properties.exceptions.*;

import java.lang.*;
import java.lang.reflect.*;
import java.util.Properties;

public class MultiLineStringPropertiesTest extends GroovyTestCase {

  public void testGetPropertyUnixLineEnding(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\nopenshift.env=dev");
      
      assertEquals  "adop", cartridgeProperties.getProperty("sonar.projectKey");
      assertEquals  "dev", cartridgeProperties.getProperty("openshift.env");
  }
  
  public void testGetPropertyWindowsLineEndings(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\r\nopenshift.env=dev");
      
      assertEquals  "adop", cartridgeProperties.getProperty("sonar.projectKey");
      assertEquals  "dev", cartridgeProperties.getProperty("openshift.env");
  }

  public void testGetPropertyThatDoesNotExist(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\r\nopenshift.env=dev");
      
      shouldFail(MissingPropertyException) {
          cartridgeProperties.getProperty("sonar.projectName");
      }
  }
    
  public void testGetPropertyNullKey(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\r\nopenshift.env=dev");
      
      shouldFail(IllegalArgumentException) {
          cartridgeProperties.getProperty(null);
      }
  }
    
  public void testGetDefaultPropertyForKeyThatDoesNotExist(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\r\nopenshift.env=dev");
      assertEquals  "default", cartridgeProperties.getProperty("openshift.env.prod", "default");
  }  
}