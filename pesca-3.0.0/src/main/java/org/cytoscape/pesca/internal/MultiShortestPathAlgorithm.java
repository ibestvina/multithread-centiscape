/*
 * MultiShortestPathAlgorithm.java
 *
 * Created on 1 febbraio 2007, 12.35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author scardoni
 */
package org.cytoscape.pesca.internal;

import java.util.*;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;


public class MultiShortestPathAlgorithm {
   
    /**
     * Creates a new instance of MultiShortestPathAlgorithm
     */
    public MultiShortestPathAlgorithm() {
    }
    
    
    
    public static Vector ExecuteMultiShortestPathAlgorithm(CyNetwork network,  CyNode root, CyNode target) {
     
        //Declaration of set and list used in the algorithm 
        //PathSet is the set of the shortest path
        HashSet PathSet;
        //TempSet is the set of temporary Shortest path
        HashSet TempSet;
        //Queue is the list of the temporary shortest path
        LinkedList Queue;
       
        //The vector of the final results
        Vector ShortestPathVector = new Vector();
      
        PathSet = new HashSet();
        //create the set of temporary Shortest path
        TempSet = new HashSet();
        //create the list of the temporary shortest path
        Queue = new LinkedList();
        //initialize shortest path Queue and Tempset
      
        System.out.println("root = " + root.getSUID() + "target = " + target.getSUID());
        initialize(TempSet,Queue,network,root);
       ShortestPathCore(PathSet,TempSet,Queue,network,target);
         /*initialize(TempSet,Queue,network,view,root);
         // Start the Core of the multi shortestpath algorithm
         * 
         ShortestPathCore(PathSet,TempSet,Queue,network,target,view);
         //now go through our PathSet and select the PescaMultiSPath corresponding to the target node*/
      
        
         boolean result = false;
         for (Iterator i = PathSet.iterator(); i.hasNext();) {
            PescaMultiSPath tmpspath = (PescaMultiSPath)i.next();
             System.out.println("percorro il pathseth "  +  PathSet.toString()) ;
            if (tmpspath.getNode().equals(target)) { 
         // select each nodeview belonging to the Shortest Path found in order 
         // to visualize them
               PescaShortestPathList prova = new PescaShortestPathList();
               ShortestPathVector.addElement(prova);
               createShortestPathVector(prova,tmpspath,ShortestPathVector);
               result = true;
               break;
            }
         }
         if (!result) {
              System.out.println("ritorno null");
             /* 
             JOptionPane.showMessageDialog(view.getComponent(), " The nodes are not connected. No Shortest Path found ");*/
        return null;
         }
          //tell the view to redraw since we've changed the selection         
           // view.redrawGraph(false, true);
         else {  
             String vettorestringa;
             for (int i=0; i < ShortestPathVector.size(); i++) {
                 vettorestringa = ShortestPathVector.elementAt(i).toString();
            // JOptionPane.showMessageDialog(view.getComponent(),
            //         "Il vettore e' " + vettorestringa);
             }
            // ResultWindow prova = new ResultWindow();
            // prova.changelist(ShortestPathVector);
            // prova.setVisible(true);
             System.out.println("ritorno " + ShortestPathVector.toString());
             return ShortestPathVector;
         }
         
         
         
    }   
     
