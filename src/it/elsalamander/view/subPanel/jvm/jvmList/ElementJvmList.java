package it.elsalamander.view.subPanel.jvm.jvmList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import it.elsalamander.view.subPanel.jvm.JvmPanel;

public class ElementJvmList extends JButton{
	
	private static final long serialVersionUID = -1480651471856582346L;

	private int index;
	
	/**
	 * Create the panel.
	 */
	public ElementJvmList(JvmPanel consolesPanel, String name, int toGo){
		super(name);
		this.index = toGo;
		
		super.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	consolesPanel.goToConsole(toGo);
	        }
	    });
	}

	/**
	 * @return the index
	 */
	public int getIndex(){
		return index;
	}
}
