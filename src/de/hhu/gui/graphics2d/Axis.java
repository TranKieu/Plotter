
package de.hhu.gui.graphics2d;

import java.awt.*;

import javax.swing.*;

import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.Vector;

/** 
 * Axis.java ist ein JInternalFrame auf dem das Gitter gezeichnet wird;
 * Die einzelnen Funktionen werden auf das Frame getackert 
 * 
 */

public class Axis extends JPanel{

	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -2148358884036783028L;
	protected Plot wnd;
	protected DrawAxis Doit;
	protected JLayeredPane Section;
	protected JPanel panel;
	protected  Vector<Function> functions = new Vector<Function>();
	protected Grid background;




	Function example ;
	int index;



	// Konstruktor
	public Axis(Plot wnd){

		this.wnd = wnd;
		Doit = new DrawAxis(wnd.Width,wnd.Height);
		add(Doit);
		Doit.setOpaque(false);
		index = 0;

		setBounds(0,-20,wnd.Width,wnd.Height);
		setVisible(true);
		setOpaque(false);
		CreateAxis();

	}

    /**
     * Rundet double Werte, um bei der Achsenbeschriftung unnoetige Genauigkeit und
     * damiteinhergenden Laenge der angezeigten Ziffern zu vermeiden.
     * @return
     */
	public  double getLabelValue(double in){
		int k = 0;
		int signum;
		if(in < 0){
			signum = -1;
			in = - in ;
		}else if(in > 0){
			signum = 1;
		}else{
			return in;
		}

		while(Math.pow(10,k)>in  ){
			k--;
		}

		if(k<0){
			in = in *Math.pow(10,-k+2);
			in = Math.floor(in);
			in = in /(Math.pow(10,-k+2));
		}


		return signum * in;
	}

	/**
	 * Setzt die platziert die Achsenbeschriftungen und berechnet die richtigen Werte, die angezeigt werden sollen. Es gibt drei Zustaende der Gittereinteilung
	 * (Horizontal und Vertikal jeweils getrennt,15px, 30px, 70px). Diese sind abhaengig von der Wahl der Intervalle. Dabei ist vorab nicht festgelegt, welche 
	 * Intervalllaenge damit erzeugt wird. Ist die Gittergroe�e 30px, so entspricht bei der Wahl des Intervalls [-10,10] 30px genau dem Intervall [k-1,k], k ganze Zahl.
	 */

