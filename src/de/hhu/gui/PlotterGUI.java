/**
 * @author Viet Dung Tran
 *
 * created on 30.06.2011
 */
package de.hhu.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.hhu.gui.actions.*;
import de.hhu.gui.graphics2d.Plot;
import de.hhu.parser.AusdHilfer;


public class PlotterGUI extends JFrame {
	private static final long	serialVersionUID	= 120220102010L;



	// Menu und menuItem deklaration
	private final JMenuBar	menuBar	=	new JMenuBar();
	private final JMenu		datei	= 	new JMenu("Datei");
	private final JMenu  bearbeiten =   new JMenu("Bearbeiten");
	private final JMenu 	hilfe	= 	new JMenu("Hilfe");		

	private final JMenuItem beenden	 = 	new JMenuItem("Beenden");
	private final JMenuItem bestFit	 = 	new JMenuItem("BestFit");
	private final JMenuItem leer  	 = new JMenuItem("neue Plotter");
	private final JMenuItem handbuch = 	new JMenuItem("Handbuch");
	private final JMenuItem export 	 = new JMenuItem("Bild exportieren"); 
	private final JMenuItem about  = new JMenuItem("Ueber");

	static ButtonGroup gWahlPlott 			= new ButtonGroup();
	       JRadioButton rbPlotter			= new JRadioButton("f(x):" );
	       JRadioButton rbmitBedingung		= new JRadioButton("f(x) mit zwei Bedingungen: a = " );

	static JCheckBox cbCharFunk 			= new JCheckBox("Funktionenschar:  a:= "); 
	static JCheckBox cbGeschachtelFunk 		= new JCheckBox("Geschachtelte Funk: g(x)= "); 
	static JCheckBox cbAbleitung            = new JCheckBox("Ableitung");
	static JCheckBox cbFkAusblend           = new JCheckBox("Funktion ausblenden");
	static JCheckBox cbWendePkt           = new JCheckBox("Wendepunkte");
	static JCheckBox cbLokaExtrem         = new JCheckBox("Lokale Extremwerte");
	static JCheckBox cbIntegral           = new JCheckBox("Integralfunktion");
	//static JCheckBox cbGlobExtrem         = new JCheckBox("Globalen Extremwerte");

	public  static JTextField txtPlotter  = 	new JTextField(" x ",250);
	public  static JTextField txtGx		  = 	new JTextField(100);
	public  static JTextField txtChar	  = 	new JTextField(10);
	public  static JTextField txtBedigung = 	new JTextField(10);
	public  static JTextField txtErsteF   = 	new JTextField(150);	
	public  static JTextField txtZweiteF  = 	new JTextField(150);
	public  static JTextField txtFa	      = 	new JTextField(150);
	public static JTextField txtSchritt  =     new JTextField("0.5",10);

	public static JTextField txtIntstart = new JTextField("-10");
	public static JTextField txtIntend   = new JTextField("10");
	public static JTextField txtMinimum  = new JTextField("-10");
	public static JTextField txtMaximum = new JTextField("10");
	
	public static JLabel lbNst = new JLabel("");

	public static String[] colornames = {"Rot","Blau","Gruen","Gelb","Pink","Orange","Cyan","Magenta","Schwarz"};

	public static Color[] colors = {Color.red,Color.blue,Color.green,Color.yellow,
		Color.pink ,Color.orange,Color.cyan,Color.magenta,Color.black};

	public static JComboBox<String> combFuncHandle = new JComboBox<String>();
	public static JComboBox<String> combColorHandle = new JComboBox<String>(colornames);

	JTable table = new JTable( WerteTabelle.leerData, WerteTabelle.COLHEADS);
	JScrollPane scroll  = new JScrollPane(table);

