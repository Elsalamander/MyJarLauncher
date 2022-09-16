package it.elsalamander.view.subPanel.globalMetrics.metriche;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import it.elsalamander.MyJarLauncher;
import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.view.subPanel.globalMetrics.GraphPanel;
import it.elsalamander.view.subPanel.globalMetrics.Metrics;

/*********************************************************************
 * Metrica per la RAM
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v2.0.2
 * 
 *********************************************************************/
public class RAMMetric extends Metrics{
	
	private static final long serialVersionUID = -5352664349206535284L;
	private static final String current = "Corrente";
	private static final String media = "Medio";
	private static final String max = "Massimo";
	private static final String min = "Minimo";
	
	private static final String separator = ": ";
	private static final String nullValue = "-";
	
	private static final double MOLT = 1024*1024*1024;
	private static final double TOTALRAM = MyJarLauncher.getInstance().getSysteminfo().getHardware().getMemory().getTotal() / MOLT;
	
	protected GraphPanel graph;
	
	/**
	 * Create the panel.
	 */
	public RAMMetric(){
		super("RAM", 1);
		
		//avvia il task
		super.startTask();
	}
	
	@Override
	protected void initMetrics(){
		//crea il grafico
		this.graph = new GraphPanel(super.data,TOTALRAM , 0);
	}

	@Override
	protected void createView(){
		//imposta la view
		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//imposta il bordo
		super.setBorder(BorderFactory.createTitledBorder(super.name));
				
		Dimension[] dim = new Dimension[2];
		dim[0] = new Dimension(100, 100);
		dim[1] = new Dimension(100, 100);
		super.createSubPanels(3, dim);
		
		super.addLabelTo(0, current + separator + nullValue, FONT);
		super.addLabelTo(0, media + separator + nullValue, FONT);
		
		super.addLabelTo(1, max + separator + nullValue, FONT);
		super.addLabelTo(1, min + separator + nullValue, FONT);
		
		super.addComponent(2, this.graph);
	}

	@Override
	protected void upDate(){
		double total = MyJarLauncher.getInstance().getOperatesystem().getProcess(MyJarLauncher.getInstance().getPid()).getResidentSetSize();
		for(ExecuteJar jar : MyJarLauncher.getInstance().getContainer().getJars().values()) {
			if(jar.getProcess() != null && jar.getProcess().isAlive()) {
				total += jar.getDataProcess().getData().getResidentSetSize();
			}
		}
		super.addValueToList(total/MOLT);
	}

	@Override
	public void upDateValue() {
		super.upDateValue();
		this.graph.setScores(super.data);
		
		super.text.get(0).setText(current + separator + df.format(super.current));
		super.text.get(1).setText(media + separator +  df.format(super.med));
		super.text.get(2).setText(max + separator +  df.format(super.max));
		super.text.get(3).setText(min + separator +  df.format(super.min));
	}
}