        //initialize shortest path 
        /*public static void initialize(HashSet TempSet, LinkedList Queue, CyNetwork network, CyNetworkView view, CyNode root) {
            //create the root Shortest Path (cost=o, predecessor = null) 
            PescaMultiSPath primospath = new PescaMultiSPath(root,0); 
            //initialize shortest path for each node
            // predecessor is null and shortestpath cost is the maximum
            //i.e. number of node plus 1. for root node cost is 0;
            int numberofnodes = network.getNodeCount();
            //add the root Shortest Path to the Queue
            Queue.add(primospath);
            //iterate on all the nodes of the view
            for (Iterator i = view.getNodeViewsIterator();i.hasNext(); ) {
                NodeView nView = (NodeView)i.next();
                CyNode currentnode = (CyNode)nView.getNode();
                // initialize all the node except of root
                if (!currentnode.equals(root)) {
                    PescaMultiSPath currentSPath = new PescaMultiSPath(currentnode,numberofnodes + 1);
                    TempSet.add(currentSPath);
                }
            }
       }*/
    
    
       /*public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, LinkedList Queue, CyNetwork network, CyNode target, CyNetworkView view) {
            //Iterate the minimum path algorithm on the Queue list 
            for (Iterator i = Queue.iterator();i.hasNext(); ) {
            //get the first element of the Queue and add it to the set of the Shortest Path  
                PescaMultiSPath currentSPath = (PescaMultiSPath)Queue.remove(0);
                PathSet.add(currentSPath);
                // if it is the target node the algorithm is finished
                if (currentSPath.getNode().equals(target)) {
                    break;                   
                }
                // get the neighbors of the selected PescaMultiSPath node
                List neighbors = network.neighborsList(currentSPath.getNode());
                // and iterate over the neighbors 
                for (Iterator ni = neighbors.iterator(); ni.hasNext(); ) {
                    CyNode neighbor = (CyNode)ni.next();
                    //relax the currentSPath with its neighbors          
                    relax(currentSPath,neighbor,TempSet,Queue,view); 
                }
            }
       }*/
       /*
       public static void relax(PescaMultiSPath CurrentSPath, CyNode NeighBor, HashSet TempSet, LinkedList Queue) { 
            // verify if NeighBor is in the TempSet end put the corresponding PescaMultiSPath in NeighborSPAth 
            PescaMultiSPath NeighborSPath = findSPath(NeighBor,TempSet);
            // if yes then add NeighborSPath to the Queue and set the cost and predecessor
            if (NeighborSPath != null) {
                 NeighborSPath.setCost(CurrentSPath.getCost() + 1);
                 NeighborSPath.addPredecessor(CurrentSPath);
                 Queue.addLast(NeighborSPath);
                 // then remove it from the TempSet
                 TempSet.remove(NeighborSPath);
            }
            else {
                // if Neighbor is not in TempSet verify if it is in Queue
                NeighborSPath = findSPath(NeighBor,Queue);
                // if yes put it in NeighborSPath
                if (NeighborSPath != null) {
                    // then verify if its cost is greater then the one of the current SPath
                    if (NeighborSPath.getCost() > CurrentSPath.getCost()) {
                        // if yes we have found a new minimium shortestpath so we have another predecessor 
                        // for neighbor. we update cost(useless) and we add the new predecessor
                        NeighborSPath.setCost(CurrentSPath.getCost() + 1);
                        NeighborSPath.addPredecessor(CurrentSPath);  
                    }
                }           
            }
        }
    
     
       // Verify if Node is in TempSet and return the corresponding
       // PescaMultiSPath element
       public static PescaMultiSPath findSPath(CyNode Node,Collection TempSet) {
          PescaMultiSPath foundSPath= null;
          for (Iterator i = TempSet.iterator(); i.hasNext();) {
             PescaMultiSPath tempSPath = (PescaMultiSPath)i.next();
             if (Node.equals(tempSPath.getNode())) { 
                foundSPath = tempSPath;
                break;
             }
          }
          return foundSPath;      
       }     
        
       
        // create the vector ShortestPathVector of the shortest path exploring the predecessor tree from the 
        // tmpspath element. tmpspath at the first call is the target element. 
        // a vector element of type LinkedList is created for each shortest path found.
        // spathlist represent the shortestpath explored at the calling time. Initially is empty
        public static void createShortestPathVector(PescaShortestPathList spathlist, PescaMultiSPath tmpspath, Vector ShortestPathVector) {
           //set vectorsize equals to the size of the vector predecessor
           int vectorsize = tmpspath.PredecessorVectorSize();
           // add the element tmpspath to the spathlist representing the current shortestpath
           spathlist.addFirst(tmpspath);
           // if tmpspath has one or more predecessors we have not finished 
           if (vectorsize != 0) {
                // we create a copy of spathlist
                PescaShortestPathList tmplist = (PescaShortestPathList)spathlist.clone();
                for (int i=0; i < vectorsize; i++) {
                    // the first predecessor build the Shortestpath in the current element of the vector
                    if (i == 0) {
                        PescaMultiSPath newPescaMultiSPath = tmpspath.getPredecessor(i);
                        createShortestPathVector(spathlist, newPescaMultiSPath, ShortestPathVector);
                    }
                    else {
                        //if there are more predecessors we have to build a new vector element
                        //for each predecessor exceeding the first. This because each predecessor contributes 
                        //for a different shortest path
                        PescaMultiSPath newPescaMultiSPath = tmpspath.getPredecessor(i);
                        PescaShortestPathList newlist = (PescaShortestPathList)tmplist.clone();
                        ShortestPathVector.addElement(newlist);
                        createShortestPathVector(newlist, newPescaMultiSPath, ShortestPathVector);
                    }
                }
           }    
       }
         public static void initialize(HashSet TempSet, LinkedList Queue, CyNetwork network, CyNode root) {
        //create the root Shortest Path (cost=o, predecessor = null)
        PescaMultiSPath primospath = new PescaMultiSPath(root, 0, network);
        //initialize shortest path for each node
        // predecessor is null and shortestpath cost is the maximum
        //i.e. number of node plus 1. for root node cost is 0;
        int numberofnodes = network.getNodeCount();
        //add the root Shortest Path to the Queue
        Queue.add(primospath);
        //iterate on all the nodes of the view*/

