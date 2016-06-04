
package org.cytoscape.pesca.internal;

/*
 * PescaShortestPathList.java
 *
 * Created on 14 febbraio 2007, 17.06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author scardoni
 */

import java.util.*;



public class PescaShortestPathList extends LinkedList{
    int length;
    /**
     * Creates a new instance of PescaShortestPathList
     */
    public PescaShortestPathList() {
         super();
         length = 0;
    }
    
    public String toString() {
        String result = "";
        for (int i=0; i < this.size(); i++) {
            result = result + ">" + ((PescaMultiSPath)this.get(i)).getName();
        }
        return result;
    }
    
    public String getSource()
    {
    	
    	 return ((PescaMultiSPath)this.get(0)).getName();
    	
    }
    
    public String getSourceDest()
    {
    	
    	 return ((PescaMultiSPath)this.get(0)).getName() + " " + ((PescaMultiSPath)this.get(this.size()-1)).getName();
    	
    }
    
       public int getlength() {
        return length;
    }
    
    public void setlength(int newlength) {
        length = newlength;
    } 
}
