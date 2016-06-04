package org.cytoscape.pesca.internal;

/*
 * PescaMultiSPath.java
 *
 * Created on 17 gennaio 2007, 16.13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author sc ardoni
 */



import java.util.*;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;



public class PescaMultiSPath {
    
    private CyNode node;
    private String nodename;
    private int cost;
    private PescaMultiSPath predecessor;
    private Vector predecessorVector;
  //  private double Stresscount;
    private int shortestpathcount;
        CyNetwork currentnetwork;
         private long nodesuid;
           
    /**
     * Creates a new instance of PescaMultiSPath
     */
    public PescaMultiSPath(CyNode Node, PescaMultiSPath Predecessor) {
        node = Node;
    //    nodename = node.getNetworkPointer().getRow(node).get("name",String.class);
       // nodename = currentnetwork.getRow(node).get("name",String.class)
       // nodename = node.getIdentifier();
        cost = 1;
        predecessor = Predecessor;
        predecessorVector = new Vector();
        predecessorVector.addElement(Predecessor);
    //    Stresscount = 0;
        shortestpathcount = 0;
        nodesuid = Node.getSUID();      
      //  nodelist = new Vector();
      //  nodelist.addElement(Node);
      
        
        
        }
      //creates an instance of Spath with empty vector 
      //cost should be set = 0 to create the root Spath
      public PescaMultiSPath(CyNode Node, int n, CyNetwork currentnetwork) {
       
        
        
           node = Node;
        this.currentnetwork = currentnetwork;
        nodename =  currentnetwork.getRow(node).get("name",String.class);
         nodesuid = Node.getSUID();      
        cost = n;
        predecessor = null;
        predecessorVector = new Vector();
      //  Stresscount = 0;
        shortestpathcount = 0;
        
        }
    
    
      // set the cost to Cost
      public void setCost(int Cost) {
          cost = Cost;
      }
      // set the predecessor to newPredecessor
      public void addPredecessor(PescaMultiSPath newPredecessor){
          predecessorVector.addElement(newPredecessor);
      }
      
      public PescaMultiSPath getPredecessor(int i) {
          return (PescaMultiSPath)predecessorVector.elementAt(i);
      }
      
      public int PredecessorVectorSize() {
          return predecessorVector.size();
      }
      
      // return the node name of the PescaMultiSPath instance
      public CyNode getNode() {
        return this.node;  
      }
      
      public int getCost()  {
          return this.cost;
      }
      
      public String getName() {
      return nodename;
      }
      
      public Long getSUID() {
          return nodesuid;
      }
      
     // public void setStressCount(int i) {
     //     Stresscount = i;
     // }
       
           
     // public double getStressCount() {
     //     return Stresscount;
     // }
      
       public void setShortestPathCount(int i) {
          shortestpathcount = i;
      }
       
      public void incrementShortestPathCount() {
          shortestpathcount++;
      }
      
      public double getShortestPathCount() {
          return shortestpathcount;
      }
      
      
      public String predecessortoString() {
          String predecessorString = " no predecessor ";
          for (int i = 0; i < predecessorVector.size(); i++) {
              if (i == 0) { 
                  predecessorString = " " + getPredecessor(i).node.getNetworkPointer().getRow(node).get("name",String.class);
              }
              else {
              predecessorString = predecessorString + " " + getPredecessor(i).node.getNetworkPointer().getRow(node).get("name",String.class);
              }
          }
          return predecessorString;
      }
      
      public String toString() {
        String PathString;
        PathString = "origine = " + node.getSUID()+ " costo = " + cost;
        if (cost == 0) {PathString = PathString + " Root and Target are the same ";}
        else
            //if (predecessor == null) { PathString = PathString + "cazzoooo ";}
            if (predecessorVector.size() != 0) {    
        //{PathString = PathString + " " +((CyNode)nodelist.elementAt(0)).getIdentifier();}
        {PathString = PathString + "predecessori= " + predecessortoString();} }
        return PathString;
      }
	public void removeAllPredecessors() {
		 predecessorVector.removeAllElements();
		
		
	}
	
     
       
}