        /*for (Iterator i = view.getNodeViewsIterator();i.hasNext(); ) {
        NodeView nView = (NodeView)i.next();
        CyNode currentnode = (CyNode)nView.getNode();*/
        // System.out.println("inizio inizialize");
        
      /*  for (Iterator i = network.getNodeList().listIterator(); i.hasNext();) {
            CyNode currentnode = (CyNode) i.next();
            // initialize all the node except of root
            if (!currentnode.equals(root)) {
                //        System.out.println("dentro inizialize " + " nodo= " + currentnode.getIdentifier());
                PescaMultiSPath currentSPath = new PescaMultiSPath(currentnode, numberofnodes + 1, network);
                TempSet.add(currentSPath);
            }
        }
    }

    // public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, LinkedList Queue, CyNetwork network,CyNetworkView view, CyNode root ) {
    public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, LinkedList Queue, CyNetwork network, CyNode root) {
        //Iterate the minimum path algorithm on the Queue list
        for (Iterator i = Queue.iterator(); i.hasNext();) {
            //get the first element of the Queue and add it to the set of the Shortest Path  
            PescaMultiSPath currentSPath = (PescaMultiSPath) Queue.remove(0);
            if (!currentSPath.getNode().equals(root)) {
                PathSet.add(currentSPath);
            }

            // get the neighbors of the selected PescaMultiSPath node
            List neighbors = network.getNeighborList(currentSPath.getNode(), CyEdge.Type.ANY);
            // and iterate over the neighbors
            for (Iterator ni = neighbors.iterator(); ni.hasNext();) {
                CyNode neighbor = (CyNode) ni.next();
                //relax the currentSPath with its neighbors

                relax(currentSPath, neighbor, TempSet, Queue);
            }
        }
    }
        */
        public static void initialize(HashSet TempSet, LinkedList Queue, CyNetwork network, CyNode root) {
        //create the root Shortest Path (cost=o, predecessor = null)
        PescaMultiSPath primospath = new PescaMultiSPath(root, 0, network);
        //initialize shortest path for each node
        // predecessor is null and shortestpath cost is the maximum
        //i.e. number of node plus 1. for root node cost is 0;
        int numberofnodes = network.getNodeCount();
        //add the root Shortest Path to the Queue
        Queue.add(primospath);
        //iterate on all the nodes of the view

        /*for (Iterator i = view.getNodeViewsIterator();i.hasNext(); ) {
        NodeView nView = (NodeView)i.next();
        CyNode currentnode = (CyNode)nView.getNode();*/
        // System.out.println("inizio inizialize");
        
        for (Iterator i = network.getNodeList().listIterator(); i.hasNext();) {
            CyNode currentnode = (CyNode) i.next();
            // initialize all the node except of root
            if (!currentnode.equals(root)) {
                //        System.out.println("dentro inizialize " + " nodo= " + currentnode.getIdentifier());
                PescaMultiSPath currentSPath = new PescaMultiSPath(currentnode, numberofnodes + 1, network);
                TempSet.add(currentSPath);
            }
        }
    }

