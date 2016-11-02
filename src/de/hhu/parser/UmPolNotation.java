package de.hhu.parser;
import java.util.*;

/**
 * @author Viet Dung Tran
 *
 * created on 23.05.2011
 */

public class UmPolNotation {
	
    /**
     * <pre>
	 * von Infix-Notation ins prefix-Notation umschreiben
	 * Idee fuer Aglorithmus: http://de.wikipedia.org/wiki/Umgekehrte_Polnische_Notation
	 * 
	 * Wir benutze eine Stack fuer Operation und Klammern um zu schreiben:
	 * Schreiben die String(input) ins eine Array (tockens)
	 * Nehmen jede Stueck aus, 
	 *   - wenn ein Operand ist, holen wir raus ins ein StringBuilder schreiben(Output)
	 *   - wenn "(" ist, schreiben ins Stack (push)
	 *   - wenn ")" ist, holen alles String von Stack bis die "(", dann ins Output schreiben (append)
	 *   - wenn ein Operation oder funktion ist, werten wir die Prioritaet von Operation aus.
	 *   - Holen die letzte Element von Stack raus. wenn:   
	 *       + Solange der Operation an der Spitze des Stapels eine Prioritaet groesser oder gleich dem Prioritaet der aktueller Operation ist, 
	 * 			dann nehme die aus. Und schrieben ins StringBuilder ein
	 *       + Schreiben die aktuelle Operation ins Stapels.
	 * 
	 * Machen wie das bis die letzte Elemente von Array(input). Holen wir den Rest von Stack raus und ins StringBuilder schreiben 		 
	 *  => Wir bekommen eine umgekehrten polnischen Notation.(in prefix-Notation Form)
	 * 
	 *ZB:
	 * A*B+C*((D-E)+F)/G    Infix to Postfix:
	 * Token 		Stack 			Output
	 * A 		   {Empty} 			A
	 * * 			*    			A
	 * B 			*   			A B
	 * + 			+   			A B *
	 * C 			+   			A B * C
	 * * 			+ * 			A B * C
	 * ( 			+ *( 			A B * C
	 * ( 			+ * ( ( 		A B * C
	 * D 			+ * ( ( 		A B * C D
	 * - 			+ * ( ( - 		A B * C D
	 * E 			+ * ( ( - 		A B * C D E
	 * ) 			+ * ( 			A B * C D E -
	 * + 			+ * ( + 		A B * C D E -
	 * F 			+ * ( + 		A B * C D E - F
	 * ) 			+ * 			A B * C D E - F +
	 * / 			+ / 			A B * C D E - F + *
	 * G 			+ / 			A B * C D E - F + * G
	 * 	 		  {Empty}   		A B * C D E - F + * G / +
	 *</pre>
	 **/

	public static String infixToPostfixNotation(String infix){
		
		infix = AusdHilfer.FormatAusdruck(infix);
		String[] tokens = infix.split(" ");  
		
	    Stack<String> stack = new Stack<String>();
	    StringBuilder postfix = new StringBuilder();
		
	    for (int i = 0; i < tokens.length; i++)    {
	    	
            String token = tokens[i];
            
            if (AusdHilfer.IstOperator(token)) {
            	
                if ((i == 0) || (i > 0 && (AusdHilfer.IstOperator(tokens[i - 1]) || tokens[i - 1].equals("(")))) {
                	
                    if (token.equals("-"))  {					//fuer - a  = -a
                    	
                        postfix.append(token + tokens[i + 1]).append(" ");
                        i++;
                    }  else {
                    	if (AusdHilfer.IstUndeFunk(token))  {
                        	
                            stack.push(token);
                        }
                    }
                } else {
                	
                    while (stack.size() > 0 && AusdHilfer.GetPrioritat(token) <= AusdHilfer.GetPrioritat(stack.peek())) //Frioritaet vergleichen
                    	postfix.append(stack.pop()).append(" ");
                    stack.push(token);
                }
            } 
            else if (token.equals("("))
                stack.push(token);
            else if (token.equals(")")) {
            	
                String x = stack.pop();
                try{
	                while(!x.equals("("))   {
	                	
	                    postfix.append(x).append(" ");
	                    x = stack.pop();
	                }
                } catch (Exception e) {     	                }
            }	else {											// IstOperander
            
            	postfix.append(token).append(" ");
            }
        }

        while (stack.size() > 0)									//holen die Rest von Stack raus
            postfix.append(stack.pop()).append(" ");

        return postfix.toString().trim();
    }
	
}
