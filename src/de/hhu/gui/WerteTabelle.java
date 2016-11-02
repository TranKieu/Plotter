/**
 * @author Viet Dung Tran
 *
 * created on 01.07.2011
 */
package de.hhu.gui;


import de.hhu.parser.Auswerten;


public class WerteTabelle{
	/**
	 * Header fuer Wertetablle
	 */
	public static final String[] COLHEADS = { "x", "f(x)" };
	public static final String[][] leerData = {{ " ", " " }};
	private double startInterval;
	private double endeInterval;
	private double schritt;
	private String funcString;
	

	/**
	 * die Schritt eingestellt
	 * @param step
	 */
	public void setSchritt(double step){
		this.schritt = step;
	}
	
	 /**
	  * die startInterval eingestellt
	  * @param startInterval 
	  */
	public void setStartInterval(double startInterval) {
		this.startInterval = startInterval;
	}
	/**
	 * die endeInterval eingestellt
	 * @param endeInterval 
	 */
	public void setEndeInterval(double endeInterval) {
		this.endeInterval = endeInterval;
	}
	/**
	 * die Plotter eingestellt
	 * @param input
	 */
	public void setfuncString(String input){
		this.funcString = input;
	}
	
	/**
	 *  Wert fuer Tabelle wird ausgerechnet.
	 * @return Wertetabelle
	 */
	 public String[][] rowData(){
		 int n=(int) ((endeInterval-startInterval)/schritt);
		 String[][] Data = new String[n+1][2];
		 for(int i = 0;i<=n;i++){
			 Data[i][0] = String.valueOf(startInterval + i*schritt);
			 Data[i][1] = String.valueOf(Auswerten.plotter((startInterval + i*schritt),funcString));
		 }
		return Data ; 
	 }
	 
}
