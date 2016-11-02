	   /**
	   * @author Huu Tung Nguyen
	   *
	   *  created on 10.07.2011
	   */

package de.hhu.gui.actions;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Vector;

import de.hhu.parser.Auswerten;


public class flotterHilfer {

	   /**
	   * @param f,a,b (f = Funktion, [a,b] = Intervall, in dem Hochpunkt, Tiefpunkt und Wendepunkt liegen)
	   * @return Vektor v 
	   * Wobei: 
	   * Vector htwp_H = v.elementAt(0)= (x1,y1,x2,y2,...), mit (xi,yi) ist Hochpunkt i; 
	   * Vector htwp_T = v.elementAt(1)= (x1,y1,x2,y2,...), mit (xi,yi) ist Tiefpunkt i;
	   * Vector htwp_W = v.elementAt(2)= (x1,y1,x2,y2,...), mit (xi,yi) ist Wendepunkt i.
	   */
	public static Vector<Vector> htw_punkt (String f, double a, double b) {  
	    double h = 0.02;//1E-5; 
	    double h1 = 0.0001; //1E-8;
	    double h2 = 0.5;
		double abl_Stelle = a;
		double f_wert;
		double abl_Stelle2 = a +h ;
		double abl_Wert1 = Auswerten.plotter(a,f); 
		double abl_Wert2 = Auswerten.plotter(abl_Stelle2,f); 
		int i = 0 ;
		int j = 0 ;
		
		Vector<Vector> v = new Vector<Vector>();
	
		Vector<Point2D> htwp_H = new Vector<Point2D>();
		Vector<Point2D> htwp_T = new Vector<Point2D>();
		Vector<Point2D> htwp_W = new Vector<Point2D>();

		// Vector htwp_H der Hochpunkte, Vector htwp_T der Tiefpunkte berechnen Anfang
		
		if (abl_Wert1 > abl_Wert2)
		{
			i = -1;
			
		}
		
		if (abl_Wert1 < abl_Wert2 ) 
		{
			i = 1;
			
		}
		
		if (abl_Wert1 - abl_Wert2 ==  0)
		{
			i = 0;
			
		}
      
		do {
			if (i != 0)
			{
				j = i;
			}
			abl_Wert1 = abl_Wert2;
			abl_Stelle2 = abl_Stelle2 +h;
			
			
			abl_Wert2 = Auswerten.plotter(abl_Stelle2,f );
			if (abl_Wert1 > abl_Wert2 )
			{
				i = -1;
			}
			
			if (abl_Wert1 < abl_Wert2) 
			{
				i = 1;
			}
			
			if (abl_Wert1 - abl_Wert2 ==  0)
			{
				i = 0;
			}
			
			if (i == -1 && j == 1)  // Vector htwp_H der Hochpunkte berechnen Anfang
			{   
				abl_Stelle = abl_Stelle2 -h;	
	   			f_wert = Auswerten.plotter(abl_Stelle,f);
				
				htwp_H.addElement(new Point.Double(abl_Stelle,f_wert));
			//	htwp_H.addElement(f_wert);
				
			} // Vector htwp_H der Hochpunkte berechnen Ende
			
			if (i == 1 && j == -1)  // Vector htwp_T der Tiefpunkte berechnen Anfang
			{
				abl_Stelle = abl_Stelle2 -h;	
			
  			f_wert = Auswerten.plotter(abl_Stelle,f);
				htwp_T.addElement(new Point.Double(abl_Stelle,f_wert));
				
			//	htwp_T.addElement(f_wert);
		
			}// Vector htwp_T der Tiefpunkte berechnen Ende
                          
                   
		} while ( abl_Stelle2 < b);         
		// Vector htwp_H der Hochpunkte, Vector htwp_T der Tiefpunkte berechnen Ende
				
		v.add(0, htwp_H);  //Vector htwp_H der Hochpunkte in Vector v einf�gen
		v.add(1, htwp_T);  //Vector htwp_T der Tiefpunkte in Vector v einf�gen
		
		abl_Wert1 = ableitungFunk.ableitung(f,a); 
		abl_Stelle2 = a +h ;
		abl_Wert2 = ableitungFunk.ableitung(f,abl_Stelle2); 
		i = 0 ;
		j = 0 ;
		
		// Vector htwp_H der Wendepunkte berechnen Anfang
				
		if (abl_Wert1 - abl_Wert2 > h1 )
		{
			i = -1;
		}
		
		else if (abl_Wert1 - abl_Wert2 < -h1 ) 
		{
			i = 1;
		}
		else 
		{
			i = 0;
		}

		do {
			if (i != 0)
			{
				j = i;
			}
			
			abl_Wert1 = abl_Wert2;
			abl_Stelle2 = abl_Stelle2 +h;
			abl_Wert2 = ableitungFunk.ableitung(f,abl_Stelle2 );
			
			if (abl_Wert1 - abl_Wert2 > h1)
				// vorher: if (abl_Wert1 > abl_Wert2)
				{
				i = -1;
				}
				else if (abl_Wert1 - abl_Wert2 < -h1)
				//if (abl_Wert1 < abl_Wert2)
				{
				i = 1;
				}
				else
				//vorher: if (abl_Wert1 == abl_Wert2)
				{
				i = 0;
				}
			
			if ((i == 1 && j == -1) || (i == -1 && j == 1) ) 
			{
				
				if (Math.abs(abl_Stelle - abl_Stelle2) > h2)
				{

				
				abl_Stelle = abl_Stelle2 - h;
				f_wert = Auswerten.plotter(abl_Stelle,f);
				
				htwp_W.addElement(new Point.Double(abl_Stelle,f_wert));
				
			//	htwp_W.addElement(f_wert);
				}
			}
			
		} while ( abl_Stelle2 < b);                               
		// Vector htwp_H der Wendepunkte berechnen Ende
		
		v.add(2, htwp_W); 
		//Vector htwp_W der Wendepunkte in Vector v einf�gen
		return v;
	}

}
