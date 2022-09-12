package it.elsalamander.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import it.elsalamander.view.subPanel.SubPanel;
import it.elsalamander.view.subPanel.globalMetrics.GlobalMetrics;
import it.elsalamander.view.subPanel.jvm.JvmPanel;
import it.elsalamander.view.toolBar.ToolBar;

/*********************************************************************
 * Frame dell'applicazione,
 * ci sono 2 panel principali qua:
 * - ToolBar
 * - SubPanel
 * 
 * 
 * @author: Elsalamander
 * @data: 11 set 2022
 * @version: v2.0.0
 * 
 ********************************************************************/
public class MainGui extends JFrame{
	
	private static final long serialVersionUID = -7947945617511266110L;
	private static final String TITLE = "Launcher";
	private static final int XSIZE = 1000;
	private static final int YSIZE = 800;
	private static final Dimension STARTDIM = new Dimension(XSIZE, YSIZE);
	private static final int toolBarXSize = 75;
	
	private List<SubPanel> subPanels;
	private SubPanel current;
	
	
	public MainGui(){
		super();
		
		//cosa fare quando si chiude la finestra
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//evento chiususra
		super.addWindowListener(new MyWindowListener(this));
		
		//imposta il titolo della finestra
		super.setTitle(TITLE);
		
		//lista delle subPanels
		this.subPanels = new ArrayList<SubPanel>(2);
		
		//crea i pannelli
		this.subPanels.add(new GlobalMetrics());
		this.subPanels.add(new JvmPanel());
		
		//Dimensioni finestra iniziali
		setPreferredSize(STARTDIM);
		
		//Aggiungi la barra dei tools
		this.addToolsBar();
		
		//Aggiungi il panel a destra, il primo per cominciare
		this.goToPanel(0);
		
		//Esegui il pack e rendi visibile.
		super.pack();
		super.setVisible(true);
	}

	/**
	 * Vai al pannello dato
	 * @param index
	 */
	private void goToPanel(int index){
		if(this.current != null) {
			super.remove(this.current);
		}
		this.current = this.subPanels.get(index);
		super.add(this.current);
		
		this.current.repaint();
		super.validate();
		super.setVisible(true);
	}

	/**
	 * Creazione e piazzamento della ToolBar
	 */
	private void addToolsBar(){
		ToolBar bar = new ToolBar();
		
		GridBagConstraints attr = new GridBagConstraints();
		
		//bottone per le metriche
		bar.add(this.createButton("Metriche", 0), attr);
		
		//bottone per le JVM
		bar.add(this.createButton("JVM", 1), attr);
		
		bar.setPreferredSize(new Dimension(toolBarXSize, YSIZE));
		bar.setMaximumSize(new Dimension(toolBarXSize, Integer.MAX_VALUE));
	    super.add(bar, BorderLayout.WEST);
	}

	/**
	 * Crea il bottone per andare al subpanel indicato dall'indice
	 * @param name
	 * @param index
	 * @return
	 */
	private JButton createButton(String name, int index){
		JButton button = new JButton(name);
		button.addActionListener((event) -> {
			//quando cliccato deve cambiare subpanel
			this.goToPanel(index);
		});
		
		button.setMaximumSize(new Dimension(toolBarXSize, 50));
		
		return button;
	}
	
	public void close() {
		for(SubPanel sub : this.subPanels) {
			sub.close();
		}
	}
	
}
