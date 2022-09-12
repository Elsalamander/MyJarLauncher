package it.elsalamander.view.subPanel.globalMetrics;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import it.elsalamander.view.subPanel.SubPanel;
import it.elsalamander.view.subPanel.globalMetrics.metriche.CPUMetric;

public class GlobalMetrics extends SubPanel{
	
	private static final long serialVersionUID = -9016795548581627336L;

	private List<Metrics> metrics;
	
	/**
	 * Create the panel.
	 */
	public GlobalMetrics(){
		this.metrics = new ArrayList<Metrics>();
		
		this.addMetrics(new CPUMetric());
		//...
		
		setLayout(new GridLayout(super.getComponentCount(),0));
		
		super.setBorder(BorderFactory.createTitledBorder("Global Metrics"));
	}
	
	private void addMetrics(Metrics metric) {
		super.add(metric);
		this.metrics.add(metric);
	}

	@Override
	public void close(){
		for(Metrics metr : this.metrics) {
			metr.close();
		}
	}
	
}
