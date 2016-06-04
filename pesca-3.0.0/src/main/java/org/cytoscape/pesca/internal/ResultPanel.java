/*
 * ResultPanel2.java
 *
 * Created on 29 dicembre 2008, 17.18
 */
package org.cytoscape.pesca.internal;

/**
 *
 * @author scardoni
 */
import java.awt.Component;
import java.util.*;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;

public class ResultPanel extends javax.swing.JPanel implements Observer, CytoPanelComponent {
    //CyNetworkView view = Cytoscape.getCurrentNetworkView();
    //   CyNetwork network = Cytoscape.getCurrentNetwork();

    CyNode source;
    String resulttype;
    static Vector ShortestPathVector;
    static Vector orderedbysizeVector;
    static Vector orderedbyNameVector;
    Vector provavector;
    PescaCore pescacore;
    PescaSPMap pescaSPmap;
    CyNetwork network;
    CyNetworkView view;
    

    /**
     * Creates new form ResultPanel
     * @param network 
     * @param view 
     */
    public ResultPanel(Vector spvector, String resulttype, PescaCore pescacore, PescaSPMap pescaSPmap, CyNetwork network, CyNetworkView view) {
        
        
        this.pescacore = pescacore;
        this.network=network;
        this.view=view;
        initComponents();
        this.resulttype = resulttype;
      //  sptable.setRowSelectionAllowed(true);
      //  sptable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      //  jScrollPane1.getViewport().add(sptable);
     //   sptable.setRowSelectionAllowed(true);
        sptable.getColumnModel().getColumn(0).setHeaderValue("Node Name");
        sptable.getColumnModel().getColumn(1).setHeaderValue("PescaMultiShortestPathTree Value");

        sptable.setVisible(false);
       // sptable.setAutoCreateRowSorter(true);
        CyNode nodes;
       
        ShortestPathVector = spvector;
        provavector = spvector;
        
        Vector sizeVector = addElements(0);

        printresult(sizeVector);
        
        sizetable.getColumnModel().getColumn(0).setHeaderValue("Size");
        sizetable.getColumnModel().getColumn(1).setHeaderValue("Number of SPs ");

        sizetable.setVisible(true);
        
      connectivityTable.getColumnModel().getColumn(0).setHeaderValue("Unique Paths");
        connectivityTable.getColumnModel().getColumn(1).setHeaderValue("Expected Paths");
        connectivityTable.getColumnModel().getColumn(2).setHeaderValue("Connected");
        connectivityTable.setVisible(true);
        
        this.pescaSPmap = pescaSPmap;
        showsizedistribution(pescaSPmap);
        showgraphConnectivity(pescaSPmap);
      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sptable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        selectedNodesButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        sizetable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        connectivityTable = new javax.swing.JTable();
        selectallButton = new javax.swing.JButton();

        jPanel1.setAutoscrolls(true);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        sptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        sptable.setColumnSelectionAllowed(true);
        sptable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sptableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(sptable);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Multi Shortest Path Tree");

        closeButton.setText("Close");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        selectedNodesButton.setText("pass through S-P");
        selectedNodesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedNodesButtonMouseClicked(evt);
            }
        });
        selectedNodesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedNodesButtonActionPerformed(evt);
            }
        });

        sizetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane2.setViewportView(sizetable);

        jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane3MouseClicked(evt);
            }
        });

        connectivityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        connectivityTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectivityTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(connectivityTable);

        selectallButton.setText("Select all paths");
        selectallButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectallButtonMouseClicked(evt);
            }
        });
        selectallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectallButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(jScrollPane3)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(selectallButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(selectedNodesButton)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 204, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(closeButton))
                .add(18, 18, 18)
                .add(selectedNodesButton)
                .add(13, 13, 13)
                .add(selectallButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void sptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptableMouseClicked

    
   /* for (ListIterator i = network.getDefaultNodeTable().getAllRows().listIterator(); i.hasNext();) {
        
    CyRow tmprow = (CyRow)i.next();
    System.out.println("il nodo è" + tmprow.get(CyNetwork.NAME, String.class));
    tmprow.set(CyNetwork.SELECTED, false);
    
    }*/
   
             
    /*System.out.println("cliccato table");
         
    Vector nameVector = addElements(1);
    Collections.sort(nameVector);
    printresult(nameVector);        
  
        int prova2 = sptable.rowAtPoint(evt.getPoint());
    int selectedrows[] = sptable.getSelectedRows();
    int prova = sptable.getSelectedColumn();
      System.out.println("cliccato colonna prova= " + prova+prova2 );
    String listaclick = " ";
    for (int j = 0; j < selectedrows.length; j++) {
        System.out.println("cliccate le righe" + j);
        listaclick = listaclick + selectedrows[j];


        int elementrow = selectedrows[j];
        PescaShortestPathList clickedlist = (PescaShortestPathList) sptable.getModel().getValueAt(elementrow, 0);



        for (Iterator i = clickedlist.iterator(); i.hasNext();) {
            PescaMultiSPath tmpPescaMultiSPath = (PescaMultiSPath) i.next();
            //    NodeView tmpnodeView = view.getNodeView(tmpPescaMultiSPath.getNode());
            // tmpnodeView.setSelected(true);

        }
         System.out.println("cliccato2" + listaclick);
    }
System.out.println(listaclick);
    // view.redrawGraph(false, true);*/
}//GEN-LAST:event_sptableMouseClicked
    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public CytoPanelName getCytoPanelName() {
        return CytoPanelName.EAST;
    }

    @Override
    public String getTitle() {
        return "PescaResultt " + resulttype;
    }

    @Override
    public Icon getIcon() {
        return null;
    }

