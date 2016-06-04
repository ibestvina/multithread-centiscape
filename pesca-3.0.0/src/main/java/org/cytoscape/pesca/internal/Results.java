/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.pesca.internal;


/**
 *
 * @author scardoni
 */
public class Results implements Comparable{
    String name;
    int size;
    PescaShortestPathList splist;
    
    public Results() {
        
    }
    
    public int getSize() {
        return size;
    }
    
    public String getName() {
        return name;
    }
    
    
    
    public PescaShortestPathList getSPlist() {
        return splist;
    }
    
     public int compareTo(Object r) {
            return 0;
    }
}
