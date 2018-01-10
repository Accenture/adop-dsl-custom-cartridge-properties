
import adop.cartridge.properties.*;

import java.lang.*;
import java.lang.reflect.*;
import java.util.Properties;

public class MultiLineStringPropertiesTest extends GroovyTestCase {

  public void testGetScmType(){
      CartridgeProperties cartridgeProperties = new CartridgeProperties("sonar.projectKey=adop\nopenshift.env=dev");
      
      assertEquals  "adop", cartridgeProperties.get("sonar.projectKey")
      assertEquals  "dev", cartridgeProperties.get("openshift.env")
  }
}