private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

}//GEN-LAST:event_jScrollPane1MouseClicked

private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
    pescacore.closeCurrentResultPanel(this);
    // TODO add your handling code here:
    /*CytoscapeDesktop desktop = Cytoscape.getDesktop();
     CytoPanel cytoPaneleast = desktop.getCytoPanel(SwingConstants.EAST);
     int index = cytoPaneleast.indexOfComponent(this);
     cytoPaneleast.remove(index);*/
}//GEN-LAST:event_closeButtonMouseClicked

private void selectedNodesButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
    // TODO add your handling code here:
}                                                   

private void allNodesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedNodesButtonActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_selectedNodesButtonActionPerformed


private void viewSelectButtonMouseClicked(java.awt.event.MouseEvent evt) {
	
	 int selectedrows[] = sptable.getSelectedRows();
	   String s=sptable.getValueAt(selectedrows[0],0).toString();
	   String m[]=s.split(">");
	  
	   CyTable t=network.getDefaultNodeTable(); 
	   List allNodes=new ArrayList();
	   System.out.println(t.getRowCount());
	   
	   for(int i=1;i<m.length;i++)
	   {
		   System.out.println(m[i]);
	   }
	   System.out.println("-------------------");
	   for(int i=1;i<m.length;i++)
	   {
		   System.out.println(m[i]);
	    List c=(List) t.getMatchingRows("name", m[i]);    
	    allNodes.add(c.get(0));
	    if(i>1)
	    {
	    	CyNode n1= network.getNode(((CyRow)allNodes.get(i-2)).get(CyIdentifiable.SUID, Long.class));
	    	CyNode n2= network.getNode(((CyRow)allNodes.get(i-1)).get(CyIdentifiable.SUID, Long.class));
	    	CyEdge e=network.getConnectingEdgeList(n1, n2, CyEdge.Type.ANY).get(0);
	    	CyTable tb=network.getDefaultEdgeTable();
	    	System.out.println(e+"----"+tb.getRow(e.getSUID()));
	    	
	    	tb.getRow(e.getSUID()).set(CyNetwork.SELECTED,true);
	    	
	    }
	    
	   // CyNode n= network.getNode(((CyRow)c.get(0)).get(CyIdentifiable.SUID, Long.class));	    
        
	    ((CyRow)c.get(0)).set(CyNetwork.SELECTED,true);
	    view.updateView();
	   }
	   

}



