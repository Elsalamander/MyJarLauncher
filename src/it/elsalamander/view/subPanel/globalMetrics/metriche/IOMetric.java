package it.elsalamander.view.subPanel.globalMetrics.metriche;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import it.elsalamander.MyJarLauncher;
import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.view.subPanel.globalMetrics.GraphPanel;
import it.elsalamander.view.subPanel.globalMetrics.Metrics;

/*********************************************************************
 * Tieni il precedente fai (precedente-nuovo)/tempo di campionamento
 * 
 * 
 * 
 * @author: Elsalamander
 * @data: 13 set 2022
 * @version: v2.0.1
 * 
 *********************************************************************/
public class IOMetric extends Metrics{
	
	private static final long serialVersionUID = -5352664349206535284L;
	private static final String currentInput = "Corr.Rd";
	private static final String mediaInput = "Med.Rd";
	private static final String maxInput = "Max.Rd";
	private static final String minInput = "Min.Rd";
	
	private static final String currentOutPut = "Corr.wr";
	private static final String mediaOutPut = "Med.wr";
	private static final String maxOutPut = "Max.wr";
	private static final String minOutPut = "Min.wr";
	
	private static final String separator = ": ";
	private static final String nullValue = "-";
	
	private static final double MULT = 1024*1024*10;
	
	protected GraphPanel graphRead;
	protected GraphPanel graphWrite;
	
	/*
	private double oldRead;
	private double oldWrite;
	*/
	
	private List<Double> writeData;;
	
	/**
	 * Create the panel.
	 */
	public IOMetric(){
		super("I/O", 1);
		
		//avvia il task
		super.startTask();
	}
	
	@Override
	public void resetMetrics() {
		super.resetMetrics();
		
		if(this.writeData == null) {
			this.writeData = new ArrayList<Double>(SIZEGRAPH);
		}
		
		this.writeData.clear();
		for(int i = 0; i < SIZEGRAPH - 1; i++) {
			this.writeData.add(0.0);
		}
	}
	
	@Override
	protected void initMetrics(){
		//crea il grafico
		this.graphRead = new GraphPanel(super.data,100 , 0);
		this.graphWrite = new GraphPanel(this.writeData,100 , 0);
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
		
		super.addLabelTo(0, currentInput + separator + nullValue, FONT);
		super.addLabelTo(0, mediaInput + separator + nullValue, FONT);
		super.addLabelTo(0, maxInput + separator + nullValue, FONT);
		super.addLabelTo(0, minInput + separator + nullValue, FONT);
		
		super.addLabelTo(1, currentOutPut + separator + nullValue, FONT);
		super.addLabelTo(1, mediaOutPut + separator + nullValue, FONT);
		super.addLabelTo(1, maxOutPut + separator + nullValue, FONT);
		super.addLabelTo(1, minOutPut + separator + nullValue, FONT);
		
		super.addComponent(2, this.graphRead);
		super.addComponent(2, this.graphWrite);
	}

	@Override
	protected void upDate(){
		double totalRead = 0;
		double totalWrite = 0;
		
		for(ExecuteJar jar : MyJarLauncher.getInstance().getContainer().getJars().values()) {
			if(jar.getProcess() != null && jar.getProcess().isAlive()) {
				totalRead += jar.getDataProcess().getData().getBytesRead();
				totalWrite += jar.getDataProcess().getData().getBytesWritten();
			}
		}
		for(int i = 0; i < this.writeData.size() - 1; i++) {
			this.writeData.set(i, this.writeData.get(i+1));
		}
		/*
		double tmp = 0.0;
		tmp = totalWrite / MULT;
		totalWrite = ((totalWrite / MULT) - this.oldWrite)/ super.secondTask;
		this.oldWrite = tmp;
		*/
		this.writeData.set(this.writeData.size()-1, totalWrite / MULT);
		
		/*
		tmp = totalRead / MULT;
		totalRead = ((totalRead / MULT) - this.oldRead) / super.secondTask;
		this.oldRead = tmp;
		*/
		super.addValueToList(totalRead / MULT);
	}

	@Override
	public void upDateValue() {
		super.upDateValue();
		this.graphRead.setScores(super.data);
		
		double total = 0;
		for(int i = 0; i < this.writeData.size(); i++) {
			total += data.get(this.writeData.size()-i);
		}
		total /= this.writeData.size();
		this.graphWrite.setScores(this.writeData);
		
		super.text.get(0).setText(currentInput + separator + df.format(super.current));
		super.text.get(1).setText(mediaInput + separator +  df.format(super.med));
		super.text.get(2).setText(maxInput + separator +  df.format(super.max));
		super.text.get(3).setText(minInput + separator +  df.format(super.min));
		
		super.text.get(4).setText(currentOutPut + separator + df.format(this.writeData.get(this.writeData.size()-1)));
		super.text.get(5).setText(mediaOutPut + separator +  df.format(total));
		super.text.get(6).setText(maxOutPut + separator +  df.format(this.graphWrite.getMaxScore()));
		super.text.get(7).setText(minOutPut + separator +  df.format(this.graphWrite.getMinScore()));
	}
}