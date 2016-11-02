package de.hhu.parser;

import java.util.Stack;



import de.hhu.gui.PlotterGUI;
import static de.hhu.funktionen.KansFunks.*;
import static de.hhu.funktionen.LogaritFunks.*;
import static de.hhu.funktionen.PotenFunks.*;
import static de.hhu.funktionen.SinhFunks.*;
import static de.hhu.funktionen.WinkelFunks.*;


/**
 * @author Viet Dung Tran
 *
 * created on 23.05.2011
 */

public class Auswerten {
		

	/**
	 * Die Eingabe, die wir auswerten mussen, wird ins prefix-Notation umgeschrieben.
	 * <br> Wir werten von link nach rech aus = for (0 : length.stokens)
	 * <br>wenn wir Orperander treffen, wird Orperander ins Stack reingeschrieben (push)
	 * <br>wenn wir Operator treffen, holen wir alles von Stack raus unddann auswerten
	 * <br>Schreiben Wir wieder die Ergebnis ins Stack..
	 * <br>machen wir weiter bis Ende der prefix-Notation. 
	 * <br>Dann erhalten Wir den Wert der Plotter an der Stelle x.
	 * <br>
	 * @param varialbex 
	 * @param PlottString ist  die Plotter,die wir auswerten mussen
	 * @return y = f(varialbex) 
	 */
     public static double plotterRechnen(double varialbex, String func)    {
    	 
    	 String	postfix = UmPolNotation.infixToPostfixNotation(func);
    	 String[] tokens = postfix.split(" ");
         Stack<Double> stack = new Stack<Double>();
         
         for (int i = 0; i < tokens.length; i++) {
        	 
            if (AusdHilfer.IstOperator(tokens[i])){
            	 
                 double x = stack.pop();
                 
                 if (AusdHilfer.IstUndeFunk(tokens[i])&& (!AusdHilfer.Istfunk2Parameter(tokens[i]))) {
                	 
                	
                	 if(tokens[i].equals("sqrt")){
                		x = quaWurzel(x);
                	 }
                	 //logarithemus
	                 if(tokens[i].equals("ln")){
		         		    x = logaNa(x);
		                 }
	                 if(tokens[i].equals("log")){
		         		    x = logaZehn(x);
		                 }
	                 //Winkel
	                 if(tokens[i].equals("sin")){
	            		    x = sinFunk(x);
	            	 } 
	                 if(tokens[i].equals("cos")){
	         		    x = cosFunk(x);
	                 }
	                 if(tokens[i].equals("tan")){
		         		    x = tanFunk(x);
		                 }
	                 if(tokens[i].equals("cot")){
		         		    x = kotanFunk(x);
		                 }
	                 //Arcus Funktion
	                 if(tokens[i].equals("arcsin")){
		         		    x = arsinFunk(x);
		                 }
	                 if(tokens[i].equals("arccos")){
		         		    x = arcosFunk(x);
		                 }
	                 if(tokens[i].equals("arctan")){
		         		    x = artanFunk(x);
		                 }
	                 if(tokens[i].equals("arccot")){
		         		    x = arkotanFunk(x);
		                 }
	                 //Hyperbolicus + Arcus von Hyperbolicus
	                 if(tokens[i].equals("shcos")){
		         		    x = coshypFunk(x);
		                 }
	                 if(tokens[i].equals("shsin")){
		         		    x = sinhypFunk(x);
		                 }
	                 if(tokens[i].equals("tanh")){
		         		    x = tanhypFunk(x);
		                 }
	                 if(tokens[i].equals("coth")){
		         		    x = cotanhypFunk(x);
		                 }
	                 if(tokens[i].equals("arcosh")){
		         		    x = arcoshypFunk(x);
		                 }
	                 if(tokens[i].equals("arsinsh")){
		         		    x = arsinhypFunk(x);
		                 }
	                 if(tokens[i].equals("artanh")){
		         		    x = artanhypFunk(x);
		                 }
	                 if(tokens[i].equals("arcoth")){
		         		    x = arcotanhypFunk(x);
		                 }
	                 //Sekans und Kosekans
	                 if(tokens[i].equals("sec")){
		         		    x = sekFunk(x);
		                 }
	                 if(tokens[i].equals("csc")){
		         		    x = kosekFunk(x);
		                 }
	                 if(tokens[i].equals("arcsec")){
		         		    x = arsekFunk(x);
		                 }
	                 if(tokens[i].equals("arccsc")){
		         		    x = arkosekFunk(x);
		                 }
                     if(tokens[i].equals("abs")){
                    	 x = Math.abs(x);
                	 }
                     
                     if(tokens[i].equals("exp")){
                    	 x = Math.exp(x);
                	 }
	                 //hier muss weiter Funktion definieren...    
	                 
	                 stack.push(x);
                     
                     
                 } else  {						
                	 
                     double y = stack.pop();
                     
                     
                     if(tokens[i].equals("+")){
                    	 y += x;
                	 }
                     if(tokens[i].equals("-")){
                    	 y -= x;
                	 }
                     if(tokens[i].equals("*")){
                    	 y *= x;
                	 }
                     if(tokens[i].equals("/")){
                    	 y = nDivision(y, x);
                	 }
                     if(tokens[i].equals("^")||tokens[i].equals("pow")){ // y^x
                		 y = nExponenten(y, x );
                	 }
                     if(tokens[i].equals("log")){
                    	 y = logaBasis(y, x);
                	 }

                     //hier fuer funktion mit 2 Parameter
                     // die method muss immer gleich Form
                     // public static double funkName(double Par1, double Par2)
                     // zb: logPar1(Pas2), Par1^Par2

                     stack.push(y);
                 }
             }
             else  // Ist Operand
             {
            	 if(AusdHilfer.IstVariable(tokens[i])){
            		 
            		 //fuer x
	                 if(tokens[i].equals("x")||tokens[i].equals("X")){
	                	 double x = varialbex;
	                	 stack.push(x);
	                 }
	                 
	                 if(tokens[i].equals("-x")||tokens[i].equals("-X")){
	                	 double x = 0.0-varialbex;
	                	 stack.push(x);
	                 }
	                 if(tokens[i].equals("e")){
	                	 stack.push(Math.E);
                	 }
	                 if(tokens[i].equals("pi")){
	                	 stack.push(Math.PI);
                	 }
	                 
               	 } else{
	                
                 stack.push(Double.parseDouble(tokens[i]));
               	 }
             }

         }

         return stack.pop(); 
     }
     