private void viewDistributionButtonMouseClicked(java.awt.event.MouseEvent evt) {
	 JFrame f = new JFrame();
	    f.setSize(400, 300);
	    Map h=pescaSPmap.pescaSPmap;
	    Set s=h.entrySet();
	   
	    int length=h.size();
	    double[] values = new double[length];
	    String[] names = new String[length];
	    
	    int i=0;
	    Iterator<Map.Entry<Integer, Integer>> entries = h.entrySet().iterator();
	    while (entries.hasNext()) {
	    	
	        Map.Entry<Integer, Integer> entry = entries.next();
	         values[i]=entry.getValue();
	        names[i]="Sz:"+entry.getKey();
	        i++;
	    }
	    
	   
	   
	    f.getContentPane().add(new newFile(values, names, "SP distribution"));

	   
	    f.setVisible(true);
	  
}

private void allNodesButtonMouseClicked(java.awt.event.MouseEvent evt) {                                                 

	CyTable t=network.getDefaultNodeTable(); 
	for(int z=0;z<sptable.getRowCount();z++)
	{
	 
	   String s=sptable.getValueAt(z,0).toString();
	   String m[]=s.split(">");
	  
	   
	   List allNodes=new ArrayList();
	   System.out.println(t.getRowCount());
	   	   
	   for(int i=1;i<m.length;i++)
	   {
		   System.out.println(m[i]);
	    List c=(List) t.getMatchingRows("name", m[i]);    
	    allNodes.add(c.get(0));
	    if(i>1)
	    {
	    	CyNode n1= network.getNode(((CyRow)allNodes.get(i-2)).get(CyIdentifiable.SUID, Long.class));
	    	CyNode n2= network.getNode(((CyRow)allNodes.get(i-1)).get(CyIdentifiable.SUID, Long.class));
	    	CyEdge e=network.getConnectingEdgeList(n1, n2, CyEdge.Type.ANY).get(0);
	    	CyTable tb=network.getDefaultEdgeTable();
	    	System.out.println(e+"----"+tb.getRow(e.getSUID()));
	    	
	    	tb.getRow(e.getSUID()).set(CyNetwork.SELECTED,true);
	    	
	    }
	    ((CyRow)c.get(0)).set(CyNetwork.SELECTED,true);
	   }
	    
	   // CyNode n= network.getNode(((CyRow)c.get(0)).get(CyIdentifiable.SUID, Long.class));	    
      
	   
	    view.updateView();
	   }

}


