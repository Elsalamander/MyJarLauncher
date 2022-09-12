package it.elsalamander.view.subPanel.globalMetrics;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Metrics extends JPanel{
	
	private static final long serialVersionUID = 6835793251262495491L;

	public static final Font FONT = new Font(Font.DIALOG, Font.PLAIN, 14);
	private static final int SIZEGRAPH = 60;

	protected String name;
	protected long current;
	protected long med;
	protected long max;
	protected long min;
	protected List<Double> data;
	
	private JPanel[] subPanels;
	protected List<JLabel> text;
	
	protected MetricTask task;
	protected int secondTask;
	protected Timer timer;
	
	public Metrics(String title, int second) {
		this.name = title;
		
		//resetta tutti i valori
		this.resetMetrics();
		
		//funzione chiamata prima della creazione della view
		this.initMetrics();
		
		//crea la view
		this.createView();
		
		//imposta la view
		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//imposta il bordo
		super.setBorder(BorderFactory.createTitledBorder(title));
		
		//crea il task
		this.task = new MetricTask(this);
		this.secondTask = second;
	}

	/**
	 * Funzione chiamata durante la costruzione
	 * ma prima della creazione della view
	 * per la costruzione di oggetti intermedi
	 */
	protected abstract void initMetrics();

	/**
	 * Resetta tutti i valori
	 */
	public void resetMetrics(){
		this.current = 0;
		this.max = 0;
		this.med = 0;
		this.min = 0;
		this.data = new ArrayList<Double>(SIZEGRAPH);
		
        for(int i = 0; i < SIZEGRAPH + 1; i++) {
        	this.data.add(0.0);
        }
	}
	
	/**
	 * Funzione chiamata ogni X secondi dal task
	 * allegato per aggiornar i valori
	 */
	protected abstract void upDate();
	
	/**
	 * Aggiorna i valori telemetrici,
	 * da chiamaere dopo
	 * addValueToList
	 */
	protected void upDateValue() {
		this.max = (long) this.getMaxScore();
		this.min = (long) this.getMinScore();
		
		this.current = this.data.get(SIZEGRAPH-1).longValue();
		
		double total = 0;
		for(int i = 0; i < SIZEGRAPH; i++) {
			total += data.get(60-i);
		}
		this.med = (long) (total/60);
	}
	
	/**
	 * Aggiungi valore al grafico
	 * @param val
	 */
	protected void addValueToList(double val) {
		for(int i = 0; i < this.data.size() - 1; i++) {
			this.data.set(i, this.data.get(i+1));
		}
		this.data.set(this.data.size()-1, val);
		this.upDateValue();
	}
	
	/**
	 * Crea la view
	 */
	protected abstract void createView();
	
	/**
	 * Aggiungi una label che si aggiorna
	 * @param i
	 * @param string
	 */
	protected void addLabelTo(int indexPanel, String string, Font font){
		if(this.text == null) {
			this.text = new ArrayList<JLabel>();
		}
		JLabel label = new JLabel(string);
		if(font != null) {
			label.setFont(font);
		}
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.text.add(label);
		this.addComponent(indexPanel, label);
	}

	/**
	 * Aggiungi un qualsiasi componente
	 * @param indexPanel 
	 * @param component
	 */
	protected void addComponent(int indexPanel, JComponent component){
		this.subPanels[indexPanel].add(component);
	}
	
	public void addComponent(int indexPanel,Component component){
		this.subPanels[indexPanel].add(component);
	}

	/**
	 * Crea i subPanels
	 * @param num
	 * @param dimension
	 */
	protected void createSubPanels(int num, Dimension[] dimension){
		this.subPanels = new JPanel[num];
		for(int i = 0; i < num; i++) {
			this.subPanels[i] = new JPanel();
			
			this.subPanels[i].setLayout(new BoxLayout(this.subPanels[i], BoxLayout.Y_AXIS));
			
			super.add(this.subPanels[i]);
		}
        
        //size delle colonne
		if(dimension != null) {
			for(int i = 0; i < dimension.length; i++) {
				this.subPanels[i].setPreferredSize(dimension[i]);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
        g.dispose();
    }
	
	/**
	 * Avvia il task se non è già in esecuzione
	 */
	public void startTask() {
		//avvia il task
        if(this.timer == null) {
        	this.timer = new Timer();
        	this.timer.schedule(task, 0, this.secondTask * 1000);
        }
	}
	
	/**
	 * Ferma il task
	 */
	public void stopTask() {
		this.task.cancel();
		this.timer = null;
	}
	
	/**
     * Valore minimo
     * @return
     */
    public double getMinScore(){
        double minScore = Double.MAX_VALUE;
        for (Double score : this.data) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }
    

    /**
     * Valore massimo
     * @return
     */
    public double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : this.data) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

	protected void close() {
		if(this.task != null) {
			this.task.cancel();
		}
	}
	
}
