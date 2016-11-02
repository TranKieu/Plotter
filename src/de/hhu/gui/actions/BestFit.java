package de.hhu.gui.actions;

import java.util.ArrayList;

/*************************************************************************

@ author Hatice Tavli
@ date	 09.07.2011

*************************************************************************/
public class BestFit{

	/**
	 * @param startx	x Werte in double[]
	 * @param starty	y Werte in double[]
	 * @return polynom	Interpolationspolynom als String
	 */
	
	public static String BestFitIP(double[] startx, double[] starty){
		int n = starty.length;
		if (startx.length != n){
			System.out.println("Eingabewerte Ueberpruefen");
			System.exit(0);
		}
		
		if(startx.length == 0){
			String polynom = "0";
			return polynom;
		} else if(startx.length == 1){
			double y_wert= Math.round(starty[0]*100)/100.0;
			String polynom = String.valueOf(y_wert);
			return polynom;
		}else{
			double[] koeffizienten = Interpolation(startx, starty);
			String polynom = IPolynom(koeffizienten,startx);
			return polynom;
		}
	
	}

	
	/**
	 * @param startx			x Werte in double[]
	 * @param starty			y Werte in double[]
	 * @return koeffizienten	Die Koeffizienten des Interpolationspolynom als double []
	 */
public static double[] Interpolation(double[] startx, double[] starty){ // Aufstellen der Interpolationsmatrix
	int k = startx.length;
	double[][] matrix = new double[k][k]; // dividierte Differenzen  Matrix der Dimension kxk
	
	for(int i=0;i<k;){				// erste Spalte mit den y Werten auffaellen
		matrix[i][0] = starty[i];
		i++;
	}

	for(int i=1; i<k;){							// restliche Spalten mit div. Differenzen auffaellen
		for(int j=i; j<k;){
			double differenzy = matrix[j][i-1] - matrix[j-1][i-1];
			//System.out.println(matrix[j][i-1]+" | "+""+matrix[j-1][i-1]);
			//System.out.println("");
			double differenzx = startx[j] - startx[j-i];
			//System.out.println(startx[j] + " | "+ startx[j-i] );
			//System.out.println("----------------------------------------------------------");
			matrix[j][i] = differenzy/differenzx;
			j++;
		}
		i++;	
	}
	
	//for(int i=0;i<matrix.length;i++){
		
	//	for(int j=0;j<matrix[i].length;j++){
	//		System.out.print(matrix[i][j]);
	//	}
	//	System.out.println("");
	//}
	
	double[] koeffizienten = new double[k];
	
	for(int i=0; i<k;){  	// diagonale im array speichern
		koeffizienten[i] = matrix[i][i];
		koeffizienten[i] = Math.round(koeffizienten[i]*100)/100.0;
		//System.out.println(koeffizienten[i] );
		i++;
	}

	/*
	for ( int zeile = 0; zeile <matrix.length; zeile++ ){
		System.out.print("Zeile " + zeile + ": ");
		for ( int spalte=0; spalte < matrix[zeile].length; spalte++ )
		    System.out.print( matrix[zeile][spalte] + " ");
		System.out.println();
	}
	*/	
	return koeffizienten;
}

/**
 * 
 * @param koeffizienten	Die berechneten Koeffizienten als double[]
 * @param startx		x Werte in double[]
 * @return polynom		Interpolationspolynom als String
 */
public static String IPolynom(double[] koeffizienten, double[] startx){
	
	ArrayList<String> poly = new ArrayList<String>();
	
	String koeff_a = String.valueOf(koeffizienten[0]);
	poly.add(koeff_a);

	for(int i=1; i<startx.length;){
		poly.add("+");
		poly.add(String.valueOf(koeffizienten[i]));
		for(int j=0; j<i;){
		    poly.add("*(");
			poly.add("x");
			poly.add("-(");
			poly.add(String.valueOf(startx[j]));
			poly.add("))");
			j++;
		}
		i++;
	}
   
	String[] polynom1 = (String[])poly.toArray(new String[poly.size()]); // ArrayList in String[] umwandeln
	
	StringBuilder polytext = new StringBuilder("");
	String polynom ;
	for(int j=0;j<polynom1.length;j++){
    	polytext.append(polynom1[j]);
    }
	polynom = polytext.toString();
  	//System.out.println(polynom);
	return polynom;
}
}