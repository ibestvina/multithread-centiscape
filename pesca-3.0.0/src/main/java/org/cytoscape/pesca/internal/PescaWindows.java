package org.cytoscape.pesca.internal;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package Pesca;
//import Visualizer2.*;

/**
 *
 * @author scardoni
 */
public class PescaWindows {
//private Visualizer visualizer;
private PescaStartMenu startmenu;
//private ResultPanel resultpanel;


public PescaWindows(PescaStartMenu startmenu) {
 //   visualizer = new Visualizer();
    startmenu = startmenu;
    //startmenu = new PescaStartMenu();
    
    
}

public void destroyall() {
   // visualizer = null;
    startmenu = null;
  //  resultpanel = null;
}

public void destroystart() {

    startmenu = null;
}    

public void destroyvisualizer() {

    //visualizer = null;
}

//public void destroyresultpanel(ResultPanel p) {
  //  p=null;
//}
//public void setvisualizer(Visualizer v) {

  //  visualizer = v;
//}

public void setstartmenu(PescaStartMenu sm) {
    startmenu = sm;
}

//public Visualizer getvisualizer() {

  //  return visualizer;
//}

public PescaStartMenu getstartmenu() {

    return startmenu;
}
}