package it.elsalamander.view.subPanel.globalMetrics;

import java.util.TimerTask;

/*********************************************************************
 * Task per l'aggiornamento dei dati
 * 
 * 
 * @author: Elsalamander
 * @data: 12 set 2022
 * @version: v1.0.0
 * 
 *********************************************************************/
public class MetricTask extends TimerTask {

	private Metrics metric;
	
	public MetricTask(Metrics metric) {
		this.metric = metric;
	}
	
	@Override
	public void run(){
		metric.upDate();
	}
	
}
