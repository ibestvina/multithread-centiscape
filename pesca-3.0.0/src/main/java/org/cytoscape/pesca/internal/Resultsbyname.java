/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.pesca.internal;


/**
 *
 * @author scardoni
 */
public class Resultsbyname extends Results {
 //String name;
  //  int size;
  //  PescaShortestPathList splist;
    
    public Resultsbyname() {
            }
    
    public Resultsbyname(PescaShortestPathList pescalist) {
        splist = pescalist;
        name = ((PescaMultiSPath)pescalist.getLast()).getName();
        size = pescalist.size();
            }
    
    public int compareTo(Object r) {
            return (this.getName()).compareTo(((Resultsbyname)r).getName());
    }

  
    
}