    // public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, LinkedList Queue, CyNetwork network,CyNetworkView view, CyNode root ) {
    public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, LinkedList Queue, CyNetwork network, CyNode root) {
        //Iterate the minimum path algorithm on the Queue list
        for (Iterator i = Queue.iterator(); i.hasNext();) {
            //get the first element of the Queue and add it to the set of the Shortest Path  
            PescaMultiSPath currentSPath = (PescaMultiSPath) Queue.remove(0);
            if (!currentSPath.getNode().equals(root)) {
                PathSet.add(currentSPath);
            }

            // get the neighbors of the selected PescaMultiSPath node
            List neighbors = network.getNeighborList(currentSPath.getNode(), CyEdge.Type.ANY);
            // and iterate over the neighbors
            for (Iterator ni = neighbors.iterator(); ni.hasNext();) {
                CyNode neighbor = (CyNode) ni.next();
                //relax the currentSPath with its neighbors

                relax(currentSPath, neighbor, TempSet, Queue);
            }
        }
    }
     public static void relax(PescaMultiSPath CurrentSPath, CyNode NeighBor, HashSet TempSet, LinkedList Queue) {
        // verify if NeighBor is in the TempSet end put the corresponding PescaMultiSPath in NeighborSPAth
        PescaMultiSPath NeighborSPath = findSPath(NeighBor, TempSet);
        // if yes then add NeighborSPath to the Queue and set the cost and predecessor
        if (NeighborSPath != null) {
            //   int distance = getDistance(CurrentSPath.getNode(),NeighBor);
            NeighborSPath.setCost(CurrentSPath.getCost() + 1);
            NeighborSPath.addPredecessor(CurrentSPath);
            Queue.addLast(NeighborSPath);
            // then remove it from the TempSet
            TempSet.remove(NeighborSPath);
        } else {
            // if Neighbor is not in TempSet verify if it is in Queue
            NeighborSPath = findSPath(NeighBor, Queue);
            // if yes put it in NeighborSPath
            if (NeighborSPath != null) {
                // then verify if its cost is greater then the one of the current SPath
                //       int distance = getDistance(CurrentSPath.getNode(),NeighBor);
                if (NeighborSPath.getCost() > CurrentSPath.getCost()) {
                    // if yes we have found a new minimium shortestpath so we have another predecessor
                    // for neighbor. we update cost(useless) and we add the new predecessor
                    NeighborSPath.setCost(CurrentSPath.getCost() + 1);
                    NeighborSPath.addPredecessor(CurrentSPath);
                }
            }
        }
    }

    // Verify if Node is in TempSet and return the corresponding
    // PescaMultiSPath element
    public static PescaMultiSPath findSPath(CyNode Node, Collection TempSet) {
        PescaMultiSPath foundSPath = null;
        for (Iterator i = TempSet.iterator(); i.hasNext();) {
            PescaMultiSPath tempSPath = (PescaMultiSPath) i.next();
            if (Node.equals(tempSPath.getNode())) {
                foundSPath = tempSPath;
                break;
            }
        }
        return foundSPath;
    }

    // create the vector ShortestPathVector of the shortest path exploring the predecessor tree from the
    // tmpspath element. tmpspath at the first call is the target element.
    // a vector element of type LinkedList is created for each shortest path found.
    // spathlist represent the shortestpath explored at the calling time. Initially is empty
    public static void createShortestPathVector(PescaShortestPathList spathlist, PescaMultiSPath tmpspath, Vector ShortestPathVector) {
        //set vectorsize equals to the size of the vector predecessor
        int vectorsize = tmpspath.PredecessorVectorSize();
        // add the element tmpspath to the spathlist representing the current shortestpath
        spathlist.addFirst(tmpspath);
        // if tmpspath has one or more predecessors we have not finished
        if (vectorsize != 0) {
            // we create a copy of spathlist
            PescaShortestPathList tmplist = (PescaShortestPathList) spathlist.clone();
            for (int i = 0; i < vectorsize; i++) {
                // the first predecessor build the Shortestpath in the current element of the vector
                if (i == 0) {
                    PescaMultiSPath newMultiSPath = tmpspath.getPredecessor(i);
                 //   contachiamate++;
                 //   if (contachiamate % 100 == 0) {
                 //       System.out.println("contachiamate=" + contachiamate);
                 //   }
                    createShortestPathVector(spathlist, newMultiSPath, ShortestPathVector);
                } else {
                    //if there are more predecessors we have to build a new vector element
                    //for each predecessor exceeding the first. This because each predecessor contributes
                    //for a different shortest path
                    PescaMultiSPath newMultiSPath = tmpspath.getPredecessor(i);
                    PescaShortestPathList newlist = (PescaShortestPathList) tmplist.clone();
                    ShortestPathVector.addElement(newlist);
                  //  contachiamate++;
                  //  if (contachiamate % 100 == 0) {
                  //      System.out.println("contachiamate=" + contachiamate);
                  //  }
                    createShortestPathVector(newlist, newMultiSPath, ShortestPathVector);

                }
            }
        }
    }
    }


     