private void selectedNodesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedNodesButtonMouseClicked
    // TODO add your handling code here:
    
    Vector subvector = new Vector();
    Vector selectedvector = new Vector();
    Long nodeSUID, selectedSUID;
    CyNode tmpnode;
    /*    List selectednodeslist = view.getSelectedNodes();
     for (int i=0; i<selectednodeslist.size();i++) {
     NodeView currentnodeView = (NodeView)selectednodeslist.get(i);
     CyNode currentnode = (CyNode)currentnodeView.getNode();
     System.out.println("nodocorrente = " + currentnode.getIdentifier());
     selectedvector.addElement(currentnode.getIdentifier());
     }*/
   // System.out.println("entro in pass through");
    
    List<CyNode> selectednodes = CyTableUtil.getNodesInState(network,"selected",true);
    //System.out.println("network= " + network.getSUID());
    Collections.sort(selectedvector);

    for (Iterator j = selectednodes.listIterator(); j.hasNext(); ) {
         tmpnode = (CyNode)j.next();
        // System.out.println("selectednodes" + tmpnode.getSUID());
            selectedSUID = tmpnode.getSUID();
          //   System.out.println("provavector" + provavector.size());
        for (int i = 0; i < provavector.size(); i++) {
            
           
            selectedSUID = tmpnode.getSUID();
            PescaShortestPathList resultelement = (PescaShortestPathList) provavector.elementAt(i);
            for (Iterator k = resultelement.iterator();k.hasNext();) {
            PescaMultiSPath currentmultispath = (PescaMultiSPath)k.next();
            //nodeSUID = ((PescaMultiSPath) resultelement.getLast()).getSUID();
            nodeSUID = currentmultispath.getSUID();
           // System.out.println("nodesuid= " + nodeSUID);
           //  System.out.println("selectednodesuid= " + selectedSUID);
            if (nodeSUID.equals(selectedSUID)) {
           //    System.out.println("eccolo" + resultelement.toString());
                subvector.add(resultelement);
            }
            }
        }
    }
    for (int i = 0; i < subvector.size(); i++) {
        PescaShortestPathList currentelement = (PescaShortestPathList) subvector.elementAt(i);
        for (int j = 0; j < currentelement.size(); j++) {
            
            CyNode currentnodetohighlight = ((PescaMultiSPath) currentelement.get(j)).getNode();
            System.out.println("accendo " + (((PescaMultiSPath) currentelement.get(j)).getName()));
            network.getRow(currentnodetohighlight).set(CyNetwork.SELECTED, true);
            //    network.setSelectedNodeState( ((PescaMultiSPath)currentelement.get(j)).getNode(),true);
                view.updateView();
        }
    }
    
    
    /*
    
    
    
     List<CyNode> rootlist = CyTableUtil.getNodesInState(network, "selected", true);
      
   
    Vector subvector = new Vector();
    Vector selectedvector = new Vector();
    String name;
    
   

    ListSelectionModel model = sptable.getSelectionModel();
    
     model.clearSelection();      
    int flag=0;
    for(int i=0;i<sptable.getRowCount();i++)
    {
    	flag=0;
    	for(int j=0;j<rootlist.size();j++)
        {
    		String s[]=sptable.getValueAt(i, 0).toString().split(">");
    		for(int k=1;k<s.length;k++)
    		{
    		    if(network.getRow(rootlist.get(j)).get("name",String.class).equals(s[k]))
    	         {
    			  model.addSelectionInterval(i, i);
    			  flag=1;
    			  break;    			 
    	    	
    	          }
    		}
    		if(flag==1)
    			break;
        }
    }*/
    /*    List selectednodeslist = view.getSelectedNodes();
     for (int i=0; i<selectednodeslist.size();i++) {
     NodeView currentnodeView = (NodeView)selectednodeslist.get(i);
     CyNode currentnode = (CyNode)currentnodeView.getNode();
     System.out.println("nodocorrente = " + currentnode.getIdentifier());
     selectedvector.addElement(currentnode.getIdentifier());
     }*/
//    Collections.sort(selectedvector);
//
//    for (int j = 0; j < selectedvector.size(); j++) {
//        for (int i = 0; i < provavector.size(); i++) {
//
//            PescaShortestPathList resultelement = (PescaShortestPathList) provavector.elementAt(i);
//            name = ((PescaMultiSPath) resultelement.getLast()).getName();
//            if (name.equals((String) selectedvector.elementAt(j))) {
//               // System.out.println("eccolo" + resultelement.toString());
//                subvector.add(resultelement);
//            }
//        }
//    }
//    for (int i = 0; i < subvector.size(); i++) {
//        PescaShortestPathList currentelement = (PescaShortestPathList) subvector.elementAt(i);
//        for (int j = 0; j < currentelement.size(); j++) {
//            System.out.println("accendo " + (((PescaMultiSPath) currentelement.get(j)).getName()));
//            //    network.setSelectedNodeState( ((PescaMultiSPath)currentelement.get(j)).getNode(),true);
//            //    view.redrawGraph(true, true);
//        }
//    }
}//GEN-LAST:event_selectedNodesButtonMouseClicked

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane3MouseClicked

    private void connectivityTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectivityTableMouseClicked
