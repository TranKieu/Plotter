/**
 * @author Viet Dung Tran
 *
 * created on 01.08.2011
 */
package de.hhu.gui.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.hhu.gui.Fehler;

public class bestFitGUI extends JFrame{
	private static final long	serialVersionUID	= 120220102010L;
	
	private String[] COLHEADS = { "x-Wert","y-Wert"};
	private String[][] leerData = {};

	
	private final JSplitPane jspanel 	= new JSplitPane();
	
	private final JPanel	plPolynom	= new JPanel();
	private final JPanel	plEingabe	= new JPanel();
	private final JPanel	plTabele	= new JPanel();
	private final JPanel	plManuel	= new JPanel();
	private final JPanel	plVonTxt	= new JPanel();
	private final JPanel	plPoly		= new JPanel();
	private final JPanel	plHeader	= new JPanel();

	private JComboBox<String>  combAnzahlStuetz = new JComboBox<String>();
	JTable table = new JTable(leerData,COLHEADS);
	JTable tab ;
	private JScrollPane scroll = new JScrollPane(table);
	private static JTextField jlShowPoly = new JTextField("");
	private final JButton buEinlesen = new JButton("Daten einlesen");
	private final JButton buPolynom = new JButton("Polynom bestimmen");
	
	public bestFitGUI() {
		// TODO Auto-generated constructor stub
		super("Best-Fit");
		setLocation(400,150);
		setSize(450,500);
		setVisible(true);	
		setResizable(false);
		
		bauGUI();
		registerListener();
		
	}

	private void bauGUI() {
		// TODO Auto-generated method stub
		setLayout(new FlowLayout());
		add(plPolynom);
		plPolynom.setPreferredSize(new Dimension(435,50));
		plPolynom.setBorder(BorderFactory.createTitledBorder(""));
		plPolynom.setLayout(new GridBagLayout());
		plPolynom.add(new JLabel("Polynom : "),erzeugFixedConstraints(0, 0, 1, 1));
		plPolynom.add(jlShowPoly, erzeugHorzConstraints(1, 0, 3, 1, 1));
		
		add(jspanel);
		jspanel.setPreferredSize(new Dimension(425,395));
		jspanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		jspanel.setDividerSize(4);                  // Dicke der Linie
		jspanel.setDividerLocation(250);            //Position der Mittelinie
		jspanel.setLeftComponent(plEingabe);		//set linkes Element
		jspanel.setRightComponent(plTabele);
		
		
		//Linke Element
		plEingabe.setLayout(new BorderLayout());
		plEingabe.add(plManuel,BorderLayout.PAGE_START);
		plManuel.setPreferredSize(new Dimension(245,65));
		plManuel.setLayout(new GridBagLayout());
		plManuel.setBorder(BorderFactory.createTitledBorder("Manuel Wertetable eingeben"));
		// Box befuellen, maximal 99 Stuetzstellen, ansonsten einlesen
		for(int i=1;i<100;i++){
			combAnzahlStuetz.addItem(String.valueOf(i));
		}
		plManuel.add(new JLabel("Anzahl der Startpunkte :"),erzeugFixedConstraints(0, 0, 1, 1));
		plManuel.add(combAnzahlStuetz,erzeugHorzConstraints(1, 0, 1, 1,1));
		
		plEingabe.add(plVonTxt);
		plVonTxt.setBorder(BorderFactory.createTitledBorder(""));
		plVonTxt.add(new JLabel("Eingabe von Text :"),erzeugFixedConstraints(0, 1, 1, 1));
		plVonTxt.add(buEinlesen,erzeugHorzConstraints(1, 1, 1, 1,1));

		plEingabe.add(plPoly,BorderLayout.PAGE_END);
		plPoly.add(buPolynom);
		
		
		//Rechte Element
		plTabele.setLayout(new BorderLayout());
		plTabele.add(plHeader, BorderLayout.PAGE_START);
		plHeader.add(new JLabel("Wertetabelle"));
		plTabele.add(scroll,BorderLayout.CENTER );
	}

