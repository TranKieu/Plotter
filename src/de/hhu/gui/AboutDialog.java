package de.hhu.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = -2170913200150836089L;

	public AboutDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		//Hintergrundfarbe
		this.setBackground(new Color(255,255,255));
		this.getContentPane().setBackground(new Color(255,255,255));
		//Textinhalt
		JLabel para1=new JLabel("<html><h1>ProPra - Plotter 2.0.11</h1><p>Der ProPra - Plotter 2.0.11 wurde im Rahmen des Programmierpraktikums 2011 der Heinrich-Heine Universität Düsseldorf erstellt von<br><br>Hattice Tavli<br>Tran Viet Dung<br>Huu Tung Nguyen<br>Michael Pott<br>Christian Wolf</p><br><p>Ziel der Anwendung ist es, schnell und unkompliziert einen Funktionsplotter nutzen zu können. Auch stellt dieses Programm eine kostenlose Alternative zu kommerziellen Produkten dar.</p></html>");
		para1.setPreferredSize(new Dimension(280,300));
		para1.setMaximumSize(new Dimension(280,300));
		//Grafik
		JLabel grafik  = new JLabel(new ImageIcon(ClassLoader.getSystemResource("de/hhu/imgs/grafik.jpg")));
		grafik.setPreferredSize(new Dimension(120,250));
		grafik.setMinimumSize(new Dimension(120,250));
		this.getContentPane().setLayout(new GridBagLayout());
		
		//Koordinaten Gridbag
		this.getContentPane().add(grafik,new GridBagConstraints(1, 1, 1, 3, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
		this.getContentPane().add(para1,new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5,5,5,5), 0, 0));

		this.setSize(new Dimension(500,400));
		this.pack();
	}
	
}
