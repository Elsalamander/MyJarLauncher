package it.elsalamander.view.subPanel.jvm.jvmView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*********************************************************************
 * Interfaccia grafiche per le opzioni di avvio. da finire
 * 
 * 
 * @author: Elsalamander
 * @data: 14 set 2022
 * @version: v1.0.0
 * 
 *********************************************************************/
public class JvmSettings extends JDialog{
	
	private static final long serialVersionUID = -1640036551203278022L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 * @param jvmView 
	 */
	public JvmSettings(JvmView jvmView){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
