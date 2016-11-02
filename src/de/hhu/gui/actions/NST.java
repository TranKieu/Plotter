package de.hhu.gui.actions;

import java.util.ArrayList;

import de.hhu.parser.Auswerten;

public class NST {

/**
	   * @author Huu Tung Nguyen
	   *
	   *  created on 13.08.2011
	   */

	   /**
	   * @param <Vector>
	   * @param <Vector>
	   * @param f,a,b (f = Funktion, [a,b] = Intervall, in dem Nullstellen liegen)
	   * @return String v 
	   */
	
	public static ArrayList<String> null_stelle (String f, double a, double b) 
	{  
	    double h = 0.01;                                
	    double h1 = 0.01;
	    double h2 = 0.3;
	    int schalter = 0;
	    
		double stelle = a;                       
		double stelle_H = 0;
		
		ArrayList<String> v = new ArrayList<String>();
		
		do { 
				try
				{                                                

					double stelle_f = Auswerten.plotter(stelle,f);
					if (Math.abs(stelle_f) < h1 && schalter == 1)
					{

						if (Math.abs(stelle - stelle_H) > h2)
						{
							v.add(String.valueOf(stelle));     // Einfuegen weiterer Nullstellen
							stelle_H = stelle;
						}
					
					}
					
					if 	(schalter == 0 && Math.abs(stelle_f) < h1)
					{
						v.add(String.valueOf(stelle));         // Einfuegen der ersten Nullstelle in den Vektor
						stelle_H = stelle;            
						schalter = 1;                 // setze schalter von 0 auf 1
					}
					
					stelle = stelle + h;              // a+(1E-5)                      
				}
			
				catch (Exception e) 
				{
					;
				}
                    
			} while ( stelle < b);                    // Erreichen vom Ende des Intervalls [a,b]          
		
		String[] nullst = (String[])v.toArray(new String[v.size()]); // ArrayList in String[] umwandeln
		
		StringBuilder polytext = new StringBuilder("");
		for(int j=0;j<nullst.length;j++){
	    	polytext.append(nullst[j]);
	    }
		return v;                                     /* Ausgabe des Vektors v mit all den gefundenen 
		                                              Nullstellen im Intervall [a,b]*/
	}
}