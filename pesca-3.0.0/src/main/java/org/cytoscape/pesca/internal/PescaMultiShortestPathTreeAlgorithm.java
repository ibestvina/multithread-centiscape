package org.cytoscape.pesca.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyEdge.Type;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * PescaMultiShortestPathTreeAlgorithm.java
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
public class PescaMultiShortestPathTreeAlgorithm {

    static int contachiamate = 0;
    static CyNetwork currentnetwork;
    static LinkedList targetlist;
    //   public static HashSet PathSet;

    /**
     * Creates a new instance of PescaMultiShortestPathTreeAlgorithm
     */
    public PescaMultiShortestPathTreeAlgorithm() {
    }

    public static Vector ExecuteMultiShortestPathTreeAlgorithm(CyNetwork network, CyNode root, boolean directed, boolean weighted, String weightColumn) {

        currentnetwork = network;
        //Declaration of set and list used in the algorithm 
        //PathSet is the set of the shortest path
        HashSet PathSet;
        //TempSet is the set of temporary Shortest path
        HashSet TempSet;
        //Queue is the list of the temporary shortest path
        PriorityQueue Queue;

        //The vector of the final results
        Vector ShortestPathVector = new Vector();

        // Create the set of the Shortest Path
        PathSet = new HashSet();
        //create the set of temporary Shortest path
        TempSet = new HashSet();
        //create the list of the temporary shortest path
        Queue = new PriorityQueue<PescaMultiSPath>();
        //initialize shortest path Queue and Tempset
        //initialize(TempSet,Queue,network,view,root);
        initialize(TempSet, Queue, network, root);
        // Start the Core of the multi shortestpath algorithm
        //ShortestPathCore(PathSet,TempSet,Queue,network,view, root);
        //      System.out.println("entro spcore");
        ShortestPathCore(PathSet, TempSet, Queue, network, root, directed, weighted, weightColumn);
        //        System.out.println("esco spcore");
        //now go through our PathSet and select the PescaMultiSPath corresponding to the target node

        boolean result = false;
        for (Iterator i = PathSet.iterator(); i.hasNext();) {
            PescaMultiSPath tmpspath = (PescaMultiSPath) i.next();

            PescaShortestPathList prova = new PescaShortestPathList();
            ShortestPathVector.addElement(prova);
            createShortestPathVector(prova, tmpspath, ShortestPathVector);
            result = true;

        }
        //System.out.println("ritorno spvector" +  ShortestPathVector.get(1).toString());
        return ShortestPathVector;
    }

    public static Vector ExecuteMultiShortestPathSourceTargetAlgorithm(CyNetwork network, CyNode root, CyNode target, boolean directed, boolean weighted, String weightColumn) {

        currentnetwork = network;
        //Declaration of set and list used in the algorithm 
        //PathSet is the set of the shortest path
        HashSet PathSet;
        //TempSet is the set of temporary Shortest path
        HashSet TempSet;
        //Queue is the list of the temporary shortest path
        PriorityQueue Queue;

        //The vector of the final results
        Vector ShortestPathVector = new Vector();

        // Create the set of the Shortest Path
        PathSet = new HashSet();
        //create the set of temporary Shortest path
        TempSet = new HashSet();
        //create the list of the temporary shortest path
        Queue = new PriorityQueue<PescaMultiSPath>();
        //initialize shortest path Queue and Tempset
        //initialize(TempSet,Queue,network,view,root);
        initialize(TempSet, Queue, network, root);
        // Start the Core of the multi shortestpath algorithm
        //ShortestPathCore(PathSet,TempSet,Queue,network,view, root);
        //System.out.println("entro spcore");
        ShortestPathCore(PathSet, TempSet, Queue, network, root, directed, weighted, weightColumn);
        //System.out.println("esco spcore");

        CyNode currenttarget = target;
        boolean result = false;
        for (Iterator i = PathSet.iterator(); i.hasNext();) {
            PescaMultiSPath tmpspath = (PescaMultiSPath) i.next();
            // System.out.println("percorro il pathseth "  +  PathSet.size()) ;
            if (tmpspath.getNode().equals(currenttarget)) {
                //r select each nodeview belonging to the Shortest Path found in order 
                // to visualize them
                PescaShortestPathList prova = new PescaShortestPathList();
                ShortestPathVector.addElement(prova);
                createShortestPathVector(prova, tmpspath, ShortestPathVector);
                result = true;
                break;
            }
        }
        if (!result) {
            System.out.println("ritorno null");
            /* 
             JOptionPane.showMessageDialog(view.getComponent(), " The nodes are not connected. No Shortest Path found ");*/
            return null;
        } //tell the view to redraw since we've changed the selection         
        // view.redrawGraph(false, true);
        else {
            String vettorestringa;
            for (int i = 0; i < ShortestPathVector.size(); i++) {
                vettorestringa = ShortestPathVector.elementAt(i).toString();
                // JOptionPane.showMessageDialog(view.getComponent(),
                //         "Il vettore e' " + vettorestringa);
            }
            // ResultWindow prova = new ResultWindow();
            // prova.changelist(ShortestPathVector);
            // prova.setVisible(true);
            //  System.out.println("ritorno " + ShortestPathVector.toString());
            return ShortestPathVector;
        }

    }

