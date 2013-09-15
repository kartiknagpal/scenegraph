/*
 * This interface provides methods to get Scene graph structure at the runtime
 * @author Kartik
 */
package scenegraph.util;

import java.util.ArrayList;
import java.util.Map;


public interface SceneGraphInterface {
    
    public Map<String,ArrayList<String>> getChildrens();
    
}