	private final JPanel	Plott 	 	= new JPanel();
	private final JPanel	plOption 	= new JPanel();
	private final JPanel	plBut  	 	= new JPanel();
	private final JPanel	plBedi  	= new JPanel();
	private final JPanel  	plIntervall = new JPanel();
	private final JPanel  	plValues 	= new JPanel();
	private final JPanel  	plSchrittOption = new JPanel();
	private final JPanel	plFunc 		= new JPanel();
	private final JPanel	funktion  	= new JPanel();
    private final JPanel    plNullstellen = new JPanel();

	
	private final JSplitPane graph 		= new JSplitPane();
	private final JTabbedPane tabbedPane= new JTabbedPane();
	private final JScrollPane spWerte = new JScrollPane(plValues);
    private final JScrollPane spNullst = new JScrollPane(plNullstellen);
	private final JButton buGraph	= 	new JButton("Zeichnen");
	private final JButton buGrid = new JButton("Gitter");
	private final JButton buSchritt = new JButton("Berechnen");
	private final JButton buLoes = new JButton("Daten entfernen");

	private final JFileChooser fc = new JFileChooser();

	private static Plot plotPanel;
	private  WerteTabelle tab = new WerteTabelle();


	public PlotterGUI(int w, int h) { // kontruct

		super("Funktionsgraphen-Plotter");
		plotPanel = new Plot(600,600);
		bauGUI();
		registerListener();

		setPreferredSize(new Dimension(w, h));
		setLocation(150, 10);
		setResizable(false);		//kann nicht groesser aederen
		pack();						//alles zusammen bauen
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

	}