	private void registerListener() {
		// TODO Auto-generated method stub
		
		combAnzahlStuetz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                      int k = 0;
                      if(k == Integer.parseInt((String)combAnzahlStuetz.getSelectedItem())){
                    	  
                      }else if(k < Integer.parseInt((String)combAnzahlStuetz.getSelectedItem()) || k > Integer.parseInt((String)combAnzahlStuetz.getSelectedItem())){
                    	  setTabelle(Integer.parseInt((String)combAnzahlStuetz.getSelectedItem()));  
                      } 
				}
			});
		
		// bestimen die Polynom
		buPolynom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					double[][] Data = getData();
					int n = Data.length;
					double[] startx = new double[n];
					double[] starty = new double[n];
					for(int i = 0; i<n;i++){
						startx[i]=Data[i][0];
						starty[i]=Data[i][1];
					}
					jlShowPoly.setText(getPolynom(startx, starty));
				}catch(Exception e3){			}
			}
		});
		//wahlen ein Textdatein ein. dann darstellen die Wertetabelle
		buEinlesen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			    JFileChooser fc = new JFileChooser();
			    fc.setFileFilter( new FileNameExtensionFilter("text dateien", "txt" ) );
			    fc.setSelectedFile(new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"filename.txt"));
			    int state = fc.showOpenDialog( null );
			    
			    if ( state == JFileChooser.APPROVE_OPTION ){
			       File file = fc.getSelectedFile();
				   schreibTab(lesenDatei(file));
			    }
			    else
			     Fehler.undefinedString("Geben Sie Textdatei ein !");
			  }
		});
		
		
	}
	
	
	  public static GridBagConstraints erzeugFixedConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
	        return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 0, 0,
	                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 2, 1, 1), 0, 0);
	    }
	       
	    public static GridBagConstraints erzeugHorzConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx) {
	        return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, 0.0,
	                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 2, 1, 1), 0, 0);
	       
	    }
	    /* <--GUI ENDE-->*/
	    /**
	     * 
	     */
		public void setTabelle(int k){
			
		    plTabele.remove(scroll);
		    String[][] DATA = new String[k][2];	
		    table = new JTable(DATA,COLHEADS);
			scroll = new JScrollPane(table);
			plTabele.add(scroll);
			table.setFillsViewportHeight(true);
			plTabele.revalidate();
			}
		/**
		 * Hollen alles Wert in Wertetabelle. Schreiben ins Array
		 * @return double Array[ ][ ]
		 */
		public double[][] getData(){
			int index = table.getRowCount();
			double[][] Data = new double[index][2];
			for(int i = 0; i < index; i++){
				double x = 0,y=0; 
				try{
					x = Double.parseDouble((String)table.getValueAt(i,0));
					}catch(Exception e){}
				Data[i][0] = x;
				try{
					y = Double.parseDouble((String)table.getValueAt(i,1));
					}catch(Exception e){}
					Data[i][1] = y;
			}
			
			return Data;			
		}
		/**
		 * Textdatei einlesen. Textdatei muss in Form
		 *<br> #kommentar, aber muss in eine Zeile schreiben
		 *<br>#x-wert,y-wert in jede zeile 
		 * 
		 * @param File
		 * @return ArrayList<String>
		 */
		public ArrayList<String> lesenDatei(File file){
			ArrayList<String> dateiLinien = new ArrayList<String>();
			
			try		{
				InputStreamReader in = new InputStreamReader(new FileInputStream(file));
				BufferedReader liest = new BufferedReader(in);
				String linie;
				while ((linie = liest.readLine()) != null ) {
					if(linie.indexOf('#')==-1){ //kommenterlinien wird gelöst
						dateiLinien.add(linie);
					}
	            }
				liest.close();		// BufferesReader closen
			} catch ( IOException e ) {		}
			
			return dateiLinien;
		}
		
		/**
		 * schreiben wert von Textdatei ins Wertetabelle
		 * @param dateiLinien
		 */
		public void schreibTab(ArrayList<String> dateiLinien){
			int index = dateiLinien.size();
			String[][] Data = new String [index][2];
			for (int i = 0;i<index;i++) {
				    String [] array = dateiLinien.get(i).split(",");
				    Data[i][0] = array[0];
				    Data[i][1] = array[1];
		            }
		    plTabele.remove(scroll);	
		    table = new JTable(Data,COLHEADS);
			scroll = new JScrollPane(table);
			plTabele.add(scroll);
			table.setFillsViewportHeight(true);
			plTabele.revalidate();
		}
		/**
		 * Bestimen die Polynom.
		 * @param startx
		 * @param starty
		 * @return Polynom
		 */
		public String getPolynom(double[] startx, double[] starty){
			try{
			String polynom = BestFit.BestFitIP(startx, starty);
			return polynom;
			}catch(Exception e){
				return null;
			}		
		}
		
}
