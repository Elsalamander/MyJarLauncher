package it.elsalamander.view.subPanel.globalMetrics.metriche;

import java.awt.Dimension;

import it.elsalamander.MyJarLauncher;
import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.view.subPanel.globalMetrics.GraphPanel;
import it.elsalamander.view.subPanel.globalMetrics.Metrics;

public class CPUMetric extends Metrics{
	
	private static final long serialVersionUID = -5352664349206535284L;
	private static final String current = "Corrente";
	private static final String media = "Medio";
	private static final String max = "Massimo";
	private static final String min = "Minimo";
	
	private static final String separator = ": ";
	private static final String nullValue = "-";
	
	protected GraphPanel graph;
	
	/**
	 * Create the panel.
	 */
	public CPUMetric(){
		super("CPU", 1);
		
		//avvia il task
		super.startTask();
	}
	
	@Override
	protected void initMetrics(){
		//crea il grafico
		this.graph = new GraphPanel(super.data);
	}

	@Override
	protected void createView(){
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
		double total = 0;
		
		for(ExecuteJar jar : MyJarLauncher.getInstance().getContainer().getJars().values()) {
			if(jar.getProcess() != null && jar.getProcess().isAlive()) {
				total += jar.getDataProcess().getData().getProcessCpuLoadCumulative();
			}
		}
		super.addValueToList(total);
	}

	@Override
	public void upDateValue() {
		super.upDateValue();
		this.graph.setScores(super.data);
		
		super.text.get(0).setText(current + separator + super.current);
		super.text.get(1).setText(media + separator + super.med);
		super.text.get(2).setText(max + separator + super.max);
		super.text.get(3).setText(min + separator + super.min);
	}
}
