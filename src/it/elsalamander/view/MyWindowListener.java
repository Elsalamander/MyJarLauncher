package it.elsalamander.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*********************************************************************
 * Listener del Frame del launcher
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v1.0.0
 * 
 *********************************************************************/
public class MyWindowListener implements WindowListener{

	private MainGui gui;
	
	/**
	 * @param gui
	 */
	public MyWindowListener(MainGui gui){
		super();
		this.gui = gui;
	}

	@Override
	public void windowOpened(WindowEvent e){
	}

	@Override
	public void windowClosing(WindowEvent e){
		this.gui.close();
	}

	@Override
	public void windowClosed(WindowEvent e){
	}

	@Override
	public void windowIconified(WindowEvent e){
	}

	@Override
	public void windowDeiconified(WindowEvent e){
	}

	@Override
	public void windowActivated(WindowEvent e){
	}

	@Override
	public void windowDeactivated(WindowEvent e){
	}
}
