package org.cytoscape.pesca.internal;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.view.model.CyNetworkView;


/**
 * Creates a new menu item under Apps menu section.
 *
 */
public class MenuAction extends AbstractCyAction {

	private  CyApplicationManager applicationManager;
	public  CySwingApplication cytoscapeDesktopService;
         public CyActivator cyactivator;
        //public PescaStartMenu pescastartmenu;
 public CyNetworkView currentNetworkView ;
	public MenuAction(final String menuTitle, CyActivator cyactivator) {
	//public MenuAction(CySwingApplication cytoscapeDesktopService, final CyApplicationManager applicationManager, final String menuTitle, PescaStartMenu pescastartmenu) {
		super(menuTitle, cyactivator.getcyApplicationManager(), null, null);
		
		//this.cytoscapeDesktopService = cytoscapeDesktopService;
                this.cyactivator = cyactivator;
                this.cytoscapeDesktopService = cyactivator.getcytoscapeDesktopService();
                this.applicationManager = cyactivator.getcyApplicationManager();
		//this.pescastartmenu = pescastartmenu;
		setPreferredMenu("Apps");
	}
	
	
	public void actionPerformed(ActionEvent e) {

	  currentNetworkView = applicationManager.getCurrentNetworkView();
	    System.out.println("ciao345467 hello!3");
	   /* if (currentNetworkView == null)
	       return;
	    
	    // View is always associated with its model.
	    final CyNetwork network = currentNetworkView.getModel();
	    for (CyNode node : network.getNodeList()) {

	        if (network.getNeighborList(node,CyEdge.Type.ANY).isEmpty()) {
	        	currentNetworkView.getNodeView(node).setVisualProperty(BasicVisualLexicon.NODE_VISIBLE, false);	        	
	        }
	    }
	    currentNetworkView.updateView();*/
	    PescaCore pescacore = new PescaCore(cyactivator);
	 System.out.println("ciao3454678");
        }
	
}



