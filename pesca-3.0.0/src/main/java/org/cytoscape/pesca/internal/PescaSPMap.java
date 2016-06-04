/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.pesca.internal;

import java.util.*;
import java.util.Iterator;

/**
 *
 * @author scardoni
 */
public class PescaSPMap {
    HashMap pescaSPmap;
    HashMap pescaPathsCountmap;
    int expectedPaths;
    int pathLength;
    
public PescaSPMap() {
    pescaSPmap = new HashMap();   
    pescaPathsCountmap = new HashMap();   
    pathLength=0;
}


public void update(Integer value, String path) {
    
    if (pescaSPmap.containsKey(value)) {
        Integer sizecount = (Integer)pescaSPmap.get(value);
        pescaSPmap.put(value, sizecount+1);       
    }
    else {
        pescaSPmap.put(value, new Integer(1));
    }
    
    if (!pescaPathsCountmap.containsKey(path)) {     
    	pescaPathsCountmap.put(path, new Integer(1));
    	pathLength+=value;    	    
    }
    
   
    
}


public int getExpectedPaths()
{
	return expectedPaths;
}

public void setExpectedPaths(int paths)
{
	expectedPaths =paths;
}


public int getPathLength() {
	return pathLength;
}

public void setPathLength(int pathLength) {
	this.pathLength = pathLength;
}


public String toString() {
    
    Iterator it = pescaSPmap.entrySet().iterator();
    String tmp = "";
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        tmp = tmp + "  " +pairs.getKey() + "   |    " + pairs.getValue() + "\n";
      //  it.remove(); // avoids a ConcurrentModificationException
    }


return tmp;
    
}

public int getAllPathsCount()
{
	return pescaPathsCountmap.size();
		
}

public Vector MaptoVector() {
    Vector result = new Vector();
    Iterator it = pescaSPmap.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        result.addElement(pairs);
  //      System.out.println("aggiunto = "+ pairs.getKey() + " = " + pairs.getValue() + "\n" );
      //  it.remove(); // avoids a ConcurrentModificationException
    }
    
    
    return result;
}


}


