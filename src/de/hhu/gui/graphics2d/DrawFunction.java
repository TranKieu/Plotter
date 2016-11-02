package de.hhu.gui.graphics2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComponent;

public class DrawFunction extends JComponent{
	

	
	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -2317072013882184771L;
	protected int numpoints;
	protected ArrayList<Shape>FunctionMap = new ArrayList<Shape>();
	protected Vector<Point2D> Hochpkt = new Vector<Point2D>();
	protected Vector<Point2D> Tiefpkt = new Vector<Point2D>();
	protected Vector<Point2D>  Wendepkt = new Vector<Point2D>();
	protected Vector<Point2D>  Nullst = new Vector<Point2D>();
	protected Color DrawColor;
	protected int k =0;
	
	protected int xTranslation;
	protected int yTranslation;
	protected double VerticalMetric;
	protected double HorizontalMetric;
	
	protected boolean Hpt = false;
	protected boolean Tpt = false;
	protected boolean Wpt = false;
	Plot wnd;
	
	float [] array = {2,3};
	BasicStroke pointstroke = new BasicStroke(1.7f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,array,0);
    
	BasicStroke func; 
	
	DrawFunction(int numpoints,Plot wnd){
		this.wnd = wnd;
		this.numpoints = numpoints;
		setVisible(true);
		setOpaque(false);
		VerticalMetric = wnd.getVerticalMetric();
		HorizontalMetric = wnd.getHorizontalMetric();
	}
	/**
	 * Setzt die Farbe fest, in der gezeichnet werden soll. 
	 * @param NewDrawColor
	 */
	public void setColor(Color NewDrawColor){
		DrawColor = NewDrawColor;
		
	}
	/**
	 * Setzt die Objekte fest, die gezeichnet werden sollen. Das Eingabeargument ist eine ArrayList
	 * vom Typ java.awt.Graphics2D.Shape.
	 * @param input
	 */
	public void setShape(ArrayList<Shape> input){
		FunctionMap.clear();
		FunctionMap.addAll(input);
	}
	
	/**
	 * Setzt die Hochpunkte der Funktion fest, die gezeichnet werden sollen. Eingabe ist 
	 * ein java.Vector vom Typ Point2D
	 * @param input
	 */
	public void setHochpunkt(Vector<Point2D> input){
		this.Hochpkt = input;
		Hpt = true;
	}
	
	/**
	 * Legt fest, dass die Hochpunkte vom DrawPanel nicht gezeichnet werden sollen.
	 */
	public void HideHochpunkt(){
		Hpt = false;
	
	}
	
	/**
	 * Setzt die Tiefpunkte der Funktion fest, die gezeichnet werden sollen. Eingabe ist 
	 * ein java.Vector vom Typ Point2D
	 * @param input
	 */
	public void setTiefpunkt(Vector<Point2D> input){
		this.Tiefpkt = input;
		Tpt = true;
	}
	
	/**
	 * Legt fest, dass die Tiefpunkte vom DrawPanel nicht gezeichnet werden sollen.
	 */
	public void HideTiefpunkt(){
		Tpt = false;
		
	}
	
	/**
	 * Setzt die Wendepunkte der Funktion fest, die gezeichnet werden sollen. Eingabe ist 
	 * ein java.Vector vom Typ Point2D
	 * @param input
	 */
	public void setWendepunkt(Vector<Point2D> input){
		this.Wendepkt =input;
		Wpt = true;
	}
	/**
	 * Legt fest, dass die Wendepunkte vom DrawPanel nicht gezeichnet werden sollen.
	 */
	public void HideWendepunkt(){
		Wpt  = false;
	
	}
	
	
	
	/**
	 * Methode um die Verschiebung der X-Koordinate des Koordinatensystems festzulegen
	 * @param x
	 */
	public void setX(int x){
		xTranslation =x;
	}
	
	/**
	 * Methode um die Verschiebung der Y-Koordinate des Koordinatensystems festzulegen
	 * @param y
	 */
	public void setY(int y){
		yTranslation = y;
	}
	
	/**
	 * Legt die Linienart fest, mit der gezeichnet wird. Eingabeparameter ist vom Typ
	 * java.BasicStroke 
	 * @param input
	 */
	public void setStroke(BasicStroke input){
		func = new BasicStroke();
		func = input;
	}
	
	
	/**
	 * Paint-Methode zum Zeichnen 
	 */
	@Override 
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		  
		

		 g2D.translate(0+xTranslation,0+yTranslation);
		 
		 g2D.setStroke(func);
	
		g2D.setColor(DrawColor);
		k=0;
		while(k<FunctionMap.size()){
			
			g2D.draw(FunctionMap.get(k));
			k++;
		}
		k=0;
		
		if(Hpt == true){
		while(k<Hochpkt.size()){
			 g2D.setStroke(pointstroke); 
			 g2D.setColor(new Color(0,	191,	255	)); // Deepskyblue
			 
		    g2D.draw(new Ellipse2D.Double(HorizontalMetric*Hochpkt.get(k).getX()-10,VerticalMetric*Hochpkt.get(k).getY()-10,20,20));
			k++;
		}
		}
		if(Tpt == true){
		k=0;
		while(k<Tiefpkt.size()){
			 g2D.setStroke(pointstroke);
			 g2D.setColor(new Color(139	,69	,19)); // saddlebrown
			 g2D.draw(new Ellipse2D.Double(HorizontalMetric*Tiefpkt.get(k).getX()-10,VerticalMetric*Tiefpkt.get(k).getY()-10,20,20));
			k++;
		}
		}
		k=0;
		if(Wpt == true){
		while(k<Wendepkt.size()){
			 g2D.setStroke(pointstroke);
			 g2D.setColor(new Color(205	,92,92)); // indian red
			 g2D.draw(new Ellipse2D.Double(HorizontalMetric*Wendepkt.get(k).getX()-10,VerticalMetric*Wendepkt.get(k).getY()-10,20,20));
			k++;
		}
		}
	
	}
	
}

 
 
