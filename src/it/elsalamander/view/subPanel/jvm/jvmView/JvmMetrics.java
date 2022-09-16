package it.elsalamander.view.subPanel.jvm.jvmView;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;

import it.elsalamander.loader.executeJar.ExecuteOptions;
import it.elsalamander.view.subPanel.globalMetrics.GraphPanel;
import it.elsalamander.view.subPanel.globalMetrics.Metrics;
import oshi.software.os.OSProcess;

/*********************************************************************
 * Metriche nella console
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v1.1.2
 * 
 *********************************************************************/
public class JvmMetrics extends Metrics{
	
	private static final long serialVersionUID = 4662307929554679579L;
	private static final String RamCurr = "RAM Curr";
	private static final String RamMed  = "RAM Med";
	private static final String RamMax  = "RAM Max";
	private static final String RamMin  = "RAM Min";
	private static final String Cpu     = "CPU";
	
	private static final String separator = ": ";
	private static final String nullValue = "-";

	private GraphPanel graph;
	
	private JvmView jvmView;
	private double mult;
	private boolean create;
	private double cpu;
	
	/**
	 * Create the panel.
	 * @param jvmView 
	 */
	public JvmMetrics(JvmView jvmView){
		super(null, 1);
		this.jvmView = jvmView;
		this.create = false;
	}

	@Override
	protected void initMetrics(){
		//crea il grafico
		this.graph = new GraphPanel(super.data, 100, 0);
	}

	@Override
	protected void createView(){
		//imposta la view
		super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				
		Dimension[] dim = new Dimension[2];
		dim[0] = new Dimension(150, 100);
		dim[1] = new Dimension(150, 100);
		super.createSubPanels(3, dim);
		
		super.addLabelTo(0, Cpu + separator + nullValue, FONT);
		super.addLabelTo(0, RamCurr + separator + nullValue, FONT);
		super.addLabelTo(0, RamMed + separator + nullValue, FONT);
		
		super.addLabelTo(1, RamMax + separator + nullValue, FONT);
		super.addLabelTo(1, RamMin + separator + nullValue, FONT);
		
		super.addComponent(2, this.graph);
	}
	
	@Override
	public void paint(Graphics g) {
		//imposta il grafico
		if(!create) {
			ExecuteOptions options = this.jvmView.getJar().getOptions();
			double maxRam = options.getXmx();
			this.mult = options.getMultiply().getMultiplicator();
			this.graph.setMax(maxRam);
			this.create = true;
		}
		super.paint(g);
	}

	@Override
	protected void upDate(){
		OSProcess data = this.jvmView.getJar().getDataProcess().getData();
		if(data == null) {
			super.stopTask();
			return;
		}
		super.addValueToList(data.getResidentSetSize() / this.mult);
		this.cpu = data.getProcessCpuLoadCumulative();
	}

	@Override
	public void upDateValue() {
		super.upDateValue();
		this.graph.setScores(super.data);
		
		super.text.get(0).setText(Cpu + separator + df.format(this.cpu));
		super.text.get(1).setText(RamCurr + separator + df.format(super.current));
		super.text.get(2).setText(RamMed + separator + df.format(super.med));
		super.text.get(3).setText(RamMax + separator + df.format(super.max));
		super.text.get(4).setText(RamMin + separator + df.format(super.min));
	}
	
}
