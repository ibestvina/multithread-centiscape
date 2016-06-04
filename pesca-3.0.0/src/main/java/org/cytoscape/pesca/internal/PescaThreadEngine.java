package org.cytoscape.pesca.internal;



/*
 * PescaThreadEngine.java
 *
 * Created on 20 marzo 2007, 14.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author scardoni
 */

import javax.swing.*;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
//import cytoscape.Cytoscape;
//import cytoscape.CyNetwork;
//import cytoscape.view.CyNetworkView;


public class PescaThreadEngine extends Thread {
    
    private PescaAlgorithm Pescaalg;
    CyNetwork network;
    CyNetworkView view;
    //private JFrame c;
    private JPanel c;
    /**
     * Creates a new instance of PescaThreadEngine
     */
    public PescaThreadEngine() {
    }
    
    /** Creates a new instance of ThreadEngine */
    public PescaThreadEngine(PescaAlgorithm Pescaalgorithm, CyNetwork currentnetwork, CyNetworkView currentnetworkview) {
        Pescaalg=Pescaalgorithm;
         network = currentnetwork;
          view = currentnetworkview; 
    }
    
    public void run(){
         //get the network object; this contains the graph
           // network = currentnetwork;
            //get the network view object
           // view = currentnetworkview; 
           //JOptionPane.showMessageDialog(view.getComponent(),
            //            "comincio2 = " );
            // inizializzo la pescanetwork
          //  PescaNetwork pescanetwork = new PescaNetwork(network.getNodeCount());
        Pescaalg.ExecutePescaAlgorithm(network, view, c);
        c.setEnabled(true);
    }
    
    public void setCaller(JPanel caller){
        c=caller;
    }

    public void endprogram() {
        Pescaalg.endalgorithm();
    }
    
}

    

