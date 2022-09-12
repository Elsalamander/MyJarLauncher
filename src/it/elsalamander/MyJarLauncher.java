package it.elsalamander;

import java.awt.EventQueue;

import it.elsalamander.processi.ContainerProcess;
import it.elsalamander.view.MainGui;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

public class MyJarLauncher{
	
	private static MyJarLauncher instance;
	private static final SystemInfo systemInfo = new SystemInfo();
	private static final OperatingSystem operateSystem = systemInfo.getOperatingSystem();
	
	private ContainerProcess container;
	
	
	public static void main(String[] args){
		instance = new MyJarLauncher();
	}
	
	public MyJarLauncher() {
		this.container = new ContainerProcess();
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
	public static SystemInfo getSysteminfo(){
		return systemInfo;
	}

	/**
	 * @return the operatesystem
	 */
	public static OperatingSystem getOperatesystem(){
		return operateSystem;
	}
}
