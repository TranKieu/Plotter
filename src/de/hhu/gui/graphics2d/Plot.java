package de.hhu.gui.graphics2d;


import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Vector;

import javax.swing.*;


public class Plot extends JPanel{

	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -2300379011433364521L;
	protected  int Width;
	protected  int Height;


	protected  double intstart = -10;	
	protected  double intend   =  10;

	protected  double Maximum = 10;
	protected  double Minimum = -10;

	protected  double HorizontalMetric;
	protected  double VerticalMetric;



	public  Vector<Function> functions = new Vector<Function>();
	public Vector<Derivative> derivatives =  new Vector<Derivative>();
	protected boolean Gridflag = true;

	private Color[] colors = {Color.red,Color.blue,Color.green,Color.yellow,
			Color.pink ,Color.orange,Color.cyan,Color.magenta,Color.black};

	public Axis PlotBase;
	public Grid Gitter;

	public JPanel labels = new JPanel();

	JLabel xLabel1 = new JLabel("xLabel1");
	JLabel xLabel2 = new JLabel("xLabel2");
	JLabel xLabel3 = new JLabel("xLabel3");
	JLabel xLabel4 = new JLabel("xLabel4");
	JLabel xLabel5 = new JLabel("xLabel5");


	JLabel yLabel1 = new JLabel("yLabel1");
	JLabel yLabel2 = new JLabel("yLabel2");
	JLabel yLabel3 = new JLabel("yLabel3");
	JLabel yLabel4 = new JLabel("yLabel4");
	JLabel yLabel5 = new JLabel("yLabel5");

	public JLayeredPane plotManager;

	public	int index;



	public Plot(int Width, int Height){

		this.Width  = Width;
		this.Height = Height;
		setSize(Width,Height);
		setVisible(true);

		PlotBase = new Axis(this);
		Gitter = new Grid(this);
		Gitter.setVisible(false);
		Gitter.setBounds(0,-20,600,600);
		Gitter.CreateGrid();
		index =0;


		plotManager = new JLayeredPane();
		plotManager.setPreferredSize(new Dimension(600,600));
		add(plotManager);




		//plotManager.add(labels,new Integer(95));
		labels.setBounds(0,-20,600,600);
		plotManager.add(PlotBase,new Integer(105));
		PlotBase.setBounds(0,-20,600,600);
		PlotBase.add(labels);
		plotManager.add(Gitter,new Integer(100));
		labels.setLayout(null);


	}





	/**
	 * Skalierung des Koordinatensystems festlegen bzgl. der horizontalen Achse 
	 */
	public  void setHorizontalMetric(){
		HorizontalMetric = (double)Width /Math.abs(getIntstart()- getIntend()) ;	
	}

	/**
	 * Skalierung des Koordinatensystems festlegen bzlg. der vertikalen Achse
	 */
	public  void setVerticalMetric(){
		VerticalMetric = - (double)Height /Math.abs(getMaximum()- getMinimum()) ;

	}
	/**
	 * Skalierung der horziontalen Achse abfragen
	 * @return
	 */
	public  double getHorizontalMetric(){
		return HorizontalMetric;
	}

	/**
	 * Skalierung der vertikalen Achse abfragen
	 * @return
	 */
	public  double getVerticalMetric(){
		return VerticalMetric;
	}

	/**
	 * Intervallanfang der x-Achse abfragen
	 * @return
	 */
	public  double getIntstart(){
		return intstart;
	}

	/**
	 * Intervallende der x-Achse abfragen
	 * @return
	 */
	public  double getIntend(){
		return intend;
	}

	/**
	 * Intervallanfang der x-Achse festlegen
	 * @param a
	 */
	public void setIntstart(double a){
		intstart = a;
		setHorizontalMetric();
	}

	/**
	 * Intervallende der x-Achse festlegen
	 * @param b
	 */
	public void setIntend(double b){
		intend = b;
		setHorizontalMetric();
	}

	/**
	 * Intervallende der y-Achse abfragen
	 * @return
	 */
	public  double getMaximum(){
		return Maximum ;
	}

	/**
	 *Intervallanfang der y-Achse abfragen
	 */
	public  double getMinimum(){
		return Minimum;
	}


	/**
	 * Intervallanfang der y-Achse festlegen
	 * @param a
	 */
	public void setMinimum(double a){
		Minimum = a;
		setVerticalMetric();
	}

	/**
	 * Intervallende der y-Achse festlegen
	 * @param b
	 */
	public void setMaximum(double b){
		Maximum = b;
		setVerticalMetric();
	}


	/**
	 * Neue Funktion zum Plot hinzufuegen. Alle Funktionen werden in einem java.util.Vector gespeichert 
	 * und verwaltet.  
	 * @param funcinput
	 * 
	 */
	public void newFunction(String funcinput){


		for(int j=0;j<functions.size();j++){
			if(funcinput.equals(functions.elementAt(j).funcString)){
				reloadFunctions();
				return;

			}

		}

		functions.add(new Function(this,funcinput));
		functions.lastElement().valueAll();
		functions.elementAt(index).DrawPanel.setColor(colors[index%9]);

		index++;
		reloadFunctions();

	}

	/**
	 * Funktionen werden neugezeichnet. Die Funktionswerte werden dabei nicht neu berechnet, d.h. 
	 * beim aendern des Intervalls muss erst jede Funktion neu berechnet werden.
	 */
	public void reloadFunctions(){
		int j=0;



		while(j<functions.size()){
			functions.elementAt(j).setBounds(0,-20,600,600);
			functions.elementAt(j).CreateGraph();
			plotManager.add(functions.elementAt(j),new Integer(110+j*19));
			j++;

		}




	}


	public  Point2D getValuesAt(double px, double py){

		setHorizontalMetric();
		setVerticalMetric();


		double xval ,yval;
		double signumpx,signumpy;
		if(px <0){
			signumpx = -1;

		}else if(px ==0){
			signumpx =0;
		}else{
			signumpx = 1;
		}
		if(py <0){
			signumpy = -1;

		}else if(py ==0){
			signumpy =0;
		}else{
			signumpy = 1;
		}

		double j=0;
		while(j < Math.abs(px) ){
			j++;
		}
		double k  = 0;
		while(k < Math.abs(py) ){
			k++;
		}

		xval = signumpx*j*(1/getHorizontalMetric());
		yval = signumpy*k*(1/getVerticalMetric());

		Double Value = new Point.Double(xval,yval);
		return Value;
	}

	/**
	 * Neue Ableitung zum Plot hinzufuegen. Die Ableitungen werden in einem java.util.Vector gespeichert. Hier wird nur berechnet.
	 * @param input
	 */

	public void newDerivative(String input){
		int j =0;
		while(j<functions.size()){
			if(functions.elementAt(j).funcString.equals(input)){
				functions.elementAt(j).ableitung.evaluate();
				derivatives.add(functions.elementAt(j).ableitung);
				functions.elementAt(j).ableitung.setColor(colors[index-1%9]);
				break;
			}
			j++;

		}
		reloadDerivatives();
	}

	/**
	 * Graphen der Ableitung erneuern. Hier wird nur gezeichnet.
	 */
	public void reloadDerivatives(){
		int j=0;

		while(j<derivatives.size()){
			derivatives.elementAt(j).CreateDerivative();
			derivatives.elementAt(j).setBounds(0,-20,600,600);
			plotManager.add(derivatives.elementAt(j),new Integer(1200 + j*17));
			j++;
		}
	}


}