	private void bauGUI() {
		//Menu
		setJMenuBar(menuBar);
		menuBar.add(datei);
		datei.add(leer);
		datei.addSeparator();
		datei.add(beenden);		

		menuBar.add(bearbeiten);

		bearbeiten.add(export);
		bearbeiten.add(bestFit);

		menuBar.add(hilfe);
		hilfe.add(handbuch);
		hilfe.add(about);
		//hier weiter fuer Meunu und itMenu add
		rbPlotter.setSelected(true);
		rbPlotter.setActionCommand("dung");
		rbmitBedingung.setActionCommand("");
		gWahlPlott.add(rbPlotter);
		rbPlotter.setToolTipText("Bite Funktion f(x) eintragen");
		gWahlPlott.add(rbmitBedingung);
		rbmitBedingung.setToolTipText("Aktiviert oder Deaktiviert eine Funktion mit zwei Bedingungen");

		add(graph);
		graph.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		graph.setDividerSize(4);                   // Dicke der Linie
		graph.setDividerLocation(300);             //Position der Mittelinie
		graph.setLeftComponent(funktion);			//set linkes Element
		graph.setRightComponent(tabbedPane);

		//Linkes Element	
		funktion.setLayout(new FlowLayout());

		//Plott Panel
		funktion.add(Plott);
		Plott.setBorder(BorderFactory.createTitledBorder("Funktion"));
		Plott.setPreferredSize(new Dimension(299,100));
		Plott.setLayout(new GridBagLayout());
		Plott.add(rbPlotter, erzeugFixedConstraints(0, 0, 1, 1));
		Plott.add(txtPlotter, erzeugHorzConstraints(1, 0, 3, 1, 1));
		Plott.add(cbCharFunk, erzeugFixedConstraints(0, 1, 2, 1));
		cbCharFunk.setToolTipText("Aktiviert oder deaktiviert die Funktionschar");
		Plott.add(txtChar, erzeugHorzConstraints(2, 1, 1, 1, 1));
		Plott.add(cbGeschachtelFunk, erzeugFixedConstraints(0, 2, 2, 1));
		cbGeschachtelFunk.setToolTipText("Aktiviert oder deaktviert die geschachtelte Funktion");
		Plott.add(txtGx, erzeugHorzConstraints(2, 2, 1, 1, 1));

		//Bedingung Panel
		funktion.add(plBedi);
		plBedi.setPreferredSize(new Dimension(299,115));
		plBedi.setLayout(new GridBagLayout());
		plBedi.setBorder(BorderFactory.createTitledBorder("Funktion mit Bedingung"));
		plBedi.add(rbmitBedingung,erzeugFixedConstraints(0, 0, 2, 1));
		plBedi.add(txtBedigung,erzeugFixedConstraints(2, 0, 1, 1));
		plBedi.add(new JLabel("F1(x<a):"),erzeugFixedConstraints(0, 1, 1, 1));
		txtErsteF.setToolTipText("Funktion f�r x kleiner a");
		plBedi.add(txtErsteF, erzeugHorzConstraints(1, 1, 2, 1, 1));
		plBedi.add(new JLabel("F2(x>a):"),erzeugFixedConstraints(0, 2, 1, 1));
		plBedi.add(txtZweiteF, erzeugHorzConstraints(1, 2, 2, 1, 1));
		txtZweiteF.setToolTipText("Funktion f�r x groesser a");
		plBedi.add(new JLabel("F(x=a):"),erzeugFixedConstraints(0, 3, 1, 1));
		plBedi.add(txtFa, erzeugHorzConstraints(1, 3, 2, 1, 1));
		txtFa.setToolTipText("Funktion f�r x gleich a");

		// Intervalloptionen
		funktion.add(plIntervall);
		plIntervall.setPreferredSize(new Dimension(299,75));
		plIntervall.setLayout(new GridBagLayout());
		plIntervall.add(new JLabel("Beginn: "),	erzeugFixedConstraints(0, 0, 1, 1));
		plIntervall.add(txtIntstart,			erzeugHorzConstraints(1, 0, 1, 1,1));
		txtIntstart.setToolTipText("Bitte ganzzahligen Startwert eintragen");
		plIntervall.add(new JLabel("   Ende: "),	erzeugFixedConstraints(2, 0, 1, 1));		
		plIntervall.add(txtIntend, 				erzeugHorzConstraints(3, 0, 1, 1,1));
		txtIntend.setToolTipText("Bitte ganzzahligen Endwert eintragen");
		plIntervall.add(new JLabel("Minimum: "),	erzeugFixedConstraints(0, 2, 1, 1));
		plIntervall.add(txtMinimum, 			erzeugHorzConstraints(1, 2, 1, 1,1));
		txtMinimum.setToolTipText("Bitte ganzzahligen Minimalwert eintragen");
		plIntervall.add(new JLabel("   Maximum: "),	erzeugFixedConstraints(2, 2, 1, 1));
		plIntervall.add(txtMaximum, 			erzeugHorzConstraints(3, 2, 1, 1,1));
		txtMaximum.setToolTipText("Bitte ganzzahligen Maximalwert eintragen");
		plIntervall.setBorder(BorderFactory.createTitledBorder("Intervalloptionen"));

		//add Option
		funktion.add(plOption);
		plOption.setBorder(BorderFactory.createTitledBorder("Plotteroptionen"));
		plOption.setPreferredSize(new Dimension(299,120));
		plOption.setLayout(new BoxLayout(plOption, BoxLayout.Y_AXIS));
		plOption.add(cbWendePkt);
		cbWendePkt.setToolTipText("Aktiviert oder deaktiviert die Anzeige der Wendepunkte");
		plOption.add(cbLokaExtrem);
		cbLokaExtrem.setToolTipText("Aktiviert oder deaktiviert die Anzeige der lokalen Extremwerte");
		//plOption.add(cbGlobExtrem);

		//funktion wahlen
		funktion.add(plFunc);
		plFunc.setBorder(BorderFactory.createTitledBorder(""));
		plFunc.setPreferredSize(new Dimension(299,145));
		plFunc.setLayout(new GridBagLayout());
		plFunc.add(new JLabel("Funktion wahlen : "),erzeugFixedConstraints(0, 0, 1, 1));
		plFunc.add(combFuncHandle,erzeugHorzConstraints(1, 0, 2, 1,1));
		plFunc.add(new JLabel("Farbe wahlen : "),erzeugFixedConstraints(0, 1, 1, 1));
		plFunc.add(combColorHandle,erzeugFixedConstraints(1, 1, 1, 1));
		plFunc.add(cbFkAusblend,erzeugFixedConstraints(0, 2, 1, 1));
		cbFkAusblend.setToolTipText("Aktiviert oder deaktiviert die Funktion");
		plFunc.add(cbAbleitung,erzeugFixedConstraints(0, 3, 1, 1));
		cbAbleitung.setToolTipText("Aktiviert oder Deaktiviert die Ableitung");
		plFunc.add(cbIntegral,erzeugFixedConstraints(0, 4, 1, 1));
		cbIntegral.setToolTipText("Aktiviert oder deaktiviert die Integralfunktion");




		//Zeichen button
		funktion.add(plBut,BorderLayout.PAGE_END);
		plBut.add(buLoes);
		buLoes.setToolTipText("F�hrt Reset der Daten aus");
		
		plBut.add(buGraph);
		plBut.add(buGrid);
		buGrid.setToolTipText("Aktiviert oder deaktiviert das Gitter");


		//Rechtes Element

		tabbedPane.addTab( "Graphen", plotPanel );
		tabbedPane.addTab( "Wertetabelle", spWerte);
		tabbedPane.addTab("Nullstellen",spNullst);
	
		plotPanel.setLocation(0,-50);
		//tabb 		
		plValues.setLayout(new BorderLayout());
		plValues.add(plSchrittOption,BorderLayout.PAGE_START);
		plSchrittOption.add(new JLabel("Schrittweite :="));
		plSchrittOption.add(txtSchritt);
		plSchrittOption.add(buSchritt);			
		plValues.add(scroll);

	}




