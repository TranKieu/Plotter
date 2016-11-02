package de.hhu.gui.actions;

import de.hhu.parser.Auswerten;
  /**
   * @author Huu Tung Nguyen
   *
   *  created on 18.06.2011
   */

public class ableitungFunk{
	
   /**
   * @param f,x
   * @return Ableitungswert von f an der Stelle x
   */

   public static double ableitung(String f, double x) {  
		double h = 0.01;                                
		double abl =ableitungquot(f,x,h);                       // Ableitungsquotient (f(x+h)-f(x-h))/(2*h)
		double abl_H;                                    
		do{                                            
			abl_H = abl;
			h = h/2;                                    
			abl = ableitungquot(f,x,h);   
		} while ( abweichung_not_OK(abl, abl_H));               // Ableitungsquotientabweichung vergleichen                
		
		return abl;
	}
   
	public static double ableitungquot(String f, double x, double h){
		
		return ((Auswerten.plotter(x+h, f) - Auswerten.plotter(x-h,f))/(2*h));
	}
	private static boolean abweichung_not_OK (double abl1, double abl2) {
		return Math.abs(abl1-abl2) > 1E-10;                   
		
	}
	
}
