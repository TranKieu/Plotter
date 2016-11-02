package de.hhu.gui.graphics2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.hhu.gui.actions.ableitungFunk;

public class Derivative extends JPanel{
	
	private static final long serialVersionUID = 1L;
	protected int numpoints = 1000;
	Plot wnd;
	public DrawFunction DrawPanel;
	public ArrayList<Point2D> data = new ArrayList<Point2D>(numpoints);
	public String funcString;
	
	Derivative(Plot wnd,String funcString){
	this.wnd = wnd;	
	this.funcString = funcString;
	setSize(wnd.Width,wnd.Height);
	setVisible(true);
	
	setOpaque(false);
	DrawPanel = new DrawFunction(numpoints,this.wnd);
	DrawPanel.setPreferredSize(new Dimension(wnd.Width+2,wnd.Height+2));
    add(DrawPanel);
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
	 * Berechnet die Ableitung auf dem gesamten sichtbaren Intervall
	 */
	
	public void evaluate(){
		
		data.clear();
		 wnd.setHorizontalMetric();
	        wnd.setVerticalMetric();
	        double intstart = wnd.getIntstart();
	        double intend = wnd.getIntend();
	        
	        double step;
	        double j = intstart;
	        step = Math.abs(intend -intstart)/(double) numpoints;
	        while(j<=intend){
	        if(Double.isNaN(ableitungFunk.ableitung(funcString, j))==true){
	       
	        }else{
	        	data.add(new Point.Double(j,ableitungFunk.ableitung(funcString, j)));
	        }
	        j+=step;
	        }
	}
	
	/**
	 * Erzeugt den Graphen der Ableitung auf dem sichtbaren Intervall. Dabei wird eine ArrayList erzeugt die Shapes der Form (Line2D) speichert und an das 
	 * DrawPanel uebergeben wird.
	 */
	public void CreateDerivative(){
		
		ArrayList<Shape> Graph = new ArrayList<Shape>();
        wnd.setHorizontalMetric();
        wnd.setVerticalMetric();
        double HorizontalMetric = wnd.getHorizontalMetric();
        double VerticalMetric = wnd.getVerticalMetric();
       
        for(int k=1;k<data.size();k++){
        	
        	  
        	    Graph.add( new Line2D.Double(HorizontalMetric *data.get(k-1).getX(),VerticalMetric*data.get(k-1).getY(),HorizontalMetric *data.get(k).getX(),VerticalMetric* data.get(k).getY()));  
        }
        
        int x;
		x = (int)-( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);
        
		float [] array = {30,20};
		BasicStroke Derivatstroke = new BasicStroke(1.35f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,array,0);
		
		
		DrawPanel.setStroke(Derivatstroke);
        DrawPanel.setShape(Graph);
        DrawPanel.setX(x);
        DrawPanel.setY(y);
        DrawPanel.repaint();
		
		
	}
	
	/**
	 * Setzt die Farbe des Graphen der Ableitung fest
	 * @param DrawColor
	 */
	  public void setColor(Color DrawColor){
	    	DrawPanel.setColor(DrawColor);
	    }
	  
	  
	  
	    
	
}