	public void CreateLabels(){
		Doit.removeAll();
		double fillin,j;	 

		if(wnd.getMinimum()<0 && wnd.getMaximum() >0 && wnd.getIntstart()<0 && wnd.getIntend()>0){     // falls die x-Achse sichtbar 

			if(Math.abs(wnd.getVerticalMetric())<10){           
				j=0;


				fillin = getLabelValue( (wnd.getValuesAt((double)0,j*70).getY())); 
				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){

					fillin = getLabelValue( (wnd.getValuesAt((double)0,j*70).getY())); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*70 -5,50,20);
					Doit.add(Peter);
					j++;
				}
				j = -1;
				fillin = getLabelValue((wnd.getValuesAt((double)0,j*70).getY())); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*70).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*70 -5,50,20);
					Doit.add(Peter);
					j--;
				}

			}else if(Math.abs(wnd.getVerticalMetric())>50){
				j=0;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 
				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 

					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*15 -5,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j++;
				}
				j=-1;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*15 -5,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j--;
				}
			}else{
				j=0;


				fillin = getLabelValue(  wnd.getValuesAt((double)0,j*30).getY());

				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){
					fillin = getLabelValue(  wnd.getValuesAt((double)0,j*30).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*30-5,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}

					j++;


				}
				j=-1;

				fillin = getLabelValue(wnd.getValuesAt((double)0,j*30).getY()); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){



					fillin = getLabelValue( wnd.getValuesAt((double)0,j*30).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds(0,(int)j*30 -5,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}
					j--;
				}
			}


		}else{ // falls Achse nicht sichtbar, plaziere am Rand

			if(Math.abs(wnd.getVerticalMetric())<10){

				j=0;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*70).getY()); 
				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*70).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)(wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*70 -5,50,20);
					Doit.add(Peter);
					j++;
				}

				j=-1;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*70).getY()); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*70).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int) (wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*70 -10,50,20);
					Doit.add(Peter);
					j--;
				}

			}else if(Math.abs(wnd.getVerticalMetric())>50){
				j=0;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY());                 
				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 

					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)(wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*15-10 ,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}

					j++;
				}
				j=-1;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*15).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)(wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*15 -10,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j--;
				}
			}else{
				j=0;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*30).getY()); 
				while(Math.abs(fillin) < Math.abs(wnd.getMinimum())){

					fillin = getLabelValue( wnd.getValuesAt((double)0,j*30).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)(wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*30-10,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}
					j++;

				}
				j=-1;
				fillin = getLabelValue( wnd.getValuesAt((double)0,j*30).getY()); 
				while(Math.abs(fillin)<Math.abs(wnd.getMaximum())){


					fillin = getLabelValue( wnd.getValuesAt((double)0,j*30).getY()); 
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)(wnd.getHorizontalMetric() *wnd.getIntstart())+5,(int)j*30-10,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}
					j--;
				}
			}

		}

		if( wnd.getIntstart() <0 && wnd.getIntend() >0 && wnd.getMinimum()<0 && wnd.getMaximum()>0 ){  //falls y-Achse sichtbar


			if(Math.abs(wnd.getHorizontalMetric())<10){

				j=1;
				fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){

					fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*70 -15,0,50,20);
					Doit.add(Peter);

					j++;

				}
				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){

					fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*70 -15,0 ,50,20);
					Doit.add(Peter);
					j--;
				}

			}else if(Math.abs(wnd.getHorizontalMetric())>50){


				j=1;
				fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){

					fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*15 -15,0,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j++;

				}

				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){
					fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*15 -15,0 ,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j--;
				}
				//step2 = 15;
			}else{

				j=1;
				fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){

					fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*30 -10,0,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}
					j++;
				}

				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){
					fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*30 -15,0 ,50,20);
					if(j*30%60 == 0 ){
						Doit.add(Peter);
					}
					j--;
				}
				//	step2 = 30;
			}
		}else{ // falls Achse nicht sichtbar, plaziere Labels am Rand

			if(Math.abs(wnd.getHorizontalMetric())<10){


				j=1;
				fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){

					fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*70 -15,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20,50,20);
					Doit.add(Peter);
					j++;

				}

				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){

					fillin = getLabelValue( wnd.getValuesAt(j*70,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*70 -15,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20 ,50,20);
					Doit.add(Peter);
					j--;
				}

			}else if(Math.abs(wnd.getHorizontalMetric())>50){

				j = 1;
				fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){
					fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*15 -15,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j++;

				}
				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){
					fillin = getLabelValue( wnd.getValuesAt(j*15,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*60 -15,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20 ,50,20);
					if(j*15%60 == 0){
						Doit.add(Peter);
					}
					j--;
				}
				//step2 = 15;
			}else{
				j=1;
				fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntend())){
					fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*30 -10,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20,50,20);
					if(j*30%60 == 0){
						Doit.add(Peter);
					}
					j++;

				}

				j=-1;
				fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
				while(Math.abs(fillin)<Math.abs(wnd.getIntstart())){

					fillin = getLabelValue( wnd.getValuesAt(j*30,(double)0).getX());
					JLabel Peter = new JLabel(""+ fillin);
					Peter.setBounds((int)j*30 -15,(int)(wnd.getVerticalMetric()*wnd.getMinimum())-20 ,50,20);
					if(j*30%60 == 0){
						Doit.add(Peter);
					}
					j--;
				}
			}
		}
	}

	/**
	 * Erzeugt das Achsengitter des Koordinatensystems. Bei Aenderung der Intervalle sollte diese
	 * Funktion aufgerufen werden.
	 */
	public void CreateAxis(){

		wnd.setVerticalMetric();
		wnd.setHorizontalMetric();
		Shape [] AxisLine = new Shape[2];
		double x1,x2,y1,y2;
		double HorizontalMetric = wnd.getHorizontalMetric();
		double VerticalMetric = wnd.getVerticalMetric();

		if(wnd.getIntstart()<= 0 && wnd.getIntend() >= 0){
			x1 = 0;
			x2 = 0;
			y1 = wnd.getMinimum();
			y2 = wnd.getMaximum();
			AxisLine[0] = new Line2D.Double(HorizontalMetric *x1,VerticalMetric*y1,HorizontalMetric *x2,VerticalMetric*y2);
		}

		if(wnd.getMinimum()<=0 && wnd.getMaximum()>=0){
			x1 = wnd.getIntstart();
			x2 = wnd.getIntend();
			y1 = 0;
			y2 = 0;
			AxisLine[1] = new Line2D.Double(HorizontalMetric*x1,VerticalMetric*y1,HorizontalMetric*x2,VerticalMetric*y2);
		}
        
		// Verschiebung des Nullpunktes festlegen
		int x;
		x = (int) -( wnd.getIntstart()*HorizontalMetric);
		int y;
		y = (int) -( wnd.getMaximum()*VerticalMetric);

		Doit.setShape(AxisLine);
		Doit.setX(x );
		Doit.setY(y);
		CreateLabels();
		Doit.repaint();


	}
}

// Klasse zum Zeichnen der Achsen
class DrawAxis extends JComponent{

	/**
	 * @author Michael Pott
	 *
	 * created on 30.06.2011
	 */
	private static final long serialVersionUID = -2665473917990276380L;
	protected Shape [] Drawing = new Shape[2];
	protected Color DrawColor = Color.black;

	protected int xTranslation;
	protected int yTranslation;

	DrawAxis(int Width, int Height){
		setVisible(true);
		setPreferredSize(new Dimension(Width,Height));
	}
	
    /**
     * Methode zur Festlegung der Objekte, welche gezeichnet werden sollen
     * @param input
     */
	public void setShape(Shape[]input){
		Drawing= input;
	}
	/**
	 * Setzt die Farbe fest, in der gezeichnet werden soll
	 * @param newColor
	 */
	public void setColor(Color newColor){
		DrawColor = newColor;		
	}

    /**
     * legt die Verschiebung entlang der x-Achse fest
     * @param x
     */
	public void setX(int x){
		xTranslation = x;
	}
    
	/**
	 * legt die Verschiebung entlang der y-Achse fest
	 * @param y
	 */
	public void setY(int y){
		yTranslation = y;
	}

    /**
     * zeichnet die Achsen
     */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(0+xTranslation,0+yTranslation);

		g2d.setColor(DrawColor);
		if(Drawing[0]!=null){
			g2d.draw(Drawing[0]);

		}
		if(Drawing[1]!=null){
			g2d.draw(Drawing[1]);
		}
	}


}




