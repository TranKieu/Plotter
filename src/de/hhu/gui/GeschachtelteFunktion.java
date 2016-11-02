/**
 * @author Viet Dung Tran
 *
 * created on 30.06.2011
 */
package de.hhu.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeschachtelteFunktion {

	/**
	 * g(x) wird ins f(x) geschrieben. Dann bekommen wir ganzen normale Plotter(nur Variable und Zahlen)..
	 * @param fx ist Geschachtelte Funktionen.
	 * @param  gx
	 * @return String y = f(x) 
	 */
  public static String gxErsetzen(String fx, String gx){
	
		Matcher matcher = Pattern.compile("g\\(x\\)").matcher( fx );
		StringBuffer stringBuffer = new StringBuffer( fx.length() );
		
		while ( matcher.find() )
		matcher.appendReplacement( stringBuffer, "("+gx+")" );
		
		matcher.appendTail( stringBuffer );	
		return stringBuffer.toString();	    
  }
}
