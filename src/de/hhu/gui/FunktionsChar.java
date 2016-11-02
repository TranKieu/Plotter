/**
 * @author Viet Dung Tran
 *
 * created on 01.07.2011
 */
package de.hhu.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FunktionsChar {

 	/**
 	 * ein Zeichen a in der Plotter fx wird von ein Wert ersetzt.
 	 * <br> Dann erhalten wir eine normale Plotter.
 	 * 
 	 * @param fx mit definiert a oder A
 	 * @param a ist ein zahl
 	 * @return y = f(x) wird ein Eingabewert ersetzt
 	 */
	  public static String charErsetzen(String fx, String a){
			
			Matcher matcher = Pattern.compile("a|A").matcher( fx );
			StringBuffer stringBuffer = new StringBuffer( fx.length() );
			
			while ( matcher.find() )
			matcher.appendReplacement( stringBuffer, "("+a+")" );
			
			matcher.appendTail( stringBuffer );	
			return stringBuffer.toString();	    
	  }

}
