//package org.cytoscape.pesca.internal;
package org.cytoscape.pesca.internal;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.read.LoadNetworkURLTaskFactory;
import org.cytoscape.task.read.OpenSessionTaskFactory;
import org.cytoscape.work.TaskManager;
//import org.cytoscape.application.CyApplicationManager;
//import org.cytoscape.application.swing.CySwingApplication;
//import org.cytoscape.application.swing.CytoPanelComponent;
//import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {
 public CyApplicationManager cyApplicationManager;
    public CySwingApplication cyDesktopService;
    public CyServiceRegistrar cyServiceRegistrar;
    public 	LoadNetworkURLTaskFactory networkURLLoader;
	public 	OpenSessionTaskFactory sessionLoader;
 	public TaskManager <?,?> taskManager;
    public    RowsSetListener myrowssetlistener; 

    
	@Override
	public void start(BundleContext context) throws Exception {
		
		
		CySwingApplication cytoscapeDesktopService = getService(context,CySwingApplication.class);
		             
		 this.cyApplicationManager = getService(context, CyApplicationManager.class);
        this.cyDesktopService = getService(context, CySwingApplication.class);
        this.cyServiceRegistrar = getService(context, CyServiceRegistrar.class);
    	this.networkURLLoader = getService(context, LoadNetworkURLTaskFactory.class);
    	this.sessionLoader = getService(context, OpenSessionTaskFactory.class);
		this.taskManager = getService(context, TaskManager.class);
                this.myrowssetlistener = getService(context, RowsSetListener.class);
                
             
		//PescaStartMenu pescastartmenu = new PescaStartMenu(cyApplicationManager, cytoscapeDesktopService);
		//Sample02 sample02Action = new Sample02(cytoscapeDesktopService,myCytoPanel);
		
		//registerService(context,pescastartmenu,CytoPanelComponent.class, new Properties());
		//registerService(bc,sample02Action,CyAction.class, new Properties());

                
                
                
                
		MenuAction action = new MenuAction( "Pesca3.0.8", this);
		
		Properties properties = new Properties();
                
             
		
		registerAllServices(context, action, properties);
	}
        
        
        
        public RowsSetListener getRowsSetListener() {
            return myrowssetlistener;
        }
        
         public CyServiceRegistrar getcyServiceRegistrar() {
        return cyServiceRegistrar;
    }

    public CyApplicationManager getcyApplicationManager() {
        return cyApplicationManager;
    }

    public CySwingApplication getcytoscapeDesktopService() {
        return cyDesktopService;
    }

    public LoadNetworkURLTaskFactory getNetworkURLLoader() {
		return networkURLLoader;
	}

    public OpenSessionTaskFactory getOpenSessionTaskFactory() {
		return sessionLoader;
	}


	public TaskManager<?, ?> getTaskManager() {		
		return taskManager;
	}
	public CyServiceRegistrar getCyServiceRegistrar() {
		return cyServiceRegistrar;
	}

}
