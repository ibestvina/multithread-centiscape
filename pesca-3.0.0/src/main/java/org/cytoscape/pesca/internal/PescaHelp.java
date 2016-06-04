package org.cytoscape.pesca.internal;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PescaHelp extends JFrame{
	JTextArea text;
	public PescaHelp()

	{
		super("Pesca Help");
		this.setSize(300, 400);
		text=new JTextArea( );
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		
		
		JScrollPane scroll=new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroll);
		//this.add(text);
		
		
	}
	public void setText(String content)
	{
		text.append(content);
	
		text.setEditable(false);
		
		
	}
	
	
	
    
    
    
	

}