// TODO add your handling code here:
    }//GEN-LAST:event_connectivityTableMouseClicked

    private void sptableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptableMouseReleased
        // TODO add your handling code here:
       
        List<CyNode> listofselectednodes = CyTableUtil.getNodesInState(network,"selected",true); 
    for (ListIterator i = listofselectednodes.listIterator(); i.hasNext();) {
        
        CyNode currentnode = (CyNode)i.next();
        network.getRow(currentnode).set(CyNetwork.SELECTED, false);
    //CyRow tmprow = (CyRow)i.next();
 //   System.out.println("il nodo è" + tmprow.get(CyNetwork.NAME, String.class));
   // tmprow.set(CyNetwork.SELECTED, false);
    
    }
        
        
   //     System.out.println("lasciato table");
        int selectedrows[] = sptable.getSelectedRows();
     //    System.out.println("lasciato " + selectedrows[0]);
       //  System.out.println("le righe sono" + selectedrows.length);
        
           String listaclick = " ";
    for (int j = 0; j < selectedrows.length; j++) {
       // System.out.println("cliccate e lasciate le righe" + j);
        listaclick = listaclick + " " + selectedrows[j];
        int elementrow = selectedrows[j];
        PescaShortestPathList clickedlist = (PescaShortestPathList) sptable.getModel().getValueAt(elementrow, 0);
       // System.out.println("clicca list= " + clickedlist.toString());
        
         for (Iterator i = clickedlist.iterator(); i.hasNext();) {
            PescaMultiSPath tmpPescaMultiSPath = (PescaMultiSPath) i.next();
                network.getRow(tmpPescaMultiSPath.getNode()).set(CyNetwork.SELECTED, true);
                //  NodeView tmpnodeView = view.getNodeView(tmpPescaMultiSPath.getNode());
             // tmpnodeView.setSelected(true);
         }
         
         view.updateView();
     //    System.out.println("finito");
        /*System.out.println("cliccato table");
         
    Vector nameVector = addElements(1);
    Collections.sort(nameVector);
    printresult(nameVector);        
  
        int prova2 = sptable.rowAtPoint(evt.getPoint());
    int selectedrows[] = sptable.getSelectedRows();
    int prova = sptable.getSelectedColumn();
      System.out.println("cliccato colonna prova= " + prova+prova2 );
    String listaclick = " ";
    for (int j = 0; j < selectedrows.length; j++) {
        System.out.println("cliccate le righe" + j);
        listaclick = listaclick + selectedrows[j];


        int elementrow = selectedrows[j];
        PescaShortestPathList clickedlist = (PescaShortestPathList) sptable.getModel().getValueAt(elementrow, 0);



        for (Iterator i = clickedlist.iterator(); i.hasNext();) {
            PescaMultiSPath tmpPescaMultiSPath = (PescaMultiSPath) i.next();
            //    NodeView tmpnodeView = view.getNodeView(tmpPescaMultiSPath.getNode());
            // tmpnodeView.setSelected(true);

        }
         System.out.println("cliccato2" + listaclick);
    }
System.out.println(listaclick);
    // view.redrawGraph(false, true);*/
        
        
    }
 //   System.out.println("listaclick= " + listaclick);
    }//GEN-LAST:event_sptableMouseReleased

    private void selectallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectallButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectallButtonActionPerformed

    private void selectallButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectallButtonMouseClicked
        // TODO add your handling code here:
         System.out.println("cliccato selected" );
        sptable.selectAll();
        for (ListIterator i = network.getDefaultNodeTable().getAllRows().listIterator(); i.hasNext();) {
        
    CyRow tmprow = (CyRow)i.next();
 //   System.out.println("il nodo è" + tmprow.get(CyNetwork.NAME, String.class));
    tmprow.set(CyNetwork.SELECTED, false);
    
    }
        
        
   //     System.out.println("lasciato table");
        int selectedrows[] = sptable.getSelectedRows();
     //    System.out.println("lasciato " + selectedrows[0]);
         System.out.println("le righe sono" + selectedrows.length);
        
           String listaclick = " ";
    for (int j = 0; j < selectedrows.length; j++) {
       // System.out.println("cliccate e lasciate le righe" + j);
        listaclick = listaclick + " " + selectedrows[j];
        int elementrow = selectedrows[j];
        PescaShortestPathList clickedlist = (PescaShortestPathList) sptable.getModel().getValueAt(elementrow, 0);
       // System.out.println("clicca list= " + clickedlist.toString());
        
         for (Iterator i = clickedlist.iterator(); i.hasNext();) {
            PescaMultiSPath tmpPescaMultiSPath = (PescaMultiSPath) i.next();
                network.getRow(tmpPescaMultiSPath.getNode()).set(CyNetwork.SELECTED, true);
                //  NodeView tmpnodeView = view.getNodeView(tmpPescaMultiSPath.getNode());
             // tmpnodeView.setSelected(true);
         }
    }
         view.updateView();
      /*  Vector nameVector = addElements(1);
        Collections.sort(nameVector);
        printresult(nameVector);*/
    }//GEN-LAST:event_selectallButtonMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTable connectivityTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton selectallButton;
    private javax.swing.JButton selectedNodesButton;
    private javax.swing.JTable sizetable;
    private javax.swing.JTable sptable;
    // End of variables declaration//GEN-END:variables

    public Vector addElements(int choosed) {
         Vector svector = new Vector();
        for (int i = 0; i < provavector.size(); i++) {
            if (choosed == 0) {
                Resultsbysize resultelement = new Resultsbysize((PescaShortestPathList) provavector.elementAt(i));
                svector.add(i, resultelement);
            } else {
                Resultsbyname resultelement = new Resultsbyname((PescaShortestPathList) provavector.elementAt(i));
                svector.add(i, resultelement);
            }
        }
        return svector;
    }

    public void printresult(final Vector data) {
        
        
          
        
selectallButton.setVisible(true);
        //orderSizeButton.setVisible(false);
        selectedNodesButton.setVisible(true);
        sptable.setModel(new javax.swing.table.AbstractTableModel() {
            
             Class[] types = { Object.class, String.class, Integer.class };
             
            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
            public int getColumnCount() {
                return 3;
            }

            public int getRowCount() {
                return data.size();
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    //      return (data.get(rowIndex));
                    return (((Results) data.get(rowIndex)).getSPlist());
                } else if (columnIndex == 1) {
                    //              return  ((PescaMultiSPath)(((PescaShortestPathList)data.get(rowIndex)).getLast())).getName();
                	  
                	return (((Results) data.get(rowIndex)).getName());
                       } else {
                    //    return ( ((PescaShortestPathList)data.get(rowIndex)).size() );
                    return (((Results) data.get(rowIndex)).getSize() - 1);
                }


            }
        });
        /*
          jTableSP.setModel(new javax.swing.table.AbstractTableModel() {
            
          //   Class[] types = { Object.class, String.class, Integer.class };
             
          //  public Class getColumnClass(int columnIndex) {
          //      return this.types[columnIndex];
           // }
            public int getColumnCount() {
                return 3;
            }

            public int getRowCount() {
                return data.size();
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    //      return (data.get(rowIndex));
                    return (((Results) data.get(rowIndex)).getSPlist());
                } else if (columnIndex == 1) {
                    //              return  ((PescaMultiSPath)(((PescaShortestPathList)data.get(rowIndex)).getLast())).getName();
                	  
                	return (((Results) data.get(rowIndex)).getName());
                       } else {
                    //    return ( ((PescaShortestPathList)data.get(rowIndex)).size() );
                    return (((Results) data.get(rowIndex)).getSize() - 1);
                }


            }
        });
        jTableSP.getColumnModel().getColumn(0).setHeaderValue("Shortest Path");
        jTableSP.getColumnModel().getColumn(1).setHeaderValue("Target Node");
        jTableSP.getColumnModel().getColumn(2).setHeaderValue("size");
        jTableSP.setAutoCreateRowSorter(true);
        */
        // this.jProgressBar1.setIndeterminate(false);