    //initialize shortest path
    public static void initialize(HashSet TempSet, PriorityQueue Queue, CyNetwork network, CyNode root) {
        //create the root Shortest Path (cost=o, predecessor = null)
        PescaMultiSPath primospath = new PescaMultiSPath(root, 0, network);
        //initialize shortest path for each node
        // predecessor is null and shortestpath cost is the maximum
        //i.e. number of node plus 1. for root node cost is 0;
        int numberofnodes = network.getNodeCount();
        //add the root Shortest Path to the Queue
        Queue.add(primospath);
        //iterate on all the nodes of the view

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

    public static void ShortestPathCore(HashSet PathSet, HashSet TempSet, PriorityQueue Queue, CyNetwork network, CyNode root, boolean directed, boolean weighted, String weightColumn) {
        //Iterate the minimum path algorithm on the Queue list
        int adjSize = 1;
        Type type = CyEdge.Type.ANY;

        while(!Queue.isEmpty()){

            PescaMultiSPath currentSPath = (PescaMultiSPath) Queue.poll();
            if (!currentSPath.getNode().equals(root)) {
                PathSet.add(currentSPath);
            }

            // get the neighbors of the selected PescaMultiSPath node
            List neighbors;
            if (directed == true) {
                neighbors = network.getNeighborList(currentSPath.getNode(), CyEdge.Type.OUTGOING);
            } else {
                neighbors = network.getNeighborList(currentSPath.getNode(), CyEdge.Type.ANY);
            }
            // and iterate over the neighbors
            for (Iterator ni = neighbors.iterator(); ni.hasNext();) {
                CyNode neighbor = (CyNode) ni.next();
                //relax the currentSPath with its neighbors
                if (weighted) {
                    CyEdge e = network.getConnectingEdgeList(currentSPath.getNode(), neighbor, type).get(0);
                    CyRow r1 = network.getDefaultEdgeTable().getRow(e.getSUID());
                    adjSize = r1.get(weightColumn, Integer.class);
                }
                relax(currentSPath, neighbor, TempSet, Queue, adjSize);
            }
        }
    }

    public static void relax(PescaMultiSPath CurrentSPath, CyNode NeighBor, HashSet TempSet, PriorityQueue Queue, int adjSize) {
        // verify if NeighBor is in the TempSet end put the corresponding PescaMultiSPath in NeighborSPAth
        //System.out.println(adjSize);
        //System.out.println(NeighBor);

        PescaMultiSPath NeighborSPath = findSPath(NeighBor, TempSet);

        // if yes then add NeighborSPath to the Queue and set the cost and predecessor
        if (NeighborSPath != null) {
            //   int distance = getDistance(CurrentSPath.getNode(),NeighBor);
            NeighborSPath.setCost(CurrentSPath.getCost() + adjSize);
            NeighborSPath.addPredecessor(CurrentSPath);
            Queue.add(NeighborSPath);
            // then remove it from the TempSet
            TempSet.remove(NeighborSPath);
            //System.out.println("1: " + NeighborSPath.getCost());
        } else {
            // if Neighbor is not in TempSet verify if it is in Queue
            NeighborSPath = findSPath(NeighBor, Queue);
            // if yes put it in NeighborSPath
            if (NeighborSPath != null) {
                //System.out.println("--" + adjSize + "->" + CurrentSPath.getCost() + "hereee: " + NeighborSPath.getCost());

                // then verify if its cost is greater then the one of the current SPath
                //       int distance = getDistance(CurrentSPath.getNode(),NeighBor);
                if (NeighborSPath.getCost() == CurrentSPath.getCost() + adjSize) {
                    // if yes we have found a new minimium shortestpath so we have another predecessor
                    // for neighbor. we update cost(useless) and we add the new predecessor
                    NeighborSPath.setCost(CurrentSPath.getCost() + adjSize);
                    NeighborSPath.addPredecessor(CurrentSPath);
                    //System.out.println("2: " + NeighborSPath.getCost());

                } else if (NeighborSPath.getCost() > CurrentSPath.getCost() + adjSize) {
                    // if yes we have found a new minimium shortestpath so we have another predecessor
                    // for neighbor. we update cost(useless) and we add the new predecessor

                    NeighborSPath.removeAllPredecessors();
                    NeighborSPath.setCost(CurrentSPath.getCost() + adjSize);

                    NeighborSPath.addPredecessor(CurrentSPath);
                    //System.out.println("3: " + NeighborSPath.getCost());

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
                //System.out.println("found:" + Node);
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
                    contachiamate++;
                    if (contachiamate % 100 == 0) {
                        //     System.out.println("contachiamate=" + contachiamate);
                    }
                    createShortestPathVector(spathlist, newMultiSPath, ShortestPathVector);
                } else {
                    //if there are more predecessors we have to build a new vector element
                    //for each predecessor exceeding the first. This because each predecessor contributes
                    //for a different shortest path
                    PescaMultiSPath newMultiSPath = tmpspath.getPredecessor(i);
                    PescaShortestPathList newlist = (PescaShortestPathList) tmplist.clone();
                    ShortestPathVector.addElement(newlist);
                    contachiamate++;
                    if (contachiamate % 100 == 0) {
                        //System.out.println("contachiamate=" + contachiamate);
                    }
                    createShortestPathVector(newlist, newMultiSPath, ShortestPathVector);

                }
            }
        }
    }
    

}
