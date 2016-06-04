/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.cytoscape.pesca.internal;
/**
 *
 * @author scardoni
 */
public class Resultsbysize extends Results {
    
 //   String name;
 //   int size;
 //   PescaShortestPathList splist;
    
    public Resultsbysize() {
            }
    
    public Resultsbysize(PescaShortestPathList pescalist) {
        splist = pescalist;
        name = ((PescaMultiSPath)pescalist.getLast()).getName();
        size = pescalist.size();
            }
    
    public int compareTo(Object r) {
            return this.getSize() - ((Resultsbysize)r).getSize();
    }

  
    
}
