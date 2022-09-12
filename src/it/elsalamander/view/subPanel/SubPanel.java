package it.elsalamander.view.subPanel;

import javax.swing.JPanel;

/*********************************************************************
 * Definisce come deve comportarsi il subpanel
 * 
 * 
 * @author: Elsalamander
 * @data: 11 set 2022
 * @version: v1.0.0
 * 
 *********************************************************************/
public abstract class SubPanel extends JPanel{
	
	private static final long serialVersionUID = 7087746953744799284L;

	public SubPanel(){
		super();
	}

	/**
	 * Chiusura della finestra
	 */
	public abstract void close();
	
}