 	/**
 	 * Die Eingabe ist die Plotter oder ein String "Funktion mit Bedingung".
 	 * <br> wenn Eingabe = "Funktion mit Bedingung". das bedeutet, dass es 2 Plotter gibt.
 	 * <br> wenn Eingabe = die Plotter => es gibt nur eine Plotter
 	 * <br> Dann rechnen wir mit Hilfe von method {@link #plotterRechnen()}
 	 * 
 	 * @param func ist  die Plotter,die wir auswerten mussen
 	 * @param x an stelle x
 	 * @return y = f(x) 
 	 */
     public static double plotter(double x, String func ){
    	
    	 boolean bedingung = func.equalsIgnoreCase("Funktion mit Bedingung");
    	 if(!bedingung){	// normale Funktion
    		 
    		 return plotterRechnen(x, func);
    		 
    	 } else {	//Funktionen mit zwei Bedingungen
    		String f1a 	= PlotterGUI.getErsteF();
    		String fa	= PlotterGUI.getTxtFa();
    		String f2a 	= PlotterGUI.getZweiteF();    		
    		double bedingungWert = PlotterGUI.getBedingungWert();
    		
    		if(x<bedingungWert){
    			
    			return plotterRechnen(x, f1a);
    			
    		}else if(x == bedingungWert) {
				return plotterRechnen(x, fa);
			} else {
				return plotterRechnen(x, f2a);
			}

    	 }
     }
     
}
