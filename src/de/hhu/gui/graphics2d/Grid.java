
package de.hhu.gui.graphics2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.Vector;

import javax.swing.*;




public class Grid extends JPanel{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3062778032926968861L;
	protected Plot plot;
	protected DrawGrid drawing;

	private Vector<Shape> HorizontalLines = new Vector<Shape>();
	private Vector<Shape> VerticalLines = new Vector<Shape>();


	public Grid(Plot plot){
		this.plot = plot;
		drawing = new DrawGrid();
		drawing.setPreferredSize(new Dimension(plot.Width+2,plot.Height+2));
		drawing.setVisible(true);
		add(drawing);
		setVisible(true);
		setOpaque(false);

		setBounds(0,-20,plot.Width,plot.Height);

	}





	/**
	 * Erzeugt das Gitter des Koordinatensystems. 
	 */
	public void CreateGrid(){

		HorizontalLines.removeAllElements();
		VerticalLines.removeAllElements();

		plot.setVerticalMetric();
		plot.setHorizontalMetric();

		double y2 = plot.getMaximum();
		double y1= plot.getMinimum();
		double x1 = plot.getIntstart();
		double x2 = plot.getIntend();
		double step1, step2;

		if(Math.abs(plot.getVerticalMetric())<10){
			step1 =  70;

		}else if(Math.abs(plot.getVerticalMetric())>50){
			step1 = 15;
		}else{
			step1 =  30;
		}

		if(Math.abs(plot.getHorizontalMetric())<10){
			step2 =  70;
		}else if(Math.abs(plot.getHorizontalMetric())>50){
			step2 = 15;
		}else{
			step2 = 30;
		}

		double j = 0;
		while( Math.abs(step2*j) <= Math.abs(plot.getHorizontalMetric()*x2)){
			VerticalLines.add( new Line2D.Double(step2*j,plot.getVerticalMetric()*y2,step2*j,plot.getVerticalMetric()*y1));
			j++;
		}
		j=0;
		while( Math.abs(step2*j) <= Math.abs(plot.getHorizontalMetric()*x1)){
			VerticalLines.add( new Line2D.Double(step2*j,plot.getVerticalMetric()*y2,step2*j,plot.getVerticalMetric()*y1));
			j--;
		}
		j = 0;
		while( Math.abs(step1*j) <= Math.abs(plot.getVerticalMetric()*y2)){
			HorizontalLines.add(new Line2D.Double(plot.getHorizontalMetric()*x1,-step1*j,plot.getHorizontalMetric()*x2,-step1*j));
			j++;
		}
		j=0;
		while(Math.abs(step1*j) <= Math.abs(plot.getVerticalMetric()*y1)){
			HorizontalLines.add(new Line2D.Double(plot.getHorizontalMetric()*x1,-step1*j,plot.getHorizontalMetric()*x2,-step1*j));
			j--;
		}
        
		//Verschiebung des Nullpunktes festlegen
		int x;
		x = (int)-( plot.getIntstart()*plot.getHorizontalMetric());
		int y;
		y = (int) -( plot.getMaximum()*plot.getVerticalMetric());

		drawing.setShape(VerticalLines,HorizontalLines);
		drawing.setX(x);
		drawing.setY(y);
		drawing.repaint();

	}	



}

class DrawGrid extends JComponent{

	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -6881504081177396973L;
	private Vector<Shape> HorizontalLines1 = new Vector<Shape>();
	private Vector<Shape> VerticalLines1= new Vector<Shape>();
	protected int xtranslation;
	protected int ytranslation;

	/**
	 * Zeichnet das Gitter.
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(0+xtranslation,0+ytranslation);

		g2d.setColor(Color.lightGray);
		int j =0;
		while(j<HorizontalLines1.size()){
			g2d.draw(HorizontalLines1.elementAt(j));
			j++;
		}
		j = 0;
		while(j<VerticalLines1.size()){
			g2d.draw(VerticalLines1.elementAt(j));
			j++;
		}
	}

	/**
	 * Verschiebung des Nullpunktes entlang der x-Achse festlegen
	 * @param x
	 */
	public void setX(int x){
		this.xtranslation = x;
	}

	/**
	 * Verschiebung des Nullpunktes entlang der y-Achse festlegen
	 * @param x
	 */
	public void setY(int y){
		this.ytranslation = y;
	}

    /**
     * Festlegen der Objekte, die gezeichnet werden sollen. In diesem 
     * Fall werden zwei Argumente des Typs java.Vector eingegeben.
     * @param in1
     * @param in2
     */
	public void setShape(Vector<Shape> in1, Vector<Shape> in2){
		HorizontalLines1.removeAllElements();
		VerticalLines1.removeAllElements();
		this.HorizontalLines1.addAll(in2);
		this.VerticalLines1.addAll(in1);
	}



}