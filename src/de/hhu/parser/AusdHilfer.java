package de.hhu.parser;

import java.util.regex.*;


/**
 * @author Viet Dung Tran
 *
 * created on 23.05.2011
 */

public class AusdHilfer {
	

	/**
	 * Geben Name der Funktion in Form *|* aus
	 * @return String Function Name
	 */
	public static String undefiFunk(){
		String flot = "" ;
		
		for( Konstants.FunkName name : Konstants.FunkName.values() ) 
			flot +="|"+ name.name();				// name der Funktion |sin|cos..
		return flot;
	}
	
	/**
	 * Lesen Plotter von Input ein. 
	 * <br>Schreiben wieder in Form: jeden Teilen wird durch ein Leerzeichen getrennt.
	 * @param ausdruck
	 * @return ausdruck 
	 */
	public static String FormatAusdruck(String ausdruck){						
		
		ausdruck = ausdruck.toLowerCase().replaceAll(" ", "");			// Alle leere Zeichen loesen
		String regex = "\\+|\\-|\\*|\\/|\\%|\\^|\\)|\\("+undefiFunk()+"|"+Konstants.VARIABLE;
		ausdruck = ersetzen(ausdruck, regex).trim();					
		
		ausdruck = ausdruck.replaceAll("  ", " ");						// 2 leere Zeichen = 1 mal
		return ausdruck;
	}
	/**
	 * regulaere Ausdruecke 
	 * @param text
	 * @param regex
	 * @return sb = jeden Teilen von Text wird durch ein Leerzeichen getrennt.
	 */
	public static String ersetzen(String text, String regex){
		Matcher matcher = Pattern.compile( regex ).matcher( text );
		StringBuffer sb = new StringBuffer( text.length() );
		
		while ( matcher.find() )
		matcher.appendReplacement( sb, " $0 " );
		
		matcher.appendTail( sb );
		
		return sb.toString();
	}

	/**
	 * Sie Betrachten die Prioritaet eines Operators.
	 * <br> funktion (zb sin, cos..) ist 3
	 * <br> *, /, ^ ist 2
	 * <br> +,- ist 1 sondern 0 
	 * @param op
	 * @return  prioritaet von op 
	 */
	public static int GetPrioritat(String op){
		
		for( Konstants.FunkName name : Konstants.FunkName.values() ) {
			if (name.name().equalsIgnoreCase(op))
			return 3;
		}
        if (op.equalsIgnoreCase("*") || op.equalsIgnoreCase("/") || op.equalsIgnoreCase("%")||op.equalsIgnoreCase("^"))
            return 2;
        if (op.equalsIgnoreCase("+") || op.equalsIgnoreCase("-"))
            return 1;
        return 0;
	}
	
	/**
	 * ueberpruefen, ob Eingabe ein Funktion und Operation ist
	 * @param String operation 
	 * @return true def: undefiFunk, +, -, *, /, ^ 
	 */
	public static boolean IstOperator(String operation){
		
		String regex = "^(\\+|\\-|\\*|\\/|\\%|\\^"+undefiFunk()+")$";
		return Pattern.matches(regex,operation );	
	}
	
	/**
	 * ueberpruefen, ob Eingabe ein Funktion ist
	 * @param operation
	 * @return true def: sin, cos... = undefiFunk
	 */
	public static boolean IstUndeFunk(String operation){
		
		String regex = "^("+undefiFunk()+")$";
		return Pattern.matches(regex,operation);	
	}
	
	/**
	 * ueberpruefen, ob Eingabe ein Funktion, die mit 2 Parameter brauche, ist
	 * @param operation
	 * @return true wenn Funktion mit 2 Parameter ZB: Pow(x,y), lg2(9)
	 */
	public static boolean Istfunk2Parameter(String operation){
		
		String regex = "^("+Konstants.ZWEI_PARAMETER+")$";
		return Pattern.matches(regex,operation);	
	}
	
	
	/**
	 * ueberpruefen, ob Eingabe ein Variable ist
	 * @param str
	 * @return ob str  eine Variable def: Variable x oder X ist
	 */
	public static boolean IstVariable(String str){
		
		String regex = "^("+Konstants.VARIABLE+")$";
		return Pattern.matches(regex,str);	
	}
	
	/**
	 * @param str
	 * @return ob str eine Funktion, Zahlen und Variable x oder X ist
	 */
	public static boolean IstFunk(String str){
		String regex = "^\\d+$|^(\\+|\\-|\\*|\\/|\\%|\\^|\\)|\\("+undefiFunk()+"|"+Konstants.VARIABLE+")$";
		return Pattern.matches(regex,str);	
	}
	
	/**
	 * ueberpruefen, ob Eingabe ein Plotter ist
	 * @param str
	 * @return ob 
	 */
	public static boolean IstPlott(String str){
			
		str = AusdHilfer.FormatAusdruck(str);
		String[] tokens = str.split(" ");
		for(int i = 0; i < tokens.length; i++){
			if(!AusdHilfer.IstFunk(tokens[i])){
				return false;				
			}
		}
		return true;
				
	}
	/**
	 * ueberpruefen, ob Eingabe ein Zahl ist
	 * @param a
	 * @return true ob a Zahl ist
	 */
	public static boolean isZahl(String a){
		String regex = "\\d*|-\\d*";
		return Pattern.matches(regex,a);
	}
	
}
