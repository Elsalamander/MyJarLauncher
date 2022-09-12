package it.elsalamander.processi;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import it.elsalamander.loader.executeJar.ExecuteJar;

public class ContainerProcess{
	
	private Map<Integer, ExecuteJar> jars;
	private int count;

	/**
	 * 
	 */
	public ContainerProcess(){
		super();
		this.jars = new TreeMap<Integer, ExecuteJar>();
		this.count = 0;
	}
	
	public int loadJar(File file) {
		ExecuteJar jar = new ExecuteJar(file);
		this.jars.put(this.count, jar);
		this.count++;
		return this.count-1;
	}

	/**
	 * @return the jars
	 */
	public Map<Integer, ExecuteJar> getJars(){
		return jars;
	}

	public ExecuteJar get(int index){
		return this.jars.get(index);
	}
	
	
}