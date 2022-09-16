package it.elsalamander.view.subPanel.jvm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import it.elsalamander.view.subPanel.SubPanel;
import it.elsalamander.view.subPanel.jvm.jvmList.JvmList;

/*********************************************************************
 * Panel per mostrare:
 * - Lista vericale di tutti i jar aperti e esecuzioni.
 * - Pannello in visualizzazione
 * 
 * @author: Elsalamander
 * @data: 11 set 2022
 * @version: v2.1.2
 * 
 *********************************************************************/
public class JvmPanel extends SubPanel{
	
	private static final long serialVersionUID = 105827229445999697L;
	
	private Map<Integer,JvmSubPanel> subPanel;
	private JvmSubPanel current;
	private JvmList list;
	
	/**
	 * Create the panel.
	 */
	public JvmPanel(){
		//creazione mappa
		this.subPanel = new TreeMap<Integer,JvmSubPanel>();
		
		//bordo
		super.setBorder(BorderFactory.createTitledBorder("Jvm Manager"));
		
		//creazione panel di load
		this.subPanel.putIfAbsent(-1, new JvmLoadPanel(this));
		
		//creo la lista
		this.list = new JvmList(this);
		this.list.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		
		//aggiungi la lista al panel
		super.add(this.list, BorderLayout.WEST);
		
		//navigo al panel di creazione
		this.goToConsole(-1);
		
		//set layout
		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}


	/**
	 * Vai alla console numero X, il numero è contenuto nel bottone
	 * @param toGo
	 */
	public void goToConsole(int toGo){
		if(this.current != null) {
			super.remove(this.current);
		}
		
		JvmSubPanel sub = this.subPanel.get(toGo);
		if(sub == null) {
			sub = this.subPanel.get(-1);
		}
		super.add(sub);
		this.current = sub;
		
		this.current.repaint();
		super.validate();
		super.setVisible(true);
	}
	
	/**
	 * @return the subPanel
	 */
	public Map<Integer, JvmSubPanel> getSubPanel(){
		return subPanel;
	}
	
	/**
	 * @return the list
	 */
	public JvmList getList(){
		return list;
	}

	@Override
	public void close(){
		for(JvmSubPanel sub : this.subPanel.values()) {
			sub.close();
		}
	}

	
}
