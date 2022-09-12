package it.elsalamander.view.subPanel.jvm;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import it.elsalamander.MyJarLauncher;
import it.elsalamander.loader.executeJar.ExecuteJar;
import it.elsalamander.processi.ContainerProcess;
import it.elsalamander.view.subPanel.jvm.jvmList.ElementJvmList;
import it.elsalamander.view.subPanel.jvm.jvmList.ErrorNotValidFile;

public class JvmLoadPanel extends JvmSubPanel{
	
	private static final long serialVersionUID = -1713934939552871137L;

	/**
	 * Create the panel.
	 */
	public JvmLoadPanel(JvmPanel consoles){
		JButton button = new JButton("Load new Jar");
		
		button.addActionListener((event) -> {
			JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if(returnValue == JFileChooser.APPROVE_OPTION){
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            //controlla il file
	            if(!selectedFile.isFile()) {
	            	new ErrorNotValidFile("Non è stato selezionato un file!").setVisible(true);
	            	return;
	            }
	            
	            String name = selectedFile.getName();
	            if(!name.substring(name.length() - 3).equals("jar")) {
	            	new ErrorNotValidFile("Non è stato selezionato un file JAR!").setVisible(true);
	            	return;
	            }
	            
	            
	            name = name.substring(0, name.length() - 4);
	            
	            //carica il file jar
	            ContainerProcess container = MyJarLauncher.getInstance().getContainer();
	            int indexJar = container.loadJar(selectedFile);
	            ExecuteJar execJar = container.get(indexJar);
	            
	            ElementJvmList element = consoles.getList().createNewElement(indexJar, execJar, name);
	            
	            consoles.getList().addElementList(element);
	        }
		});
		
		super.add(button);
	}

	@Override
	public void close(){
		//niente in particolare
	}
	
}
