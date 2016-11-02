package de.hhu.gui.actions;
import de.hhu.parser.Auswerten;

/**
 * 
 * @author Hatice Tavli
 *
 */


public class newtonVerfahren{

/**
 * @param f		Funktion
 * @param x		Startwert fuer die Nullstelle
 * @return x	Approximation an die Nullstelle
 */

public static double Newton(String f, double x) {
	int n = 100;
	for (int i=0; i<n; i++){
		x = x - Auswerten.plotter(x,f)/ableitungFunk.ableitung(f,x); 
	}
	return x;
}

/**
 * @param f		Funktion
 * @param x		approximierte Nullstelle
 * @return x 	falls Approximation gut genug
 */

public static double NewtonKonvergenz(String f, double x){
	int maxIteration = 100;
	for (int i=0; i<maxIteration; i++){
		double dx = Auswerten.plotter(x,f)/ableitungFunk.ableitung(f, x);
		x = x - dx;
		if (Math.abs(dx) < 1E-8) 
			return x;
	}
	System.out.println("Maximale Anzahl der Iterationen ueberschritten");
	return 0.0;
	}

}