//        jScrollPane2.setVisible(true);  
        // jButton1.setEnabled(true);
        sptable.getColumnModel().getColumn(0).setHeaderValue("Shortest Path");
        sptable.getColumnModel().getColumn(1).setHeaderValue("Target Node");
        sptable.getColumnModel().getColumn(2).setHeaderValue("size");
        sptable.setAutoCreateRowSorter(true);
        sptable.setVisible(true);
        //  jLabel2.setVisible(true);
        // jLabel3.setVisible(true);
        // jLabel3.setText("Finished:");  
        // jLabel4.setText(data.size() + " nodes worked");  
        // jLabel2.setText("Source Node = " + source.getIdentifier() + " Results found: ");  
        // calculating = false;
     
       

      
        

    }

    public void showsizedistribution(PescaSPMap pescaSPmap) {
    	
    	if( pescaSPmap != null)
    	{

          final Vector data = pescaSPmap.MaptoVector();
  //  System.out.println(data.toString());
       
 


        sizetable.setModel(new javax.swing.table.AbstractTableModel() {
            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public int getRowCount() {
            	
                return data.size();
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
            	
//            	
                if (columnIndex == 0) {
                    //      return (data.get(rowIndex));
                    return (((Map.Entry)data.get(rowIndex)).getKey());
                } else  {
                    //              return  ((PescaMultiSPath)(((PescaShortestPathList)data.get(rowIndex)).getLast())).getName();
                    return (((Map.Entry)data.get(rowIndex)).getValue());
                }


            }
        });

        sizetable.getColumnModel().getColumn(0).setHeaderValue("Size");
        sizetable.getColumnModel().getColumn(1).setHeaderValue("Number of Sp");
        sizetable.setVisible(true);
    	}

    }
    
    public void showgraphConnectivity(final PescaSPMap pescaSPmap)
    {
    	
    	if( pescaSPmap != null)
    	{
          connectivityTable.setModel(new javax.swing.table.AbstractTableModel() {
            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public int getRowCount() {
                return 1;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
              
               if(columnIndex==0)
                {
                	return Math.round(((float) pescaSPmap.getPathLength()/ pescaSPmap.getAllPathsCount())*100)/100.0;
                	
                }
               else if (columnIndex == 1) {
              	  //      return (data.get(rowIndex));
                  return pescaSPmap.getAllPathsCount();
              } 
              
                else if (columnIndex == 2) {
                		int paths=pescaSPmap.getExpectedPaths();
                	
                	if(paths==0)
                		return "N/A";
                    //              return  ((PescaMultiSPath)(((PescaShortestPathList)data.get(rowIndex)).getLast())).getName();
                	 return paths;
                }
             
                else
                	 {
                	
                	if(pescaSPmap.getExpectedPaths()==0)
                		return "N/A";
                	
                	if( pescaSPmap.getAllPathsCount()-pescaSPmap.getExpectedPaths()==0)
                	 	return true;
                	 else
                		 return false;
                	 }


            }
        });

          connectivityTable.getColumnModel().getColumn(0).setHeaderValue("Avg Path Length");
          connectivityTable.getColumnModel().getColumn(1).setHeaderValue("Unique S->T Paths");
          connectivityTable.getColumnModel().getColumn(2).setHeaderValue("Expected Paths");
          connectivityTable.getColumnModel().getColumn(3).setHeaderValue("Connected");
          connectivityTable.setVisible(true);
    	}
    	
    	
    }
    
}
