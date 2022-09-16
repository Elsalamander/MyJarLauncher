package it.elsalamander.view.subPanel.jvm.jvmView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.prove.TextAreaOutputStream;
import it.elsalamander.view.subPanel.globalMetrics.Metrics;
import it.elsalamander.view.subPanel.jvm.JvmSubPanel;

/*********************************************************************
 * Interfaccia grafica per la console
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v2.1.1
 * 
 *********************************************************************/
public class JvmView extends JvmSubPanel{
	
	private static final long serialVersionUID = 2365749296751448554L;

	private ExecuteJar jar;
	private JPanel head;
	private JButton start;
	private JButton settings;
	
	private Metrics metrics;
	
	private JTextArea ta;
	private PrintStream ps;
	
	
	/**
	 * Create the panel.
	 * @param jar 
	 */
	public JvmView(ExecuteJar jar){
		this.jar = jar;
		
		//imposta il layout
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		//Aggiungi un testo
		this.creatHeadPanel();

		//aggiungi il testo della console
		this.createConsoleOutPut();
		
        //aggiungi input per la console
		this.createConsoleInput();
        
	}

	/**
	 * Crea il pannello di testa dove ci saranno
	 * - Pulsante di start
	 * - Pulsante di Settings
	 * - Metriche(CPU, RAM,...)
	 */
	private void creatHeadPanel(){
		this.head = new JPanel();
		this.head.setLayout(new BoxLayout(this.head, BoxLayout.X_AXIS));
		
		//crea il panel di metrica
		this.metrics = new JvmMetrics(this);
		
		//dimensione massima del pannello di testa
		this.head.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
		
		//Pannello interno per i pulsanti di start e settings
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.Y_AXIS));
		
		//pulsante di start
		this.start = new JButton("Start");
        start.addActionListener(event -> {
        	if(jar.getProcess() == null || !jar.getProcess().isAlive()) {
        		jar.runJar(ps);
        		this.metrics.startTask();
        		try{
					ps.write("Avvio\n".getBytes());
				}catch(IOException e){
					e.printStackTrace();
				}
        	}
        });
        internalPanel.add(start);
        
        //bottone di settings
        this.settings = new JButton("Settings");
        this.settings.addActionListener(event -> {
        	new JvmSettings(this).setVisible(true);
        });
        internalPanel.add(this.settings);
        
        //FINE pannello interno
        this.head.add(internalPanel);
        
        this.head.add(Box.createHorizontalStrut(10));
        
		this.head.add(this.metrics);
		
		super.add(this.head, BorderLayout.NORTH);
	}
	
	/**
	 * crea la textarea dove ci sarà scritta la console
	 */
	private void createConsoleOutPut(){
		this.ta = new JTextArea();
		this.ta.setEditable(false);
        TextAreaOutputStream taos = new TextAreaOutputStream(ta, 60);
        this.ps = new PrintStream(taos);
        super.add(new JScrollPane(ta));
	}
	
	private void createConsoleInput(){
		JTextArea inTa = new JTextArea();
        inTa.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e){
				
			}
			
			@Override
			public void keyTyped(KeyEvent e){
				
			}

			@Override
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
			        jar.getConsole().sendCommand(inTa.getText());
			        inTa.setText(null);
			    }
			}
        	
        });
        inTa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        inTa.setBorder(BorderFactory.createLineBorder(Color.black));
        super.add(inTa);
	}

	

	@Override
	public void close(){
		if(this.jar.getProcess() != null && this.jar.getProcess().isAlive()) {
			this.jar.getProcess().destroy();
		}
	}

	/**
	 * @return the jar
	 */
	public ExecuteJar getJar(){
		return this.jar;
	}
}
