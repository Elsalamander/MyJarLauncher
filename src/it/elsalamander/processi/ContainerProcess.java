package it.elsalamander.processi;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import it.elsalamander.loader.executeJar.ExecuteJar;

/*********************************************************************
 * Contenitore di tutti gli oggetti per i processi da eseguire
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v2.0.3
 * 
 *********************************************************************/
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
		if(this.contain(file)) {
			return -1;
		}
		
		ExecuteJar jar = new ExecuteJar(file);
		this.jars.put(this.count, jar);
		this.count++;
		return this.count-1;
	}

	/**
	 * Controlla se questo file è gia stato caricato
	 * @param file
	 * @return
	 */
	public boolean contain(File file){
		for(ExecuteJar jar : this.jars.values()) {
			if(jar.getFile().getPath().equals(file.getPath())) {
				return true;
			}
		}
		return false;
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