package it.elsalamander.view.toolBar;

import javax.swing.BoxLayout;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/*********************************************************************
 * Creazione della Toolbar
 * 
 * 
 * @author: Elsalamander
 * @data: 11 set 2022
 * @version: v2.0.0
 * 
 *********************************************************************/
public class ToolBar extends JToolBar{
	
	private static final long serialVersionUID = -8988717545989043130L;

	/**
	 * Create the panel.
	 */
	public ToolBar(){
		//nome barra e orientazione
		super("MyToolBar", SwingConstants.VERTICAL);
		
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		super.setBorder(null);	//elimina i punti
	}
	
}
