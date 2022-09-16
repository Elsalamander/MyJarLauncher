package it.elsalamander.view.subPanel.jvm.jvmList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.view.subPanel.jvm.JvmPanel;
import it.elsalamander.view.subPanel.jvm.jvmView.JvmView;

/*********************************************************************
 * Colonna verticale per la visualizzazione di tutti i file JAR
 * caricati.
 * In testa c'è un bottone per la lettura di un file jar.
 * 
 * 
 * @author: Elsalamander
 * @data: 12 set 2022
 * @version: v2.2.1
 * 
 *********************************************************************/
public class JvmList extends JToolBar{
	
	private static final long serialVersionUID = 3996263871456694201L;

	private JvmPanel consoles;
	private JPanel mainList;
	
	/**
	 * Create the panel.
	 */
	public JvmList(JvmPanel consoles){
		//nome barra e orientazione
		super("Lista Consoles", SwingConstants.VERTICAL);
		
		//bordo
		super.setBorder(BorderFactory.createTitledBorder("Lista"));
		
		//variabile ocnsoles di riferimento
		this.consoles = consoles;
		
		//impostazione layout
		super.setLayout(new BorderLayout());
		
		//Panel della lista interna
		this.mainList = new JPanel();
		
		//impostazione layout della lista interna
		this.mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));
		
		//creazione pulsante di Add
		JButton add = new JButton("Add");
		add.setPreferredSize(new Dimension(150, 20));
		super.add(add, BorderLayout.NORTH);
		
		//azione da fare quando is preme il pulsante add
		add.addActionListener((event) ->{
			//vai alla console di selezione di file
		    this.consoles.goToConsole(-1);
		 });
		
		super.add(new JScrollPane(this.mainList));
	}

	/**
	 * Crea un elemento per la lista
	 * @param indexJar
	 * @param jar
	 * @param label
	 * @return
	 */
	public ElementJvmList createNewElement(int indexJar, ExecuteJar jar, String label){
		//crea il panel
		JvmView view = new JvmView(jar);
		this.consoles.getSubPanel().put(indexJar, view);
		
		return new ElementJvmList(this.consoles, label, indexJar);
	}

	/**
	 * Aggiungi un elemento alla lista
	 * @param element
	 */
	public void addElementList(ElementJvmList element){
		element.setMaximumSize(new Dimension(200, 20));
		
        GridBagConstraints attr = new GridBagConstraints();
        this.mainList.add(element, attr);
        
        validate();
        repaint();
	}
	
}
