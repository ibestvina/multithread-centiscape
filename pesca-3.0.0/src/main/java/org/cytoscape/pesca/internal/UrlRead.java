
package org.cytoscape.pesca.internal;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import org.cytoscape.task.read.LoadNetworkURLTaskFactory;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskMonitor;

public class UrlRead {
	DefaultComboBoxModel model;
	CyActivator cyActivator;
	private LoadNetworkURLTaskFactory networkURLLoader;
	private final TaskManager taskManager;
	private URL networkURLs;
	
	
	UrlRead( LoadNetworkURLTaskFactory networkURLLoader,TaskManager taskManager)
	{
		
		model=new DefaultComboBoxModel();
		
		this.networkURLLoader =networkURLLoader;
		this.taskManager=taskManager;
		
	}
	public  DefaultComboBoxModel getURLS() 
	{
		model.addElement("Select network");
		URL connect;
		try {
			connect = new URL("http://dp.univr.it/~laudanna/LCTST/downloads/index.html");
		
        URLConnection yc = connect.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
        	if(inputLine.contains("<a href=\"files/"))
        	{	int start=inputLine.indexOf('"');
        			int end=inputLine.substring(start+1).indexOf('"');
        			if(inputLine.substring(end+start-2, end+start+1).equals("sif") || inputLine.substring(end+start-2, end+start+1).equals("cys"))        
        			{ System.out.println("http://dp.univr.it/~laudanna/LCTST/downloads/"+inputLine.substring(start+7, end+start+1));
        		      model.addElement(inputLine.substring(start+7, end+start+1));
        			}
        	}
       		
        in.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
		
	}
	
	public void start() {
		
		ArrayList<TaskIterator> taskIteratorList = new ArrayList<TaskIterator>();
	    System.out.println(networkURLs);
	    taskIteratorList.add( networkURLLoader.loadCyNetworks(networkURLs) );		
		Task initTask = new DummyTask();
		TaskIterator taskIterator = new TaskIterator(taskIteratorList.size(), initTask);
		for (int i= taskIteratorList.size()-1; i>= 0 ; i--){
			TaskIterator ti = taskIteratorList.get(i);
			taskIterator.insertTasksAfter(initTask, ti);
		}
		
		taskManager.execute(taskIterator);
		
	}
	
	public class DummyTask extends AbstractTask{

		@Override
		public void run(TaskMonitor taskMonitor) throws Exception {
			//DO nothing it is a dummy tas just to initiate the iterator
		}
		
	}

	
	public void loadNetwork (String u){
	
	
		URL r;
		try {
			
			System.out.print(u);
			r = new URL("http://dp.univr.it/~laudanna/LCTST/downloads/files/"+u);
			networkURLs=r;
		} catch (MalformedURLException em) {
			// TODO Auto-generated catch block
			em.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		start();
		// TODO Auto-generated method stub
		
	}
	

}