	/**
	 * Methode fuer ActionListener
	 */
	private void registerListener() {

		/* Menubar Ereignis(Event)*/

		// GUI beenden
		beenden.addActionListener(new ActionListener(){           
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// alles Funktion loesen
		leer.addActionListener(new ActionListener(){           
			public void actionPerformed(ActionEvent e){

				plotPanel.plotManager.removeAll();
				plotPanel.plotManager.add(plotPanel.PlotBase,new Integer(105));
				plotPanel.plotManager.add(plotPanel.Gitter,new Integer(100));


				plotPanel.functions.clear();
				plotPanel.derivatives.clear();

				plotPanel.revalidate();
				plotPanel.index = 0;

				combFuncHandle.removeAllItems();
				plValues.remove(scroll);
				plValues.revalidate();

			}
		});

		// handbuch oeffen
		handbuch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					File pdf = new File("resource/Dokumentation.pdf");
					System.out.println(pdf.getAbsolutePath());
					Desktop.getDesktop().open(pdf);
				}catch (Exception ie ){
					System.err.println(ie.toString());
				}
			}
		});

		//ueber Uns
		about.addActionListener(new ActionListener(){           
			public void actionPerformed(ActionEvent e) {
				JDialog diag = new AboutDialog(PlotterGUI.this,"ueber uns",true);
				diag.setVisible(true);

			}
		});

		// BestFist oeffen
		bestFit.addActionListener(new ActionListener(){ 

			public void actionPerformed(ActionEvent e) { 
				new bestFitGUI();
			}
		}); 

		// Bild exportieren
		export.addActionListener(new ActionListener(){ 

			public void actionPerformed(ActionEvent e) { 
				if(plotPanel==null){
					JOptionPane.showMessageDialog(PlotterGUI.this, "Es wurde noch kein Graph gezeichnet!","Fehler",JOptionPane.INFORMATION_MESSAGE); //Fehlerdialog

					return; //"rest" wird nicht mehr ausgef�hrt
				}
				int rueckgabe = fc.showSaveDialog(PlotterGUI.this); //geh�rt zum fenster flotGUI

				if (rueckgabe == JFileChooser.APPROVE_OPTION) { //vergleicht "werte"
					File file = fc.getSelectedFile(); //ausgewaehlte datei

					BufferedImage img = new BufferedImage(plotPanel.getWidth(), plotPanel.getHeight(), BufferedImage.TYPE_INT_RGB);  // bekommt vom plotpanel (rechte seite)die breite und hoehe in rgb
					Graphics2D g2d=img.createGraphics(); 
					plotPanel.paint(g2d); //benutzt zeichenfunktion 

					// Durchlaufe alle JPanels f�r die Funktionsgraphen und Zeichen diese in das Bild
					for(int x=0;x<plotPanel.functions.size();x++){
						plotPanel.functions.get(x).DrawPanel.paintComponents(g2d); 
					}
					try {
						ImageIO.write(img, "jpg", file); //versucht in ausgewaehlter datei zu schreiben
					} catch (IOException e1) { //faengt fehler auf

						e1.printStackTrace(); //beschreibung des fehlers in konsole
					}

				} 
			}
		});



		/* Button Event			 */
		//Gitter einblende
		buGrid.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				if(plotPanel.Gitter.isVisible() ==true){
					plotPanel.Gitter.setVisible(false);
					plotPanel.reloadFunctions();
				}else{
					plotPanel.Gitter.setVisible(true);
					plotPanel.reloadFunctions();
				}	
			}		
		});

		//Plotter Zeichen ->> Wichtig..
		buGraph.setToolTipText("Zeichnet den Graphen");
		buGraph.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(intevallFehleHandel()){
					plotPanel.setMinimum(Double.parseDouble(getTxtMinimum()));	            	 
					plotPanel.setMaximum(Double.parseDouble(getTxtMaximum()));
					plotPanel.setIntstart(Double.parseDouble(getTxtIntstart()));
					plotPanel.setIntend(Double.parseDouble(getTxtIntend()));

					plotPanel.plotManager.removeAll();
					plotPanel.plotManager.add(plotPanel.PlotBase,new Integer(105));
					plotPanel.plotManager.add(plotPanel.Gitter,new Integer(100));
					plotPanel.plotManager.add(plotPanel.labels,new Integer(95));




					for(int j=0; j<plotPanel.functions.size();j++){
						plotPanel.functions.elementAt(j).valueAll();
					}

					for(int j=0;j<plotPanel.derivatives.size();j++){
						plotPanel.derivatives.elementAt(j).evaluate();
					}

					plotPanel.setVerticalMetric();
					plotPanel.setHorizontalMetric();
					plotPanel.PlotBase.CreateAxis();
					plotPanel.reloadFunctions();
					plotPanel.reloadDerivatives();
					plotPanel.Gitter.CreateGrid();


					boolean isDefined = false;
					if(AusdHilfer.IstPlott(getFunktion())||(!getWahle())){// Fehle handeln
						try{
							plotPanel.newFunction(getFunktion());
							for(int j=0;j < combFuncHandle.getItemCount();j++){
								if(getFunktion().equals(combFuncHandle.getItemAt(j))){
									isDefined = true;
									break;
								}
							}
							if(isDefined == false){
								combFuncHandle.addItem(getFunktion());
							}
						}catch (Exception et) { // Fehle fuer Funktion mit bedingung
							Fehler.undefinedString("Undefined String !");
						}
					} else{
						Fehler.undefinedString("Undefined String !");
					}

				}}
		});

		//berechen Tablewert
		buSchritt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setWertetabelle();
				plValues.revalidate();
			}
		});

		//alles Function losen = neue Plotter
		buLoes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				plotPanel.plotManager.removeAll();
				plotPanel.plotManager.add(plotPanel.PlotBase,new Integer(105));
				plotPanel.plotManager.add(plotPanel.Gitter,new Integer(100));


				plotPanel.functions.clear();
				plotPanel.derivatives.clear();

				plotPanel.revalidate();
				plotPanel.index = 0;

				combFuncHandle.removeAllItems();
				plValues.remove(scroll);
				plValues.revalidate();
			}
		});


		/* Checkbox Event			 */
		
		
	

		//ableitung checkbox
		cbAbleitung.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				boolean isDefined = false;
				int k = 0;
				try{
					while( k < plotPanel.functions.size()){
						if(plotPanel.functions.elementAt(k).funcString.equals((String)combFuncHandle.getSelectedItem())){
							break;
						}else{
							k++;
						}
					}

					if(cbAbleitung.isSelected()== true){
						int j =0;
						while(j<plotPanel.derivatives.size()){
							if(plotPanel.derivatives.elementAt(j).funcString.equals((String)combFuncHandle.getSelectedItem())){
								isDefined = true;
								break;
							}else{

								j++;
							}


						}

						if(isDefined == false){	
							plotPanel.newDerivative((String)combFuncHandle.getSelectedItem());	
							plotPanel.functions.elementAt(k).ableitung.setVisible(true);
							plotPanel.functions.elementAt(j).setCbAbleitung(true);
							plotPanel.reloadFunctions();
							plotPanel.reloadDerivatives();
							plotPanel.revalidate();
						}else{
							plotPanel.functions.elementAt(k).ableitung.setVisible(true);
							plotPanel.reloadFunctions();
							plotPanel.reloadDerivatives();
							plotPanel.revalidate();
						}

					}else{

						plotPanel.functions.elementAt(k).setCbAbleitung(false);
						plotPanel.functions.elementAt(k).hideDerivative();
						plotPanel.reloadFunctions();
						plotPanel.reloadDerivatives();
						plotPanel.revalidate();
					}



				}catch(Exception eAbleitung){
				};
			}

		});

		

		//wendepunkt anzeigen
		cbWendePkt.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				int k = 0;
				try{
					while( k < plotPanel.functions.size()){
						if(plotPanel.functions.elementAt(k).funcString.equals((String)combFuncHandle.getSelectedItem())){
							break;
						}else{
							k++;
						}
					}

					if(cbWendePkt.isSelected()==true){

						plotPanel.functions.elementAt(k).setWendepunkte();
						plotPanel.functions.elementAt(k).setCbWendepkt(true);
						plotPanel.functions.elementAt(k).DrawPanel.repaint();
						plotPanel.reloadFunctions();
						plotPanel.reloadDerivatives();
					}else{
						plotPanel.functions.elementAt(k).DrawPanel.HideWendepunkt();
						plotPanel.functions.elementAt(k).setCbWendepkt(false);
						plotPanel.functions.elementAt(k).DrawPanel.repaint();
						plotPanel.reloadFunctions();
						plotPanel.reloadDerivatives();
					}

				}catch(Exception eWendepunkt){}

			}

		});

		//Global Extrem
		/*cbGlobExtrem.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				//hier	

			}
		});
		*/

		//local Extrem
		cbLokaExtrem.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				int k = 0;
				try{
					while( k < plotPanel.functions.size()){
						if(plotPanel.functions.elementAt(k).funcString.equals((String)combFuncHandle.getSelectedItem())){
							break;
						}else{
							k++;
						}
					}

					if(cbLokaExtrem.isSelected()==true){

						plotPanel.functions.elementAt(k).setHochpunkte();
						plotPanel.functions.elementAt(k).setTiefpunkte();
						plotPanel.functions.elementAt(k).setCbLokaExtrem(true);
						plotPanel.functions.elementAt(k).DrawPanel.repaint();
						plotPanel.reloadFunctions();
						plotPanel.reloadDerivatives();
					}else{
						plotPanel.functions.elementAt(k).DrawPanel.HideHochpunkt();
						plotPanel.functions.elementAt(k).DrawPanel.HideTiefpunkt();
						plotPanel.functions.elementAt(k).setCbLokaExtrem(false);
						plotPanel.functions.elementAt(k).DrawPanel.repaint();
						plotPanel.reloadFunctions();
						plotPanel.reloadDerivatives();
					}


				}catch(Exception eLokaExtrem){}

			}

		});

		//gewahlende Funktion ausblenden
		cbFkAusblend.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(cbFkAusblend.isSelected() == true){
					int j = 0;
					while(j<plotPanel.functions.size()){
						if(plotPanel.functions.elementAt(j).funcString.equals((String)combFuncHandle.getSelectedItem())){
							plotPanel.functions.elementAt(j).setVisible(false);
							plotPanel.functions.elementAt(j).setCbFkAusblend(true);
						}
						j++;
					}
					plotPanel.reloadFunctions();
					plotPanel.reloadDerivatives();
					plotPanel.revalidate();
				}
				if(cbFkAusblend.isSelected() == false){
					int j = 0;
					while(j<plotPanel.functions.size()){
						if(plotPanel.functions.elementAt(j).funcString.equals((String)combFuncHandle.getSelectedItem())){
							plotPanel.functions.elementAt(j).setVisible(true);
							plotPanel.functions.elementAt(j).setCbFkAusblend(false);
						}
						j++;
					}
					plotPanel.reloadFunctions();
					plotPanel.reloadDerivatives();
					plotPanel.revalidate();
				}
			}
		});


		/* Combobox Event			 */
		//funktion wahlen dann neue werteTabelle darstellen
		combFuncHandle.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setWertetabelle();
				plValues.revalidate();
				int k = 0;

				while( k < plotPanel.functions.size()){
					if(plotPanel.functions.elementAt(k).funcString.equals((String)combFuncHandle.getSelectedItem())){
						break;
					}else{
						k++;
					}

				}   
				if(combFuncHandle.getItemCount()!=0){
					cbWendePkt.setSelected(plotPanel.functions.elementAt(k).getCbWendepkt()); 
					//cbGlobExtrem.setSelected(plotPanel.functions.elementAt(k).getCbGlobExtrem());
					cbLokaExtrem.setSelected(plotPanel.functions.elementAt(k).getCbLokaExtrem());
					cbFkAusblend.setSelected(plotPanel.functions.elementAt(k).getCbFkAusblend());
					cbAbleitung.setSelected(plotPanel.functions.elementAt(k).getCbAbleitung());
				}



			}
		});

		//Farbe fuer Funktion wahlen
		combColorHandle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int colorindex;
				int index = -1;
				colorindex = combColorHandle.getSelectedIndex();
				for(int j = 0;j<plotPanel.functions.size(); j++){
					if(combFuncHandle.getSelectedItem().equals(plotPanel.functions.elementAt(j).funcString)){
						index = j;
						break;
					}

				}
				if(index > -1){
					plotPanel.functions.elementAt(index).setColor(colors[colorindex]);
					plotPanel.reloadFunctions();
					plotPanel.reloadDerivatives();
				} 
			}	
		});

		/*	Tab event	*/
		tabbedPane.addChangeListener(new ChangeListener(){	    		
			public void stateChanged(ChangeEvent e){
				String tabtitle;
				tabtitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				if(tabtitle.equals("Graphen")){
					plotPanel.reloadFunctions();
					plotPanel.reloadDerivatives();
					plotPanel.revalidate();
				}	
				if(tabtitle.equals("Wertetabelle")){
					setWertetabelle();  
				} 
				if(tabtitle.equals("Nullstellen")){
					
					if(plotPanel.functions.size()>0){
					plNullstellen.add(lbNst);
					int j =0;
					ArrayList<String> inNst = NST.null_stelle((String)combFuncHandle.getSelectedItem(), plotPanel.getIntstart(), plotPanel.getIntend());
					String text = new String("<b> Nullstellen im angegeben Intervall </b>");	
					while(j <inNst.size()){
					text = text + "<li>"+inNst.get(j) +"<br>";
					System.out.println(text);
					j++;
					}
					lbNst.setText("<html>"+text+"<html>");
					
					}
				 }
			}			    	
		});
	}
    


	/**
	 * erzeugFixedConstraints:  (Label)
	 * erzeugHorzConstraints:  (TextField)
	 * GridBagConstraints( int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady )
	 */

	public static GridBagConstraints erzeugFixedConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
		return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 2, 1, 1), 0, 0);
	}

	public static GridBagConstraints erzeugHorzConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx) {
		return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 2, 1, 1), 0, 0);

	}
	//<-- GUI END-->


	//Wert fuer Tabelle anzeigen
	public void setWertetabelle(){

		if((combFuncHandle.getSelectedItem()==null)){	

		}else{
			plValues.remove(scroll);
			tab.setfuncString((String)combFuncHandle.getSelectedItem());
			tab.setStartInterval(plotPanel.getIntstart());
			tab.setEndeInterval(plotPanel.getIntend());
			tab.setSchritt(Double.parseDouble(txtSchritt.getText()));
			table = new JTable(tab.rowData(), WerteTabelle.COLHEADS);
			scroll = new JScrollPane(table);
			plValues.add(scroll);

		}
	}
	/**
	 * ueberpruefen, ob Intevalloption richtig oder nicht
	 * @return true wenn Intevalloption richtig ít
	 * 
	 */
	public boolean intevallFehleHandel(){

		if(!AusdHilfer.isZahl(getTxtMaximum())||!AusdHilfer.isZahl(getTxtMinimum())||!AusdHilfer.isZahl(getTxtIntend())||!AusdHilfer.isZahl(getTxtIntstart())){
			Fehler.undefinedString("Undefined Intervall !");
			return false;
		}else{
			double start = Double.parseDouble(getTxtIntstart());
			double ende  = Double.parseDouble(getTxtIntend());
			double max   = Double.parseDouble(getTxtMaximum());
			double min   = Double.parseDouble(getTxtMinimum());
			if((start<ende)&&(min<max)){
				return true;
			} else{
				Fehler.undefinedString("Undefined Intervall !");
				return false;
			}
		}
	}

	/**
	 * methoden fuer get value von GUI
	 */
	/**
	 * @return plotter String
	 */
	public static String getFunktion( ){

		boolean bedingung = getWahle();

		if(bedingung){	// normale Funktion
			boolean isChar 		= getAusgewahltCharFunk();
			boolean isGeschachlt	= getAusgewahltGeschachtel();

			String func 			= getFx();
			if(isChar){// wenn f(x) Funktionschar ist, wird Char ersetzt
				String wertChar = getChar();
				func = FunktionsChar.charErsetzen(func, wertChar);
			}
			if(isGeschachlt){
				String gx = getGx();
				func = GeschachtelteFunktion.gxErsetzen(func, gx);
			}

			return func;
		} else {	//Funktionen mit zwei Bedingungen

			return "Funktion mit Bedingung";
		}
	}
	/**
	 * @return Funktion f(x)
	 */
	public static String getFx(){
		return txtPlotter.getText();
	}

	/**
	 * @return Funktion g(x)
	 */
	public static String getGx(){

		return txtGx.getText();

	}

	/**
	 * @return ob Funktion mit Bedingung oder nicht ist
	 */
	public static boolean getWahle(){
		String gewaehlt = gWahlPlott.getSelection().getActionCommand();
		if(gewaehlt.equalsIgnoreCase("dung"))
			return true;
		return false;
	}

	/**
	 * @return Char a fuer Funktionschar
	 */
	public static String getChar() {
		return txtChar.getText();
	}
	/**
	 * @return BedingungWert
	 */
	public static double getBedingungWert(){
		if(AusdHilfer.isZahl(txtBedigung.getText())==false){
			Fehler.undefinedString("Bedingung muss Zahl sein!");
			txtBedigung.setText("");
			txtBedigung.revalidate();
			return 0;
		}
		return Double.parseDouble(txtBedigung.getText());
	}
	/**
	 * @return F1(x)
	 */
	public static String getErsteF() {
		return txtErsteF.getText();
	}

	/**
	 * @return F2(x)
	 */
	public static String getZweiteF() {
		return txtZweiteF.getText();
	}

	/**
	 * @return F(x=a)
	 */
	public static String getTxtFa() {
		return txtFa.getText();
	}

	/**
	 * 
	 * @return Intstart
	 */
	public static String getTxtIntstart(){
		return txtIntstart.getText();
	}
	/**
	 * 
	 * @return Intend
	 */
	public static String getTxtIntend(){
		return txtIntend.getText();
	}
	/**
	 * 
	 * @return Minimum 
	 */
	public static String getTxtMinimum(){
		return txtMinimum.getText();
	}

	/**
	 * 
	 * @return Maximum
	 */
	public static String getTxtMaximum(){
		return txtMaximum.getText();
	}

	/**
	 * 
	 * @param j
	 * @return combFuncHandle.getItemAt(j)
	 */

	public static String getFuncHandleItemAt(int j){

		if(combFuncHandle.getItemAt(j)!=null){
			return (String) combFuncHandle.getItemAt(j);
		}else{
			return null;
		}
	}

	/**
	 * @return ob Funktionschar gewaehlt oder nicht
	 */
	public static boolean getAusgewahltCharFunk() {
		return cbCharFunk.isSelected() ;
	}
	/**
	 * @return ob Geschachtelte Funktionen gewählt oder nicht
	 */
	public static boolean getAusgewahltGeschachtel() {
		return cbGeschachtelFunk.isSelected() ;
	}

	/**
	 * Main 
	 */
	public  static void main(String[] agrs) throws IOException{

		new PlotterGUI(920, 675);
	}

}
