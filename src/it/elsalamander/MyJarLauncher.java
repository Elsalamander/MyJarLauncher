package it.elsalamander;

import java.awt.EventQueue;
import java.lang.management.ManagementFactory;

import it.elsalamander.processi.ContainerProcess;
import it.elsalamander.view.MainGui;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

/*********************************************************************
 * Classe main del progetto
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v2.1.3
 * 
 *********************************************************************/
public class MyJarLauncher{
	
	private static MyJarLauncher instance;
	private static final SystemInfo systemInfo = new SystemInfo();
	private static final OperatingSystem operateSystem = systemInfo.getOperatingSystem();
	private int pid;
	
	private ContainerProcess container;
	
	
	public static void main(String[] args){
		instance = new MyJarLauncher();
	}
	
	public MyJarLauncher() {
		this.container = new ContainerProcess();
		this.pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		this.start();
	}
	
	public void start() {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					MainGui frame = new MainGui();
					frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @return the instance
	 */
	public static MyJarLauncher getInstance(){
		return instance;
	}

	/**
	 * @return the container
	 */
	public ContainerProcess getContainer(){
		return container;
	}

	/**
	 * @return the systeminfo
	 */
	public SystemInfo getSysteminfo(){
		return systemInfo;
	}

	/**
	 * @return the operatesystem
	 */
	public OperatingSystem getOperatesystem(){
		return operateSystem;
	}

	/**
	 * @return the pid
	 */
	public int getPid(){
		return pid;
	}
	
	
}
