/*
 *
 * @author Kartik
 */
package scenegraph.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneGraph implements SceneGraphInterface {
    
    private final Node root;
    private final Map<String,ArrayList<String>> map;
    
    //static initializer
    {
        map = new LinkedHashMap<>();
    }

    public SceneGraph(Scene scene) {
        this.root = scene.getRoot();     
        constructSceneGraph();
       
    }
    
    @Override
    public Map<String,ArrayList<String>> getChildrens() {
        return map;
    }
    
    
    private void constructSceneGraph() {
        constructSceneGraph(root,null);
    }
    
    @SuppressWarnings("unchecked")
	private void constructSceneGraph(Node node,Iterator<Node> it ) {
        Parent parent;
                                                    
        try{
               parent = (Parent) node;  //assuming a non-leaf node
        }
                                                    
        catch(ClassCastException e) {   //Some of the leaf nodes are caught here such as - ImageView, Canvas, Shape
            return;
        }
                                                    
                                                        
       if(!(parent.getChildrenUnmodifiable().isEmpty())) {   
           //get an iterator over childrens attatched to non-leaf node
           it = parent.getChildrenUnmodifiable().iterator();            
        }
       
       else {
           try {  
                //for non-leaf nodes such as toolBar getItems method is used instead of getChildrenUnmodifiable
                it = ((ObservableList<Node>)parent.getClass().getMethod("getItems").invoke(parent)).iterator();            
            }
            //Other leaf nodes are caught here - such as all controls(buttons,text,label...etc)
            catch (NoSuchMethodException |  IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
               // Logger.getLogger(Ensemble2.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
       }
       
        while ( it.hasNext()) {
            node = it.next();
            
            constructSceneGraph(node,it);   //non-tail recursive call
            
            //building data structure
            if(map.containsKey(parent.toString())) {
                map.get(parent.toString()).add(node.toString());
            }
            else {
                ArrayList<String> nodes = new ArrayList<>();
                nodes.add(node.toString());
                map.put(parent.toString(),nodes);
            }
        }
    }
    
    
}
