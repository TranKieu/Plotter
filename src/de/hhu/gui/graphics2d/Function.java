
package de.hhu.gui.graphics2d;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import de.hhu.gui.actions.flotterHilfer;
import de.hhu.parser.Auswerten;

import java.util.ArrayList;
import java.util.Vector;


public class Function extends JPanel{

	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -9093651596312895701L;
	protected int numpoints = 1000;
	public DrawFunction DrawPanel;
	protected Plot wnd;
	public ArrayList<Point2D> data = new ArrayList<Point2D>(numpoints);
	public String funcString;
	public Derivative ableitung;
	@SuppressWarnings("rawtypes")
	public Vector <Vector> HTW;

	protected boolean cbWendepkt= false;
	protected boolean cbGlobExtrem = false;
	protected boolean cbLokaExtrem = false;
	protected boolean cbFkAusblend = false;
	protected boolean cbAbleitung = false;

	@SuppressWarnings("rawtypes")
	public Function(Plot wnd,String funcString){
		this.wnd= wnd;
		this.funcString = funcString;
		setSize(wnd.Width,wnd.Height);
		setVisible(true);
		setOpaque(false);

		DrawPanel = new DrawFunction(numpoints,this.wnd);
		DrawPanel.setPreferredSize(new Dimension(wnd.Width+2,wnd.Height+2));
		add(DrawPanel);
		ableitung = new Derivative(this.wnd,this.funcString);

		HTW = new Vector<Vector>();
		HTW = flotterHilfer.htw_punkt(funcString, wnd.getIntstart(), wnd.getIntend());
	}
	
	/**
	 * Eingabe eines boolean Wertes. Setzt fest ob die Wendepunkte der Funktion angezeigt werden 
	 * sollen , oder nicht.
	 * @param in
	 */
	public void setCbWendepkt(boolean in){
		cbWendepkt = in;
	}
	
	/**
	 * Methode zum Abfragen, ob Wendepunkte angezeigt werden oder nicht. Gibt einen Boolean wieder. 
	 * @return boolean Wendepunkte werden angezeigt true/false
	 */
	public boolean getCbWendepkt(){
		return cbWendepkt;
	}
	
	/**
	 * Eingabe eines boolean Wertes. Setzt fest ob die globalen Extremstellen der Funktion angezeigt werden 
	 * sollen , oder nicht.
	 * @param in
	 */
	public void setCbGlobExtrem(boolean in){
		cbGlobExtrem = in;
	}

	/**
	 * Methode zum Abfragen, ob globalen Extremstellen angezeigt werden oder nicht. Gibt einen Boolean wieder. 
	 * @return boolean globale Extremstellen werden angezeigt true/false
	 */
	public boolean getCbGlobExtrem(){
		return cbGlobExtrem;
	}

	/**
	 * Eingabe eines boolean Wertes. Setzt fest ob die lokalen Extremstellen der Funktion angezeigt werden 
	 * sollen , oder nicht.
	 * @param in
	 */
	public void setCbLokaExtrem(boolean in){
		cbLokaExtrem = in;
	}

	/**
	 * Methode zum Abfragen, ob lokalen Extremstellen angezeigt werden oder nicht. Gibt einen Boolean wieder. 
	 * @return boolean lokale Extremstellen werden angezeigt true/false
	 */
	public boolean getCbLokaExtrem(){
		return cbLokaExtrem;
	}

	/**
	 * Eingabe eines boolean Wertes. Setzt fest ob die  Funktion ausgeblendet werden soll, oder nicht.
	 * @param in
	 */
	public void setCbFkAusblend(boolean in){
		cbFkAusblend = in;
	}

	/**
	 * Methode zum Abfragen, ob die Funktion ausgeblendet ist, oder nicht. 
	 * @return boolean ist ausgeblendet true/false
	 */
	public boolean  getCbFkAusblend(){
		return cbFkAusblend;
	}

	/**
	 * Eingabe eines boolean Wertes. Setzt fest ob die Ableitung der Funktion angezeigt werden soll, oder nicht.
	 * @param in
	 */
	public void setCbAbleitung(boolean in){
		cbAbleitung = in;
	} 
	
	/**
	 * Methode zum Abfragen, ob die Ableitung der Funktion angezeigt wird, oder nicht. 
	 * @return boolean ist ausgeblendet true/false
	 */
	public boolean getCbAbleitung(){
		return  cbAbleitung;
	}

	/**
	 * Wertet die Funktion auf dem sichtbaren Intervall aus und erzeugt ruft die Methode CreateGraph() auf,
	 * um den Graphen zu erzeugen.
	 */

	public void valueAll(){
		data = evaluate(wnd.getIntstart(),wnd.getIntend()) ;

		CreateGraph();
	}


	/**
	 * Aendert die Anzahl der Punkte, die berechnet werden, um die Funktion zu zeichnen. Mehr Punkte bedeutet hoehere Genauigkeit aber auch 
	 * hoeheren Rechenaufwand
	 * @param newValue
	 */
	public void setNumpoints(int newValue){
		numpoints = newValue;
	}


