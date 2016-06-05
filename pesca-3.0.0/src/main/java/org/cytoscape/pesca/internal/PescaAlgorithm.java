/*
 * PescaAlgorithm.java
 *
 * Created on 7 marzo 2007, 10.01
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
import javax.swing.*;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
//import giny.view.NodeView;
//import cytoscape.data.CyAttributes;
//import cytoscape.*;
//import cytoscape.view.cytopanels.*;
//import cytoscape.view.*;

//import cytoscape.view.CytoscapeDesktop;
public class PescaAlgorithm {

    private boolean directed = false;
    private boolean weighted = false;
    private boolean stop = false;
    private boolean treeisOn = false;
    private boolean shortestPathisOn = false;
    private boolean spClusterisOn = false;
    private boolean spIsolatednodesisOn = false;
    private boolean spAllNodesIsOn = false;
    private String weightColumn = "";
    public CyNode source;
    static CyNetworkView vista;
    Vector vectorOfNodeAttributes = new Vector();
    Vector vectorOfNetworkAttributes = new Vector();
    //  public Vector<Centrality> VectorResults = new Vector();
    public boolean openResultPanel;
    public PescaCore pescacore;
    public PescaSPMap pescamap;
    public int totalnodecount;

    /**
     * Creates a new instance of PescaAlgorithm
     */
    public PescaAlgorithm(PescaCore pescacore) {
        this.pescacore = pescacore;
    }

    public void ExecutePescaAlgorithm(CyNetwork currentnetwork, CyNetworkView currentview, JPanel c) {

        openResultPanel = false;
        stop = false;
        PescaStartMenu menustart = (PescaStartMenu) c;
        // JOptionPane.showMessageDialog(view.getComponent(),
        //          "comincio1 = " );

        // System.out.println("execute algorithm view = " + view.getTitle() + "networkidentifier = " + network.getIdentifier());
        totalnodecount = currentnetwork.getNodeCount();

        //String networkidentifier = network.getIdentifier();
        int nodeworked = 0;
        Vector PescaMultiShortestPathVector = null;
        Vector SPdistributionVector;

        //CyAttributes currentNetworkAttributes = Cytoscape.getNetworkAttributes();
        //CyAttributes currentNodeAttributes = Cytoscape.getNodeAttributes();
        List<CyNode> rootlist = CyTableUtil.getNodesInState(currentnetwork, "selected", true);

        //  pescaSPmap = new PescaSPMap();
        CyNode root = null;

        CyNode target = null;
        if (rootlist.size() >= 1) {
            root = (CyNode) rootlist.get(0);
        }

        //  System.out.println("nodo root è " + root.getSUID());
        //  System.out.println("current network è " + currentnetwork.getSUID());
        //  System.out.println("current networkview è " + currentview.getSUID());
        if (rootlist.size() == 2) {
            target = (CyNode) rootlist.get(1);

        }

        // Execute the multi shortest path algorithm for node root and put the results on the
        // vector called ShortestPathVector
        if (treeisOn) {

            openResultPanel = true;
            root = (CyNode) rootlist.get(0);
            PescaMultiShortestPathVector = new Vector();
            Vector tmpPescaMultiShortestPathVector = new Vector();

            tmpPescaMultiShortestPathVector = PescaMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathTreeAlgorithm(currentnetwork, root, directed, weighted, weightColumn);

            if (tmpPescaMultiShortestPathVector != null) {
                PescaMultiShortestPathVector = tmpPescaMultiShortestPathVector;
            }

            pescamap = shortestPathsDistribution(tmpPescaMultiShortestPathVector);
            // System.out.println(PescaMultiShortestPathVector.toString());
            //  pescacore.createPescaResultPanel(PescaMultiShortestPathVector);

        }

        if (spIsolatednodesisOn) {

            openResultPanel = true;
            PescaMultiShortestPathVector = new Vector();
            Vector SingleSPVector = new Vector();
            Vector tmpPescaMultiShortestPathVector = new Vector();
            SPdistributionVector = new Vector();

            // List<CyNode> rootlist2 = CyTableUtil.getNodesInState(currentnetwork, "selected", true);
            List<CyNode> rootlist2 = menustart.GiantComponent;
            source = menustart.NodestoConnect.get(0);
            System.out.println(rootlist2);

            int size = 100000;
            for (int i = 0; i < rootlist2.size(); i++) {
                System.out.println("i= " + i + "size = " + rootlist2.size());
                target = (CyNode) rootlist2.get(i);
                System.out.println("target = " + target.toString());
                if (target != source) {
                    tmpPescaMultiShortestPathVector = PescaMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathSourceTargetAlgorithm(currentnetwork, source, target, directed, weighted, weightColumn);
                    if (tmpPescaMultiShortestPathVector != null) {
                        PescaShortestPathList p = (PescaShortestPathList) tmpPescaMultiShortestPathVector.firstElement();

                        if (p.size() == size) {
                            System.out.println("same");
                            SingleSPVector.add(tmpPescaMultiShortestPathVector.firstElement());
                            PescaMultiShortestPathVector.addAll(tmpPescaMultiShortestPathVector);
                            size = p.size();
                        } else if (p.size() < size) {
                            System.out.println("lesser");
                            SingleSPVector.removeAllElements();
                            PescaMultiShortestPathVector.removeAllElements();
                            SingleSPVector.add(tmpPescaMultiShortestPathVector.firstElement());
                            PescaMultiShortestPathVector.addAll(tmpPescaMultiShortestPathVector);
                            size = p.size();
                        }

                    }
                }

            }
            pescamap = shortestPathsDistribution(SingleSPVector);
        }

        if (shortestPathisOn) {
            openResultPanel = true;

            CyNode source = (CyNode) rootlist.get(0);
            target = (CyNode) rootlist.get(1);
            Vector tmpPescaMultiShortestPathVector = new Vector();

            PescaMultiShortestPathVector = new Vector();

            tmpPescaMultiShortestPathVector = PescaMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathSourceTargetAlgorithm(currentnetwork, source, target, directed, weighted, weightColumn);

            if (tmpPescaMultiShortestPathVector != null) {
                PescaMultiShortestPathVector = tmpPescaMultiShortestPathVector;
            }

            //  System.out.println(PescaMultiShortestPathVector.toString());
            //   pescacore.createPescaResultPanel(PescaMultiShortestPathVector);
            pescamap = shortestPathsDistribution(PescaMultiShortestPathVector);
        }
        // Calculate each properties

        if (spClusterisOn) {
            openResultPanel = true;
            PescaMultiShortestPathVector = new Vector();
            Vector SingleSPVector = new Vector();
            Vector tmpPescaMultiShortestPathVector = new Vector();
            SPdistributionVector = new Vector();

            
            for (int i = 0; i < rootlist.size(); i++) {
                for (int j = 0; j < rootlist.size(); j++) {
                    if (i != j) {

                        CyNode source = (CyNode) rootlist.get(i);
                        target = (CyNode) rootlist.get(j);
                        tmpPescaMultiShortestPathVector = PescaMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathSourceTargetAlgorithm(currentnetwork, source, target, directed, weighted, weightColumn);

                        if (tmpPescaMultiShortestPathVector != null) {
                            SingleSPVector.add(tmpPescaMultiShortestPathVector.firstElement());
                            PescaMultiShortestPathVector.addAll(tmpPescaMultiShortestPathVector);
                        }
                    }
                }
            }
            pescamap = shortestPathsDistribution(SingleSPVector);
            //  System.out.println("la pesca map dopo la chiamataaaa = " + pescamap.toString());
        }

        if (spAllNodesIsOn) {
            openResultPanel = true;
            PescaMultiShortestPathVector = new Vector();
            Vector SingleSPVector = new Vector();
            Vector tmpPescaMultiShortestPathVector = new Vector();
            SPdistributionVector = new Vector();
            List<CyNode> allNodes = currentnetwork.getNodeList();
            for (int i = 0; i < allNodes.size(); i++) {

                tmpPescaMultiShortestPathVector = PescaMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathTreeAlgorithm(currentnetwork, allNodes.get(i), directed, weighted, weightColumn);
                if (tmpPescaMultiShortestPathVector != null && tmpPescaMultiShortestPathVector.size() >= 1) {
                    SingleSPVector.addAll(tmpPescaMultiShortestPathVector);
                    PescaMultiShortestPathVector.addAll(tmpPescaMultiShortestPathVector);
                }

            }
            pescamap = shortestPathsDistribution(SingleSPVector);
            //  System.out.println("la pesca map dopo la chiamataaaa = " + pescamap.toString());
        }

        if (openResultPanel) {
            //  System.out.println(PescaMultiShortestPathVector.toString());
            String resulttype = null;

            // network.unselectAllNodes();
            menustart.endcalculus(totalnodecount);
            //        VectorResults.clear();

            //      Visualizer visualizer = PescaCore.windows.getvisualizer();
            //      visualizer.setEnabled(VectorResults);
            //    CytoscapeDesktop desktop = Cytoscape.getDesktop();
            //    CytoPanel cytoPaneleast = desktop.getCytoPanel(SwingConstants.EAST);
            //cytoPaneleast.removeAll();
            //    int index = cytoPaneleast.indexOfComponent(visualizer);
            //    cytoPaneleast.setSelectedIndex(index);
            //  cytoPaneleast.add("Pesca Results", visualizer);
            if (treeisOn) {
                resulttype = root.getSUID() + " SPtree";

                // cytoPaneleast.add(root.getIdentifier() + " SPtree", prova);
                // openResultPanel = true;
            }

            if (spIsolatednodesisOn) {
                resulttype = root.getSUID() + " isolatednodes";
                // ResultPanel prova = new ResultPanel(PescaMultiShortestPathVector);
                //  prova.add(mainwindow);
                // resultframe prova2 = new resultframe();
                // prova2.add(prova);

                // cytoPaneleast.add(root.getIdentifier() + " isolatednodes", prova);
                // openResultPanel = true;
            }

            if (shortestPathisOn) {
                resulttype = root.getSUID() + "-" + target.getSUID() + " SP";
                //  ShortestPathResultPanel prova = new ShortestPathResultPanel();
                //  prova.changelist(PescaMultiShortestPathVector);
                //  cytoPaneleast.add(root.getIdentifier() + "-" + target.getIdentifier() + " SP", prova);
                // openResultPanel = true;
            }

            if (spClusterisOn) {
                // ShortestPathResultPanel prova = new ShortestPathResultPanel();
                resulttype = "SPCluster";
                //    ResultPanel prova = new ResultPanel(PescaMultiShortestPathVector);
                // prova.changelist(PescaMultiShortestPathVector);
                //   cytoPaneleast.add(" SPCluster", prova);
                // openResultPanel = true;
            }
            if (spAllNodesIsOn) {
                resulttype = "All Nodes SP";

                // cytoPaneleast.add(root.getIdentifier() + " SPtree", prova);
                // openResultPanel = true;
            }
            //  System.out.println("scrivo la pesca map 1" + pescamap.toString());
            pescacore.createPescaResultPanel(PescaMultiShortestPathVector, resulttype, pescamap);

        }
    }

    public void endalgorithm() {
        stop = true;
    }

    public void setChecked(boolean[] ison, boolean directed, boolean weighted) {
        String columnName = "";

        // Diameter = checkbox 0
        treeisOn = ison[0];
        //     DiameterisSelected = ison[0];
        // Average-Distance = checkbox 1
        shortestPathisOn = ison[1];
        // Degree = checkbox 2
        spClusterisOn = ison[2];
        spIsolatednodesisOn = ison[3];
        spAllNodesIsOn = ison[4];
        this.directed = directed;
        this.weighted = weighted;

        if (weighted) {

            columnName = JOptionPane.showInputDialog("Please input name of the Edge Table column for weights : ");
            this.weightColumn = columnName;
        }

        System.out.println(columnName);

    }

    public PescaSPMap shortestPathsDistribution(Vector PescaMultiShortestPathVector) {
        //  System.out.println("stampo risultati singolo vettore");
        PescaSPMap pescaspmap = new PescaSPMap();

        int paths = 0;
        if (spAllNodesIsOn) {
            paths = totalnodecount * (totalnodecount - 1);

        }

        if (treeisOn) {
            paths = totalnodecount - 1;

        }

        pescaspmap.setExpectedPaths(paths);
        for (Iterator i = PescaMultiShortestPathVector.iterator(); i.hasNext();) {
            PescaShortestPathList currentpath = (PescaShortestPathList) i.next();

            //     System.out.println("il currentpath = " + currentpath.toString());
            int currentsize = currentpath.size() - 1;

            pescaspmap.update(new Integer(currentsize), currentpath.getSourceDest());
        }
        System.out.println(" size |  number of sp \n" + pescaspmap.toString());
        //    CyTable networkTable = pescacore.network.getDefaultNetworkTable();
        //      networkTable.createColumn("SP-Distribution", Long.class, false);
        return pescaspmap;
    }
    /*
     public double CalculateDiameter(Vector SingleShortestPathVector) {
     PescaShortestPathList currentdiameterlist;
     int currentmaxvalue = 0;
     double currentvalue = 0;
     for (int j = 0; j < SingleShortestPathVector.size(); j++) {
     currentdiameterlist = (PescaShortestPathList) SingleShortestPathVector.elementAt(j);
     //    currentRadiality.updatesizevector(new Integer(currentlist.size()-1));
     currentmaxvalue = Math.max(currentmaxvalue, currentdiameterlist.size() - 1);
     }
     if (currentmaxvalue > currentvalue) {
     currentvalue = ((double) currentmaxvalue);
     //   JOptionPane.showMessageDialog(view.getComponent(),
     //   "diametro current" + currentDiametervalue);
     }
     return currentvalue;
     }*/
}
