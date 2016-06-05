/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.centiscape.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.centiscape.internal.visualizer.CentVisualizer;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;

/**
 *
 * @author scardoni
 */
public class CentiScaPeCore {

    public CyNetwork network;
    public CyNetworkView view;
    public CyApplicationManager cyApplicationManager;
    public CySwingApplication cyDesktopService;
    public CyServiceRegistrar cyServiceRegistrar;
    public CyActivator cyactivator;
    public CentiScaPeStartMenu centiscapestartmenu;
    public CentVisualizer centvisualizer;
    public ArrayList visualizerlist;
    

    public CentiScaPeCore(CyActivator cyactivator) {
        this.cyactivator = cyactivator;
        this.cyApplicationManager = cyactivator.getApplicationManager();
        this.cyDesktopService = cyactivator.getDesktopService(); 
        this.cyServiceRegistrar = cyactivator.getServiceRegistrar();
        centiscapestartmenu = createCentiScaPeStartMenu();
        visualizerlist = new ArrayList();
        updatecurrentnetwork();
    }

    public void updatecurrentnetwork() {

        //get the network view object
        if (view == null) {
            view = null;
            network = null;
        } else {
            view = cyApplicationManager.getCurrentNetworkView();
            //get the network object; this contains the graph  
            network = view.getModel();
        }

    }
    

    public void closecore() {
        network = null;
        view = null;

    }

 

    public CentiScaPeStartMenu createCentiScaPeStartMenu() {
        CentiScaPeStartMenu startmenu = new CentiScaPeStartMenu(cyactivator, this);
        cyServiceRegistrar.registerService(startmenu, CytoPanelComponent.class, new Properties());
        CytoPanel cytopanelwest = cyDesktopService.getCytoPanel(CytoPanelName.WEST);
        int index = cytopanelwest.indexOfComponent(startmenu);
        cytopanelwest.setSelectedIndex(index);
        return startmenu;
    }

    public void closeCentiscapeStartMenu() {

        cyServiceRegistrar.unregisterService(centiscapestartmenu, CytoPanelComponent.class);
    }

    
    public CentVisualizer createCentiScaPeVisualizer() {
       // System.out.println("create1");
        centvisualizer = new CentVisualizer(cyApplicationManager,this);//(cyactivator,this);
      //  System.out.println("create2");
        cyServiceRegistrar.registerService(centvisualizer, CytoPanelComponent.class, new Properties());
      //  System.out.println("create3");
        CytoPanel cytopaneleast = cyDesktopService.getCytoPanel(CytoPanelName.EAST);
        cytopaneleast.setState(CytoPanelState.DOCK);
        int index = cytopaneleast.indexOfComponent(centvisualizer);
        cytopaneleast.setSelectedIndex(index);
        visualizerlist.add(centvisualizer);
      //  System.out.println("create4");
        return centvisualizer;
    }

    public void closeCentiscapevisualizer() {
        for (Iterator i = visualizerlist.listIterator(); i.hasNext();) {
            CentVisualizer currentvisualizer = (CentVisualizer) i.next();    
 
        cyServiceRegistrar.unregisterService(currentvisualizer, CytoPanelComponent.class);
        }
    }

    public CentVisualizer getvisualizer() {
        return centvisualizer;
    }
    
    public    CyApplicationManager getCyApplicationManager()
    {
        return this.cyApplicationManager;
    }
     public CySwingApplication getCyDesktopService(){
         return this.cyDesktopService;
     }
     
     public void closeCurrentResultPanel(CentVisualizer currentresultpanel) {
        cyServiceRegistrar.unregisterService(currentresultpanel, CytoPanelComponent.class);
    }
}
