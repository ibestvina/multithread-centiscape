package org.cytoscape.centiscape.internal;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator { 
	private final static String VERSION = "2.1";
    private CyApplicationManager applicationManager;
    private CySwingApplication desktopService;
    private CyServiceRegistrar serviceRegistrar;
    private CentiScaPeMenuAction menuAction;

    @Override
    public void start(BundleContext context) throws Exception {
        this.applicationManager = getService(context, CyApplicationManager.class);
        this.desktopService = getService(context, CySwingApplication.class);
        this.serviceRegistrar = getService(context, CyServiceRegistrar.class);
        this.menuAction = new CentiScaPeMenuAction(applicationManager, "Centiscape" + VERSION, this);

        registerAllServices(context, menuAction, new Properties());
    }
    
    public CyServiceRegistrar getServiceRegistrar() {
        return serviceRegistrar;
    }

    public CyApplicationManager getApplicationManager() {
        return applicationManager;
    }

    public CySwingApplication getDesktopService() {
    	return desktopService;
    }

    public CentiScaPeMenuAction getMenuAction() {
        return menuAction;
    }    
}