	/**
	 * Berechnet die Punkte vom Punkt intstart bis intend und schreibt diese in die zugehoerige ArrayList der Funktion
	 * @param intstart
	 * @param intend
	 * @return
	 */
	public ArrayList<Point2D> evaluate(double intstart, double intend){

		ArrayList<Point2D> Valued = new ArrayList<Point2D>();
		Point2D buffer = new Point(0,0);
		double Maximum = wnd.getMaximum();
		double Minimum = wnd.getMinimum();
		double x,y;
		double j;
		double step;
		step = Math.abs(intend -intstart)/(double) numpoints;
		
		wnd.setHorizontalMetric();
		wnd.setVerticalMetric();
		Valued.clear();
		
		j = intstart;
		while(j<= intend){
			x = j;
			if(Auswerten.plotter(j,funcString)>Maximum || Auswerten.plotter(j,funcString) < Minimum || Double.isNaN(Auswerten.plotter(j,funcString))==true){
				
				if( Double.isNaN(Auswerten.plotter(j,funcString))==true){
					j+=step;
				}

				if(Auswerten.plotter(j,funcString)>Maximum && Double.isNaN(Auswerten.plotter(j,funcString))==false ){
					y = Auswerten.plotter(j,funcString);
					buffer =new Point.Double(0,0);
					buffer.setLocation(x, y) ;
					j+=step;
					Valued.add(buffer);
				}

				if(Auswerten.plotter(j,funcString) < Minimum  && Double.isNaN(Auswerten.plotter(j,funcString))==false){
					y = Auswerten.plotter(j,funcString);
					buffer =  new Point.Double(0,0);
					buffer.setLocation(x,y) ;
					j+=step;
					Valued.add(buffer);    			
				}
			}else{
				y = Auswerten.plotter(j,funcString);
				buffer =  new Point.Double(0,0);
				buffer.setLocation(x,y) ;
				j+=step;
				Valued.add(buffer);

			}
		}  
		return Valued;
	}

	/**
	 * Erzeugt den Graphen der Funktion in Form von Shapes(Line2D), speichert diese in einer ArrayList und uebergibt sie
	 * an das DrawPanel, auf welchem der Graph gezeichnet wird
	 */
	public void CreateGraph(){
		int k = 1;

		wnd.setHorizontalMetric();
		wnd.setVerticalMetric();
		ArrayList<Shape> Graph = new ArrayList<Shape>();
		double HorizontalMetric = wnd.getHorizontalMetric();
		double VerticalMetric = wnd.getVerticalMetric();

		while(k < data.size()){
			if(data.get(k-1).getY()>= wnd.getMaximum() && data.get(k).getY()<= wnd.getMinimum() ||data.get(k-1).getY() <= wnd.getMinimum() && data.get(k).getY() >= wnd.getMaximum()){
            // do nothing
			}else{
				// Linien zeichnen
				Graph.add( new Line2D.Double(HorizontalMetric *data.get(k-1).getX(),VerticalMetric*data.get(k-1).getY(),HorizontalMetric *data.get(k).getX(),VerticalMetric* data.get(k).getY()));
			}
			k++;
		}
		 
		// Verschiebung des Nullpunktes berechnen
		int x;
		x = (int)-( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);
		DrawPanel.setStroke(new BasicStroke(1.35f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		DrawPanel.setShape(Graph);
		DrawPanel.setX(x);
		DrawPanel.setY(y);
		DrawPanel.repaint();
	}
	
	/**
	 * Setzt die Farbe des Funtkionsgraphen fest.
	 * @param DrawColor
	 */
	public void setColor(Color DrawColor){
		DrawPanel.setColor(DrawColor);
	}
	
	/**
	 * Setzt die Ableitung auf nicht sichtbar
	 */
	public void hideDerivative(){
		ableitung.setVisible(false);
	}
	
	/**
	 * Setzt die Position der Hochpunkte fest und ruft das DrawPanel zum Neuzeichnen auf.
	 * Die Hochpunkte sind in einem Java.Vector in der Klasse Function vorhanden und dort ueberall
	 * sichtbar.
	 */
	@SuppressWarnings("unchecked")
	public void setHochpunkte(){
		double HorizontalMetric = wnd.getHorizontalMetric();
		double VerticalMetric = wnd.getVerticalMetric();
        
		// Verschiebung des Nullpunktes berechnen
		int x;
		x = (int)-( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);

		DrawPanel.setHochpunkt(HTW.elementAt(0));
		DrawPanel.setX(x);
		DrawPanel.setY(y);
		DrawPanel.repaint();
	}

	/**
	 * Setzt die Position der Tiefpunkte fest und ruft das DrawPanel zum Neuzeichnen auf.
	 * Die Tiefpunkte sind in einem Java.Vector in der Klasse Function vorhanden und dort ueberall
	 * sichtbar.
	 */
	@SuppressWarnings("unchecked")
	public void setTiefpunkte(){
		double HorizontalMetric = wnd.getHorizontalMetric();
		double VerticalMetric = wnd.getVerticalMetric();
        
		// Verschiebung des Nullpunktes berechnen
		int x;
		x = (int)-( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);

		DrawPanel.setTiefpunkt(HTW.elementAt(1));
		DrawPanel.setX(x);
		DrawPanel.setY(y);
		DrawPanel.repaint();
	}
	
	/**
	 * Setzt die Position der Nullstellen fest und ruft das DrawPanel zum Neuzeichnen auf.
	 * Die Tiefpunkte sind in einem Java.Vector in der Klasse Function vorhanden und dort ueberall
	 * sichtbar.
	 */
	public void setNst(){
	
	}

	/**
	 * Setzt die Position der Wendepunkte fest und ruft das DrawPanel zum Neuzeichnen auf.
	 *  Die Wendepunkte sind in einem Java.Vector in der Klasse Function vorhanden und dort ueberall
	 * sichtbar.
	 */
	@SuppressWarnings("unchecked")
	public void setWendepunkte(){
		double HorizontalMetric = wnd.getHorizontalMetric();
		double VerticalMetric = wnd.getVerticalMetric();
		 
		// Verschiebung des Nullpunktes berechnen
		int x;
		x = (int)-( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);

		DrawPanel.setWendepunkt(HTW.elementAt(2));
		DrawPanel.setX(x);
		DrawPanel.setY(y);
		DrawPanel.repaint();
	}

}

















