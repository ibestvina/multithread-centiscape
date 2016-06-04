package org.cytoscape.pesca.internal;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;

public class PescaCore {

    public CyNetwork network;
    public CyNetworkView view;
    
    public CyApplicationManager cyApplicationManager;
    public CySwingApplication cyDesktopService;
    public CyServiceRegistrar cyServiceRegistrar;
    public CyActivator cyactivator;
   
   
    public CyApplicationManager applicationmanager;
    private CySwingApplication desktopApp;
  
	private final CytoPanel cytoPanelWest;
	//private MyCytoPanel myCytoPanel;
    public static PescaWindows windows;
    public PescaStartMenu pescastartmenu;
    public ResultPanel resultpanel;
     public ArrayList resultpanellist;
  //  public PescaListener listener;
    
    

    
public PescaCore(CyActivator cyactivator) {
//public PescaCore(CySwingApplication cytoscapeDesktopService, CyApplicationManager applicationManager, PescaStartMenu pescastartmenu) {
	
   
    
    //        updatecurrentnetwork();
    this.cyactivator = cyactivator;
     this.cyServiceRegistrar = cyactivator.getcyServiceRegistrar();
       this.cyDesktopService = cyactivator.getcytoscapeDesktopService();
	pescastartmenu = createPescaStartMenu();
        //new PescaStartMenu(applicationManager, cytoscapeDesktopService);
//	System.out.println("entrato in pesca core1");
	this.applicationmanager = cyactivator.getcyApplicationManager();
	view = applicationmanager.getCurrentNetworkView();
	network = applicationmanager.getCurrentNetwork();     
        resultpanellist = new ArrayList();
        
	//System.out.println("entrato in pesca core1");
	
	//System.out.println("entrato in pesca core2");
          //  System.out.println("la view e' jjjj" +  network.getRow(network).get("name", String.class) + " primo identifier = " );
          //  System.out.println("fatto cytopanel context1");
      //     windows = new PescaWindows(pescastartmenu);

        desktopApp= cyDesktopService;
     //    System.out.println("fatto cytopanel context3");
            this.cytoPanelWest = this.desktopApp.getCytoPanel(CytoPanelName.WEST);
    		
     //       CytoscapeDesktop desktop = Cytoscape.getDesktop();
     //       CytoPanel cytoPanel = desktop.getCytoPanel(SwingConstants.WEST);
     //       cytoPanel.add("Pesca", windows.getstartmenu());
     //       cytoPanel.setState(CytoPanelState.DOCK);
    	//	System.out.println("fatto cytopanel context2");
    		if (cytoPanelWest.getState() == CytoPanelState.HIDE) {
    			cytoPanelWest.setState(CytoPanelState.DOCK);
    		}	
                
    		// Select my panel
    		int index = cytoPanelWest.indexOfComponent(pescastartmenu);
    		if (index == -1) {
    			return;
    		}
    		cytoPanelWest.setSelectedIndex(index);
    		
            // set focus to this component
     //       int index = cytoPanel.indexOfComponent(windows.getstartmenu());
     //       cytoPanel.setSelectedIndex(index);
           
     //       listener = new PescaListener(this);
            //    System.out.println("fatto tutto");
           
        }

public PescaStartMenu createPescaStartMenu() {
      //  System.out.println("create1");
        PescaStartMenu startmenu = new PescaStartMenu(applicationmanager, cyDesktopService, cyactivator, this);
      //  System.out.println("create2");
        cyServiceRegistrar.registerService(startmenu, CytoPanelComponent.class, new Properties());
      //  System.out.println("create3");
        CytoPanel cytopanelwest = cyDesktopService.getCytoPanel(CytoPanelName.WEST);
        int index = cytopanelwest.indexOfComponent(startmenu);
        cytopanelwest.setSelectedIndex(index);
        return startmenu;
    }

 public ResultPanel createPescaResultPanel(Vector spvector, String resulttype, PescaSPMap pescaSPmap) {
  //      System.out.println("create1");
     network = applicationmanager.getCurrentNetwork();   
     view = applicationmanager.getCurrentNetworkView();
        resultpanel = new ResultPanel(spvector, resulttype, this, pescaSPmap, network,view);
        //(cyactivator,this);
    //    System.out.println("create2");
        cyServiceRegistrar.registerService(resultpanel, CytoPanelComponent.class, new Properties());
    //    System.out.println("create3");
        CytoPanel cytopaneleast = cyDesktopService.getCytoPanel(CytoPanelName.EAST);
        cytopaneleast.setState(CytoPanelState.DOCK);
        int index = cytopaneleast.indexOfComponent(resultpanel);
        cytopaneleast.setSelectedIndex(index);
        resultpanellist.add(resultpanel);
        return resultpanel;
    }

    public void closePescavisualizer() {
        for (Iterator i = resultpanellist.listIterator(); i.hasNext();) {
            ResultPanel currentPanel = (ResultPanel) i.next();    
 
        cyServiceRegistrar.unregisterService(currentPanel, CytoPanelComponent.class);
        }
    }
    
    public void closeCurrentResultPanel(ResultPanel currentresultpanel) {
        cyServiceRegistrar.unregisterService(currentresultpanel, CytoPanelComponent.class);
    }
    
     public void closePescaStartMenu() {

        cyServiceRegistrar.unregisterService(pescastartmenu, CytoPanelComponent.class);
    }

}

    


/*public void updatecurrentnetwork() {
    
            //get the network view object
            view = Cytoscape.getCurrentNetworkView();
            //get the network object; this contains the graph  
            network = Cytoscape.getCurrentNetwork();
        } 


public void hideresults() {
    
            CytoscapeDesktop desktop = Cytoscape.getDesktop();
            CytoPanel cytoPaneleast = desktop.getCytoPanel(SwingConstants.EAST);
            cytoPaneleast.setState(CytoPanelState.HIDE);
           
}

public void showresults() {
    
            CytoscapeDesktop desktop = Cytoscape.getDesktop();
            CytoPanel cytoPaneleast = desktop.getCytoPanel(SwingConstants.EAST);
            cytoPaneleast.setState(CytoPanelState.DOCK);
    
}

 public void hideattributes() {
      CytoscapeDesktop desktop = Cytoscape.getDesktop();
            CytoPanel cytoPanelsouth = desktop.getCytoPanel(SwingConstants.SOUTH);
             cytoPanelsouth.setState(CytoPanelState.HIDE);
            cytoPanelsouth.setState(CytoPanelState.DOCK);
 }


public void removecurrentnetworkattributes() {
        
       PescaStartMenu startmenu = windows.getstartmenu();
       startmenu.removeattributes();
} */


